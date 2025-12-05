<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <!-- 引入导航栏 -->
    <%@ include file="../common/header.jsp" %>

    <!-- 登录表单 -->
    <div class="container">
        <div class="form-container">
            <h2 class="text-center mb-4">用户登录</h2>

            <!-- 成功信息显示 -->
            <% if (request.getAttribute("success") !=null) {%>
                <div class="alert alert-success" role="alert">
                    <%= request.getAttribute("success") %>
                </div>
            <% } %>

            <!-- 错误信息显示 -->
            <% if (request.getAttribute("error") !=null) {%>
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <form action="<%= request.getContextPath() %>/login" method="post">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" class="form-control" id="username" name="username"
                        placeholder="请输入用户名" required>
                </div>
                <div class="form-group">
                    <label for="password">密码</label>
                    <input type="password" class="form-control" id="password" name="password"
                        placeholder="请输入密码" required>
                </div>
                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" id="remember">
                    <label class="form-check-label" for="remember">记住我</label>
                </div>
                <button type="submit" class="btn btn-primary btn-block">登录</button>
            </form>

            <div class="text-center mt-3">
                <p>还没有账号？<a href="${pageContext.request.contextPath}/jsp/user/register.jsp">立即注册</a></p>
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