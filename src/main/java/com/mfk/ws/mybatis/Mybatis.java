package com.mfk.ws.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Mybatis {
    Logger logger = LoggerFactory.getLogger(Mybatis.class);
    @Autowired
    MybatisMapper mybatisMapper;
    public Classes getClassById(Integer id){

        Classes classes = mybatisMapper.getClass(id);
        logger.info(classes.toString());
        return classes;
    }
}
