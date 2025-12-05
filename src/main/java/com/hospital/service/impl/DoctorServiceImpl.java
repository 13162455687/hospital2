package com.hospital.service.impl;

import com.hospital.dao.AppointmentDAO;
import com.hospital.dao.DoctorDAO;
import com.hospital.dao.ScheduleDAO;
import com.hospital.dao.impl.AppointmentDAOImpl;
import com.hospital.dao.impl.DoctorDAOImpl;
import com.hospital.dao.impl.ScheduleDAOImpl;
import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
import com.hospital.entity.Schedule;
import com.hospital.service.DoctorService;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 医生服务实现类
 */
public class DoctorServiceImpl implements DoctorService {
    private static final Logger logger = LogUtil.getLogger(DoctorServiceImpl.class);
    private DoctorDAO doctorDAO = new DoctorDAOImpl();

    @Override
    public Doctor findById(Integer id) {
        return doctorDAO.findById(id);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorDAO.findAll();
    }

    @Override
    public List<Doctor> findByDeptId(Integer deptId) {
        return doctorDAO.findByDeptId(deptId);
    }

    @Override
    public void save(Doctor doctor) {
        // 可以添加业务逻辑验证
        doctorDAO.save(doctor);
        logger.info("保存医生成功：user_id=" + doctor.getUserId());
    }

    @Override
    public void update(Doctor doctor) {
        // 可以添加业务逻辑验证
        doctorDAO.update(doctor);
        logger.info("更新医生成功：" + doctor.getId());
    }

    @Override
    public void delete(Integer id) {
        try {
            // 1. 获取相关的依赖记录DAO
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
            ScheduleDAO scheduleDAO = new ScheduleDAOImpl();
            
            // 2. 先删除该医生相关的预约记录
            List<Appointment> appointments = appointmentDAO.findByDoctorId(id);
            for (Appointment appointment : appointments) {
                appointmentDAO.delete(appointment.getId());
            }
            
            // 3. 再删除该医生相关的排班记录
            List<Schedule> schedules = scheduleDAO.findByDoctorId(id);
            for (Schedule schedule : schedules) {
                scheduleDAO.delete(schedule.getId());
            }
            
            // 4. 最后删除医生记录
            doctorDAO.delete(id);
            logger.info("删除医生成功：" + id);
        } catch (Exception e) {
            logger.error("删除医生失败：" + id, e);
            throw new RuntimeException("删除医生失败，可能存在关联数据", e);
        }
    }

    @Override
    public List<Doctor> findAllByPage(int pageNum, int pageSize) {
        return doctorDAO.findAllByPage(pageNum, pageSize);
    }

    @Override
    public List<Doctor> findByDeptIdAndPage(Integer deptId, int pageNum, int pageSize) {
        return doctorDAO.findByDeptIdAndPage(deptId, pageNum, pageSize);
    }

    @Override
    public int getTotalCount() {
        return doctorDAO.getTotalCount();
    }

    @Override
    public int getTotalCountByDeptId(Integer deptId) {
        return doctorDAO.getTotalCountByDeptId(deptId);
    }
}