<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>管理员首页 - 医院预约挂号系统</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <!-- 引入导航栏 -->
        <%@ include file="../common/header.jsp" %>

            <!-- 主要内容 -->
            <div class="container mt-5">
                <h2 class="mb-4 text-center text-primary fw-bold">管理员控制台</h2>
                <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
                    <!-- 用户管理 -->
                    <div class="col">
                        <div
                            class="card admin-card text-center h-100 border-0 rounded-16 overflow-hidden position-relative">
                            <div class="card-body p-5">
                                <div
                                    class="position-absolute top-0 right-0 w-24 h-24 bg-primary bg-opacity-10 rounded-bl-full">
                                </div>
                                <div class="admin-card-icon mb-3 text-primary">👥</div>
                                <div class="mb-2">
                                    <span
                                        class="badge bg-primary bg-opacity-20 text-primary px-3 py-1 rounded-full fs-6">1,234</span>
                                </div>
                                <h5 class="card-title">用户管理</h5>
                                <p class="card-text text-muted mb-4">管理系统用户</p>
                                <a href="${pageContext.request.contextPath}/jsp/admin/user/index.jsp"
                                    class="btn btn-primary btn-lg rounded-5 px-5 py-2 shadow-lg"><span
                                        class="me-2">→</span>进入管理</a>
                            </div>
                        </div>
                    </div>

                    <!-- 医生管理 -->
                    <div class="col">
                        <div
                            class="card admin-card text-center h-100 border-0 rounded-16 overflow-hidden position-relative">
                            <div class="card-body p-5">
                                <div
                                    class="position-absolute top-0 right-0 w-24 h-24 bg-success bg-opacity-10 rounded-bl-full">
                                </div>
                                <div class="admin-card-icon mb-3 text-success">👨‍⚕️</div>
                                <div class="mb-2">
                                    <span
                                        class="badge bg-success bg-opacity-20 text-success px-3 py-1 rounded-full fs-6">89</span>
                                </div>
                                <h5 class="card-title">医生管理</h5>
                                <p class="card-text text-muted mb-4">管理医生信息</p>
                                <a href="${pageContext.request.contextPath}/jsp/admin/doctor/index.jsp"
                                    class="btn btn-success btn-lg rounded-5 px-5 py-2 shadow-lg"><span
                                        class="me-2">→</span>进入管理</a>
                            </div>
                        </div>
                    </div>

                    <!-- 科室管理 -->
                    <div class="col">
                        <div
                            class="card admin-card text-center h-100 border-0 rounded-16 overflow-hidden position-relative">
                            <div class="card-body p-5">
                                <div
                                    class="position-absolute top-0 right-0 w-24 h-24 bg-info bg-opacity-10 rounded-bl-full">
                                </div>
                                <div class="admin-card-icon mb-3 text-info">🏥</div>
                                <div class="mb-2">
                                    <span
                                        class="badge bg-info bg-opacity-20 text-info px-3 py-1 rounded-full fs-6">23</span>
                                </div>
                                <h5 class="card-title">科室管理</h5>
                                <p class="card-text text-muted mb-4">管理科室信息</p>
                                <a href="${pageContext.request.contextPath}/jsp/admin/department/index.jsp"
                                    class="btn btn-info btn-lg rounded-5 px-5 py-2 shadow-lg"><span
                                        class="me-2">→</span>进入管理</a>
                            </div>
                        </div>
                    </div>

                    <!-- 预约管理 -->
                    <div class="col">
                        <div
                            class="card admin-card text-center h-100 border-0 rounded-16 overflow-hidden position-relative">
                            <div class="card-body p-5">
                                <div
                                    class="position-absolute top-0 right-0 w-24 h-24 bg-warning bg-opacity-10 rounded-bl-full">
                                </div>
                                <div class="admin-card-icon mb-3 text-warning">📅</div>
                                <div class="mb-2">
                                    <span
                                        class="badge bg-warning bg-opacity-20 text-warning px-3 py-1 rounded-full fs-6">567</span>
                                </div>
                                <h5 class="card-title">预约管理</h5>
                                <p class="card-text text-muted mb-4">管理预约信息</p>
                                <a href="${pageContext.request.contextPath}/jsp/admin/appointment/index.jsp"
                                    class="btn btn-warning btn-lg rounded-5 px-5 py-2 shadow-lg"><span
                                        class="me-2">→</span>进入管理</a>
                            </div>
                        </div>
                    </div>

                    <!-- 排班管理 -->
                    <div class="col">
                        <div
                            class="card admin-card text-center h-100 border-0 rounded-16 overflow-hidden position-relative">
                            <div class="card-body p-5">
                                <div
                                    class="position-absolute top-0 right-0 w-24 h-24 bg-danger bg-opacity-10 rounded-bl-full">
                                </div>
                                <div class="admin-card-icon mb-3 text-danger">⏰</div>
                                <div class="mb-2">
                                    <span
                                        class="badge bg-danger bg-opacity-20 text-danger px-3 py-1 rounded-full fs-6">120</span>
                                </div>
                                <h5 class="card-title">排班管理</h5>
                                <p class="card-text text-muted mb-4">管理医生排班</p>
                                <a href="${pageContext.request.contextPath}/jsp/admin/schedule/index.jsp"
                                    class="btn btn-danger btn-lg rounded-5 px-5 py-2 shadow-lg"><span
                                        class="me-2">→</span>进入管理</a>
                            </div>
                        </div>
                    </div>

                    <!-- 挂号管理 -->
                    <div class="col">
                        <div
                            class="card admin-card text-center h-100 border-0 rounded-16 overflow-hidden position-relative">
                            <div class="card-body p-5">
                                <div
                                    class="position-absolute top-0 right-0 w-24 h-24 bg-purple bg-opacity-10 rounded-bl-full">
                                </div>
                                <div class="admin-card-icon mb-3 text-purple">📋</div>
                                <div class="mb-2">
                                    <span
                                        class="badge bg-purple bg-opacity-20 text-purple px-3 py-1 rounded-full fs-6">456</span>
                                </div>
                                <h5 class="card-title">挂号管理</h5>
                                <p class="card-text text-muted mb-4">管理挂号信息</p>
                                <a href="${pageContext.request.contextPath}/jsp/admin/registration/index.jsp"
                                    class="btn btn-purple btn-lg rounded-5 px-5 py-2 shadow-lg"><span
                                        class="me-2">→</span>进入管理</a>
                            </div>
                        </div>
                    </div>

                    <!-- 系统日志 -->
                    <div class="col">
                        <div
                            class="card admin-card text-center h-100 border-0 rounded-16 overflow-hidden position-relative">
                            <div class="card-body p-5">
                                <div
                                    class="position-absolute top-0 right-0 w-24 h-24 bg-teal bg-opacity-10 rounded-bl-full">
                                </div>
                                <div class="admin-card-icon mb-3 text-teal">📝</div>
                                <div class="mb-2">
                                    <span
                                        class="badge bg-teal bg-opacity-20 text-teal px-3 py-1 rounded-full fs-6">9,876</span>
                                </div>
                                <h5 class="card-title">系统日志</h5>
                                <p class="card-text text-muted mb-4">查看系统日志</p>
                                <a href="${pageContext.request.contextPath}/jsp/admin/log/index.jsp"
                                    class="btn btn-teal btn-lg rounded-5 px-5 py-2 shadow-lg"><span
                                        class="me-2">→</span>进入查看</a>
                            </div>
                        </div>
                    </div>

                    <!-- 系统设置 -->
                    <div class="col">
                        <div
                            class="card admin-card text-center h-100 border-0 rounded-16 overflow-hidden position-relative">
                            <div class="card-body p-5">
                                <div
                                    class="position-absolute top-0 right-0 w-24 h-24 bg-orange bg-opacity-10 rounded-bl-full">
                                </div>
                                <div class="admin-card-icon mb-3 text-orange">⚙️</div>
                                <div class="mb-2">
                                    <span
                                        class="badge bg-orange bg-opacity-20 text-orange px-3 py-1 rounded-full fs-6">24</span>
                                </div>
                                <h5 class="card-title">系统设置</h5>
                                <p class="card-text text-muted mb-4">系统参数设置</p>
                                <a href="${pageContext.request.contextPath}/jsp/admin/setting/index.jsp"
                                    class="btn btn-orange btn-lg rounded-5 px-5 py-2 shadow-lg"><span
                                        class="me-2">→</span>进入设置</a>
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
            
            <!-- 导航栏滚动效果 -->
            <script>
                $(window).scroll(function() {
                    if ($(this).scrollTop() > 50) {
                        $('.navbar').addClass('navbar-scrolled');
                    } else {
                        $('.navbar').removeClass('navbar-scrolled');
                    }
                });
            </script>
    </body>

    </html>