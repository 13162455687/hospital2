<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>
            <%= request.getAttribute("title") !=null ? request.getAttribute("title") : "医院预约挂号系统" %>
        </title>
        <!-- CSS引用 -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <!-- 引入导航栏 -->
        <%@ include file="header.jsp" %>

            <!-- 主要内容区域，由具体页面填充 -->
            <div class="container mt-4 mb-4">
                <jsp:doBody />
            </div>

            <!-- 引入页脚 -->
            <%@ include file="footer.jsp" %>

                <!-- JavaScript引用 -->
                <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
                <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
                <script src="https://kit.fontawesome.com/a076d05399.js"></script>
    </body>

    </html>