<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>编辑挂号 - 医院预约挂号系统</title>
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
                        <li class="nav-item active">
                            <a class="nav-link"
                                href="${pageContext.request.contextPath}/admin/registration?action=list">挂号管理 <span
                                    class="sr-only">(current)</span></a>
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
            <h2 class="mb-4">编辑挂号</h2>

            <!-- 错误信息显示 -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>

            <!-- 编辑挂号表单 -->
            <div class="card">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/registration?action=update" method="post">
                        <input type="hidden" name="id" value="${registration.id}">

                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>患者信息</label>
                                    <div class="p-3 bg-light border rounded">
                                        <p class="mb-0">姓名：${registration.appointment.user.name}</p>
                                        <p class="mb-0">手机：${registration.appointment.user.phone}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>医生信息</label>
                                    <div class="p-3 bg-light border rounded">
                                        <p class="mb-0">姓名：${registration.appointment.doctor.name}</p>
                                        <p class="mb-0">职称：${registration.appointment.doctor.title}</p>
                                        <p class="mb-0">专长：${registration.appointment.doctor.specialty}</p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="appointmentDate">预约日期</label>
                                    <input type="text" class="form-control" id="appointmentDate"
                                        value="${registration.appointment.appointmentDate}" readonly>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="timeSlot">时间段</label>
                                    <input type="text" class="form-control" id="timeSlot"
                                        value="${registration.appointment.timeSlot}" readonly>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="cost">费用</label>
                            <input type="number" step="0.01" class="form-control" id="cost" name="cost"
                                value="${registration.cost}" required placeholder="请输入费用">
                        </div>
                        <div class="form-group">
                            <label for="paymentStatus">支付状态</label>
                            <select class="form-control" id="paymentStatus" name="paymentStatus" required>
                                <option value="0" ${registration.paymentStatus==0 ? 'selected' : '' }>未支付</option>
                                <option value="1" ${registration.paymentStatus==1 ? 'selected' : '' }>已支付</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="doctorAdvice">医生建议</label>
                            <textarea class="form-control" id="doctorAdvice" name="doctorAdvice" rows="4"
                                placeholder="请输入医生建议">${registration.doctorAdvice}</textarea>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary mr-2">更新</button>
                            <a href="${pageContext.request.contextPath}/admin/registration?action=list"
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