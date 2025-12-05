package com.hospital.service.impl;

import com.hospital.dao.AppointmentDAO;
import com.hospital.dao.impl.AppointmentDAOImpl;
import com.hospital.entity.Appointment;
import com.hospital.service.AppointmentService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 预约Service实现类
 */
public class AppointmentServiceImpl implements AppointmentService {
    private static final Logger logger = Logger.getLogger(AppointmentServiceImpl.class);
    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    @Override
    public Appointment findById(Integer id) {
        try {
            return appointmentDAO.findById(id);
        } catch (Exception e) {
            logger.error("根据ID查找预约失败", e);
            return null;
        }
    }

    @Override
    public List<Appointment> findAll() {
        try {
            return appointmentDAO.findAll();
        } catch (Exception e) {
            logger.error("查找所有预约失败", e);
            return null;
        }
    }

    @Override
    public boolean save(Appointment appointment) {
        try {
            return appointmentDAO.save(appointment);
        } catch (Exception e) {
            logger.error("保存预约失败", e);
            return false;
        }
    }

    @Override
    public boolean update(Appointment appointment) {
        try {
            return appointmentDAO.update(appointment);
        } catch (Exception e) {
            logger.error("更新预约失败", e);
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return appointmentDAO.delete(id);
        } catch (Exception e) {
            logger.error("删除预约失败", e);
            return false;
        }
    }

    @Override
    public List<Appointment> findByUserId(Integer userId) {
        try {
            return appointmentDAO.findByUserId(userId);
        } catch (Exception e) {
            logger.error("根据用户ID查找预约失败", e);
            return null;
        }
    }

    @Override
    public List<Appointment> findByDoctorId(Integer doctorId) {
        try {
            return appointmentDAO.findByDoctorId(doctorId);
        } catch (Exception e) {
            logger.error("根据医生ID查找预约失败", e);
            return null;
        }
    }
    
    @Override
    public boolean checkAppointmentConflict(Integer userId, java.util.Date appointmentDate, String timeSlot) {
        try {
            // 转换日期类型，从java.util.Date转换为java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(appointmentDate.getTime());
            return appointmentDAO.checkAppointmentConflict(userId, sqlDate, timeSlot);
        } catch (Exception e) {
            logger.error("检查预约冲突失败", e);
            return false;
        }
    }
}