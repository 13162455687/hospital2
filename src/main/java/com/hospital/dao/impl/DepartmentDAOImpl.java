package com.hospital.dao.impl;

import com.hospital.dao.DepartmentDAO;
import com.hospital.entity.Department;
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
 * 科室DAO实现类
 */
public class DepartmentDAOImpl implements DepartmentDAO {
    private static final Logger logger = LogUtil.getLogger(DepartmentDAOImpl.class);

    @Override
    public Department findById(Integer id) {
        String sql = "SELECT * FROM department WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToDepartment(resultSet);
            }
        } catch (SQLException e) {
            logger.error("根据ID查询科室失败：" + id, e);
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
    public List<Department> findAll() {
        String sql = "SELECT * FROM department WHERE status = 1";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Department> departments = new ArrayList<>();

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                departments.add(mapResultSetToDepartment(resultSet));
            }
            logger.info("查询到的科室数量：" + departments.size());
        } catch (SQLException e) {
            logger.error("查询所有科室失败", e);
            // 打印详细错误信息
            e.printStackTrace();
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
        return departments;
    }

    @Override
    public void save(Department department) {
        String sql = "INSERT INTO department (dept_name, description, image_url, status) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, department.getDeptName());
            preparedStatement.setString(2, department.getDescription());
            preparedStatement.setString(3, department.getImageUrl());
            preparedStatement.setInt(4, department.getStatus());
            preparedStatement.executeUpdate();
            logger.info("保存科室成功：" + department.getDeptName());
        } catch (SQLException e) {
            logger.error("保存科室失败：" + department.getDeptName(), e);
            throw new RuntimeException("保存科室失败", e);
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
    public void update(Department department) {
        String sql = "UPDATE department SET dept_name = ?, description = ?, image_url = ?, status = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, department.getDeptName());
            preparedStatement.setString(2, department.getDescription());
            preparedStatement.setString(3, department.getImageUrl());
            preparedStatement.setInt(4, department.getStatus());
            preparedStatement.setInt(5, department.getId());
            preparedStatement.executeUpdate();
            logger.info("更新科室成功：" + department.getId());
        } catch (SQLException e) {
            logger.error("更新科室失败：" + department.getId(), e);
            throw new RuntimeException("更新科室失败", e);
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
        String sql = "DELETE FROM department WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info("删除科室成功：" + id);
        } catch (SQLException e) {
            logger.error("删除科室失败：" + id, e);
            throw new RuntimeException("删除科室失败", e);
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

    /**
     * 将ResultSet映射为Department对象
     * 
     * @param resultSet ResultSet对象
     * @return Department对象
     * @throws SQLException SQL异常
     */
    private Department mapResultSetToDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("id"));
        department.setDeptName(resultSet.getString("dept_name"));
        department.setDescription(resultSet.getString("description"));
        department.setImageUrl(resultSet.getString("image_url"));
        department.setStatus(resultSet.getInt("status"));
        department.setCreateTime(resultSet.getTimestamp("create_time"));
        department.setUpdateTime(resultSet.getTimestamp("update_time"));
        return department;
    }
}