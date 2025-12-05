<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>医生首页 - 医院预约挂号系统</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <!-- 引入导航栏 -->
        <%@ include file="../common/header.jsp" %>

        <!-- 主要内容 -->
        <div class="container mt-5 doctor-dashboard">
            <div class="row">
                <!-- 左侧个人信息 -->
                <div class="col-md-4">
                    <div class="card doctor-profile-card mb-4">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">个人信息</h5>
                        </div>
                        <div class="card-body text-center">
                            <% 
                                // 获取当前登录用户
                                com.hospital.entity.User user = (com.hospital.entity.User) session.getAttribute("user");
                                if (user != null) {
                                    // 根据用户ID查询医生信息
                                    com.hospital.service.DoctorService doctorService = new com.hospital.service.impl.DoctorServiceImpl();
                                    com.hospital.entity.Doctor doctor = null;
                                    // 需要查询医生列表，然后找到对应的医生
                                    java.util.List<com.hospital.entity.Doctor> doctors = doctorService.findAll();
                                    for (com.hospital.entity.Doctor d : doctors) {
                                        if (d.getUserId().equals(user.getId())) {
                                            doctor = d;
                                            break;
                                        }
                                    }
                                
                                    if (doctor != null) {
                            %> 
                            <div class="doctor-avatar-container mb-3">
                                <img src="<%= doctor.getAvatar() != null ? request.getContextPath() + doctor.getAvatar() : request.getContextPath() + "/images/doctor-default.jpg" %>" alt="医生头像" class="doctor-avatar rounded-circle">
                            </div>
                            <h4 class="card-title"><%= user.getName() %></h4>
                            <p class="doctor-title text-muted"><%= doctor.getTitle() %></p>
                            <p class="doctor-department"><%= doctor.getDepartment().getDeptName() %></p>
                            <a href="#" class="btn btn-edit-profile">编辑个人信息</a>
                            <% 
                                    } 
                                } 
                            %> 
                        </div>
                    </div>
                </div>

                <!-- 右侧功能区 -->
                <div class="col-md-8">
                    <h2 class="mb-4">医生工作台</h2>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="card stats-card text-center mb-4">
                                <div class="card-body">
                                    <h5 class="card-title">今日预约</h5>
                                    <p class="stats-number">0</p>
                                    <a href="#" class="btn btn-view-details">查看详情</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card stats-card text-center mb-4">
                                <div class="card-body">
                                    <h5 class="card-title">本周预约</h5>
                                    <p class="stats-number">0</p>
                                    <a href="#" class="btn btn-view-details">查看详情</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="card appointments-card mb-4">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">近期预约</h5>
                        </div>
                        <div class="card-body">
                            <p class="text-center text-muted">暂无预约记录</p>
                        </div>
                    </div>

                    <div class="card schedule-card">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">我的排班</h5>
                        </div>
                        <div class="card-body">
                            <p class="text-center text-muted">暂无排班记录</p>
                            <div class="text-center mt-3">
                                <a href="#" class="btn btn-primary">添加排班</a>
                            </div>
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
        
        <!-- 医生工作台动态效果 -->
        <script>
            // 数字增长动画
            function animateNumber(element, target, duration) {
                let start = 0;
                const increment = target / (duration / 16);
                const timer = setInterval(function() {
                    start += increment;
                    if (start >= target) {
                        element.textContent = target;
                        clearInterval(timer);
                    } else {
                        element.textContent = Math.floor(start);
                    }
                }, 16);
            }
            
            // 页面加载完成后执行动画
            $(document).ready(function() {
                // 为统计数字添加增长动画
                $('.stats-number').each(function() {
                    const target = parseInt($(this).text());
                    animateNumber(this, target, 2000);
                });
                
                // 为卡片添加悬停效果
                $('.stats-card').hover(function() {
                    $(this).css('transform', 'translateY(-15px) scale(1.02)');
                }, function() {
                    $(this).css('transform', 'translateY(0) scale(1)');
                });
                
                // 为近期预约和排班卡片添加悬停效果
                $('.appointments-card, .schedule-card').hover(function() {
                    $(this).css('transform', 'translateY(-8px) scale(1.01)');
                }, function() {
                    $(this).css('transform', 'translateY(0) scale(1)');
                });
                
                // 平滑滚动到锚点
                $('a[href^="#"]').on('click', function(e) {
                    e.preventDefault();
                    const target = $(this.getAttribute('href'));
                    if (target.length) {
                        $('html, body').stop().animate({
                            scrollTop: target.offset().top - 80
                        }, 1000, 'easeInOutExpo');
                    }
                });
            });
            
            // 监听窗口滚动，为进入视口的元素添加动画
            $(window).on('scroll', function() {
                const scrollTop = $(window).scrollTop();
                const windowHeight = $(window).height();
                
                // 为进入视口的卡片添加动画
                $('.stats-card, .appointments-card, .schedule-card').each(function() {
                    const offsetTop = $(this).offset().top;
                    if (scrollTop + windowHeight > offsetTop + 100) {
                        $(this).css('opacity', '1');
                        $(this).css('transform', 'translateY(0)');
                    }
                });
            });
        </script>
    </body>

    </html>