package com.hospital.service.impl;

import com.hospital.dao.SystemLogDAO;
import com.hospital.dao.impl.SystemLogDAOImpl;
import com.hospital.entity.SystemLog;
import com.hospital.service.SystemLogService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 系统日志Service实现类
 */
public class SystemLogServiceImpl implements SystemLogService {
    private static final Logger logger = Logger.getLogger(SystemLogServiceImpl.class);
    private SystemLogDAO systemLogDAO = new SystemLogDAOImpl();

    @Override
    public SystemLog findById(Integer id) {
        try {
            return systemLogDAO.findById(id);
        } catch (Exception e) {
            logger.error("根据ID查找系统日志失败", e);
            return null;
        }
    }

    @Override
    public List<SystemLog> findAll() {
        try {
            return systemLogDAO.findAll();
        } catch (Exception e) {
            logger.error("查找所有系统日志失败", e);
            return null;
        }
    }

    @Override
    public boolean save(SystemLog systemLog) {
        try {
            return systemLogDAO.save(systemLog);
        } catch (Exception e) {
            logger.error("保存系统日志失败", e);
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return systemLogDAO.delete(id);
        } catch (Exception e) {
            logger.error("删除系统日志失败", e);
            return false;
        }
    }

    @Override
    public boolean deleteBatch(Integer[] ids) {
        try {
            return systemLogDAO.deleteBatch(ids);
        } catch (Exception e) {
            logger.error("批量删除系统日志失败", e);
            return false;
        }
    }

    @Override
    public List<SystemLog> findByUsername(String username) {
        try {
            return systemLogDAO.findByUsername(username);
        } catch (Exception e) {
            logger.error("按用户名查找系统日志失败", e);
            return null;
        }
    }
    
    @Override
    public boolean saveSystemLog(String username, String operation, String method, String params, String ip) {
        try {
            SystemLog systemLog = new SystemLog();
            systemLog.setUsername(username);
            systemLog.setOperation(operation);
            systemLog.setMethod(method);
            systemLog.setParams(params);
            systemLog.setIp(ip);
            
            return save(systemLog);
        } catch (Exception e) {
            logger.error("保存系统操作日志失败", e);
            return false;
        }
    }
}