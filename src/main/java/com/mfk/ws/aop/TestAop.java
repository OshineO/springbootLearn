package com.mfk.ws.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TestAop {

    public void add(int i,int j) {
        System.out.println(i+j);

    }
    @MyLog(name = "annotation 方法")
    public void cadd() {

    }
    public void addLog() {

    }
}
