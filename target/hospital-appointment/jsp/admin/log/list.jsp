<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="zh-CN">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>系统日志 - 医院预约挂号系统</title>
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
                            <li class="nav-item">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/admin/appointment?action=list">预约管理</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/admin/schedule?action=list">排班管理</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/admin/registration?action=list">挂号管理</a>
                            </li>
                            <li class="nav-item active">
                                <a class="nav-link" href="${pageContext.request.contextPath}/admin/log?action=list">系统日志
                                    <span class="sr-only">(current)</span></a>
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
                <h2 class="mb-4">系统日志</h2>

                <!-- 错误信息显示 -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>

                <!-- 系统日志列表表格 -->
                <div class="card">
                    <div class="card-body">
                        <form id="logForm" action="${pageContext.request.contextPath}/admin/log?action=deleteBatch"
                            method="post">
                            <div class="mb-3">
                                <button type="submit" class="btn btn-danger"
                                    onclick="return confirm('确定要批量删除选中的日志吗？');">批量删除</button>
                            </div>
                            <table class="table table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th><input type="checkbox" id="selectAll" onchange="selectAllCheckboxes()"></th>
                                        <th>ID</th>
                                        <th>操作用户</th>
                                        <th>操作内容</th>
                                        <th>请求方法</th>
                                        <th>请求参数</th>
                                        <th>客户端IP</th>
                                        <th>创建时间</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="log" items="${systemLogs}">
                                        <tr>
                                            <td><input type="checkbox" name="ids" value="${log.id}"
                                                    class="checkbox-item"></td>
                                            <td>${log.id}</td>
                                            <td>${log.username}</td>
                                            <td>${log.operation}</td>
                                            <td>${log.method}</td>
                                            <td>${log.params}</td>
                                            <td>${log.ip}</td>
                                            <td>${log.createTime}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/admin/log?action=delete&id=${log.id}"
                                                    class="btn btn-sm btn-danger"
                                                    onclick="return confirm('确定要删除该日志吗？');">删除</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </form>
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
            <script>
                // 全选/取消全选功能
                function selectAllCheckboxes() {
                    var selectAll = document.getElementById('selectAll');
                    var checkboxes = document.getElementsByClassName('checkbox-item');
                    for (var i = 0; i < checkboxes.length; i++) {
                        checkboxes[i].checked = selectAll.checked;
                    }
                }
            </script>
        </body>

        </html>