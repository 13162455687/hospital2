package com.hospital.service;

import com.hospital.entity.SystemLog;

import java.util.List;

/**
 * 系统日志Service接口
 */
public interface SystemLogService {
    /**
     * 根据ID查找系统日志
     */
    SystemLog findById(Integer id);

    /**
     * 查找所有系统日志
     */
    List<SystemLog> findAll();

    /**
     * 保存系统日志
     */
    boolean save(SystemLog systemLog);

    /**
     * 删除系统日志
     */
    boolean delete(Integer id);

    /**
     * 批量删除系统日志
     */
    boolean deleteBatch(Integer[] ids);

    /**
     * 按用户名查找系统日志
     */
    List<SystemLog> findByUsername(String username);
    
    /**
     * 保存系统操作日志
     */
    boolean saveSystemLog(String username, String operation, String method, String params, String ip);
}