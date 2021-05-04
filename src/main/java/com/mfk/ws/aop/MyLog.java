package com.mfk.ws.aop;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
public @interface MyLog {

    public String name ();
}
