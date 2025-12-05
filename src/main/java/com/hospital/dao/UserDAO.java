package com.hospital.dao;

import com.hospital.entity.User;

import java.util.List;

/**
 * 用户DAO接口
 */
public interface UserDAO {
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
     * 保存用户信息
     * 
     * @param user 用户信息
     */
    void save(User user);

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