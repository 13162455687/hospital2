<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>错误测试页面</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    </head>

    <body>
        <div class="container mt-5">
            <h1>错误测试页面</h1>
            <p>点击下面的按钮来触发不同类型的异常，测试详细错误信息显示功能：</p>

            <div class="mt-4">
                <h3>测试1：空指针异常</h3>
                <form action="${pageContext.request.contextPath}/jsp/test/error-test.jsp?test=1" method="post">
                    <button type="submit" class="btn btn-danger">触发空指针异常</button>
                </form>
            </div>

            <div class="mt-4">
                <h3>测试2：自定义异常</h3>
                <form action="${pageContext.request.contextPath}/jsp/test/error-test.jsp?test=2" method="post">
                    <button type="submit" class="btn btn-danger">触发自定义异常</button>
                </form>
            </div>

            <div class="mt-4">
                <h3>测试3：参数非法异常</h3>
                <form action="${pageContext.request.contextPath}/jsp/test/error-test.jsp?test=3" method="post">
                    <button type="submit" class="btn btn-danger">触发参数非法异常</button>
                </form>
            </div>
        </div>

        <%-- 测试代码 --%>
            <% if (request.getParameter("test") !=null) { int testCase=Integer.parseInt(request.getParameter("test"));
                switch (testCase) { case 1: // 测试空指针异常 String nullStr=null; nullStr.length(); break; case 2: // 测试自定义异常
                throw new com.hospital.exception.HospitalException("这是一个自定义异常测试"); break; case 3: // 测试参数非法异常 throw new
                IllegalArgumentException("参数值无效"); break; } } %>
    </body>

    </html>