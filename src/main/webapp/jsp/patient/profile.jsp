<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>个人信息 - 医院预约挂号系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <!-- 引入导航栏 -->
        <%@ include file="../common/header.jsp" %>

    <!-- 主要内容 -->
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-8 offset-md-2">
                <h2 class="mb-4">个人信息</h2>
                
                <!-- 错误信息显示 -->
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                <% } %>
                
                <!-- 成功信息显示 -->
                <% if (request.getAttribute("success") != null) { %>
                    <div class="alert alert-success" role="alert">
                        ${success}
                    </div>
                <% } %>
                
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">修改个人信息</h5>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/patient/profile/save" method="post">
                            <div class="form-group">
                                <label for="username">用户名</label>
                                <input type="text" class="form-control" id="username" value="${sessionScope.user.username}" disabled>
                                <small class="form-text text-muted">用户名不可修改</small>
                            </div>
                            
                            <div class="form-group">
                                <label for="name">真实姓名 <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="name" name="name" value="${sessionScope.user.name}" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="phone">手机号 <span class="text-danger">*</span></label>
                                <input type="tel" class="form-control" id="phone" name="phone" value="${sessionScope.user.phone}" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="email">邮箱</label>
                                <input type="email" class="form-control" id="email" name="email" value="${sessionScope.user.email}">
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="role">用户角色</label>
                                        <input type="text" class="form-control" id="role" value="${sessionScope.user.roleId == 1 ? '管理员' : sessionScope.user.roleId == 2 ? '医生' : '患者'}" disabled>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="status">账号状态</label>
                                        <input type="text" class="form-control" id="status" value="${sessionScope.user.status == 1 ? '启用' : '禁用'}" disabled>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="createTime">注册时间</label>
                                        <input type="text" class="form-control" id="createTime" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sessionScope.user.getCreateTime()) %>" disabled>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="updateTime">更新时间</label>
                                        <input type="text" class="form-control" id="updateTime" value="<%= sessionScope.user.getUpdateTime() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sessionScope.user.getUpdateTime()) : '' %>" disabled>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="mt-4 d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/jsp/patient/index.jsp" class="btn btn-secondary">返回患者首页</a>
                                <button type="submit" class="btn btn-primary">保存修改</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 引入页脚 -->
        <%@ include file="../common/footer.jsp" %>

        <!-- JavaScript -->
        <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>