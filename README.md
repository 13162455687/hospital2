# 医院预约挂号系统

## 项目概述

医院预约挂号系统是一个基于Java Web技术开发的医疗服务平台，旨在为患者提供便捷的在线预约挂号服务，同时为医院管理人员和医生提供高效的管理工具。

## 技术栈

- **开发语言**：Java 8
- **Web框架**：Servlet 3.1 + JSP 2.3 + JSTL 1.2
- **数据库**：MySQL 8.0
- **连接池**：Druid 1.2.8
- **日志框架**：Log4j 1.2.17
- **前端技术**：HTML5 + CSS3 + JavaScript + Bootstrap 4
- **构建工具**：Maven 3
- **服务器**：Tomcat 7

## 功能模块

### 1. 用户模块
- 用户注册、登录、注销
- 用户信息管理
- 角色管理（管理员、医生、患者）

### 2. 科室管理
- 科室信息维护
- 科室列表展示

### 3. 医生模块
- 医生信息管理
- 医生排班管理
- 医生详情展示

### 4. 预约模块
- 患者在线预约
- 预约记录查询
- 预约状态管理

### 5. 挂号模块
- 挂号信息管理
- 挂号费用管理
- 支付状态管理

### 6. 管理员模块
- 系统用户管理
- 科室管理
- 医生管理
- 预约管理
- 挂号管理
- 系统日志管理
- 系统设置管理

## 系统架构

系统采用经典的三层架构设计：

1. **表现层**：JSP页面 + Servlet控制器
2. **业务逻辑层**：Service接口及实现类
3. **数据访问层**：DAO接口及实现类
4. **数据层**：MySQL数据库

## 数据库设计

### 主要表结构

1. **role** - 角色表
2. **user** - 用户表
3. **department** - 科室表
4. **doctor** - 医生表
5. **schedule** - 排班表
6. **appointment** - 预约表
7. **registration** - 挂号表
8. **system_log** - 系统日志表
9. **system_setting** - 系统设置表

### 数据库连接配置

- 驱动：com.mysql.cj.jdbc.Driver
- URL：jdbc:mysql://localhost:3306/hospital_appointment?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC
- 用户名：root
- 密码：root

## 部署和运行

### 环境要求

- JDK 8+
- Maven 3+
- MySQL 8.0+
- Tomcat 7+

### 部署步骤

1. **创建数据库**
   ```sql
   CREATE DATABASE hospital_appointment CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. **执行初始化脚本**
   - 执行 `src/main/resources/schema.sql` 文件，创建表结构并初始化数据

3. **配置数据库连接**
   - 修改 `src/main/resources/db.properties` 文件，配置数据库连接信息

4. **构建项目**
   ```bash
   mvn clean package
   ```

5. **部署到Tomcat**
   - 将生成的 `target/hospital-appointment.war` 文件复制到Tomcat的 `webapps` 目录下
   - 启动Tomcat服务器

6. **访问系统**
   - 浏览器访问：http://localhost:8080/hospital

## 目录结构

```
hospital-appointment/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── hospital/
│   │   │           ├── common/          # 公共工具类
│   │   │           ├── dao/             # 数据访问接口
│   │   │           │   └── impl/        # 数据访问实现
│   │   │           ├── entity/          # 实体类
│   │   │           ├── exception/       # 自定义异常
│   │   │           ├── filter/          # 过滤器
│   │   │           ├── service/         # 业务逻辑接口
│   │   │           │   └── impl/        # 业务逻辑实现
│   │   │           └── servlet/         # Servlet控制器
│   │   ├── resources/                   # 配置文件
│   │   │   ├── db.properties            # 数据库配置
│   │   │   ├── log4j.properties         # 日志配置
│   │   │   └── schema.sql               # 数据库初始化脚本
│   │   └── webapp/                      # Web资源
│   │       ├── css/                     # CSS样式
│   │       ├── images/                  # 图片资源
│   │       ├── js/                      # JavaScript文件
│   │       ├── jsp/                     # JSP页面
│   │       │   ├── admin/               # 管理员页面
│   │       │   ├── appointment/         # 预约页面
│   │       │   ├── common/              # 公共页面
│   │       │   ├── doctor/              # 医生页面
│   │       │   ├── error/               # 错误页面
│   │       │   ├── patient/             # 患者页面
│   │       │   ├── test/                # 测试页面
│   │       │   └── user/                # 用户页面
│   │       └── WEB-INF/                 # Web配置
│   │           └── web.xml              # Web应用配置
│   └── test/                            # 测试代码
├── target/                              # 构建输出目录
└── pom.xml                              # Maven配置文件
```

## 初始数据

### 角色信息
| 角色ID | 角色名称 | 描述 |
| --- | --- | --- |
| 1 | admin | 系统管理员 |
| 2 | doctor | 医生 |
| 3 | patient | 患者 |

### 管理员账号
- 用户名：admin
- 密码：admin123

### 医生账号
- 用户名：doctor1 ~ doctor8
- 密码：doctor123

### 初始科室
- 内科、外科、妇产科、儿科、眼科、耳鼻喉科、口腔科、皮肤科

## 注意事项

1. **数据库配置**：使用前请根据实际环境修改 `db.properties` 文件中的数据库连接信息
2. **Tomcat配置**：建议使用Tomcat 7，端口号为8081（可在pom.xml中修改）
3. **初始密码**：系统默认密码为明文的MD5加密，实际部署时请修改为强密码
4. **日志配置**：日志文件默认输出到Tomcat的logs目录下
5. **安全性**：生产环境中请关闭不必要的调试信息，加强用户认证和授权机制

## 系统功能流程图

1. **患者预约流程**：
   - 用户注册/登录 → 选择科室 → 选择医生 → 选择排班 → 提交预约 → 预约成功

2. **医生接诊流程**：
   - 医生登录 → 查看预约列表 → 接诊患者 → 填写诊断意见 → 完成诊疗

3. **管理员管理流程**：
   - 管理员登录 → 系统管理 → 用户/科室/医生/预约/挂号管理 → 执行相应操作 → 操作记录日志

## 开发规范

1. **命名规范**：
   - 类名：首字母大写的驼峰命名法
   - 方法名：首字母小写的驼峰命名法
   - 变量名：首字母小写的驼峰命名法
   - 常量名：全部大写，下划线分隔

2. **代码格式**：
   - 缩进：4个空格
   - 行宽：不超过120个字符
   - 方法：不超过50行

3. **注释规范**：
   - 类注释：使用Javadoc格式，说明类的功能和作者
   - 方法注释：使用Javadoc格式，说明方法的功能、参数、返回值和异常
   - 关键代码注释：对复杂逻辑进行说明

## 维护与更新

- **版本更新**：定期更新系统功能和修复bug
- **安全更新**：及时修复安全漏洞，更新依赖库
- **性能优化**：根据系统运行情况，优化数据库查询和代码性能

## 联系方式

- 系统名称：医院预约挂号系统
- 联系电话：13800138000
- 联系邮箱：contact@hospital.com
- 医院地址：北京市朝阳区医院路123号

## 版权信息

© 2025 医院预约挂号系统. All rights reserved.
