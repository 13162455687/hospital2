package com.hospital.dao;

import com.hospital.entity.Appointment;

import java.util.List;

/**
 * 预约DAO接口
 */
public interface AppointmentDAO {
    /**
     * 根据ID查找预约
     */
    Appointment findById(Integer id);

    /**
     * 查找所有预约
     */
    List<Appointment> findAll();

    /**
     * 保存预约
     */
    boolean save(Appointment appointment);

    /**
     * 更新预约
     */
    boolean update(Appointment appointment);

    /**
     * 删除预约
     */
    boolean delete(Integer id);

    /**
     * 根据用户ID查找预约
     */
    List<Appointment> findByUserId(Integer userId);

    /**
     * 根据医生ID查找预约
     */
    List<Appointment> findByDoctorId(Integer doctorId);
    
    /**
     * 检查患者在特定日期和时间段是否有冲突的预约
     */
    boolean checkAppointmentConflict(Integer userId, java.sql.Date appointmentDate, String timeSlot);
}