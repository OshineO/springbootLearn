package com.mfk.ws.service;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mfk.ws.bean.AlipayConfigBean;
import com.mfk.ws.config.AlipayConfig;
import com.mfk.ws.mapper.AlipayConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 从数据库取值赋值给常量
 */
@Service
public class ConstantFromDbService {

    @Autowired
    AlipayConfigMapper alipayConfigMapper;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init(){

        /*AlipayConfigBean config = alipayConfigMapper.selectOne(Wrappers.emptyWrapper());
        System.out.println("config:"+config);
        System.out.println("APP_ID:"+AlipayConfig.APP_ID);
        AlipayConfig.APP_ID=config.getAppid();
        System.out.println("APP_ID:"+AlipayConfig.APP_ID);*/
    }



}

