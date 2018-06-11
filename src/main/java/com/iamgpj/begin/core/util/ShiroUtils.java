package com.iamgpj.begin.core.util;

import com.iamgpj.begin.core.shiro.subject.UserPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

/**
 * @Description: shiro 工具类
 * @author: gpj
 * @Create: 2018/6/9 17:16
 */
public class ShiroUtils {

    /**
     * 加盐参数
     */
    public final static String hashAlgorithmName = "MD5";

    /**
     * 循环次数
     */
    public final static int hashIterations = 22;

    /**
     * shiro密码加密工具类
     *
     * @param credentials 密码
     * @param salt 密码盐
     * @return
     */
    public static String md5(String credentials, String salt) {
        return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
    }

    /**
     * 获取随机盐值
     * @return
     */
    public static String getRandomSalt() {
        return ToolUtils.getRandomString(6);
    }

    /**
     * 获取当前 Subject
     *
     * @return Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public static UserPrincipal getUserPrincipal() {
        Object principal = getSubject().getPrincipal();
        if (principal == null) {
            return null;
        }
        return (UserPrincipal) principal;
    }

    /**
     * 获取当前用户id
     * @return
     */
    public static Integer getCurrentUserId() {
        UserPrincipal userPrincipal = getUserPrincipal();
        return userPrincipal == null ? 0: userPrincipal.getId();
    }

    /**
     * 是否是超级管理员
     * @return
     */
    public static boolean isAdmin() {
        return getCurrentUserId().equals(0);
    }
}
