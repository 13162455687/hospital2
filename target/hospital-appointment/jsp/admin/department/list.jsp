<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="zh-CN">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>科室管理 - 医院预约挂号系统</title>
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
                            <li class="nav-item active">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/admin/department?action=list">科室管理 <span
                                        class="sr-only">(current)</span></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/admin/appointment?action=list">预约管理</a>
                            </li>
                            <li class="nav-item">
                                <span class="nav-link text-primary">欢迎，${sessionScope.user.name}</span>
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
                <h2 class="mb-4">科室管理</h2>

                <!-- 错误信息显示 -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>

                <!-- 成功信息显示 -->
                <c:if test="${not empty success}">
                    <div class="alert alert-success" role="alert">
                        ${success}
                    </div>
                </c:if>

                <!-- 添加科室按钮 -->
                <div class="mb-3">
                    <a href="${pageContext.request.contextPath}/admin/department?action=add"
                        class="btn btn-primary">添加科室</a>
                </div>

                <!-- 科室列表表格 -->
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">科室列表</h5>
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>科室名称</th>
                                    <th>科室图片</th>
                                    <th>科室描述</th>
                                    <th>状态</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="department" items="${departments}">
                                    <tr>
                                        <td>${department.id}</td>
                                        <td>${department.deptName}</td>
                                        <td>
                                            <c:if test="${not empty department.imageUrl}">
                                                <img src="${department.imageUrl}" alt="${department.deptName}" style="width: 100px; height: auto;">
                                            </c:if>
                                            <c:if test="${empty department.imageUrl}">
                                                <span class="text-muted">无图片</span>
                                            </c:if>
                                        </td>
                                        <td>${department.description}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${department.status == 1}">
                                                    <span class="text-success">启用</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-danger">禁用</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${department.createTime}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/department?action=edit&id=${department.id}"
                                                class="btn btn-sm btn-primary mr-2">编辑</a>
                                            <a href="${pageContext.request.contextPath}/admin/department?action=delete&id=${department.id}"
                                                class="btn btn-sm btn-danger"
                                                onclick="return confirm('确定要删除该科室吗？');">删除</a>
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