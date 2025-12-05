-- 创建数据库
CREATE DATABASE IF NOT EXISTS hospital_appointment CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE hospital_appointment;

-- 1. 角色表
CREATE TABLE IF NOT EXISTS role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
    description VARCHAR(200) COMMENT '角色描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '角色表';

-- 2. 用户表
CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    name VARCHAR(50) NOT NULL COMMENT '真实姓名',
    phone VARCHAR(20) NOT NULL UNIQUE COMMENT '手机号',
    email VARCHAR(100) UNIQUE COMMENT '邮箱',
    role_id INT NOT NULL COMMENT '角色ID',
    status TINYINT DEFAULT 1 COMMENT '状态：0禁用，1启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (role_id) REFERENCES role (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '用户表';

-- 3. 科室表
CREATE TABLE IF NOT EXISTS department (
    id INT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(100) NOT NULL UNIQUE COMMENT '科室名称',
    description VARCHAR(500) COMMENT '科室描述',
    image_url VARCHAR(200) COMMENT '科室图片路径',
    status TINYINT DEFAULT 1 COMMENT '状态：0禁用，1启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '科室表';

-- 4. 医生表
CREATE TABLE IF NOT EXISTS doctor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE COMMENT '关联用户ID',
    dept_id INT NOT NULL COMMENT '所属科室ID',
    title VARCHAR(50) COMMENT '职称',
    specialty VARCHAR(200) COMMENT '专长',
    avatar VARCHAR(200) COMMENT '头像路径',
    status TINYINT DEFAULT 1 COMMENT '状态：0禁用，1启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (dept_id) REFERENCES department (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '医生表';

-- 5. 排班表
CREATE TABLE IF NOT EXISTS schedule (
    id INT PRIMARY KEY AUTO_INCREMENT,
    doctor_id INT NOT NULL COMMENT '医生ID',
    date DATE NOT NULL COMMENT '排班日期',
    time_slot VARCHAR(50) NOT NULL COMMENT '时间段',
    max_num INT NOT NULL COMMENT '最大预约数',
    current_num INT DEFAULT 0 COMMENT '当前预约数',
    status TINYINT DEFAULT 1 COMMENT '状态：0禁用，1启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (doctor_id) REFERENCES doctor (id),
    UNIQUE KEY uk_doctor_date_time (doctor_id, date, time_slot)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '排班表';

-- 6. 预约表
CREATE TABLE IF NOT EXISTS appointment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL COMMENT '用户ID',
    doctor_id INT NOT NULL COMMENT '医生ID',
    schedule_id INT NOT NULL COMMENT '排班ID',
    appointment_date DATE NOT NULL COMMENT '预约日期',
    time_slot VARCHAR(50) NOT NULL COMMENT '时间段',
    status TINYINT DEFAULT 1 COMMENT '状态：1待就诊，2已就诊，3已取消，4已过期',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (doctor_id) REFERENCES doctor (id),
    FOREIGN KEY (schedule_id) REFERENCES schedule (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '预约表';

-- 7. 挂号表
CREATE TABLE IF NOT EXISTS registration (
    id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_id INT NOT NULL COMMENT '预约ID',
    registration_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '挂号时间',
    cost DECIMAL(10, 2) NOT NULL COMMENT '挂号费用',
    payment_status TINYINT DEFAULT 0 COMMENT '支付状态：0未支付，1已支付',
    doctor_advice TEXT COMMENT '医生建议',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (appointment_id) REFERENCES appointment (id) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '挂号表';

-- 8. 系统日志表
CREATE TABLE IF NOT EXISTS system_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL COMMENT '操作用户',
    operation VARCHAR(200) NOT NULL COMMENT '操作内容',
    method VARCHAR(100) COMMENT '请求方法',
    params TEXT COMMENT '请求参数',
    ip VARCHAR(50) COMMENT '客户端IP',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '系统日志表';

-- 9. 系统设置表
CREATE TABLE IF NOT EXISTS system_setting (
    id INT PRIMARY KEY AUTO_INCREMENT,
    system_name VARCHAR(100) NOT NULL COMMENT '系统名称',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    contact_email VARCHAR(100) COMMENT '联系邮箱',
    address VARCHAR(200) COMMENT '医院地址',
    copyright VARCHAR(200) COMMENT '版权信息',
    logo_path VARCHAR(200) COMMENT 'Logo路径',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '系统设置表';

-- 初始化系统设置数据
INSERT INTO system_setting (
    system_name,
    contact_phone,
    contact_email,
    address,
    copyright
) VALUES (
    '医院预约挂号系统',
    '13800138000',
    'contact@hospital.com',
    '北京市朝阳区医院路123号',
    '© 2025 医院预约挂号系统. All rights reserved.'
);

-- 初始化角色数据
INSERT INTO
    role (role_name, description)
VALUES ('admin', '系统管理员'),
    ('doctor', '医生'),
    ('patient', '患者');

-- 初始化管理员用户（密码：admin123）
INSERT INTO
    user (
        username,
        password,
        name,
        phone,
        email,
        role_id,
        status
    )
VALUES (
        'admin',
        'e10adc3949ba59abbe56e057f20f883e',
        '管理员',
        '13800138000',
        'admin@example.com',
        1,
        1
    );

-- 初始化科室数据
INSERT INTO
    department (dept_name, description)
VALUES ('内科', '提供心血管、呼吸、消化等内科疾病的诊疗服务'),
    (
        '外科',
        '提供普外科、骨科、心胸外科等外科疾病的诊疗服务'
    ),
    ('妇产科', '提供妇科疾病诊疗、产前检查、分娩等服务'),
    ('儿科', '提供儿童常见疾病的诊疗和预防保健服务'),
    ('眼科', '提供眼科疾病的诊疗服务'),
    ('耳鼻喉科', '提供耳鼻喉科疾病的诊疗服务'),
    ('口腔科', '提供口腔科疾病的诊疗服务'),
    ('皮肤科', '提供皮肤科疾病的诊疗服务');

-- 初始化医生用户（密码：doctor123）
INSERT INTO
    user (
        username,
        password,
        name,
        phone,
        email,
        role_id,
        status
    )
VALUES 
    ('doctor1', 'e10adc3949ba59abbe56e057f20f883e', '张医生', '13900139001', 'doctor1@example.com', 2, 1),
    ('doctor2', 'e10adc3949ba59abbe56e057f20f883e', '李医生', '13900139002', 'doctor2@example.com', 2, 1),
    ('doctor3', 'e10adc3949ba59abbe56e057f20f883e', '王医生', '13900139003', 'doctor3@example.com', 2, 1),
    ('doctor4', 'e10adc3949ba59abbe56e057f20f883e', '赵医生', '13900139004', 'doctor4@example.com', 2, 1),
    ('doctor5', 'e10adc3949ba59abbe56e057f20f883e', '刘医生', '13900139005', 'doctor5@example.com', 2, 1),
    ('doctor6', 'e10adc3949ba59abbe56e057f20f883e', '陈医生', '13900139006', 'doctor6@example.com', 2, 1),
    ('doctor7', 'e10adc3949ba59abbe56e057f20f883e', '杨医生', '13900139007', 'doctor7@example.com', 2, 1),
    ('doctor8', 'e10adc3949ba59abbe56e057f20f883e', '黄医生', '13900139008', 'doctor8@example.com', 2, 1);

-- 初始化医生数据
INSERT INTO
    doctor (
        user_id,
        dept_id,
        title,
        specialty,
        status
    )
VALUES 
    (2, 1, '主任医师', '心血管疾病', 1),
    (3, 1, '副主任医师', '呼吸系统疾病', 1),
    (4, 2, '主任医师', '普外科', 1),
    (5, 2, '副主任医师', '骨科', 1),
    (6, 3, '主任医师', '妇产科', 1),
    (7, 4, '副主任医师', '儿科', 1),
    (8, 5, '主任医师', '眼科', 1),
    (9, 6, '副主任医师', '耳鼻喉科', 1);