package com.hospital.dao.impl;

import com.hospital.dao.SystemSettingDAO;
import com.hospital.entity.SystemSetting;
import com.hospital.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * 系统设置DAO实现类
 */
public class SystemSettingDAOImpl implements SystemSettingDAO {
    private static final Logger logger = Logger.getLogger(SystemSettingDAOImpl.class);

    @Override
    public SystemSetting findById(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SystemSetting systemSetting = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM system_setting WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                systemSetting = parseSystemSetting(rs);
            }
        } catch (SQLException e) {
            logger.error("根据ID查找系统设置失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return systemSetting;
    }

    @Override
    public SystemSetting getSystemSetting() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SystemSetting systemSetting = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM system_setting LIMIT 1";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                systemSetting = parseSystemSetting(rs);
            }
        } catch (SQLException e) {
            logger.error("获取系统设置失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return systemSetting;
    }

    @Override
    public boolean update(SystemSetting systemSetting) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE system_setting SET system_name = ?, contact_phone = ?, contact_email = ?, address = ?, copyright = ?, logo_path = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, systemSetting.getSystemName());
            pstmt.setString(2, systemSetting.getContactPhone());
            pstmt.setString(3, systemSetting.getContactEmail());
            pstmt.setString(4, systemSetting.getAddress());
            pstmt.setString(5, systemSetting.getCopyright());
            pstmt.setString(6, systemSetting.getLogoPath());
            pstmt.setInt(7, systemSetting.getId());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.error("更新系统设置失败", e);
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    /**
     * 解析ResultSet为SystemSetting对象
     */
    private SystemSetting parseSystemSetting(ResultSet rs) throws SQLException {
        SystemSetting systemSetting = new SystemSetting();
        systemSetting.setId(rs.getInt("id"));
        systemSetting.setSystemName(rs.getString("system_name"));
        systemSetting.setContactPhone(rs.getString("contact_phone"));
        systemSetting.setContactEmail(rs.getString("contact_email"));
        systemSetting.setAddress(rs.getString("address"));
        systemSetting.setCopyright(rs.getString("copyright"));
        systemSetting.setLogoPath(rs.getString("logo_path"));
        systemSetting.setCreateTime(rs.getTimestamp("create_time"));
        systemSetting.setUpdateTime(rs.getTimestamp("update_time"));
        return systemSetting;
    }
}