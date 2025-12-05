<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>医生列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <!-- 引入导航栏 -->
    <%@ include file="../common/header.jsp" %>

    <!-- 医生列表 -->
    <div class="container">
        <% 
            // 1. 获取科室数据
            java.util.List<com.hospital.entity.Department> departments = null;
            try {
                com.hospital.service.DepartmentService departmentService = new com.hospital.service.impl.DepartmentServiceImpl();
                departments = departmentService.findAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // 2. 获取当前页码和科室参数
            String pageStr = request.getParameter("page");
            String deptIdStr = request.getParameter("deptId");
            
            int pageNum = 1;
            if (pageStr != null && !pageStr.isEmpty()) {
                try {
                    pageNum = Integer.parseInt(pageStr);
                } catch (NumberFormatException e) {
                    pageNum = 1;
                }
            }
            
            Integer deptId = null;
            if (deptIdStr != null && !deptIdStr.isEmpty()) {
                try {
                    deptId = Integer.parseInt(deptIdStr);
                } catch (NumberFormatException e) {
                    deptId = null;
                }
            }
            
            // 3. 分页查询医生数据
            int pageSize = 4;
            java.util.List<com.hospital.entity.Doctor> doctors = null;
            int totalCount = 0;
            
            try {
                com.hospital.service.DoctorService doctorService = new com.hospital.service.impl.DoctorServiceImpl();
                
                if (deptId != null) {
                    doctors = doctorService.findByDeptIdAndPage(deptId, pageNum, pageSize);
                    totalCount = doctorService.getTotalCountByDeptId(deptId);
                } else {
                    doctors = doctorService.findAllByPage(pageNum, pageSize);
                    totalCount = doctorService.getTotalCount();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // 4. 计算总页数
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        %>
        <div class="doctor-list mt-5">
            <h2 class="text-center mb-4">医生列表</h2>

            <!-- 科室筛选 -->
            <div class="row mb-4">
                <div class="col-md-12">
                    <div class="btn-group" role="group" aria-label="科室筛选">
                        <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp?page=1" class="btn btn-outline-primary <%= deptId == null ? "active" : "" %>">全部科室</a>
                        <% if (departments != null && !departments.isEmpty()) {
                            for (com.hospital.entity.Department dept : departments) {
                        %>
                            <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp?deptId=<%= dept.getId() %>&page=1" class="btn btn-outline-primary <%= deptId != null && deptId.equals(dept.getId()) ? "active" : "" %>"><%= dept.getDeptName() %></a>
                        <% }} %>
                    </div>
                </div>
            </div>

            <!-- 医生列表 -->
            <div class="row">
                <% if (doctors != null && !doctors.isEmpty()) {
                    for (com.hospital.entity.Doctor doctor : doctors) {
                %>
                    <div class="col-md-6 mb-4">
                        <div class="doctor-item d-flex">
                            <img src="<%= doctor.getAvatar() != null && !doctor.getAvatar().isEmpty() ? doctor.getAvatar() : request.getContextPath() + "/images/doctor-default.jpg" %>", alt="<%= doctor.getUser() != null ? doctor.getUser().getName() : "" %>" class="doctor-avatar">
                            <div class="doctor-info">
                                <h5 class="doctor-name"><%= doctor.getUser() != null ? doctor.getUser().getName() : "" %></h5>
                                <p class="doctor-title text-primary"><%= doctor.getTitle() %></p>
                                <p class="doctor-dept"><%= doctor.getDepartment() != null ? doctor.getDepartment().getDeptName() : "" %></p>
                                <p class="doctor-specialty">专长：<%= doctor.getSpecialty() %></p>
                                <p class="doctor-phone">联系电话：<%= doctor.getUser() != null ? doctor.getUser().getPhone() : "" %></p>
                                <a href="${pageContext.request.contextPath}/jsp/doctor/detail.jsp?id=<%= doctor.getId() %>" class="btn btn-primary btn-sm">查看详情</a>
                                <a href="${pageContext.request.contextPath}/jsp/appointment/register.jsp?doctorId=<%= doctor.getId() %>" class="btn btn-success btn-sm ml-2">预约挂号</a>
                            </div>
                        </div>
                    </div>
                <% }} else { %>
                    <div class="col-md-12 text-center">
                        <p class="text-muted">暂无医生数据</p>
                    </div>
                <% } %>
            </div>

            <!-- 分页 -->
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <li class="page-item <%= pageNum == 1 ? "disabled" : "" %>">
                        <a class="page-link" href="<%= deptId != null ? request.getContextPath() + "/jsp/doctor/list.jsp?deptId=" + deptId + "&page=" + (pageNum - 1) : request.getContextPath() + "/jsp/doctor/list.jsp?page=" + (pageNum - 1) %>">上一页</a>
                    </li>
                    
                    <% for (int i = 1; i <= totalPages; i++) { %>
                        <li class="page-item <%= i == pageNum ? "active" : "" %>">
                            <a class="page-link" href="<%= deptId != null ? request.getContextPath() + "/jsp/doctor/list.jsp?deptId=" + deptId + "&page=" + i : request.getContextPath() + "/jsp/doctor/list.jsp?page=" + i %>"><%= i %></a>
                        </li>
                    <% } %>
                    
                    <li class="page-item <%= pageNum == totalPages ? "disabled" : "" %>">
                        <a class="page-link" href="<%= deptId != null ? request.getContextPath() + "/jsp/doctor/list.jsp?deptId=" + deptId + "&page=" + (pageNum + 1) : request.getContextPath() + "/jsp/doctor/list.jsp?page=" + (pageNum + 1) %>">下一页</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>

    <!-- 引入页脚 -->
    <%@ include file="../common/footer.jsp" %>

    <!-- JavaScript -->
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>

</html>