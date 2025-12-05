package com.hospital.dao;

import com.hospital.entity.Schedule;

import java.util.List;

/**
 * 排班DAO接口
 */
public interface ScheduleDAO {
    /**
     * 根据ID查找排班
     */
    Schedule findById(Integer id);

    /**
     * 查找所有排班
     */
    List<Schedule> findAll();

    /**
     * 保存排班
     */
    boolean save(Schedule schedule);

    /**
     * 更新排班
     */
    boolean update(Schedule schedule);

    /**
     * 删除排班
     */
    boolean delete(Integer id);

    /**
     * 根据医生ID查找排班
     */
    List<Schedule> findByDoctorId(Integer doctorId);

    /**
     * 查找医生在特定日期的排班
     */
    List<Schedule> findByDoctorIdAndDate(Integer doctorId, java.sql.Date date);
    
    /**
     * 根据医生ID、日期和时间段查找排班
     */
    Schedule findScheduleByDoctorDateAndTime(Integer doctorId, java.sql.Date date, String timeSlot);
}