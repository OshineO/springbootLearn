package com.mfk.ws.task;

import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class TestThreadTask {

    /**
     * async 表明这个方法是异步方法。这个方法会自动被注入
     * 使用ThreadPoolTaskExecutor作为TaskExectutor
     * @param
     */
    @Async
    public Future<String> testThread1 (Integer i) throws Exception{

        Thread.sleep(3000);
        System.out.println("执行异步任务testThread1:"+i+Thread.currentThread().getName());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("thread", Thread.currentThread().getName());
        jsonObject.put("time", System.currentTimeMillis());
        return new AsyncResult<String>(jsonObject.toJSONString());

    }
    @Async
    public void testThread2 (Integer i) {
        System.out.println("执行异步任务testThread2:"+i);

    }
}
