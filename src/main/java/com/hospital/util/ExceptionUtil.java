package com.hospital.util;

import com.hospital.exception.HospitalException;
import org.apache.log4j.Logger;

/**
 * 异常处理工具类
 */
public class ExceptionUtil {
    private static final Logger logger = Logger.getLogger(ExceptionUtil.class);
    
    /**
     * 处理系统异常
     * @param e 异常
     * @return 详细的错误信息
     */
    public static String handleException(Exception e) {
        logger.error("系统异常", e);
        
        // 构建详细错误信息，包括异常类型和堆栈
        StringBuilder errorMsg = new StringBuilder();
        
        // 判断异常类型
        if (e instanceof HospitalException) {
            // 自定义异常，直接返回异常信息
            errorMsg.append(e.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            // 参数非法异常，返回参数错误信息
            errorMsg.append("参数错误：").append(e.getMessage());
        } else if (e instanceof NullPointerException) {
            // 空指针异常，返回系统错误信息
            errorMsg.append("系统错误：发生空指针异常");
        } else {
            // 其他异常，返回详细错误信息
            errorMsg.append("系统错误：").append(e.getMessage());
        }
        
        // 添加完整堆栈信息
        errorMsg.append("\n\n堆栈信息：\n");
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        e.printStackTrace(pw);
        errorMsg.append(sw.toString());
        
        return errorMsg.toString();
    }
    
    /**
     * 处理系统异常并记录日志
     * @param e 异常
     * @param operation 操作描述
     * @return 详细的错误信息
     */
    public static String handleException(Exception e, String operation) {
        logger.error(operation + "失败", e);
        
        // 构建详细错误信息，包括异常类型和堆栈
        StringBuilder errorMsg = new StringBuilder();
        errorMsg.append(operation).append("失败：");
        
        // 判断异常类型
        if (e instanceof HospitalException) {
            // 自定义异常，直接返回异常信息
            errorMsg.append(e.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            // 参数非法异常，返回参数错误信息
            errorMsg.append("参数错误，").append(e.getMessage());
        } else if (e instanceof NullPointerException) {
            // 空指针异常，返回系统错误信息
            errorMsg.append("系统发生空指针异常");
        } else {
            // 其他异常，返回详细错误信息
            errorMsg.append("系统错误，").append(e.getMessage());
        }
        
        // 添加完整堆栈信息
        errorMsg.append("\n\n堆栈信息：\n");
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        e.printStackTrace(pw);
        errorMsg.append(sw.toString());
        
        return errorMsg.toString();
    }
    
    /**
     * 抛出业务异常
     * @param message 错误信息
     */
    public static void throwBusinessException(String message) {
        throw new HospitalException(message);
    }
    
    /**
     * 抛出带错误码的业务异常
     * @param code 错误码
     * @param message 错误信息
     */
    public static void throwBusinessException(Integer code, String message) {
        throw new HospitalException(code, message);
    }
    
    /**
     * 抛出带异常的业务异常
     * @param message 错误信息
     * @param cause 异常
     */
    public static void throwBusinessException(String message, Throwable cause) {
        throw new HospitalException(message, cause);
    }
    
    /**
     * 抛出带错误码和异常的业务异常
     * @param code 错误码
     * @param message 错误信息
     * @param cause 异常
     */
    public static void throwBusinessException(Integer code, String message, Throwable cause) {
        throw new HospitalException(code, message, cause);
    }
}