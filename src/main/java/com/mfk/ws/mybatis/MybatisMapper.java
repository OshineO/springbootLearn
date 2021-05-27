package com.mfk.ws.mybatis;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MybatisMapper {
   Classes getClass(Integer id);
   Classes getClass3(Integer id);
   Classes getClass4(Integer id);
   List<Classes> getClass2(Classes classe);
   Teacher getTeacher(int id);
   Teacher getTeacher2(int id);
   Student getStudent(int id);
}
