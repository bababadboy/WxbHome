package com.wangxb.wxbhome.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * demo定时任务
 * @author wangxb
 */

@Component
@Slf4j
public class DemoTask {

    @Scheduled(cron = "${app.task.demoTask.cron}")
    public void doExecute(){
        long start = System.currentTimeMillis();
        System.out.println("开始定时任务");
        long end = System.currentTimeMillis();
        log.info("任务用时 = {} ms" ,end - start);
    }
}
