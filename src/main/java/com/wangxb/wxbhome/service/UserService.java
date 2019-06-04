package com.wangxb.wxbhome.service;

import com.wangxb.wxbhome.dao.UserDao;
import com.wangxb.wxbhome.dto.MessageResponse;
import com.wangxb.wxbhome.dto.UserDto;
import com.wangxb.wxbhome.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author wangxb
 */
@Service
@Slf4j
@Transactional
public class UserService {

    @Resource
    private UserDao userDao;

    // 每个线程每次查询的条数
    private static final Integer LIMIT = 20000;
    // 线程数
    private static final Integer THREAD_NUM = 3;

    private ThreadPoolExecutor pool;

    public Object getUserInfoById(long id){
        return userDao.getUserById(id);
    }

    public Object getUserInfoByStudentId(long studentId){
        return userDao.getUserByStudentId(studentId);
    }

    public Object login(String nickname,String password){
        log.info("登录用户名：{}",nickname);
        log.info("登录密码：{}",password);
        String expectedPassword = userDao.getPasswordByNickName(nickname);

        // todo 生成cookie(保存jsessionId)

        Cookie cookie = new Cookie("sessionid","");

        return password.equals(expectedPassword);

    }

    public Object signup(User user) {

        int nameCounts = userDao.nicknameCounts(user.getNickname());
        int emailCounts = userDao.emailCounts(user.getEmail());
        MessageResponse msg = new MessageResponse();

        if (nameCounts > 0 ){
            log.warn("用户名：{} 已存在！",user.getNickname());
            msg.setStatus("fail");
            msg.setContent("用户名已存在");
            return msg;
        }
        if (emailCounts > 0){
            log.warn("邮箱:{}已存在",user.getEmail());
            msg.setStatus("fail");
            msg.setContent("邮箱已存在");
            return msg;
        }

        userDao.addUser(user);
        log.info("注册名称：{}",user.getNickname());
        log.info("注册密码：{}",user.getPassword());
        return new MessageResponse();
    }



    public Object getAllUsers() {
        log.info("所有用户查询开始");
        long start = System.currentTimeMillis();
        List<User> userList =  userDao.getAllUsers();
        long end = System.currentTimeMillis();
        log.info("查询耗时：{}ms",end-start);
        return userList;

    }

    public Object getAllUsersByMutiplyThreads(long startId) throws InterruptedException, ExecutionException {
        // 多线程查询
        pool = new ThreadPoolExecutor(THREAD_NUM, THREAD_NUM*2,
                0, TimeUnit.SECONDS,new LinkedBlockingQueue<>(10));
        log.info("所有用户多线程查询开始");

        long total = userDao.getCountAllUsers();

        long num = total/(LIMIT*THREAD_NUM) + 1;

        log.info("要经过的轮数：{}",num);

        long start = System.currentTimeMillis();

        List<User> result =new ArrayList<>((int)total);

        List<Callable<List<User>>> tasks = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            for(int j = 0; j < THREAD_NUM; j++){

                Callable<List<User>> callable = new SelectUsersTask(startId,LIMIT);

                tasks.add(callable);

                startId += LIMIT;
            }
        }

        //Future用于获取结果
        List<Future<List<User>>> futures=pool.invokeAll(tasks);
        //处理线程返回结果
        if(futures.size() > 0){
            for (Future<List<User>> future : futures){
                result.addAll(future.get());
            }
        }
        pool.shutdown();

        long end = System.currentTimeMillis();
        log.info("查询耗时：{}ms",end-start);
        return result;
    }

    class SelectUsersTask implements Callable<List<User>> {

        private long startId;
        private int limit;
        private List<User> users;

        SelectUsersTask(long startId,int limit) {
            this.startId = startId;
            this.limit = limit;
            users = userDao.getUsers(startId,limit);
        }

        @Override
        public List<User> call() throws Exception {
            return users;
        }
    }
}
