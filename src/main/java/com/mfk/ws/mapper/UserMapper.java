package com.mfk.ws.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mfk.ws.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
