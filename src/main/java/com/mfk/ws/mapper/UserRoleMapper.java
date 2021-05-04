package com.mfk.ws.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mfk.ws.bean.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserRoleMapper {

    List<Role> findByUserName(String userName);
}
