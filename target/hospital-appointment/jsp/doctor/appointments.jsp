<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>我的预约</h2>
        </div>
        
        <!-- 筛选条件 -->
        <div class="card mb-4">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/doctor/appointment" method="get">
                    <div class="row g-3">
                        <div class="col-md-3">
                            <label for="date" class="form-label">预约日期</label>
                            <input type="date" class="form-control" id="date" name="date" value="${param.date}">
                        </div>
                        <div class="col-md-3">
                            <label for="status" class="form-label">预约状态</label>
                            <select class="form-select" id="status" name="status">
                                <option value="">全部状态</option>
                                <option value="1" ${param.status == '1' ? 'selected' : ''}>待就诊</option>
                                <option value="2" ${param.status == '2' ? 'selected' : ''}>已就诊</option>
                                <option value="3" ${param.status == '3' ? 'selected' : ''}>已取消</option>
                                <option value="4" ${param.status == '4' ? 'selected' : ''}>已过期</option>
                            </select>
                        </div>
                        <div class="col-md-3 align-self-end">
                            <button type="submit" class="btn btn-primary btn-block">筛选</button>
                        </div>
                        <div class="col-md-3 align-self-end">
                            <a href="${pageContext.request.contextPath}/doctor/appointment" class="btn btn-secondary btn-block">重置</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        
        <!-- 预约列表 -->
        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead class="table-primary">
                            <tr>
                                <th>预约ID</th>
                                <th>患者信息</th>
                                <th>预约日期</th>
                                <th>时间段</th>
                                <th>预约状态</th>
                                <th>预约时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                java.util.List<com.hospital.entity.Appointment> appointments = (java.util.List<com.hospital.entity.Appointment>) request.getAttribute("appointments");
                                if (appointments != null && !appointments.isEmpty()) {
                                    for (com.hospital.entity.Appointment appointment : appointments) {
                            %> 
                            <tr>
                                <td><%= appointment.getId() %></td>
                                <td>
                                    <%= appointment.getUser().getName() %><br>
                                    <small class="text-muted"><%= appointment.getUser().getPhone() %></small>
                                </td>
                                <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(appointment.getAppointmentDate()) %></td>
                                <td><%= appointment.getTimeSlot() %></td>
                                <td>
                                    <span class="<%= appointment.getStatus() == 1 ? "text-primary" : appointment.getStatus() == 2 ? "text-success" : appointment.getStatus() == 3 ? "text-danger" : "text-muted" %>">
                                        <%= appointment.getStatus() == 1 ? "待就诊" : appointment.getStatus() == 2 ? "已就诊" : appointment.getStatus() == 3 ? "已取消" : "已过期" %>
                                    </span>
                                </td>
                                <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(appointment.getCreateTime()) %></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/doctor/appointment?action=detail&id=<%= appointment.getId() %>" class="btn btn-sm btn-info">详情</a>
                                </td>
                            </tr>
                            <% 
                                    }
                                } else { %>
                            <tr>
                                <td colspan="7" class="text-center">暂无预约记录</td>
                            </tr>
                            <% 
                                }
                            %> 
                        </tbody>
                    </table>
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