<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.List" %>
        <!DOCTYPE html>
        <html lang="zh-CN">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>患者首页 - 医院预约挂号系统</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body>
            <!-- 引入导航栏 -->
            <%@ include file="../common/header.jsp" %>

                <!-- 主要内容 -->
                <div class="container mt-5">
                    <% // 检查用户是否登录 if (session==null || session.getAttribute("user")==null) { %>
                        <div class="alert alert-warning text-center p-5" role="alert">
                            <h4 class="alert-heading">请先登录！</h4>
                            <p>个人中心需要登录后才能访问，请先登录您的账号。</p>
                            <hr>
                            <div class="d-flex justify-content-center">
                                <a href="${pageContext.request.contextPath}/jsp/user/login.jsp"
                                    class="btn btn-primary btn-lg mx-2">立即登录</a>
                                <a href="${pageContext.request.contextPath}/jsp/user/register.jsp"
                                    class="btn btn-secondary btn-lg mx-2">注册账号</a>
                            </div>
                        </div>
                        <% } else { %>
                            <div class="row">
                                <!-- 左侧个人信息 -->
                                <div class="col-md-4">
                                    <div class="card mb-4">
                                        <div class="card-header bg-primary text-white">
                                            <h5 class="mb-0">个人信息</h5>
                                        </div>
                                        <div class="card-body text-center">
                                            <div class="mb-3">
                                                <img src="${pageContext.request.contextPath}/images/doctor-default.jpg"
                                                    alt="患者头像" class="rounded-circle"
                                                    style="width: 120px; height: 120px;">
                                            </div>
                                            <h4 class="card-title">${sessionScope.user.name}</h4>
                                            <p class="card-text text-muted">用户名：${sessionScope.user.username}</p>
                                            <p class="card-text">手机号：${sessionScope.user.phone}</p>
                                            <p class="card-text">邮箱：${sessionScope.user.email}</p>
                                            <a href="${pageContext.request.contextPath}/patient/profile"
                                                class="btn btn-primary btn-sm">编辑个人信息</a>
                                        </div>
                                    </div>
                                </div>

                                <!-- 右侧功能区 -->
                                <div class="col-md-8">
                                    <h2 class="mb-4">我的预约</h2>

                                    <div class="card mb-4">
                                        <div class="card-header bg-primary text-white">
                                            <h5 class="mb-0">近期预约</h5>
                                        </div>
                                        <div class="card-body">
                                            <% List<com.hospital.entity.Appointment> appointments = (List
                                                <com.hospital.entity.Appointment>) request.getAttribute("appointments");
                                                    if (appointments == null || appointments.isEmpty()) {
                                                    %>
                                                    <p class="text-center text-muted">暂无预约记录</p>
                                                    <div class="text-center mt-3">
                                                        <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp"
                                                            class="btn btn-primary">去预约</a>
                                                    </div>
                                                    <% } else { // 只显示最近3条预约记录 int showCount=Math.min(3,
                                                        appointments.size()); %>
                                                        <table class="table table-hover">
                                                            <thead>
                                                                <tr>
                                                                    <th>医生</th>
                                                                    <th>科室</th>
                                                                    <th>预约日期</th>
                                                                    <th>时间段</th>
                                                                    <th>状态</th>
                                                                    <th>操作</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <% for (int i=0; i < showCount; i++) {
                                                                    com.hospital.entity.Appointment
                                                                    appointment=appointments.get(i); %>
                                                                    <tr>
                                                                        <td>
                                                                            <%= appointment.getDoctor().getName() %>
                                                                                <%= appointment.getDoctor().getTitle()
                                                                                    %>
                                                                        </td>
                                                                        <td>
                                                                            <%= appointment.getDoctor().getDepartment()
                                                                                !=null ?
                                                                                appointment.getDoctor().getDepartment().getDeptName()
                                                                                : "未知科室" %>
                                                                        </td>
                                                                        <td>
                                                                            <%= new
                                                                                java.text.SimpleDateFormat("yyyy-MM-dd").format(appointment.getAppointmentDate())
                                                                                %>
                                                                        </td>
                                                                        <td>
                                                                            <%= appointment.getTimeSlot() %>
                                                                        </td>
                                                                        <td>
                                                                            <% String status="未知状态" ; if
                                                                                (appointment.getStatus()==1) {
                                                                                status="<span class=\" badge
                                                                                badge-primary\">待就诊</span>";
                                                                                } else if (appointment.getStatus() == 2)
                                                                                {
                                                                                status = "<span class=\"badge
                                                                                    badge-success\">已就诊</span>";
                                                                                } else if (appointment.getStatus() == 3)
                                                                                {
                                                                                status = "<span class=\"badge
                                                                                    badge-warning\">已取消</span>";
                                                                                } else if (appointment.getStatus() == 4)
                                                                                {
                                                                                status = "<span class=\"badge
                                                                                    badge-danger\">已过期</span>";
                                                                                }
                                                                                out.print(status);
                                                                                %>
                                                                        </td>
                                                                        <td>
                                                                            <% if (appointment.getStatus()==1) { %>
                                                                                <a href="${pageContext.request.contextPath}/patient/appointment/cancel?id=<%= appointment.getId() %>"
                                                                                    class="btn btn-sm btn-danger"
                                                                                    onclick="return confirm('确定要取消该预约吗？');">取消</a>
                                                                                <% } %>
                                                                        </td>
                                                                    </tr>
                                                                    <% } %>
                                                            </tbody>
                                                        </table>
                                                        <div class="text-right mt-3">
                                                            <a href="${pageContext.request.contextPath}/patient/appointments"
                                                                class="btn btn-secondary">查看全部预约</a>
                                                        </div>
                                                        <% } %>
                                        </div>
                                    </div>

                                    <div class="card">
                                        <div class="card-header bg-primary text-white">
                                            <h5 class="mb-0">就诊记录</h5>
                                        </div>
                                        <div class="card-body">
                                            <p class="text-center text-muted">暂无就诊记录</p>
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
                    <% } %>
        </body>

        </html>