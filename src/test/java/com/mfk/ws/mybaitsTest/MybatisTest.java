package com.mfk.ws.mybaitsTest;

import com.mfk.ws.mybatis.Classes;
import com.mfk.ws.mybatis.Mybatis;
import com.mfk.ws.mybatis.MybatisMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {
    @Autowired
    Mybatis mybatis;

    @Autowired
    MybatisMapper mybatisMapper;

    @Test
    public void testGetClassById () {
        mybatis.getClassById(1);
    }

    @Test
    public void testGetClassById2 () {
        Classes classes1 = new Classes();
        List<Classes> classes = mybatisMapper.getClass2(classes1);
        classes.stream().forEach(classe->{
            System.out.println(classe);
        });
    }

    @Test
    public void testGetClassById3 () {
        Classes classes = mybatisMapper.getClass3(1);

            System.out.println(classes.toString());
    }
    @Test
    public void testGetClassById4 () {
        Classes classes = mybatisMapper.getClass4(1);

        System.out.println(classes.toString());
    }
}
