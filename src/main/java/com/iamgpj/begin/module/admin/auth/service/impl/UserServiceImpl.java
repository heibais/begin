package com.iamgpj.begin.module.admin.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.ShiroUtils;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.admin.auth.dao.UserDAO;
import com.iamgpj.begin.module.admin.auth.dto.UserDTO;
import com.iamgpj.begin.module.admin.auth.entity.RoleUser;
import com.iamgpj.begin.module.admin.auth.entity.User;
import com.iamgpj.begin.module.admin.auth.param.UpdatePwdParam;
import com.iamgpj.begin.module.admin.auth.param.UserParam;
import com.iamgpj.begin.module.admin.auth.param.UserSearchParam;
import com.iamgpj.begin.module.admin.auth.service.RoleUserService;
import com.iamgpj.begin.module.admin.auth.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/4/25 20:39
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDAO, User> implements UserService {

    @Resource
    private RoleUserService roleUserService;

    @Override
    public List<UserDTO> findAll(Pagination pageable, UserSearchParam search) {
        User user = ToolUtils.map(search, User.class);
        // 查询条件
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        if (StringUtils.hasText(user.getUsername())) {
            wrapper.eq("username", user.getUsername());
        }
        if (StringUtils.hasText(user.getMobile())) {
            wrapper.eq("mobile", user.getMobile());
        }
        if (StringUtils.hasText(user.getEmail())) {
            wrapper.eq("email", user.getEmail());
        }
        // 开始查询
        List<User> userList = baseMapper.selectPage(pageable, wrapper);
        // 封装查询结果
        List<UserDTO> dtoList = userList.stream().map(item -> adapt(item)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public User findById(Integer userId) {
        return baseMapper.selectById(userId);
    }

    /**
     * 将 User 转为 UserDTO
     * @param user
     * @return
     */
    private UserDTO adapt(User user) {
        UserDTO dto = ToolUtils.map(user, UserDTO.class);
        // 查询用户所拥有的角色id
        dto.setRoleIdList(findRoleIdsByUserId(user.getId()));
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
            baseMapper.insert(user);
        } else {
            // 编辑
            User oldUser = baseMapper.selectById(param.getId());
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
                    User byMobile = findByMobile(param.getMobile());
                    if (!byMobile.getId().equals(oldUser.getId())) {
                        throw new BeginException(ExceptionEnum.MOBILE_EXISTED);
                    }
                }
            }
            // 判断邮箱
            if (!user.getEmail().equals(oldUser.getEmail())) {
                if (StringUtils.hasText(param.getEmail())) {
                    User byEmail = findByEmail(param.getEmail());
                    if (!byEmail.getId().equals(oldUser.getId())) {
                        throw new BeginException(ExceptionEnum.EMAIL_EXISTED);
                    }
                }
            }
            baseMapper.updateById(user);
        }

    }

    private boolean checkExist(Integer id, String username) {
        Wrapper<User> wrapper = new EntityWrapper<User>().eq("username", username);
        if (id != null) {
            wrapper.ne("id", id);
        }
        return baseMapper.selectCount(wrapper) > 0;
    }

    @Override
    public void removeById(Integer userId) {
        baseMapper.deleteById(userId);
    }

    @Override
    public void changeStatus(Integer id) {
        User user = baseMapper.selectById(id);
        if (user == null) {
            throw new BeginException(ExceptionEnum.SERVER_ERROR);
        }
        user.setStatus(user.getStatus() * -1 + 1);
        baseMapper.updateById(user);
    }

    @Override
    public List<String> findRoleIdsByUserId(Integer userId) {
        List<RoleUser> roleUserList = roleUserService.selectList(new EntityWrapper<RoleUser>().eq("user_id", userId));
        if (CollectionUtils.isEmpty(roleUserList)) {
            return null;
        }
        return roleUserList.stream().map(item -> item.getRoleId().toString()).collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username) {
        List<User> userList = baseMapper.selectList(new EntityWrapper<User>().eq("username", username));
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public User findByMobile(String mobile) {
        List<User> userList = baseMapper.selectList(new EntityWrapper<User>().eq("mobile", mobile));
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public User findByEmail(String email) {
        List<User> userList = baseMapper.selectList(new EntityWrapper<User>().eq("email", email));
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserRole(Integer userId, String roleIds) {
        // 删除该用户原拥有角色
        roleUserService.delete(new EntityWrapper<RoleUser>().eq("user_id", userId));
        // 保存新角色
        if (StringUtils.hasText(roleIds)) {
            String[] strings = roleIds.split(",");
            List<RoleUser> roleUsers = new ArrayList<>(strings.length);
            for (String roleId : strings) {
                roleUsers.add(new RoleUser(Integer.parseInt(roleId), userId));
            }
            roleUserService.insertBatch(roleUsers);
        }
    }

    @Override
    public void updatePassword(Integer userId, UpdatePwdParam param) {
        if (param.getNewPassword().equals(param.getOldPassword())) {
            return;
        }
        // 查询用户
        User user = findById(userId);
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
        baseMapper.updateById(user);
    }
}
