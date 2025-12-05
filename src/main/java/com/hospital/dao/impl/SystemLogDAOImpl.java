package com.hospital.dao.impl;

import com.hospital.dao.SystemLogDAO;
import com.hospital.entity.SystemLog;
import com.hospital.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统日志DAO实现类
 */
public class SystemLogDAOImpl implements SystemLogDAO {
    private static final Logger logger = Logger.getLogger(SystemLogDAOImpl.class);

    @Override
    public SystemLog findById(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SystemLog systemLog = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM system_log WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                systemLog = parseSystemLog(rs);
            }
        } catch (SQLException e) {
            logger.error("根据ID查找系统日志失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return systemLog;
    }

    @Override
    public List<SystemLog> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<SystemLog> systemLogs = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM system_log ORDER BY create_time DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                systemLogs.add(parseSystemLog(rs));
            }
        } catch (SQLException e) {
            logger.error("查找所有系统日志失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return systemLogs;
    }

    @Override
    public boolean save(SystemLog systemLog) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO system_log (username, operation, method, params, ip, create_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, systemLog.getUsername());
            pstmt.setString(2, systemLog.getOperation());
            pstmt.setString(3, systemLog.getMethod());
            pstmt.setString(4, systemLog.getParams());
            pstmt.setString(5, systemLog.getIp());
            pstmt.setTimestamp(6, new Timestamp(systemLog.getCreateTime().getTime()));

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("保存系统日志失败", e);
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
            String sql = "DELETE FROM system_log WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("删除系统日志失败", e);
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public boolean deleteBatch(Integer[] ids) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM system_log WHERE id IN (";
            StringBuilder sb = new StringBuilder(sql);
            for (int i = 0; i < ids.length; i++) {
                sb.append("?");
                if (i < ids.length - 1) {
                    sb.append(",");
                }
            }
            sb.append(")");
            pstmt = conn.prepareStatement(sb.toString());
            for (int i = 0; i < ids.length; i++) {
                pstmt.setInt(i + 1, ids[i]);
            }

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("批量删除系统日志失败", e);
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public List<SystemLog> findByUsername(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<SystemLog> systemLogs = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM system_log WHERE username = ? ORDER BY create_time DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                systemLogs.add(parseSystemLog(rs));
            }
        } catch (SQLException e) {
            logger.error("按用户名查找系统日志失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return systemLogs;
    }

    /**
     * 解析ResultSet为SystemLog对象
     */
    private SystemLog parseSystemLog(ResultSet rs) throws SQLException {
        SystemLog systemLog = new SystemLog();
        systemLog.setId(rs.getInt("id"));
        systemLog.setUsername(rs.getString("username"));
        systemLog.setOperation(rs.getString("operation"));
        systemLog.setMethod(rs.getString("method"));
        systemLog.setParams(rs.getString("params"));
        systemLog.setIp(rs.getString("ip"));
        systemLog.setCreateTime(rs.getTimestamp("create_time"));
        return systemLog;
    }
}