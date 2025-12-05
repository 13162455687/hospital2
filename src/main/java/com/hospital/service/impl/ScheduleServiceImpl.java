package com.hospital.service.impl;

import com.hospital.dao.ScheduleDAO;
import com.hospital.dao.impl.ScheduleDAOImpl;
import com.hospital.entity.Schedule;
import com.hospital.service.ScheduleService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 排班Service实现类
 */
public class ScheduleServiceImpl implements ScheduleService {
    private static final Logger logger = Logger.getLogger(ScheduleServiceImpl.class);
    private ScheduleDAO scheduleDAO = new ScheduleDAOImpl();

    @Override
    public Schedule findById(Integer id) {
        try {
            return scheduleDAO.findById(id);
        } catch (Exception e) {
            logger.error("根据ID查找排班失败", e);
            return null;
        }
    }

    @Override
    public List<Schedule> findAll() {
        try {
            return scheduleDAO.findAll();
        } catch (Exception e) {
            logger.error("查找所有排班失败", e);
            return null;
        }
    }

    @Override
    public boolean save(Schedule schedule) {
        try {
            return scheduleDAO.save(schedule);
        } catch (Exception e) {
            logger.error("保存排班失败", e);
            return false;
        }
    }

    @Override
    public boolean update(Schedule schedule) {
        try {
            return scheduleDAO.update(schedule);
        } catch (Exception e) {
            logger.error("更新排班失败", e);
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return scheduleDAO.delete(id);
        } catch (Exception e) {
            logger.error("删除排班失败", e);
            return false;
        }
    }

    @Override
    public List<Schedule> findByDoctorId(Integer doctorId) {
        try {
            return scheduleDAO.findByDoctorId(doctorId);
        } catch (Exception e) {
            logger.error("根据医生ID查找排班失败", e);
            return null;
        }
    }

    @Override
    public List<Schedule> findByDoctorIdAndDate(Integer doctorId, java.sql.Date date) {
        try {
            return scheduleDAO.findByDoctorIdAndDate(doctorId, date);
        } catch (Exception e) {
            logger.error("根据医生ID和日期查找排班失败", e);
            return null;
        }
    }
    
    @Override
    public Schedule findScheduleByDoctorDateAndTime(Integer doctorId, java.util.Date date, String timeSlot) {
        try {
            // 转换日期类型，从java.util.Date转换为java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            return scheduleDAO.findScheduleByDoctorDateAndTime(doctorId, sqlDate, timeSlot);
        } catch (Exception e) {
            logger.error("根据医生ID、日期和时间段查找排班失败", e);
            return null;
        }
    }
}