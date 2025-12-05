package com.hospital.service.impl;

import com.hospital.dao.RegistrationDAO;
import com.hospital.dao.impl.RegistrationDAOImpl;
import com.hospital.entity.Registration;
import com.hospital.service.RegistrationService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 挂号Service实现类
 */
public class RegistrationServiceImpl implements RegistrationService {
    private static final Logger logger = Logger.getLogger(RegistrationServiceImpl.class);
    private RegistrationDAO registrationDAO = new RegistrationDAOImpl();

    @Override
    public Registration findById(Integer id) {
        try {
            return registrationDAO.findById(id);
        } catch (Exception e) {
            logger.error("根据ID查找挂号失败", e);
            return null;
        }
    }

    @Override
    public List<Registration> findAll() {
        try {
            return registrationDAO.findAll();
        } catch (Exception e) {
            logger.error("查找所有挂号失败", e);
            return null;
        }
    }

    @Override
    public boolean save(Registration registration) {
        try {
            return registrationDAO.save(registration);
        } catch (Exception e) {
            logger.error("保存挂号失败", e);
            return false;
        }
    }

    @Override
    public boolean update(Registration registration) {
        try {
            return registrationDAO.update(registration);
        } catch (Exception e) {
            logger.error("更新挂号失败", e);
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return registrationDAO.delete(id);
        } catch (Exception e) {
            logger.error("删除挂号失败", e);
            return false;
        }
    }

    @Override
    public Registration findByAppointmentId(Integer appointmentId) {
        try {
            return registrationDAO.findByAppointmentId(appointmentId);
        } catch (Exception e) {
            logger.error("根据预约ID查找挂号失败", e);
            return null;
        }
    }
}