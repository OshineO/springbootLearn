package com.mfk.ws.config;

import com.mfk.ws.autho.ShiroRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

//@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean (SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 登录的url
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后跳转的url
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权url
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();


        // 定义filterChain，静态资源不拦截
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/jquery/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        // 放开swagger
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");
        //shiro中重写doGetAuthenticationInfo，结果首次登录先执行doGetAuthenticationInfo后执行login的问题
        // 在配置ShiroConfig的时候，没有开放登录接口。
        // 开放登陆接口
        filterChainDefinitionMap.put("/login", "anon");

        // druid数据源监控页面不拦截
        filterChainDefinitionMap.put("/druid/**", "anon");
        // 配置退出过滤器，其中具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/", "anon");
        // 除上以外所有url都必须认证通过才可以访问，未通过认证自动访问LoginUrl
//        filterChainDefinitionMap.put("/**", "authc");
        // user指的是用户认证通过或者配置了Remember Me记住用户登录状态后可访问
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager () {
        // 配置SecurityManager，并注入shiroRealm
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm ());
        // 记住我功能
        securityManager.setRememberMeManager(rememberMeManager());
        return  securityManager;
    }

    public Realm shiroRealm () {
        return new ShiroRealm();
    }

  /*  流程：

            1.shiro会先删除从浏览器获得cookie，并且初始化一个cookie,内容为deleteme.

2.relam认证成功之后，shiro视当前用户登录成功之后，调用onSuccessfulLogin方法，在DefaultSerializer类中将获得的principal对象通过对象流ObjectOutputStream写入到字节数组中。

            3.第二步没有异常抛出的话则调用CookieRememberMeManager中的rememeberSerializedIdentity方法。如果第二步有异常，则会返回初始化的deleteMe的cookie,并且原来的cookie已经消失，因为第一步直接给删除了。

            4.rememeberSerializedIdentity方法中调用cookie.saeTo方法，直接设置对应的cookie返回给浏览器

            注：用户信息的实体类需要序列化 否则cookie的内容是deleteme
*/

    /*
       * cookie对象
       * @return
       */
    public SimpleCookie rememberMeCookie() {
        // 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置cookie的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(3600);
       // cookie.setMaxAge(86400);
        return cookie;
    }

    /**
     * cookie管理对象
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }


    /**
     *

    Shiro为我们提供了一些和权限相关的注解，如下所示：

     // 表示当前Subject已经通过login进行了身份验证；即Subject.isAuthenticated()返回true。
     @RequiresAuthentication

     // 表示当前Subject已经身份验证或者通过记住我登录的。
     @RequiresUser

     // 表示当前Subject没有身份验证或通过记住我登录过，即是游客身份。
     @RequiresGuest

     // 表示当前Subject需要角色admin和user。
     @RequiresRoles(value={"admin", "user"}, logical= Logical.AND)

     // 表示当前Subject需要权限user:a或user:b。
     @RequiresPermissions (value={"user:a", "user:b"}, logical= Logical.OR)
     要开启这些注解的使用，需要在ShiroConfig中添加如下配置：
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
