package com.hospital.service;

import com.hospital.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {
    /**
     * 用户登录
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户信息
     */
    User login(String username, String password);

    /**
     * 用户注册
     * 
     * @param user 用户信息
     */
    void register(User user);

    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 根据手机号查询用户
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    User findByPhone(String phone);

    /**
     * 更新用户信息
     * 
     * @param user 用户信息
     */
    void update(User user);

    /**
     * 根据ID查询用户
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    User findById(Integer id);
    
    /**
     * 查询所有用户
     * 
     * @return 用户列表
     */
    List<User> findAll();
    
    /**
     * 根据ID删除用户
     * 
     * @param id 用户ID
     */
    void delete(Integer id);
}