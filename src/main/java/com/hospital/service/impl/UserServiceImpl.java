package com.hospital.service.impl;

import com.hospital.dao.UserDAO;
import com.hospital.dao.impl.UserDAOImpl;
import com.hospital.entity.User;
import com.hospital.service.UserService;
import com.hospital.util.LogUtil;
import com.hospital.util.PasswordUtil;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 用户服务实现类
 */
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogUtil.getLogger(UserServiceImpl.class);
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public User login(String username, String password) {
        // 根据用户名查询用户
        User user = userDAO.findByUsername(username);
        if (user == null) {
            logger.error("用户不存在：" + username);
            throw new RuntimeException("用户名或密码错误");
        }

        // 检查用户状态
        if (user.getStatus() == 0) {
            logger.error("用户已禁用：" + username);
            throw new RuntimeException("用户已禁用");
        }

        // 验证密码
        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            logger.error("密码错误：" + username);
            throw new RuntimeException("用户名或密码错误");
        }

        logger.info("用户登录成功：" + username);
        return user;
    }

    @Override
    public void register(User user) {
        // 检查用户名是否已存在
        if (userDAO.findByUsername(user.getUsername()) != null) {
            logger.error("用户名已存在：" + user.getUsername());
            throw new RuntimeException("用户名已存在");
        }

        // 检查手机号是否已存在
        if (userDAO.findByPhone(user.getPhone()) != null) {
            logger.error("手机号已存在：" + user.getPhone());
            throw new RuntimeException("手机号已存在");
        }

        // 加密密码
        String encryptedPassword = PasswordUtil.md5(user.getPassword());
        user.setPassword(encryptedPassword);

        // 设置默认角色为患者
        user.setRoleId(3);
        // 设置默认状态为启用
        user.setStatus(1);

        // 保存用户
        userDAO.save(user);
        logger.info("用户注册成功：" + user.getUsername());
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User findByPhone(String phone) {
        return userDAO.findByPhone(phone);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public User findById(Integer id) {
        return userDAO.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public void delete(Integer id) {
        userDAO.delete(id);
        logger.info("删除用户成功：" + id);
    }
}