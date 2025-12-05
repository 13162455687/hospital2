<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>编辑用户 - 医院预约挂号系统</title>
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
                            <a class="nav-link" href="${pageContext.request.contextPath}/jsp/admin/index.jsp">管理员首页</a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/jsp/admin/user/index.jsp">用户管理
                                <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                                href="${pageContext.request.contextPath}/jsp/admin/doctor/index.jsp">医生管理</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                                href="${pageContext.request.contextPath}/jsp/admin/department/index.jsp">科室管理</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                                href="${pageContext.request.contextPath}/jsp/admin/appointment/index.jsp">预约管理</a>
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
            <h2 class="mb-4">编辑用户</h2>

            <!-- 错误信息显示 -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>

            <!-- 编辑用户表单 -->
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">用户信息</h5>
                    <form action="${pageContext.request.contextPath}/admin/user?action=update" method="post">
                        <input type="hidden" name="id" value="${user.id}">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="username" class="form-label">用户名</label>
                                <input type="text" class="form-control" id="username" name="username"
                                    value="${user.username}" readonly>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="password" class="form-label">密码 (留空则不修改)</label>
                                <input type="password" class="form-control" id="password" name="password">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="name" class="form-label">真实姓名</label>
                                <input type="text" class="form-control" id="name" name="name" value="${user.name}"
                                    required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="phone" class="form-label">手机号</label>
                                <input type="tel" class="form-control" id="phone" name="phone" value="${user.phone}"
                                    required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="email" class="form-label">邮箱</label>
                                <input type="email" class="form-control" id="email" name="email" value="${user.email}">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="roleId" class="form-label">角色</label>
                                <select class="form-control" id="roleId" name="roleId" required>
                                    <option value="1" ${user.roleId==1 ? 'selected' : '' }>管理员</option>
                                    <option value="2" ${user.roleId==2 ? 'selected' : '' }>医生</option>
                                    <option value="3" ${user.roleId==3 ? 'selected' : '' }>患者</option>
                                </select>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="status" class="form-label">状态</label>
                            <select class="form-control" id="status" name="status" required>
                                <option value="1" ${user.status==1 ? 'selected' : '' }>启用</option>
                                <option value="0" ${user.status==0 ? 'selected' : '' }>禁用</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">更新用户</button>
                        <a href="${pageContext.request.contextPath}/admin/user?action=list"
                            class="btn btn-secondary ml-2">取消</a>
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
    </body>

    </html>