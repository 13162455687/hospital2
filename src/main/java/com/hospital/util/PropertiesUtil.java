package com.hospital.util;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件工具类
 */
public class PropertiesUtil {
    private static final Logger logger = Logger.getLogger(PropertiesUtil.class);
    private static Properties properties = new Properties();

    /**
     * 加载属性文件
     * 
     * @param fileName 属性文件名
     */
    public static void loadProperties(String fileName) {
        try {
            InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(inputStream);
            logger.info("加载属性文件成功：" + fileName);
        } catch (Exception e) {
            logger.error("加载属性文件失败：" + fileName, e);
            throw new RuntimeException("加载属性文件失败：" + fileName, e);
        }
    }

    /**
     * 获取字符串属性值
     * 
     * @param key 属性键
     * @return 属性值
     */
    public static String getString(String key) {
        return properties.getProperty(key);
    }

    /**
     * 获取字符串属性值，如果不存在则返回默认值
     * 
     * @param key          属性键
     * @param defaultValue 默认值
     * @return 属性值
     */
    public static String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * 获取整数属性值
     * 
     * @param key 属性键
     * @return 属性值
     */
    public static int getInt(String key) {
        String value = properties.getProperty(key);
        return Integer.parseInt(value);
    }

    /**
     * 获取整数属性值，如果不存在则返回默认值
     * 
     * @param key          属性键
     * @param defaultValue 默认值
     * @return 属性值
     */
    public static int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    /**
     * 获取布尔属性值
     * 
     * @param key 属性键
     * @return 属性值
     */
    public static boolean getBoolean(String key) {
        String value = properties.getProperty(key);
        return Boolean.parseBoolean(value);
    }

    /**
     * 获取布尔属性值，如果不存在则返回默认值
     * 
     * @param key          属性键
     * @param defaultValue 默认值
     * @return 属性值
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
}