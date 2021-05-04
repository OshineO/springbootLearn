package com.mfk.ws.mapper;

import com.mfk.ws.bean.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserPermissionMapper {

    List<Permission> findByUserName(String userName);
}
