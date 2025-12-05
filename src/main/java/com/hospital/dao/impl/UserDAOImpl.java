package com.hospital.dao.impl;

import com.hospital.dao.UserDAO;
import com.hospital.entity.User;
import com.hospital.util.DBUtil;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户DAO实现类
 */
public class UserDAOImpl implements UserDAO {
    private static final Logger logger = LogUtil.getLogger(UserDAOImpl.class);

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setPhone(resultSet.getString("phone"));
                user.setEmail(resultSet.getString("email"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setStatus(resultSet.getInt("status"));
                user.setCreateTime(resultSet.getTimestamp("create_time"));
                user.setUpdateTime(resultSet.getTimestamp("update_time"));
                return user;
            }
        } catch (SQLException e) {
            logger.error("根据用户名查询用户失败：" + username, e);
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
    public User findByPhone(String phone) {
        String sql = "SELECT * FROM user WHERE phone = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, phone);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setPhone(resultSet.getString("phone"));
                user.setEmail(resultSet.getString("email"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setStatus(resultSet.getInt("status"));
                user.setCreateTime(resultSet.getTimestamp("create_time"));
                user.setUpdateTime(resultSet.getTimestamp("update_time"));
                return user;
            }
        } catch (SQLException e) {
            logger.error("根据手机号查询用户失败：" + phone, e);
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
    public void save(User user) {
        String sql = "INSERT INTO user (username, password, name, phone, email, role_id, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setInt(6, user.getRoleId());
            preparedStatement.setInt(7, user.getStatus());
            preparedStatement.executeUpdate();
            logger.info("保存用户成功：" + user.getUsername());
        } catch (SQLException e) {
            logger.error("保存用户失败：" + user.getUsername(), e);
            throw new RuntimeException("保存用户失败", e);
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
    public void update(User user) {
        String sql = "UPDATE user SET password = ?, name = ?, phone = ?, email = ?, role_id = ?, status = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.getRoleId());
            preparedStatement.setInt(6, user.getStatus());
            preparedStatement.setInt(7, user.getId());
            preparedStatement.executeUpdate();
            logger.info("更新用户成功：" + user.getId());
        } catch (SQLException e) {
            logger.error("更新用户失败：" + user.getId(), e);
            throw new RuntimeException("更新用户失败", e);
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
    public User findById(Integer id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setPhone(resultSet.getString("phone"));
                user.setEmail(resultSet.getString("email"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setStatus(resultSet.getInt("status"));
                user.setCreateTime(resultSet.getTimestamp("create_time"));
                user.setUpdateTime(resultSet.getTimestamp("update_time"));
                return user;
            }
        } catch (SQLException e) {
            logger.error("根据ID查询用户失败：" + id, e);
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
    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setPhone(resultSet.getString("phone"));
                user.setEmail(resultSet.getString("email"));
                user.setRoleId(resultSet.getInt("role_id"));
                user.setStatus(resultSet.getInt("status"));
                user.setCreateTime(resultSet.getTimestamp("create_time"));
                user.setUpdateTime(resultSet.getTimestamp("update_time"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("查询所有用户失败", e);
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
        return users;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM user WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info("删除用户成功：" + id);
        } catch (SQLException e) {
            logger.error("删除用户失败：" + id, e);
            throw new RuntimeException("删除用户失败", e);
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
}