package com.hospital.service;

import com.hospital.entity.SystemSetting;

/**
 * 系统设置Service接口
 */
public interface SystemSettingService {
    /**
     * 根据ID查找系统设置
     */
    SystemSetting findById(Integer id);

    /**
     * 获取系统设置（由于系统设置只有一条记录，所以可以直接获取）
     */
    SystemSetting getSystemSetting();

    /**
     * 更新系统设置
     */
    boolean update(SystemSetting systemSetting);
}