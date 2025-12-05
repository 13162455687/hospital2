package com.hospital.util;

import com.hospital.service.SystemLogService;
import com.hospital.service.impl.SystemLogServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * 日志工具类
 */
public class LogUtil {
    private static final SystemLogService systemLogService = new SystemLogServiceImpl();
    
    /**
     * 获取日志记录器
     * 
     * @param clazz 类对象
     * @return 日志记录器
     */
    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz);
    }

    /**
     * 获取日志记录器
     * 
     * @param name 名称
     * @return 日志记录器
     */
    public static Logger getLogger(String name) {
        return Logger.getLogger(name);
    }
    
    /**
     * 记录DEBUG级别日志
     * @param logger 日志记录器
     * @param message 日志消息
     * @param args 消息参数
     */
    public static void debug(Logger logger, String message, Object... args) {
        if (logger.isDebugEnabled()) {
            logger.debug(formatMessage(message, args));
        }
    }
    
    /**
     * 记录DEBUG级别日志，包含异常信息
     * @param logger 日志记录器
     * @param message 日志消息
     * @param e 异常
     * @param args 消息参数
     */
    public static void debug(Logger logger, String message, Throwable e, Object... args) {
        if (logger.isDebugEnabled()) {
            logger.debug(formatMessage(message, args), e);
        }
    }
    
    /**
     * 记录INFO级别日志
     * @param logger 日志记录器
     * @param message 日志消息
     * @param args 消息参数
     */
    public static void info(Logger logger, String message, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(formatMessage(message, args));
        }
    }
    
    /**
     * 记录INFO级别日志，包含异常信息
     * @param logger 日志记录器
     * @param message 日志消息
     * @param e 异常
     * @param args 消息参数
     */
    public static void info(Logger logger, String message, Throwable e, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(formatMessage(message, args), e);
        }
    }
    
    /**
     * 记录WARN级别日志
     * @param logger 日志记录器
     * @param message 日志消息
     * @param args 消息参数
     */
    public static void warn(Logger logger, String message, Object... args) {
        logger.warn(formatMessage(message, args));
    }
    
    /**
     * 记录WARN级别日志，包含异常信息
     * @param logger 日志记录器
     * @param message 日志消息
     * @param e 异常
     * @param args 消息参数
     */
    public static void warn(Logger logger, String message, Throwable e, Object... args) {
        logger.warn(formatMessage(message, args), e);
    }
    
    /**
     * 记录ERROR级别日志
     * @param logger 日志记录器
     * @param message 日志消息
     * @param args 消息参数
     */
    public static void error(Logger logger, String message, Object... args) {
        logger.error(formatMessage(message, args));
    }
    
    /**
     * 记录ERROR级别日志，包含异常信息
     * @param logger 日志记录器
     * @param message 日志消息
     * @param e 异常
     * @param args 消息参数
     */
    public static void error(Logger logger, String message, Throwable e, Object... args) {
        logger.error(formatMessage(message, args), e);
    }
    
    /**
     * 记录FATAL级别日志
     * @param logger 日志记录器
     * @param message 日志消息
     * @param args 消息参数
     */
    public static void fatal(Logger logger, String message, Object... args) {
        logger.fatal(formatMessage(message, args));
    }
    
    /**
     * 记录FATAL级别日志，包含异常信息
     * @param logger 日志记录器
     * @param message 日志消息
     * @param e 异常
     * @param args 消息参数
     */
    public static void fatal(Logger logger, String message, Throwable e, Object... args) {
        logger.fatal(formatMessage(message, args), e);
    }
    
    /**
     * 记录系统操作日志
     * @param username 操作用户名
     * @param operation 操作内容
     * @param method 请求方法
     * @param params 请求参数
     * @param ip 客户端IP
     */
    public static void recordSystemLog(String username, String operation, String method, String params, String ip) {
        try {
            // 异步记录系统日志，避免影响主流程
            new Thread(() -> {
                systemLogService.saveSystemLog(username, operation, method, params, ip);
            }).start();
        } catch (Exception e) {
            // 记录日志失败时，不影响主流程，只打印异常信息
            Logger logger = getLogger(LogUtil.class);
            logger.error("记录系统日志失败", e);
        }
    }
    
    /**
     * 记录系统操作日志
     * @param request HTTP请求对象
     * @param username 操作用户名
     * @param operation 操作内容
     */
    public static void recordSystemLog(HttpServletRequest request, String username, String operation) {
        if (request == null) {
            return;
        }
        
        String method = request.getMethod();
        String params = request.getQueryString();
        String ip = getClientIp(request);
        
        recordSystemLog(username, operation, method, params, ip);
    }
    
    /**
     * 获取客户端真实IP地址
     * @param request HTTP请求对象
     * @return 客户端真实IP地址
     */
    private static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * 格式化日志消息
     * @param message 日志消息模板
     * @param args 消息参数
     * @return 格式化后的日志消息
     */
    private static String formatMessage(String message, Object... args) {
        if (args == null || args.length == 0) {
            return message;
        }
        return MessageFormat.format(message, args);
    }
}