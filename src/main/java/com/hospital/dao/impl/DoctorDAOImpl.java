package com.hospital.dao.impl;

import com.hospital.dao.DoctorDAO;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.User;
import com.hospital.util.DBUtil;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 医生DAO实现类
 */
public class DoctorDAOImpl implements DoctorDAO {
    private static final Logger logger = LogUtil.getLogger(DoctorDAOImpl.class);

    @Override
    public Doctor findById(Integer id) {
        String sql = "SELECT d.*, de.dept_name, u.id AS user_id, u.name, u.phone, u.username, u.email, u.role_id, u.status AS user_status " +
                     "FROM doctor d " +
                     "LEFT JOIN department de ON d.dept_id = de.id " +
                     "LEFT JOIN user u ON d.user_id = u.id " +
                     "WHERE d.id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToDoctor(resultSet);
            }
        } catch (SQLException e) {
            logger.error("根据ID查询医生失败：" + id, e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    DBUtil.closeConnection(connection);
            } catch (SQLException e) {
                logger.error("关闭资源失败", e);
            }
        }
        return null;
    }

    @Override
    public List<Doctor> findAll() {
        String sql = "SELECT d.*, de.dept_name, u.id AS user_id, u.name, u.phone, u.username, u.email, u.role_id, u.status AS user_status " +
                     "FROM doctor d " +
                     "LEFT JOIN department de ON d.dept_id = de.id " +
                     "LEFT JOIN user u ON d.user_id = u.id";
        return executeQuery(sql);
    }

    @Override
    public List<Doctor> findByDeptId(Integer deptId) {
        String sql = "SELECT d.*, de.dept_name, u.id AS user_id, u.name, u.phone, u.username, u.email, u.role_id, u.status AS user_status " +
                     "FROM doctor d " +
                     "LEFT JOIN department de ON d.dept_id = de.id " +
                     "LEFT JOIN user u ON d.user_id = u.id " +
                     "WHERE d.dept_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Doctor> doctors = new ArrayList<>();

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, deptId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                doctors.add(mapResultSetToDoctor(resultSet));
            }
        } catch (SQLException e) {
            logger.error("根据科室ID查询医生失败：" + deptId, e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    DBUtil.closeConnection(connection);
            } catch (SQLException e) {
                logger.error("关闭资源失败", e);
            }
        }
        return doctors;
    }

    @Override
    public void save(Doctor doctor) {
        String sql = "INSERT INTO doctor (user_id, dept_id, title, specialty, avatar, status) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, doctor.getUserId());
            preparedStatement.setInt(2, doctor.getDeptId());
            preparedStatement.setString(3, doctor.getTitle());
            preparedStatement.setString(4, doctor.getSpecialty());
            preparedStatement.setString(5, doctor.getAvatar());
            preparedStatement.setInt(6, doctor.getStatus());
            preparedStatement.executeUpdate();
            logger.info("保存医生成功：user_id=" + doctor.getUserId());
        } catch (SQLException e) {
            logger.error("保存医生失败：user_id=" + doctor.getUserId(), e);
            throw new RuntimeException("保存医生失败", e);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    DBUtil.closeConnection(connection);
            } catch (SQLException e) {
                logger.error("关闭资源失败", e);
            }
        }
    }

    @Override
    public void update(Doctor doctor) {
        String sql = "UPDATE doctor SET user_id = ?, dept_id = ?, title = ?, specialty = ?, avatar = ?, status = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, doctor.getUserId());
            preparedStatement.setInt(2, doctor.getDeptId());
            preparedStatement.setString(3, doctor.getTitle());
            preparedStatement.setString(4, doctor.getSpecialty());
            preparedStatement.setString(5, doctor.getAvatar());
            preparedStatement.setInt(6, doctor.getStatus());
            preparedStatement.setInt(7, doctor.getId());
            preparedStatement.executeUpdate();
            logger.info("更新医生成功：" + doctor.getId());
        } catch (SQLException e) {
            logger.error("更新医生失败：" + doctor.getId(), e);
            throw new RuntimeException("更新医生失败", e);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    DBUtil.closeConnection(connection);
            } catch (SQLException e) {
                logger.error("关闭资源失败", e);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM doctor WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info("删除医生成功：" + id);
        } catch (SQLException e) {
            logger.error("删除医生失败：" + id, e);
            throw new RuntimeException("删除医生失败", e);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    DBUtil.closeConnection(connection);
            } catch (SQLException e) {
                logger.error("关闭资源失败", e);
            }
        }
    }

    @Override
    public List<Doctor> findAllByPage(int pageNum, int pageSize) {
        String sql = "SELECT d.*, de.dept_name, u.id AS user_id, u.name, u.phone, u.username, u.email, u.role_id, u.status AS user_status " +
                     "FROM doctor d " +
                     "LEFT JOIN department de ON d.dept_id = de.id " +
                     "LEFT JOIN user u ON d.user_id = u.id " +
                     "LIMIT ? OFFSET ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Doctor> doctors = new ArrayList<>();

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, (pageNum - 1) * pageSize);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                doctors.add(mapResultSetToDoctor(resultSet));
            }
        } catch (SQLException e) {
            logger.error("分页查询所有医生失败：" + e.getMessage(), e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    DBUtil.closeConnection(connection);
            } catch (SQLException e) {
                logger.error("关闭资源失败", e);
            }
        }
        return doctors;
    }

    @Override
    public List<Doctor> findByDeptIdAndPage(Integer deptId, int pageNum, int pageSize) {
        String sql = "SELECT d.*, de.dept_name, u.id AS user_id, u.name, u.phone, u.username, u.email, u.role_id, u.status AS user_status " +
                     "FROM doctor d " +
                     "LEFT JOIN department de ON d.dept_id = de.id " +
                     "LEFT JOIN user u ON d.user_id = u.id " +
                     "WHERE d.dept_id = ? " +
                     "LIMIT ? OFFSET ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Doctor> doctors = new ArrayList<>();

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, deptId);
            preparedStatement.setInt(2, pageSize);
            preparedStatement.setInt(3, (pageNum - 1) * pageSize);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                doctors.add(mapResultSetToDoctor(resultSet));
            }
        } catch (SQLException e) {
            logger.error("根据科室ID分页查询医生失败：" + deptId, e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    DBUtil.closeConnection(connection);
            } catch (SQLException e) {
                logger.error("关闭资源失败", e);
            }
        }
        return doctors;
    }

    @Override
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM doctor";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("获取医生总数失败：" + e.getMessage(), e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    DBUtil.closeConnection(connection);
            } catch (SQLException e) {
                logger.error("关闭资源失败", e);
            }
        }
        return count;
    }

    @Override
    public int getTotalCountByDeptId(Integer deptId) {
        String sql = "SELECT COUNT(*) FROM doctor WHERE dept_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, deptId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("根据科室ID获取医生总数失败：" + deptId, e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    DBUtil.closeConnection(connection);
            } catch (SQLException e) {
                logger.error("关闭资源失败", e);
            }
        }
        return count;
    }

    /**
     * 执行查询语句并返回医生列表
     * 
     * @param sql SQL语句
     * @return 医生列表
     */
    private List<Doctor> executeQuery(String sql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Doctor> doctors = new ArrayList<>();

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                doctors.add(mapResultSetToDoctor(resultSet));
            }
        } catch (SQLException e) {
            logger.error("执行查询医生SQL失败：" + sql, e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    DBUtil.closeConnection(connection);
            } catch (SQLException e) {
                logger.error("关闭资源失败", e);
            }
        }
        return doctors;
    }

    /**
     * 将ResultSet映射为Doctor对象
     * 
     * @param resultSet ResultSet对象
     * @return Doctor对象
     * @throws SQLException SQL异常
     */
    private Doctor mapResultSetToDoctor(ResultSet resultSet) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getInt("id"));
        doctor.setUserId(resultSet.getInt("user_id"));
        doctor.setDeptId(resultSet.getInt("dept_id"));
        doctor.setTitle(resultSet.getString("title"));
        doctor.setSpecialty(resultSet.getString("specialty"));
        doctor.setAvatar(resultSet.getString("avatar"));
        doctor.setStatus(resultSet.getInt("status"));
        doctor.setCreateTime(resultSet.getTimestamp("create_time"));
        doctor.setUpdateTime(resultSet.getTimestamp("update_time"));

        // 映射科室信息
        Department department = new Department();
        department.setId(resultSet.getInt("dept_id"));
        department.setDeptName(resultSet.getString("dept_name"));
        doctor.setDepartment(department);

        // 映射用户信息
        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        user.setName(resultSet.getString("name"));
        user.setPhone(resultSet.getString("phone"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setRoleId(resultSet.getInt("role_id"));
        user.setStatus(resultSet.getInt("user_status"));
        doctor.setUser(user);

        return doctor;
    }
}