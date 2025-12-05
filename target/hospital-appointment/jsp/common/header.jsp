<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!-- 导航栏 -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container">
            <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/jsp/index.jsp">
                <div class="hospital-logo me-3">🏥</div>
                <span>医院预约挂号系统</span>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">
                    <!-- 公共导航链接（所有用户可见） -->
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/jsp/index.jsp">首页</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/jsp/doctor/list.jsp">医生列表</a>
                    </li>

                    <!-- 登录状态判断 -->
                    <% if (session!=null && session.getAttribute("user")!=null) { %>
                        <% com.hospital.entity.User user=(com.hospital.entity.User) session.getAttribute("user"); %>

                            <!-- 角色专属导航链接 -->
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="roleDropdown" role="button"
                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <% if (user.getRoleId()==1) { %>管理员中心<% } else if (user.getRoleId()==2) { %>医生中心<% }
                                                else { %>个人中心<% } %>
                                </a>
                                <div class="dropdown-menu" aria-labelledby="roleDropdown">
                                    <% if (user.getRoleId()==1) { // 管理员 %>
                                        <a class="dropdown-item"
                                            href="${pageContext.request.contextPath}/jsp/admin/index.jsp">管理面板</a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item"
                                            href="${pageContext.request.contextPath}/jsp/admin/user/index.jsp">用户管理</a>
                                        <a class="dropdown-item"
                                            href="${pageContext.request.contextPath}/jsp/admin/doctor/index.jsp">医生管理</a>
                                        <a class="dropdown-item"
                                            href="${pageContext.request.contextPath}/jsp/admin/department/index.jsp">科室管理</a>
                                        <a class="dropdown-item"
                                            href="${pageContext.request.contextPath}/jsp/admin/appointment/index.jsp">预约管理</a>
                                        <a class="dropdown-item"
                                            href="${pageContext.request.contextPath}/jsp/admin/schedule/index.jsp">排班管理</a>
                                        <% } else if (user.getRoleId()==2) { // 医生 %>
                                            <a class="dropdown-item"
                                                href="${pageContext.request.contextPath}/jsp/doctor/index.jsp">医生工作台</a>
                                            <a class="dropdown-item"
                                                href="${pageContext.request.contextPath}/jsp/doctor/schedule.jsp">我的排班</a>
                                            <a class="dropdown-item"
                                                href="${pageContext.request.contextPath}/jsp/doctor/appointments.jsp">我的预约</a>
                                            <% } else { // 患者 %>
                                                <a class="dropdown-item"
                                                    href="${pageContext.request.contextPath}/jsp/patient/index.jsp">我的预约</a>
                                                <a class="dropdown-item"
                                                    href="${pageContext.request.contextPath}/jsp/doctor/list.jsp">预约挂号</a>
                                                <a class="dropdown-item"
                                                    href="${pageContext.request.contextPath}/jsp/patient/history.jsp">就诊历史</a>
                                                <% } %>
                                </div>
                            </li>
                            <% } %>
                </ul>

                <!-- 用户操作区域 -->
                <ul class="navbar-nav ml-auto">
                    <% if (session==null || session.getAttribute("user")==null) { %>
                        <!-- 未登录状态 -->
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/jsp/user/login.jsp">登录</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/jsp/user/register.jsp">注册</a>
                        </li>
                        <% } else { %>
                            <!-- 已登录状态 -->
                            <% com.hospital.entity.User user=(com.hospital.entity.User) session.getAttribute("user"); %>
                                <li class="nav-item">
                                    <span class="nav-link text-primary">欢迎，<%= user.getName() %></span>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/logout">退出登录</a>
                                </li>
                                <% } %>
                </ul>
            </div>
        </div>
    </nav>