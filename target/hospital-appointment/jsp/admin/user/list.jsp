<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="zh-CN">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>用户管理 - 医院预约挂号系统</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body>
            <!-- 引入导航栏 -->
            <%@ include file="../../common/header.jsp" %>

            <!-- 主要内容 -->
            <div class="container mt-5">
                <h2 class="mb-4">用户管理</h2>

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

                <!-- 添加用户按钮 -->
                <div class="mb-3">
                    <a href="${pageContext.request.contextPath}/admin/user?action=add" class="btn btn-primary">添加用户</a>
                </div>

                <!-- 用户列表表格 -->
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">用户列表</h5>
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>用户名</th>
                                    <th>真实姓名</th>
                                    <th>手机号</th>
                                    <th>邮箱</th>
                                    <th>角色</th>
                                    <th>状态</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${users}">
                                    <tr>
                                        <td>${user.id}</td>
                                        <td>${user.username}</td>
                                        <td>${user.name}</td>
                                        <td>${user.phone}</td>
                                        <td>${user.email}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.roleId == 1}">管理员</c:when>
                                                <c:when test="${user.roleId == 2}">医生</c:when>
                                                <c:otherwise>患者</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.status == 1}">
                                                    <span class="text-success">启用</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-danger">禁用</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${user.createTime}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/user?action=edit&id=${user.id}"
                                                class="btn btn-sm btn-primary mr-2">编辑</a>
                                            <a href="${pageContext.request.contextPath}/admin/user?action=delete&id=${user.id}"
                                                class="btn btn-sm btn-danger"
                                                onclick="return confirm('确定要删除该用户吗？');">删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- 引入页脚 -->
            <%@ include file="../../common/footer.jsp" %>

            <!-- JavaScript -->
            <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>