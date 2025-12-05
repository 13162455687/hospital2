<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="zh-CN">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>预约管理 - 医院预约挂号系统</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body>
            <!-- 导航栏 -->
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/jsp/index.jsp">医院预约挂号系统</a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/jsp/index.jsp">网站首页</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/jsp/admin/index.jsp">管理员首页</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/admin/user?action=list">用户管理</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/admin/doctor?action=list">医生管理</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/admin/department?action=list">科室管理</a>
                            </li>
                            <li class="nav-item active">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/admin/appointment?action=list">预约管理 <span
                                        class="sr-only">(current)</span></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/logout">退出登录</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <!-- 主要内容 -->
            <div class="container mt-5">
                <h2 class="mb-4">预约管理</h2>

                <!-- 错误信息显示 -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>

                <!-- 预约列表表格 -->
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">预约列表</h5>
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>患者姓名</th>
                                    <th>患者手机</th>
                                    <th>医生姓名</th>
                                    <th>医生职称</th>
                                    <th>预约日期</th>
                                    <th>时间段</th>
                                    <th>状态</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="appointment" items="${appointments}">
                                    <tr>
                                        <td>${appointment.id}</td>
                                        <td>${appointment.user.name}</td>
                                        <td>${appointment.user.phone}</td>
                                        <td>${appointment.doctor.name}</td>
                                        <td>${appointment.doctor.title}</td>
                                        <td>${appointment.appointmentDate}</td>
                                        <td>${appointment.timeSlot}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${appointment.status == 1}">
                                                    <span class="text-warning">待就诊</span>
                                                </c:when>
                                                <c:when test="${appointment.status == 2}">
                                                    <span class="text-success">已就诊</span>
                                                </c:when>
                                                <c:when test="${appointment.status == 3}">
                                                    <span class="text-danger">已取消</span>
                                                </c:when>
                                                <c:when test="${appointment.status == 4}">
                                                    <span class="text-secondary">已过期</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">未知</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${appointment.createTime}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/appointment?action=edit&id=${appointment.id}"
                                                class="btn btn-sm btn-primary mr-2">编辑</a>
                                            <a href="${pageContext.request.contextPath}/admin/appointment?action=delete&id=${appointment.id}"
                                                class="btn btn-sm btn-danger"
                                                onclick="return confirm('确定要删除该预约吗？');">删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
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
        </body>

        </html>