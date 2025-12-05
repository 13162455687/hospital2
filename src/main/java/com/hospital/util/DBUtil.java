package com.hospital.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接池工具类
 */
public class DBUtil {
    private static final Logger logger = Logger.getLogger(DBUtil.class);
    private static DruidDataSource dataSource;
    // 线程本地变量，用于存储当前线程的数据库连接
    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        try {
            // 加载配置文件
            Properties properties = new Properties();
            InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);

            // 转换配置项名称，去掉"jdbc."前缀
            Properties druidProperties = new Properties();
            for (String key : properties.stringPropertyNames()) {
                if (key.startsWith("jdbc.")) {
                    String druidKey = key.substring(5); // 去掉"jdbc."前缀
                    druidProperties.setProperty(druidKey, properties.getProperty(key));
                } else {
                    druidProperties.setProperty(key, properties.getProperty(key));
                }
            }

            // 初始化Druid连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(druidProperties);
            logger.info("Druid连接池初始化成功");
        } catch (Exception e) {
            logger.error("Druid连接池初始化失败", e);
            throw new RuntimeException("Druid连接池初始化失败", e);
        }
    }

    /**
     * 获取数据库连接
     * 
     * @return 数据库连接
     */
    public static Connection getConnection() {
        try {
            // 先从线程本地变量中获取连接
            Connection connection = threadLocal.get();
            if (connection == null || connection.isClosed()) {
                // 如果线程本地变量中没有连接或连接已关闭，则从连接池获取新连接
                connection = dataSource.getConnection();
                // 将新连接存入线程本地变量
                threadLocal.set(connection);
            }
            logger.debug("获取数据库连接成功");
            return connection;
        } catch (SQLException e) {
            logger.error("获取数据库连接失败", e);
            throw new RuntimeException("获取数据库连接失败", e);
        }
    }

    /**
     * 开始事务
     */
    public static void beginTransaction() {
        try {
            Connection connection = getConnection();
            // 设置自动提交为false，开始事务
            connection.setAutoCommit(false);
            logger.debug("事务开始");
        } catch (SQLException e) {
            logger.error("开始事务失败", e);
            throw new RuntimeException("开始事务失败", e);
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        try {
            Connection connection = threadLocal.get();
            if (connection != null && !connection.isClosed()) {
                // 提交事务
                connection.commit();
                logger.debug("事务提交成功");
            }
        } catch (SQLException e) {
            logger.error("提交事务失败", e);
            throw new RuntimeException("提交事务失败", e);
        } finally {
            // 关闭连接并从线程本地变量中移除
            closeConnection();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        try {
            Connection connection = threadLocal.get();
            if (connection != null && !connection.isClosed()) {
                // 回滚事务
                connection.rollback();
                logger.debug("事务回滚成功");
            }
        } catch (SQLException e) {
            logger.error("回滚事务失败", e);
            throw new RuntimeException("回滚事务失败", e);
        } finally {
            // 关闭连接并从线程本地变量中移除
            closeConnection();
        }
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection() {
        try {
            Connection connection = threadLocal.get();
            if (connection != null && !connection.isClosed()) {
                // 恢复自动提交
                connection.setAutoCommit(true);
                // 关闭连接
                connection.close();
                logger.debug("关闭数据库连接成功");
            }
        } catch (SQLException e) {
            logger.error("关闭数据库连接失败", e);
        } finally {
            // 从线程本地变量中移除连接
            threadLocal.remove();
        }
    }
    
    /**
     * 关闭数据库连接（兼容旧代码）
     * @param connection 数据库连接
     */
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                // 关闭连接
                connection.close();
                logger.debug("关闭数据库连接成功");
            }
        } catch (SQLException e) {
            logger.error("关闭数据库连接失败", e);
        }
    }

    /**
     * 关闭所有数据库资源
     * 
     * @param conn  数据库连接
     * @param pstmt 预编译语句
     * @param rs    结果集
     */
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                logger.debug("关闭结果集成功");
            }
            if (pstmt != null) {
                pstmt.close();
                logger.debug("关闭预编译语句成功");
            }
            // 注意：这里不再关闭conn，而是由事务管理统一处理
        } catch (SQLException e) {
            logger.error("关闭数据库资源失败", e);
        }
    }

    /**
     * 获取连接池信息
     * 
     * @return 连接池信息
     */
    public static String getPoolInfo() {
        if (dataSource == null) {
            return "连接池未初始化";
        }
        return "连接池信息：" +
                "初始化连接数：" + dataSource.getInitialSize() + ", " +
                "最大连接数：" + dataSource.getMaxActive() + ", " +
                "当前连接数：" + dataSource.getActiveCount() + ", " +
                "空闲连接数：" + dataSource.getPoolingCount();
    }
}