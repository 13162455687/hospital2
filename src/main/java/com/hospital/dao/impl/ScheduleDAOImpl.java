package com.hospital.dao.impl;

import com.hospital.dao.ScheduleDAO;
import com.hospital.entity.Doctor;
import com.hospital.entity.Schedule;
import com.hospital.entity.User;
import com.hospital.util.DBUtil;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 排班DAO实现类
 */
public class ScheduleDAOImpl implements ScheduleDAO {
    private static final Logger logger = Logger.getLogger(ScheduleDAOImpl.class);

    @Override
    public Schedule findById(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Schedule schedule = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT s.*, d.id AS doctor_id, d.name AS doctor_name, d.title, d.specialty, d.dept_id " +
                    "FROM schedule s " +
                    "LEFT JOIN doctor d ON s.doctor_id = d.id " +
                    "WHERE s.id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                schedule = parseSchedule(rs);
            }
        } catch (SQLException e) {
            logger.error("根据ID查找排班失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return schedule;
    }

    @Override
    public List<Schedule> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Schedule> schedules = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT s.*, d.id AS doctor_id, d.name AS doctor_name, d.title, d.specialty, d.dept_id " +
                    "FROM schedule s " +
                    "LEFT JOIN doctor d ON s.doctor_id = d.id " +
                    "ORDER BY s.date DESC, s.time_slot";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                schedules.add(parseSchedule(rs));
            }
        } catch (SQLException e) {
            logger.error("查找所有排班失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return schedules;
    }

    @Override
    public boolean save(Schedule schedule) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO schedule (doctor_id, date, time_slot, max_num, current_num, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, schedule.getDoctorId());
            pstmt.setDate(2, new java.sql.Date(schedule.getDate().getTime()));
            pstmt.setString(3, schedule.getTimeSlot());
            pstmt.setInt(4, schedule.getMaxNum());
            pstmt.setInt(5, schedule.getCurrentNum());
            pstmt.setInt(6, schedule.getStatus());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("保存排班失败", e);
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public boolean update(Schedule schedule) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE schedule SET doctor_id = ?, date = ?, time_slot = ?, max_num = ?, current_num = ?, status = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, schedule.getDoctorId());
            pstmt.setDate(2, new java.sql.Date(schedule.getDate().getTime()));
            pstmt.setString(3, schedule.getTimeSlot());
            pstmt.setInt(4, schedule.getMaxNum());
            pstmt.setInt(5, schedule.getCurrentNum());
            pstmt.setInt(6, schedule.getStatus());
            pstmt.setInt(7, schedule.getId());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("更新排班失败", e);
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
            String sql = "DELETE FROM schedule WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("删除排班失败", e);
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public List<Schedule> findByDoctorId(Integer doctorId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Schedule> schedules = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT s.*, d.id AS doctor_id, d.name AS doctor_name, d.title, d.specialty, d.dept_id " +
                    "FROM schedule s " +
                    "LEFT JOIN doctor d ON s.doctor_id = d.id " +
                    "WHERE s.doctor_id = ? " +
                    "ORDER BY s.date DESC, s.time_slot";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, doctorId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                schedules.add(parseSchedule(rs));
            }
        } catch (SQLException e) {
            logger.error("根据医生ID查找排班失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return schedules;
    }

    @Override
    public List<Schedule> findByDoctorIdAndDate(Integer doctorId, java.sql.Date date) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Schedule> schedules = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT s.*, d.id AS doctor_id, d.name AS doctor_name, d.title, d.specialty, d.dept_id " +
                    "FROM schedule s " +
                    "LEFT JOIN doctor d ON s.doctor_id = d.id " +
                    "WHERE s.doctor_id = ? AND s.date = ? " +
                    "ORDER BY s.time_slot";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, doctorId);
            pstmt.setDate(2, date);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                schedules.add(parseSchedule(rs));
            }
        } catch (SQLException e) {
            logger.error("根据医生ID和日期查找排班失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return schedules;
    }

    @Override
    public Schedule findScheduleByDoctorDateAndTime(Integer doctorId, java.sql.Date date, String timeSlot) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Schedule schedule = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT s.*, d.id AS doctor_id, d.name AS doctor_name, d.title, d.specialty, d.dept_id " +
                    "FROM schedule s " +
                    "LEFT JOIN doctor d ON s.doctor_id = d.id " +
                    "WHERE s.doctor_id = ? AND s.date = ? AND s.time_slot = ? AND s.status = 1";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, doctorId);
            pstmt.setDate(2, date);
            pstmt.setString(3, timeSlot);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                schedule = parseSchedule(rs);
            }
        } catch (SQLException e) {
            logger.error("根据医生ID、日期和时间段查找排班失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return schedule;
    }

    /**
     * 解析ResultSet为Schedule对象
     */
    private Schedule parseSchedule(ResultSet rs) throws SQLException {
        Schedule schedule = new Schedule();
        schedule.setId(rs.getInt("id"));
        schedule.setDoctorId(rs.getInt("doctor_id"));
        schedule.setDate(rs.getDate("date"));
        schedule.setTimeSlot(rs.getString("time_slot"));
        schedule.setMaxNum(rs.getInt("max_num"));
        schedule.setCurrentNum(rs.getInt("current_num"));
        schedule.setStatus(rs.getInt("status"));
        schedule.setCreateTime(rs.getTimestamp("create_time"));
        schedule.setUpdateTime(rs.getTimestamp("update_time"));

        // 解析医生信息
        Doctor doctor = new Doctor();
        doctor.setId(rs.getInt("doctor_id"));
        doctor.setTitle(rs.getString("title"));
        doctor.setSpecialty(rs.getString("specialty"));
        doctor.setDeptId(rs.getInt("dept_id"));
        
        // 解析医生关联的用户信息
        User user = new User();
        user.setName(rs.getString("doctor_name"));
        doctor.setUser(user);
        
        schedule.setDoctor(doctor);

        return schedule;
    }
}