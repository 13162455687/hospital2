<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的预约 - 医院预约挂号系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <!-- 引入导航栏 -->
        <%@ include file="../common/header.jsp" %>

    <!-- 主要内容 -->
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-12">
                <h2 class="mb-4">我的预约</h2>

                <!-- 错误信息显示 -->
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                <% } %>

                <!-- 成功信息显示 -->
                <% if (request.getAttribute("success") != null) { %>
                    <div class="alert alert-success" role="alert">
                        ${success}
                    </div>
                <% } %>

                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">预约记录</h5>
                    </div>
                    <div class="card-body">
                        <% 
                            List<com.hospital.entity.Appointment> appointments = (List<com.hospital.entity.Appointment>) request.getAttribute("appointments");
                            if (appointments == null || appointments.isEmpty()) {
                        %>
                            <div class="text-center py-5">
                                <h4 class="text-muted mb-3">暂无预约记录</h4>
                                <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp" class="btn btn-primary btn-lg">去预约</a>
                            </div>
                        <% } else { %>
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>预约ID</th>
                                        <th>医生</th>
                                        <th>科室</th>
                                        <th>预约日期</th>
                                        <th>时间段</th>
                                        <th>状态</th>
                                        <th>创建时间</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (com.hospital.entity.Appointment appointment : appointments) { %>
                                        <tr>
                                            <td><%= appointment.getId() %></td>
                                            <td><%= appointment.getDoctor().getName() %> <%= appointment.getDoctor().getTitle() %></td>
                                            <td>
                                                <%= appointment.getDoctor().getDeptId() == 1 ? "内科" : 
                                                    appointment.getDoctor().getDeptId() == 2 ? "外科" : 
                                                    appointment.getDoctor().getDeptId() == 3 ? "妇产科" : 
                                                    appointment.getDoctor().getDeptId() == 4 ? "儿科" : 
                                                    appointment.getDoctor().getDeptId() == 5 ? "眼科" : 
                                                    appointment.getDoctor().getDeptId() == 6 ? "耳鼻喉科" : 
                                                    appointment.getDoctor().getDeptId() == 7 ? "口腔科" : 
                                                    appointment.getDoctor().getDeptId() == 8 ? "皮肤科" : "未知科室" %>
                                            </td>
                                            <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(appointment.getAppointmentDate()) %></td>
                                            <td><%= appointment.getTimeSlot() %></td>
                                            <td>
                                                <% 
                                                    String status = "未知状态";
                                                    if (appointment.getStatus() == 1) {
                                                        status = "<span class=\"badge badge-primary\">待就诊</span>";
                                                    } else if (appointment.getStatus() == 2) {
                                                        status = "<span class=\"badge badge-success\">已就诊</span>";
                                                    } else if (appointment.getStatus() == 3) {
                                                        status = "<span class=\"badge badge-warning\">已取消</span>";
                                                    } else if (appointment.getStatus() == 4) {
                                                        status = "<span class=\"badge badge-danger\">已过期</span>";
                                                    }
                                                    out.print(status);
                                                %>
                                            </td>
                                            <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(appointment.getCreateTime()) %></td>
                                            <td>
                                                <div class="btn-group" role="group">
                                                    <a href="#" class="btn btn-sm btn-info" title="查看详情">详情</a>
                                                    <% if (appointment.getStatus() == 1) { %>
                                                        <a href="${pageContext.request.contextPath}/patient/appointment/cancel?id=<%= appointment.getId() %>" class="btn btn-sm btn-danger" title="取消预约" onclick="return confirm('确定要取消该预约吗？');">取消</a>
                                                    <% } %>
                                                </div>
                                            </td>
                                        </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        <% } %>
                    </div>
                </div>

                <div class="mt-4 text-center">
                    <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp" class="btn btn-primary">继续预约</a>
                    <a href="${pageContext.request.contextPath}/jsp/patient/index.jsp" class="btn btn-secondary">返回患者首页</a>
                </div>
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