package com.mfk.ws.autho;

import com.mfk.ws.bean.Permission;
import com.mfk.ws.bean.Role;
import com.mfk.ws.bean.User;
import com.mfk.ws.mapper.UserMapper;
import com.mfk.ws.mapper.UserPermissionMapper;
import com.mfk.ws.mapper.UserRoleMapper;
import com.mfk.ws.mapper.WsMapper;
import com.mfk.ws.utils.ApplicationContextUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;


/*在SpringMVC框架中，我们经常要使用@Autowired注解注入Service或者Mapper接口，
我们也知道，在controller层中注入service接口，
在service层中注入其它的service接口或者mapper接口都是可以的，
但是如果我们要在我们自己封装的Utils工具类中
或者非controller普通类中使用@Autowired注解注入Service或者Mapper接口，
直接注入是不可能的

 */
@Component
public class ShiroRealm extends AuthorizingRealm {


    /** Autowired 无法注入**/
    @Autowired
    UserMapper userMapper;
     @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Override
    /**
     * 获取用户角色和权限
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ApplicationContext applicationContext = ApplicationContextUtil.getApplicationContext();
        UserRoleMapper userRoleMapper = (UserRoleMapper)applicationContext.getBean(UserRoleMapper.class);
        UserPermissionMapper userPermissionMapper = (UserPermissionMapper)applicationContext.getBean(UserPermissionMapper.class);


        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userName = user.getUserName();

        System.out.println("用户" + userName + "获取权限-----ShiroRealm.doGetAuthorizationInfo");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集
        List<Role> roleList = userRoleMapper.findByUserName(userName);
        Set<String> roleSet = new HashSet<String>();
        for (Role r : roleList) {
            roleSet.add(r.getName());
        }
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        List<Permission> permissionList = userPermissionMapper.findByUserName(userName);
        Set<String> permissionSet = new HashSet<String>();
        for (Permission p : permissionList) {
            permissionSet.add(p.getName());
        }
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 获取用户输入的用户名和密码
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        System.out.println("用户" + userName + "认证-----ShiroRealm.doGetAuthenticationInfo");

        // 通过用户名到数据库查询用户信息
        Map testMap = new HashMap<>();
        ApplicationContext applicationContext = ApplicationContextUtil.getApplicationContext();
        UserMapper userMapper= (UserMapper)applicationContext.getBean(UserMapper.class);
        testMap.put("username",userName);
        List<User> users = userMapper.selectByMap(testMap);
        User user = users.get(0);

        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
  /*      if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }*/
/*        User user = new User();
        user.setUserName("mark");
        user.setPassword("1");*/
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}
