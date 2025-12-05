package com.hospital.dao.impl;

import com.hospital.dao.RegistrationDAO;
import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
import com.hospital.entity.Registration;
import com.hospital.entity.User;
import com.hospital.util.DBUtil;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 挂号DAO实现类
 */
public class RegistrationDAOImpl implements RegistrationDAO {
    private static final Logger logger = Logger.getLogger(RegistrationDAOImpl.class);

    @Override
    public Registration findById(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Registration registration = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT r.*, a.id AS app_id, a.user_id, a.doctor_id, a.appointment_date, a.time_slot, a.status AS app_status, "
                    +
                    "u.id AS user_id, u.name AS user_name, u.phone AS user_phone, " +
                    "d.id AS doctor_id, d.name AS doctor_name, d.title, d.specialty " +
                    "FROM registration r " +
                    "LEFT JOIN appointment a ON r.appointment_id = a.id " +
                    "LEFT JOIN user u ON a.user_id = u.id " +
                    "LEFT JOIN doctor d ON a.doctor_id = d.id " +
                    "WHERE r.id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                registration = parseRegistration(rs);
            }
        } catch (SQLException e) {
            logger.error("根据ID查找挂号失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return registration;
    }

    @Override
    public List<Registration> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Registration> registrations = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT r.*, a.id AS app_id, a.user_id, a.doctor_id, a.appointment_date, a.time_slot, a.status AS app_status, "
                    +
                    "u.id AS user_id, u.name AS user_name, u.phone AS user_phone, " +
                    "d.id AS doctor_id, d.name AS doctor_name, d.title, d.specialty " +
                    "FROM registration r " +
                    "LEFT JOIN appointment a ON r.appointment_id = a.id " +
                    "LEFT JOIN user u ON a.user_id = u.id " +
                    "LEFT JOIN doctor d ON a.doctor_id = d.id " +
                    "ORDER BY r.create_time DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                registrations.add(parseRegistration(rs));
            }
        } catch (SQLException e) {
            logger.error("查找所有挂号失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return registrations;
    }

    @Override
    public boolean save(Registration registration) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO registration (appointment_id, registration_time, cost, payment_status, doctor_advice) "
                    +
                    "VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, registration.getAppointmentId());
            pstmt.setTimestamp(2, new Timestamp(registration.getRegistrationTime().getTime()));
            pstmt.setBigDecimal(3, registration.getCost());
            pstmt.setInt(4, registration.getPaymentStatus());
            pstmt.setString(5, registration.getDoctorAdvice());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("保存挂号失败", e);
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public boolean update(Registration registration) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE registration SET cost = ?, payment_status = ?, doctor_advice = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setBigDecimal(1, registration.getCost());
            pstmt.setInt(2, registration.getPaymentStatus());
            pstmt.setString(3, registration.getDoctorAdvice());
            pstmt.setInt(4, registration.getId());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("更新挂号失败", e);
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public boolean delete(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM registration WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("删除挂号失败", e);
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public Registration findByAppointmentId(Integer appointmentId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Registration registration = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT r.*, a.id AS app_id, a.user_id, a.doctor_id, a.appointment_date, a.time_slot, a.status AS app_status, "
                    +
                    "u.id AS user_id, u.name AS user_name, u.phone AS user_phone, " +
                    "d.id AS doctor_id, d.name AS doctor_name, d.title, d.specialty " +
                    "FROM registration r " +
                    "LEFT JOIN appointment a ON r.appointment_id = a.id " +
                    "LEFT JOIN user u ON a.user_id = u.id " +
                    "LEFT JOIN doctor d ON a.doctor_id = d.id " +
                    "WHERE r.appointment_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, appointmentId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                registration = parseRegistration(rs);
            }
        } catch (SQLException e) {
            logger.error("根据预约ID查找挂号失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return registration;
    }

    /**
     * 解析ResultSet为Registration对象
     */
    private Registration parseRegistration(ResultSet rs) throws SQLException {
        Registration registration = new Registration();
        registration.setId(rs.getInt("id"));
        registration.setAppointmentId(rs.getInt("appointment_id"));
        registration.setRegistrationTime(rs.getTimestamp("registration_time"));
        registration.setCost(rs.getBigDecimal("cost"));
        registration.setPaymentStatus(rs.getInt("payment_status"));
        registration.setDoctorAdvice(rs.getString("doctor_advice"));
        registration.setCreateTime(rs.getTimestamp("create_time"));
        registration.setUpdateTime(rs.getTimestamp("update_time"));

        // 解析预约信息
        Appointment appointment = new Appointment();
        appointment.setId(rs.getInt("app_id"));
        appointment.setUserId(rs.getInt("user_id"));
        appointment.setDoctorId(rs.getInt("doctor_id"));
        appointment.setAppointmentDate(rs.getDate("appointment_date"));
        appointment.setTimeSlot(rs.getString("time_slot"));
        appointment.setStatus(rs.getInt("app_status"));

        // 解析患者信息
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setName(rs.getString("user_name"));
        user.setPhone(rs.getString("user_phone"));
        appointment.setUser(user);

        // 解析医生信息
        Doctor doctor = new Doctor();
        doctor.setId(rs.getInt("doctor_id"));
        doctor.setTitle(rs.getString("title"));
        doctor.setSpecialty(rs.getString("specialty"));
        
        // 解析医生关联的用户信息
        User doctorUser = new User();
        doctorUser.setName(rs.getString("doctor_name"));
        doctor.setUser(doctorUser);
        
        appointment.setDoctor(doctor);

        registration.setAppointment(appointment);

        return registration;
    }
}