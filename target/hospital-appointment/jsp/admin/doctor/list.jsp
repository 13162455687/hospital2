<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="zh-CN">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>医生管理 - 医院预约挂号系统</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body>
            <!-- 引入导航栏 -->
            <%@ include file="../../common/header.jsp" %>

            <!-- 主要内容 -->
            <div class="container mt-5">
                <h2 class="mb-4">医生管理</h2>

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

                <!-- 添加医生按钮 -->
                <div class="mb-3">
                    <a href="${pageContext.request.contextPath}/admin/doctor?action=add"
                        class="btn btn-primary">添加医生</a>
                </div>

                <!-- 医生列表表格 -->
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">医生列表</h5>
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>姓名</th>
                                    <th>科室</th>
                                    <th>职称</th>
                                    <th>专长</th>
                                    <th>手机号</th>
                                    <th>状态</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="doctor" items="${doctors}">
                                    <tr>
                                        <td>${doctor.id}</td>
                                        <td>${doctor.user.name}</td>
                                        <td>${doctor.department.deptName}</td>
                                        <td>${doctor.title}</td>
                                        <td>${doctor.specialty}</td>
                                        <td>${doctor.user.phone}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${doctor.status == 1}">
                                                    <span class="text-success">启用</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-danger">禁用</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${doctor.createTime}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/doctor?action=edit&id=${doctor.id}"
                                                class="btn btn-sm btn-primary mr-2">编辑</a>
                                            <a href="${pageContext.request.contextPath}/admin/doctor?action=delete&id=${doctor.id}"
                                                class="btn btn-sm btn-danger"
                                                onclick="return confirm('确定要删除该医生吗？');">删除</a>
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