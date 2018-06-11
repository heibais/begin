package com.iamgpj.begin.module.admin.auth.service.impl;

import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.ShiroUtils;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.admin.auth.dao.RoleUserDAO;
import com.iamgpj.begin.module.admin.auth.dao.UserDAO;
import com.iamgpj.begin.module.admin.auth.dto.UserDTO;
import com.iamgpj.begin.module.admin.auth.param.UpdatePwdParam;
import com.iamgpj.begin.module.admin.auth.param.UserSearchParam;
import com.iamgpj.begin.module.admin.auth.entity.RoleUser;
import com.iamgpj.begin.module.admin.auth.entity.User;
import com.iamgpj.begin.module.admin.auth.param.UserParam;
import com.iamgpj.begin.module.admin.auth.service.UserService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/4/25 20:39
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;
    @Resource
    private RoleUserDAO roleUserDAO;

    @Override
    public Page<UserDTO> findAll(Pageable pageable, UserSearchParam search) {
        User user = ToolUtils.map(search, User.class);
        // 查询条件
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (StringUtils.hasText(user.getUsername())) {
            matcher.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if (StringUtils.hasText(user.getMobile())) {
            matcher.withMatcher("mobile", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if (StringUtils.hasText(user.getEmail())) {
            matcher.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<User> example = Example.of(user, matcher);
        // 开始查询
        Page<User> page = userDAO.findAll(example, pageable);
        // 封装查询结果
        List<UserDTO> dtoList = page.getContent().stream().map(item -> adapt(item)).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public User findById(Integer userId) {
        Optional<User> optional = userDAO.findById(userId);
        return optional.get();
    }

    /**
     * 将 User 转为 UserDTO
     * @param user
     * @return
     */
    private UserDTO adapt(User user) {
        UserDTO dto = ToolUtils.map(user, UserDTO.class);
        // 查询用户所拥有的角色id
        dto.setRoleIdList(userDAO.findRoleIdsByUserId(user.getId()).stream().map(item -> item.toString()).collect(Collectors.toList()));
        return dto;
    }


    @Override
    public void save(UserParam param) {
        // 判断是否存在该用户
        if (checkExist(param.getId(), param.getUsername())) {
            throw new BeginException(ExceptionEnum.DATA_EXISTED);
        }
        // 拼装对象
        User user = ToolUtils.map(param, User.class);
        if (user.getId() == null) {
            // 新增
            if (!StringUtils.hasText(user.getPassword())) {
                throw new BeginException(ExceptionEnum.PASSWORD_NULL);
            } else {
                // 密码加密
                user.setSalt(ShiroUtils.getRandomSalt());
                user.setPassword(ShiroUtils.md5(user.getPassword(), user.getSalt()));
            }
            // 判断手机是否存在
            if (StringUtils.hasText(user.getMobile())) {
                if (findByMobile(user.getMobile()) != null) {
                    throw new BeginException(ExceptionEnum.MOBILE_EXISTED);
                }
            }
            // 判断邮箱是否存在
            if (StringUtils.hasText(user.getEmail())) {
                if (findByEmail(user.getEmail()) != null) {
                    throw new BeginException(ExceptionEnum.EMAIL_EXISTED);
                }
            }
        } else {
            // 编辑
            Optional<User> optional = userDAO.findById(param.getId());
            if (optional.isPresent()) {
                User oldUser = optional.get();
                // 密码为空，则表示不更新密码
                if (!StringUtils.hasText(user.getPassword())) {
                    user.setPassword(oldUser.getPassword());
                    user.setSalt(oldUser.getSalt());
                } else {
                    // 密码加密
                    user.setSalt(ShiroUtils.getRandomSalt());
                    user.setPassword(ShiroUtils.md5(user.getPassword(), user.getSalt()));
                }
                // 判断手机
                if (!user.getMobile().equals(oldUser.getMobile())) {
                    if (StringUtils.hasText(param.getMobile())) {
                        if (userDAO.findByMobileAndIdNot(param.getMobile(), param.getId()) != null) {
                            throw new BeginException(ExceptionEnum.MOBILE_EXISTED);
                        }
                    }
                }
                // 判断邮箱
                if (!user.getEmail().equals(oldUser.getEmail())) {
                    if (StringUtils.hasText(param.getEmail())) {
                        if (userDAO.findByEmailAndIdNot(param.getEmail(), param.getId()) != null) {
                            throw new BeginException(ExceptionEnum.EMAIL_EXISTED);
                        }
                    }
                }
            } else {
                throw new BeginException(ExceptionEnum.REQUEST_ERROR);
            }
        }
        // 补全参数
        userDAO.save(user);
    }

    private boolean checkExist(Integer id, String username) {
        if (id == null) {
            return userDAO.countByUsername(username) > 0;
        } else {
            return userDAO.countByIdNotAndUsername(id, username) > 0;
        }
    }

    @Override
    public void removeById(Integer userId) {
        userDAO.deleteById(userId);
    }

    @Override
    public void changeStatus(Integer id) {
        userDAO.changeStatus(id);
    }

    @Override
    public List<String> findRoleIdsByUserId(Integer id) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User findByMobile(String mobile) {
        return userDAO.findByMobile(mobile);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveUserRole(Integer userId, String roleIds) {
        // 删除该用户原拥有角色
        roleUserDAO.deleteByUserId(userId);
        // 保存新角色
        if (StringUtils.hasText(roleIds)) {
            String[] strings = roleIds.split(",");
            List<RoleUser> roleUsers = new ArrayList<>(strings.length);
            for (String roleId : strings) {
                roleUsers.add(new RoleUser(Integer.parseInt(roleId), userId));
            }
            roleUserDAO.saveAll(roleUsers);
        }
    }

    @Override
    public void updatePassword(Integer userId, UpdatePwdParam param) {
        if (param.getNewPassword().equals(param.getOldPassword())) {
            return;
        }
        // 查询用户
        Optional<User> optional = userDAO.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            if (user.getStatus() == 0) {
                throw new BeginException(ExceptionEnum.USER_LOCKED);
            }
            String md5Password = ShiroUtils.md5(param.getOldPassword(), user.getSalt());
            if (!md5Password.equals(user.getPassword())) {
                throw new BeginException(ExceptionEnum.PASSWORD_ERROE);
            }
            // 更新密码
            user.setSalt(ShiroUtils.getRandomSalt());
            user.setPassword(ShiroUtils.md5(param.getNewPassword(), user.getSalt()));
            userDAO.save(user);
        } else {
            throw new BeginException(ExceptionEnum.REQUEST_ERROR);
        }
    }
}
