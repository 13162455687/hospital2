<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
            <%@ include file="../../common/header.jsp" %>

            <!-- 主要内容 -->
            <div class="container mt-5">
                <% // 检查用户是否登录 if (session==null || session.getAttribute("user")==null) { %>
                    <div class="alert alert-warning text-center p-5" role="alert">
                        <h4 class="alert-heading">请先登录！</h4>
                        <p>预约记录需要登录后才能查看，请先登录您的账号。</p>
                        <hr>
                        <div class="d-flex justify-content-center">
                            <a href="${pageContext.request.contextPath}/jsp/user/login.jsp"
                                class="btn btn-primary btn-lg mx-2">立即登录</a>
                            <a href="${pageContext.request.contextPath}/jsp/user/register.jsp"
                                class="btn btn-secondary btn-lg mx-2">注册账号</a>
                        </div>
                    </div>
                <% } else { %>
                <h2 class="mb-4">我的预约记录</h2>

                <!-- 错误提示 -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger mb-4" role="alert">
                        ${error}
                    </div>
                </c:if>

                <!-- 预约记录列表 -->
                <div class="card">
                    <div class="card-body">
                        <c:if test="${empty appointments}">
                            <div class="text-center py-5">
                                <p class="text-muted">暂无预约记录</p>
                                <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp"
                                    class="btn btn-primary mt-3">去预约</a>
                            </div>
                        </c:if>

                        <c:if test="${not empty appointments}">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>预约ID</th>
                                        <th>医生</th>
                                        <th>科室</th>
                                        <th>预约日期</th>
                                        <th>时间段</th>
                                        <th>预约状态</th>
                                        <th>预约时间</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${appointments}" var="appointment">
                                        <tr>
                                            <td>${appointment.id}</td>
                                            <td>
                                                <c:if test="${not empty appointment.doctor}">
                                                    ${appointment.doctor.name} - ${appointment.doctor.title}
                                                </c:if>
                                                <c:if test="${empty appointment.doctor}">
                                                    未知医生
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if
                                                    test="${not empty appointment.doctor and not empty appointment.doctor.department}">
                                                    ${appointment.doctor.department.name}
                                                </c:if>
                                                <c:if test="${empty appointment.doctor}">
                                                    未知科室
                                                </c:if>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${appointment.appointmentDate}"
                                                    pattern="yyyy-MM-dd" />
                                            </td>
                                            <td>${appointment.timeSlot}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${appointment.status == 0}">
                                                        <span class="badge bg-secondary">已取消</span>
                                                    </c:when>
                                                    <c:when test="${appointment.status == 1}">
                                                        <span class="badge bg-info">待审核</span>
                                                    </c:when>
                                                    <c:when test="${appointment.status == 2}">
                                                        <span class="badge bg-success">已通过</span>
                                                    </c:when>
                                                    <c:when test="${appointment.status == 3}">
                                                        <span class="badge bg-danger">已拒绝</span>
                                                    </c:when>
                                                    <c:when test="${appointment.status == 4}">
                                                        <span class="badge bg-primary">已完成</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-warning">未知状态</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${appointment.createTime}"
                                                    pattern="yyyy-MM-dd HH:mm:ss" />
                                            </td>
                                            <td>
                                                <a href="#" class="btn btn-sm btn-info">查看详情</a>
                                                <c:if test="${appointment.status == 1}">
                                                    <a href="#" class="btn btn-sm btn-danger">取消预约</a>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                    </div>
                </div>
            </div>

            <!-- 引入页脚 -->
            <%@ include file="../../common/footer.jsp" %>

            <!-- JavaScript -->
            <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
            <!-- 引入JSTL核心标签库 -->
            <script src="https://cdn.jsdelivr.net/npm/moment@2.29.1/min/moment.min.js"></script>
            <% } %>
        </body>

        </html>