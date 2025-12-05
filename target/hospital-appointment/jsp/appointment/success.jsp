<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>预约成功 - 医院预约挂号系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- 导航栏 -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container">
            <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/jsp/index.jsp">
                <div class="hospital-logo me-3">🏥</div>
                <span>医院预约挂号系统</span>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/jsp/index.jsp">首页</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/jsp/doctor/list.jsp">医生列表</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/jsp/doctor/list.jsp">预约挂号</a>
                    </li>
                    <% 
                        // 检查用户是否登录
                        if (session == null || session.getAttribute("user") == null) {
                    %>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/jsp/user/login.jsp">登录</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/jsp/user/register.jsp">注册</a>
                    </li>
                    <% 
                        } else {
                            // 登录状态，显示用户信息
                            com.hospital.entity.User user = (com.hospital.entity.User) session.getAttribute("user");
                    %>
                    <li class="nav-item">
                        <span class="nav-link text-primary">欢迎，<%= user.getName() %></span>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">退出登录</a>
                    </li>
                    <% 
                        }
                    %>
                </ul>
            </div>
        </div>
    </nav>

    <!-- 主要内容 -->
        <div class="container mt-5">
            <% // 检查用户是否登录 if (session==null || session.getAttribute("user")==null) { %>
                <div class="alert alert-warning text-center p-5" role="alert">
                    <h4 class="alert-heading">请先登录！</h4>
                    <p>预约成功页面需要登录后才能访问，请先登录您的账号。</p>
                    <hr>
                    <div class="d-flex justify-content-center">
                        <a href="${pageContext.request.contextPath}/jsp/user/login.jsp"
                            class="btn btn-primary btn-lg mx-2">立即登录</a>
                        <a href="${pageContext.request.contextPath}/jsp/user/register.jsp"
                            class="btn btn-secondary btn-lg mx-2">注册账号</a>
                    </div>
                </div>
            <% } else { %>
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card text-center">
                        <div class="card-body">
                            <div class="mb-4">
                                <h1 class="text-success">🎉</h1>
                            </div>
                            <h2 class="card-title mb-4">预约成功！</h2>
                        <p class="card-text text-muted mb-4">感谢您使用医院预约挂号系统，您的预约已成功提交。</p>
                        
                        <div class="alert alert-success" role="alert">
                            ${success}
                        </div>
                        
                        <div class="mt-5">
                            <h4>预约信息</h4>
                            <table class="table table-bordered mt-3">
                                <tbody>
                                    <tr>
                                        <th scope="row" class="text-right">预约ID</th>
                                        <td>${appointment.id}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="text-right">患者姓名</th>
                                        <td>${sessionScope.user.name}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="text-right">预约日期</th>
                                        <td>
                                            ${appointment.appointmentDate}
                                        </td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="text-right">时间段</th>
                                        <td>${appointment.timeSlot}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="text-right">医生</th>
                                        <td>${appointment.doctor.name} - ${appointment.doctor.title}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row" class="text-right">预约状态</th>
                                        <td>
                                            <c:choose>
                                                <c:when test="${appointment.status == 1}">待就诊</c:when>
                                                <c:when test="${appointment.status == 2}">已就诊</c:when>
                                                <c:when test="${appointment.status == 3}">已取消</c:when>
                                                <c:when test="${appointment.status == 4}">已过期</c:when>
                                                <c:otherwise>未知状态</c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        
                        <div class="mt-5">
                            <h4>就诊须知</h4>
                            <ul class="list-unstyled text-left mt-3">
                                <li class="mb-2">1. 请携带有效身份证件，提前30分钟到达医院</li>
                                <li class="mb-2">2. 如有特殊情况无法就诊，请提前24小时取消预约</li>
                                <li class="mb-2">3. 就诊时请配合医护人员的安排</li>
                                <li class="mb-2">4. 如需更改预约信息，请登录个人中心操作</li>
                            </ul>
                        </div>
                        
                        <div class="mt-5 d-flex justify-content-center gap-3">
                            <a href="${pageContext.request.contextPath}/jsp/patient/index.jsp" class="btn btn-primary btn-lg">查看我的预约</a>
                            <a href="${pageContext.request.contextPath}/jsp/index.jsp" class="btn btn-secondary btn-lg">返回首页</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 页脚 -->
    <footer class="footer bg-light py-4 mt-5">
        <div class="container text-center">
            <p class="mb-0">© 2025 医院预约挂号系统. All rights reserved.</p>
        </div>
    </footer>

    <!-- JavaScript -->
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
    <% } %>
</body>
</html>