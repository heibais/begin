package com.iamgpj.begin.module.admin.auth.service;

import com.iamgpj.begin.module.admin.auth.dto.UserDTO;
import com.iamgpj.begin.module.admin.auth.param.UserSearchParam;
import com.iamgpj.begin.module.admin.auth.entity.User;
import com.iamgpj.begin.module.admin.auth.param.UserParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author: gpj
 * @Description:
 * @Date: Created in 19:09 2018/1/28
 * @Modified By:
 */
public interface UserService {

    /**
     * 查询所有
     * @return
     */
    Page<UserDTO> findAll(Pageable pageable, UserSearchParam search);

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    User findById(Integer userId);

    /**
     * 新增，更新
     * @param param 参数
     * @return
     */
    void save(UserParam param);


    /**
     * 主键删除
     * @param userId id
     * @return
     */
    void removeById(Integer userId);

    /**
     * 改变状态
     * @param id 主键
     */
    void changeStatus(Integer id);

    /**
     * 通过用户id查询所拥有的角色id
     * @param id 用户id
     * @return
     */
    List<String> findRoleIdsByUserId(Integer id);

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return
     */
    User findByUsername(String username);

    /**
     * 通过手机查询
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 通过邮箱查询
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 保存用户的角色
     * @param userId 用户id
     * @param roleIds 角色id集合
     */
    void saveUserRole(Integer userId, String roleIds);

    /**
     * 更新用户密码
     * @param userId 用户id
     * @param oldPassword 旧密码
     * @param password 新密码
     */
    void updatePassword(Integer userId, String oldPassword, String password);
}
