package com.mfk.ws.config;

import com.mfk.ws.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;

@Configuration
public class Myconfig implements AsyncConfigurer,WebMvcConfigurer {

    /**
     * 多线程
     * @return
     */
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setCorePoolSize(3);
        poolTaskExecutor.setMaxPoolSize(9);
        poolTaskExecutor.setQueueCapacity(50);
        poolTaskExecutor.initialize();
        return poolTaskExecutor;
    }
    public MyInterceptor getMyInterceptor(){
        return  new MyInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/testInterceptor");
    }
}
