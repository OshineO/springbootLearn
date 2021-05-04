package com.mfk.ws.controller;


import com.alibaba.fastjson.JSONObject;
import com.mfk.ws.aop.TestAop;
import com.mfk.ws.bean.WsBean;
import com.mfk.ws.mapper.WsMapper;
import com.mfk.ws.task.TestThreadTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@RestController
public class WsController {

    @Autowired
    WsMapper wsMapper;
    @Autowired
    TestAop testAop;
    @Autowired
    TestThreadTask testThreadTask;
    private Log log = LogFactory.getLog(WsController.class);
    @Autowired
    CacheManager cache;
    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/connect")
    public Object testConnect(@RequestParam("id") String id ) {
        //System.out.println(wsService.getById(id));
        System.out.println("wsMapper=="+wsMapper);
        System.out.println("goods={}"+wsMapper.selectById(id));
        return wsMapper.selectById(id);
    }
    @GetMapping("/thread")
    public Object testThread() {
        //System.out.println(wsService.getById(id));
        System.out.println("Thread id:"+Thread.currentThread().getId()+"name:"+Thread.currentThread().getName());
        return "Thread id:"+Thread.currentThread().getId()+"name:"+Thread.currentThread().getName();
    }

    public void testBean(String id ) {
        System.out.println("id=="+id);
    }

    @GetMapping("/testAop")
    public void testAop() {
        testAop.add(1,2);
        testAop.cadd();
    }
    @GetMapping("/testThreadTask")
    @ResponseBody
    public String testThreadTask() throws Exception {
        List<Future>  futureLs = new ArrayList();
        for (int i = 0; i <10 ; i++) {
            futureLs.add(testThreadTask.testThread1(i));
            /*testThreadTask.testThread2(i);*/

           /* while (true) {
                if(future.isCancelled()){
                    log.info("deal async task is Cancelled");
                    break;
                }
                if (future.isDone() ) {
                    log.info("deal async task is Done");
                    log.info("return result is " + future.get());
                    break;
                }
            }*/
        }
        while (true) {
            if (futureLs.size()==0){
                return "over";
            }
            for(int i=0;i<futureLs.size();i++){
                Future future = futureLs.get(i);
                if (future.isDone()) {
                    try {
                        log.info("return result is " + future.get());
                        futureLs.remove(i);
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            };

/*            futureLs.forEach(future -> {
                if (future.isDone()) {
                    try {
                        log.info("return result is " + future.get());
                        futureLs.remove(future);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });*/
        }


    }


    @GetMapping("/testThreadTask1")
    public void testThreadTask1() throws Exception {
        List<Future>  futureLs = new ArrayList();

        int k=0;
        while (true) {
            for (int i = k; i <10 ; i++) {
                k+=1;
                futureLs.add(testThreadTask.testThread1(i));
            }

            for(int i=0;i<futureLs.size();i++){
                Future future = futureLs.get(i);
                if (future.isDone()) {
                    try {
                        log.info("return result is " + future.get());
                        futureLs.remove(i);
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            };
            if (k==10 && futureLs.size()==0) {
                break;
            }


        }

    }


    /**
     * 前台以form表单方式请求不需要加@RequestBody 参数可以封装成bean，也可以单独传,参数名和前台传的要保持一致。
     * 前台以json传时后台加@RequestBody也可以封装成bean，只有第一个参数有值
     * @param ws
     * @param name
     */
    @PostMapping("/testParam")
    public void testParam(WsBean ws, String name, String test , String test1) {
        System.out.println(ws.toString());
        System.out.println("name:"+name);
        System.out.println("test:"+test);
        System.out.println("test1:"+test1);
    }
/*    @PostMapping("/testParam1")
    public void testParam1(@RequestBody JSONObject obj, WsBean ws) {
        System.out.println(obj.toString());
        System.out.println(ws.toString());
      //  System.out.println("name:"+name);
    }*/
    @PostMapping("/testParam1")
    public void testParam1(@RequestBody WsBean ws) {
       // System.out.println(obj.toString());
        System.out.println(ws.toString());
        //  System.out.println("name:"+name);
    }

    @GetMapping("/testCache")
    @Cacheable(cacheNames = {"testCache"})
    public Object testCache(@RequestParam("id") String id ) {
        System.out.println(cache.getClass());
        System.out.println(cache.getCacheNames());
        //System.out.println(wsService.getById(id));
        System.out.println("wsMapper=="+wsMapper);
        System.out.println("goods={}"+wsMapper.selectById(id));
        return wsMapper.selectById(id);
    }
    @GetMapping("/testRedisTemplate")
    public Object testRedisTemplate(@RequestParam("id") String id ) {
        redisTemplate.opsForValue().set("id",id);
        System.out.println("goods={}"+wsMapper.selectById(id));
        return wsMapper.selectById(id);
    }
}
