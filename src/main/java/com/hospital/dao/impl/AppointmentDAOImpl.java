package com.hospital.dao.impl;

import com.hospital.dao.AppointmentDAO;
import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
import com.hospital.entity.User;
import com.hospital.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 预约DAO实现类
 */
public class AppointmentDAOImpl implements AppointmentDAO {
    private static final Logger logger = Logger.getLogger(AppointmentDAOImpl.class);

    @Override
    public Appointment findById(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Appointment appointment = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT a.*, u.id AS user_id, u.username, u.name AS user_name, u.phone AS user_phone, " +
                    "d.id AS doctor_id, d.name AS doctor_name, d.title, d.specialty, d.dept_id " +
                    "FROM appointment a " +
                    "LEFT JOIN user u ON a.user_id = u.id " +
                    "LEFT JOIN doctor d ON a.doctor_id = d.id " +
                    "WHERE a.id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                appointment = parseAppointment(rs);
            }
        } catch (SQLException e) {
            logger.error("根据ID查找预约失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return appointment;
    }

    @Override
    public List<Appointment> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Appointment> appointments = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT a.*, u.id AS user_id, u.username, u.name AS user_name, u.phone AS user_phone, " +
                    "d.id AS doctor_id, d.name AS doctor_name, d.title, d.specialty, d.dept_id " +
                    "FROM appointment a " +
                    "LEFT JOIN user u ON a.user_id = u.id " +
                    "LEFT JOIN doctor d ON a.doctor_id = d.id " +
                    "ORDER BY a.create_time DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                appointments.add(parseAppointment(rs));
            }
        } catch (SQLException e) {
            logger.error("查找所有预约失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return appointments;
    }

    @Override
    public boolean save(Appointment appointment) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO appointment (user_id, doctor_id, schedule_id, appointment_date, time_slot, status) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, appointment.getUserId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setInt(3, appointment.getScheduleId());
            pstmt.setDate(4, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            pstmt.setString(5, appointment.getTimeSlot());
            pstmt.setInt(6, appointment.getStatus());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("保存预约失败", e);
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public boolean update(Appointment appointment) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE appointment SET status = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, appointment.getStatus());
            pstmt.setInt(2, appointment.getId());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("更新预约失败", e);
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
            String sql = "DELETE FROM appointment WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("删除预约失败", e);
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public List<Appointment> findByUserId(Integer userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Appointment> appointments = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT a.*, u.id AS user_id, u.username, u.name AS user_name, u.phone AS user_phone, " +
                    "d.id AS doctor_id, d.name AS doctor_name, d.title, d.specialty, d.dept_id " +
                    "FROM appointment a " +
                    "LEFT JOIN user u ON a.user_id = u.id " +
                    "LEFT JOIN doctor d ON a.doctor_id = d.id " +
                    "WHERE a.user_id = ? " +
                    "ORDER BY a.create_time DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                appointments.add(parseAppointment(rs));
            }
        } catch (SQLException e) {
            logger.error("根据用户ID查找预约失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return appointments;
    }

    @Override
    public List<Appointment> findByDoctorId(Integer doctorId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Appointment> appointments = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT a.*, u.id AS user_id, u.username, u.name AS user_name, u.phone AS user_phone, " +
                    "d.id AS doctor_id, d.name AS doctor_name, d.title, d.specialty, d.dept_id " +
                    "FROM appointment a " +
                    "LEFT JOIN user u ON a.user_id = u.id " +
                    "LEFT JOIN doctor d ON a.doctor_id = d.id " +
                    "WHERE a.doctor_id = ? " +
                    "ORDER BY a.create_time DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, doctorId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                appointments.add(parseAppointment(rs));
            }
        } catch (SQLException e) {
            logger.error("根据医生ID查找预约失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return appointments;
    }

    @Override
    public boolean checkAppointmentConflict(Integer userId, java.sql.Date appointmentDate, String timeSlot) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean hasConflict = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) AS count " +
                    "FROM appointment " +
                    "WHERE user_id = ? AND appointment_date = ? AND time_slot = ? AND status = 1";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setDate(2, appointmentDate);
            pstmt.setString(3, timeSlot);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                hasConflict = count > 0;
            }
        } catch (SQLException e) {
            logger.error("检查预约冲突失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return hasConflict;
    }

    /**
     * 解析ResultSet为Appointment对象
     */
    private Appointment parseAppointment(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(rs.getInt("id"));
        appointment.setUserId(rs.getInt("user_id"));
        appointment.setDoctorId(rs.getInt("doctor_id"));
        appointment.setScheduleId(rs.getInt("schedule_id"));
        appointment.setAppointmentDate(rs.getDate("appointment_date"));
        appointment.setTimeSlot(rs.getString("time_slot"));
        appointment.setStatus(rs.getInt("status"));
        appointment.setCreateTime(rs.getTimestamp("create_time"));
        appointment.setUpdateTime(rs.getTimestamp("update_time"));

        // 解析用户信息
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setName(rs.getString("user_name"));
        user.setPhone(rs.getString("user_phone"));
        appointment.setUser(user);

        // 解析医生信息
        Doctor doctor = new Doctor();
        doctor.setId(rs.getInt("doctor_id"));
        doctor.setTitle(rs.getString("title"));
        doctor.setSpecialty(rs.getString("specialty"));
        doctor.setDeptId(rs.getInt("dept_id"));
        
        // 解析医生关联的用户信息
        User doctorUser = new User();
        doctorUser.setName(rs.getString("doctor_name"));
        doctor.setUser(doctorUser);
        
        appointment.setDoctor(doctor);

        return appointment;
    }
}