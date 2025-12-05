package com.hospital.service.impl;

import com.hospital.dao.SystemSettingDAO;
import com.hospital.dao.impl.SystemSettingDAOImpl;
import com.hospital.entity.SystemSetting;
import com.hospital.service.SystemSettingService;
import org.apache.log4j.Logger;

/**
 * 系统设置Service实现类
 */
public class SystemSettingServiceImpl implements SystemSettingService {
    private static final Logger logger = Logger.getLogger(SystemSettingServiceImpl.class);
    private SystemSettingDAO systemSettingDAO = new SystemSettingDAOImpl();

    @Override
    public SystemSetting findById(Integer id) {
        try {
            return systemSettingDAO.findById(id);
        } catch (Exception e) {
            logger.error("根据ID查找系统设置失败", e);
            return null;
        }
    }

    @Override
    public SystemSetting getSystemSetting() {
        try {
            return systemSettingDAO.getSystemSetting();
        } catch (Exception e) {
            logger.error("获取系统设置失败", e);
            return null;
        }
    }

    @Override
    public boolean update(SystemSetting systemSetting) {
        try {
            return systemSettingDAO.update(systemSetting);
        } catch (Exception e) {
            logger.error("更新系统设置失败", e);
            return false;
        }
    }
}