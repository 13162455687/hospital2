package com.hospital.exception;

/**
 * 医院系统自定义异常类
 */
public class HospitalException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private Integer code;
    private String message;
    
    /**
     * 无参构造方法
     */
    public HospitalException() {
        super();
    }
    
    /**
     * 带错误信息的构造方法
     * @param message 错误信息
     */
    public HospitalException(String message) {
        super(message);
        this.message = message;
    }
    
    /**
     * 带错误码和错误信息的构造方法
     * @param code 错误码
     * @param message 错误信息
     */
    public HospitalException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    /**
     * 带错误信息和异常的构造方法
     * @param message 错误信息
     * @param cause 异常
     */
    public HospitalException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
    
    /**
     * 带错误码、错误信息和异常的构造方法
     * @param code 错误码
     * @param message 错误信息
     * @param cause 异常
     */
    public HospitalException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
    
    // getter和setter方法
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}