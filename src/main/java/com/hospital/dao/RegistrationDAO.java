package com.hospital.dao;

import com.hospital.entity.Registration;

import java.util.List;

/**
 * 挂号DAO接口
 */
public interface RegistrationDAO {
    /**
     * 根据ID查找挂号
     */
    Registration findById(Integer id);

    /**
     * 查找所有挂号
     */
    List<Registration> findAll();

    /**
     * 保存挂号
     */
    boolean save(Registration registration);

    /**
     * 更新挂号
     */
    boolean update(Registration registration);

    /**
     * 删除挂号
     */
    boolean delete(Integer id);

    /**
     * 根据预约ID查找挂号
     */
    Registration findByAppointmentId(Integer appointmentId);
}