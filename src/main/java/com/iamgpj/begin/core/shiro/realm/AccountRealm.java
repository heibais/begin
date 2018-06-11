package com.iamgpj.begin.core.shiro.realm;

import com.iamgpj.begin.core.shiro.subject.UserPrincipal;
import com.iamgpj.begin.core.util.RegexUtils;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.admin.auth.entity.User;
import com.iamgpj.begin.module.admin.auth.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/4/30 11:05
 */
public class AccountRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     * 授权用户权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 通过用户名获取角色

        // 通过用户名获取权限
        return null;
    }

    /**
     * 验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String account = (String) token.getPrincipal();
        // 手机登录
        User user;
        if (RegexUtils.isMobileSimple(account)) {
            user = userService.findByMobile(account);
        } else {
            user = userService.findByUsername(account);
        }
        if (user == null) {
            throw new UnknownAccountException("账号不存在");
        }
        if (user.getStatus().equals(0)) {
            throw new LockedAccountException("该帐号已冻结");
        }

        UserPrincipal principal = ToolUtils.map(user, UserPrincipal.class);
        // 盐
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        // 模拟加密之后的密码，实际从数据库中读取
        return new SimpleAuthenticationInfo(
                principal,
                user.getPassword(),
                salt,
                getName()
        );
    }
}
