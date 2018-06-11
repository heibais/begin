package com.iamgpj.begin.config;

import com.iamgpj.begin.core.shiro.realm.AccountRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: shiro 配置
 * @author: gpj
 * @Create: 2018/4/30 11:14
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        factoryBean.setLoginUrl("/v1/user/login");
        factoryBean.setSuccessUrl("/");
        factoryBean.setUnauthorizedUrl("/v1/user/403");
        // 配置过滤器
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/v1/s/**", "anon");
        map.put("/v1/sys/captcha/**", "anon");
        map.put("/**", "user");
        factoryBean.setFilterChainDefinitionMap(map);

        return factoryBean;
    }

    @Bean
    public FormAuthenticationFilter myFormAuthenticationFilter() {
        FormAuthenticationFilter filter = new FormAuthenticationFilter();
        filter.setLoginUrl("/v1/user/login");
        return filter;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置 realm
        securityManager.setRealm(myShiroRealm());
        //注入记住我管理器;
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public AccountRealm myShiroRealm() {
        AccountRealm realm = new AccountRealm();
        // 缓存
        realm.setCachingEnabled(false);
        // 密码加密
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(22);

        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    /**
     * cookie对象;
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // 设置生效时间30天， 单位 秒
        simpleCookie.setMaxAge(2592000);
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        rememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return rememberMeManager;
    }
}
