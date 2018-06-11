package com.iamgpj.begin.module.admin.user.account.controller;

import com.iamgpj.begin.core.common.RespJson;
import com.iamgpj.begin.core.util.ShiroUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/11 20:18
 */
@RestController
@RequestMapping("/v1/s")
public class LoginController {

    @GetMapping("/login")
    public RespJson login() {
        return RespJson.createError("请至登录页面登录");
    }

    @PostMapping("/login")
    public RespJson toLogin(@RequestBody UsernamePasswordToken token) {
        Subject subject = ShiroUtils.getSubject();

        String errMsg = null;
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            errMsg = "用户名/密码错误";
        } catch (LockedAccountException e) {
            errMsg = "该账号已被冻结";
        } catch (Exception e) {
            errMsg = "用户名/密码错误";
        }
        if (errMsg != null) {
            return RespJson.createError(errMsg);
        }
        return RespJson.createSuccess("登录成功");
    }

    @GetMapping("/logout")
    public RespJson toLogOut() {
        Subject subject = ShiroUtils.getSubject();
        subject.logout();
        if (subject.isAuthenticated()) {
            return RespJson.createError("注销失败");
        }
        return RespJson.createSuccess("注销成功");
    }

    @GetMapping("/403")
    public RespJson unauthorized() {
        return RespJson.createError(HttpStatus.FORBIDDEN.value(), "禁止访问");
    }
}
