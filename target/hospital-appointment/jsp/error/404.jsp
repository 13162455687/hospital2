<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>页面未找到 - 404</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <!-- 引入导航栏 -->
        <%@ include file="../common/header.jsp" %>

        <!-- 错误内容 -->
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-6 text-center">
                    <h1 class="display-1 text-primary">404</h1>
                    <h2 class="mb-4">页面未找到</h2>
                    <p class="lead mb-5">抱歉，您访问的页面不存在或已被删除。</p>
                    <a href="../index.jsp" class="btn btn-primary btn-lg">返回首页</a>
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