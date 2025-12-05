<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>编辑科室 - 医院预约挂号系统</title>
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
                        <li class="nav-item">
                            <a class="nav-link"
                                href="${pageContext.request.contextPath}/admin/user?action=list">用户管理</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                                href="${pageContext.request.contextPath}/admin/doctor?action=list">医生管理</a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link"
                                href="${pageContext.request.contextPath}/admin/department?action=list">科室管理 <span
                                    class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                                href="${pageContext.request.contextPath}/admin/appointment?action=list">预约管理</a>
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
            <h2 class="mb-4">编辑科室</h2>

            <!-- 错误信息显示 -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>

            <!-- 编辑科室表单 -->
            <div class="card">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/department?action=update" method="post">
                        <input type="hidden" name="id" value="${department.id}">
                        <div class="form-group">
                            <label for="name">科室名称</label>
                            <input type="text" class="form-control" id="name" name="name" value="${department.deptName}"
                                required placeholder="请输入科室名称">
                        </div>
                        <div class="form-group">
                            <label for="description">科室描述</label>
                            <textarea class="form-control" id="description" name="description" rows="3"
                                placeholder="请输入科室描述">${department.description}</textarea>
                        </div>
                        <div class="form-group">
                            <label for="imageUrl">科室图片URL</label>
                            <input type="text" class="form-control" id="imageUrl" name="imageUrl" value="${department.imageUrl}"
                                placeholder="请输入科室图片URL">
                        </div>
                        <div class="form-group">
                            <label for="status">状态</label>
                            <select class="form-control" id="status" name="status" required>
                                <option value="1" ${department.status==1 ? 'selected' : '' }>启用</option>
                                <option value="0" ${department.status==0 ? 'selected' : '' }>禁用</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary mr-2">更新</button>
                            <a href="${pageContext.request.contextPath}/admin/department?action=list"
                                class="btn btn-secondary">取消</a>
                        </div>
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