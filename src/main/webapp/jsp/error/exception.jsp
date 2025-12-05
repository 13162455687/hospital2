<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>系统异常</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <!-- 引入导航栏 -->
        <%@ include file="../common/header.jsp" %>

        <!-- 错误内容 -->
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8 text-center">
                    <h1 class="display-1 text-warning">⚠️</h1>
                    <h2 class="mb-4">系统异常</h2>
                    <p class="lead mb-5">抱歉，系统发生了异常，请稍后重试。</p>
                    
                    <!-- 详细错误信息 -->
                    <div class="text-left mt-5 p-4 bg-light border rounded">
                        <h4>详细错误信息：</h4>
                        <pre class="text-danger" style="font-size: 14px; white-space: pre-wrap; word-wrap: break-word;">
                            <% 
                                Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
                                if (throwable != null) {
                                    throwable.printStackTrace(new java.io.PrintWriter(out));
                                } else {
                                    out.println("无法获取详细错误信息");
                                }
                            %>
                        </pre>
                    </div>
                    
                    <a href="${pageContext.request.contextPath}/jsp/index.jsp" class="btn btn-primary btn-lg mt-5">返回首页</a>
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