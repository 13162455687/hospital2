<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>医院预约挂号系统1111</title>
        <!-- CSS引用 -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <!-- 引入导航栏 -->
        <%@ include file="common/header.jsp" %>

            <!-- 轮播图 -->
            <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <div class="carousel-image-wrapper">
                            <img class="d-block w-100" src="${pageContext.request.contextPath}/images/banner1.jpg"
                                alt="First slide">
                            <div class="carousel-gradient-mask"></div>
                        </div>
                        <div class="carousel-caption d-none d-md-block">
                            <div class="carousel-caption-content">
                                <h5>便捷的预约挂号服务</h5>
                                <p>足不出户，轻松预约专家号</p>
                            </div>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <div class="carousel-image-wrapper">
                            <img class="d-block w-100" src="${pageContext.request.contextPath}/images/banner2.jpg"
                                alt="Second slide">
                            <div class="carousel-gradient-mask"></div>
                        </div>
                        <div class="carousel-caption d-none d-md-block">
                            <div class="carousel-caption-content">
                                <h5>专业的医疗团队</h5>
                                <p>汇聚顶级专家，提供优质医疗服务</p>
                            </div>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <div class="carousel-image-wrapper">
                            <img class="d-block w-100" src="${pageContext.request.contextPath}/images/banner3.jpg"
                                alt="Third slide">
                            <div class="carousel-gradient-mask"></div>
                        </div>
                        <div class="carousel-caption d-none d-md-block">
                            <div class="carousel-caption-content">
                                <h5>舒适的就医环境</h5>
                                <p>温馨舒适的就医环境，让您安心就诊</p>
                            </div>
                        </div>
                    </div>
                </div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>

            <!-- 科室介绍 -->
            <section class="departments py-8 bg-light">
                <div class="container">
                    <h2 class="text-center mb-8">科室介绍</h2>
                    <div class="row justify-content-center">
                        <%-- 直接从数据库获取科室数据 --%>
                            <% java.util.List<com.hospital.entity.Department> departments = null;
                                try {
                                // 直接在JSP中调用Service获取数据
                                com.hospital.service.DepartmentService departmentService = new
                                com.hospital.service.impl.DepartmentServiceImpl();
                                departments = departmentService.findAll();
                                System.out.println("直接从DB获取的科室数量: " + (departments != null ? departments.size() : 0));
                                } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("获取科室数据失败: " + e.getMessage());
                                }

                                if (departments != null && !departments.isEmpty()) {
                                for (com.hospital.entity.Department dept : departments) {
                                %>
                                <div class="col-lg-3 col-md-3 col-sm-4 mb-5">
                                    <div class="card departments-card h-100">
                                        <div class="card-img-container">
                                            <% if (dept.getImageUrl() != null && !dept.getImageUrl().isEmpty()) { %>
                                                <img src="<%= dept.getImageUrl() %>" class="card-img-top" alt="<%= dept.getDeptName() %>">
                                            <% } else { %>
                                                <img src="${pageContext.request.contextPath}/images/department<%= (int)(Math.random()*4+1) %>.jpg"
                                                    class="card-img-top" alt="<%= dept.getDeptName() %>">
                                            <% } %>
                                        </div>
                                        <div class="card-body">
                                            <h5 class="card-title text-center mb-3">
                                                <%= dept.getDeptName() %>
                                            </h5>
                                            <p class="card-text text-muted text-center mb-4">
                                                <%= dept.getDescription() %>
                                            </p>
                                            <div class="text-center">
                                                <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp?deptId=<%= dept.getId() %>&page=1"
                                                    class="btn btn-primary">查看医生</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <% } } else { // 如果没有数据，显示静态科室作为备选 %>
                                    <div class="col-lg-3 col-md-3 col-sm-4 mb-5">
                                        <div class="card departments-card h-100">
                                            <div class="card-img-container">
                                                <img src="${pageContext.request.contextPath}/images/department1.jpg"
                                                    class="card-img-top" alt="内科">
                                            </div>
                                            <div class="card-body">
                                                <h5 class="card-title text-center mb-3">内科</h5>
                                                <p class="card-text text-muted text-center mb-4">
                                                    提供心血管、呼吸、消化等内科疾病的诊疗服务</p>
                                                <div class="text-center">
                                                    <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp?deptId=1&page=1"
                                                        class="btn btn-primary">查看医生</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-4 mb-5">
                                        <div class="card departments-card h-100">
                                            <div class="card-img-container">
                                                <img src="${pageContext.request.contextPath}/images/department2.jpg"
                                                    class="card-img-top" alt="外科">
                                            </div>
                                            <div class="card-body">
                                                <h5 class="card-title text-center mb-3">外科</h5>
                                                <p class="card-text text-muted text-center mb-4">
                                                    提供普外科、骨科、心胸外科等外科疾病的诊疗服务</p>
                                                <div class="text-center">
                                                    <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp?deptId=2&page=1"
                                                        class="btn btn-primary">查看医生</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-4 mb-5">
                                        <div class="card departments-card h-100">
                                            <div class="card-img-container">
                                                <img src="${pageContext.request.contextPath}/images/department3.jpg"
                                                    class="card-img-top" alt="妇产科">
                                            </div>
                                            <div class="card-body">
                                                <h5 class="card-title text-center mb-3">妇产科</h5>
                                                <p class="card-text text-muted text-center mb-4">
                                                    提供妇科疾病诊疗、产前检查、分娩等服务</p>
                                                <div class="text-center">
                                                    <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp?deptId=3&page=1"
                                                        class="btn btn-primary">查看医生</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-4 mb-5">
                                        <div class="card departments-card h-100">
                                            <div class="card-img-container">
                                                <img src="${pageContext.request.contextPath}/images/department4.jpg"
                                                    class="card-img-top" alt="儿科">
                                            </div>
                                            <div class="card-body">
                                                <h5 class="card-title text-center mb-3">儿科</h5>
                                                <p class="card-text text-muted text-center mb-4">
                                                    提供儿童常见疾病的诊疗和预防保健服务</p>
                                                <div class="text-center">
                                                    <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp?deptId=4&page=1"
                                                        class="btn btn-primary">查看医生</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-4 mb-5">
                                        <div class="card departments-card h-100">
                                            <div class="card-img-container">
                                                <img src="${pageContext.request.contextPath}/images/department1.jpg"
                                                    class="card-img-top" alt="眼科">
                                            </div>
                                            <div class="card-body">
                                                <h5 class="card-title text-center mb-3">眼科</h5>
                                                <p class="card-text text-muted text-center mb-4">提供眼科疾病的诊疗和视力保健服务
                                                </p>
                                                <div class="text-center">
                                                    <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp?dept=眼科"
                                                        class="btn btn-primary">查看医生</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-4 mb-5">
                                        <div class="card departments-card h-100">
                                            <div class="card-img-container">
                                                <img src="${pageContext.request.contextPath}/images/department2.jpg"
                                                    class="card-img-top" alt="耳鼻喉科">
                                            </div>
                                            <div class="card-body">
                                                <h5 class="card-title text-center mb-3">耳鼻喉科</h5>
                                                <p class="card-text text-muted text-center mb-4">提供耳鼻喉疾病的诊疗和预防服务
                                                </p>
                                                <div class="text-center">
                                                    <a href="${pageContext.request.contextPath}/jsp/doctor/list.jsp?dept=耳鼻喉科"
                                                        class="btn btn-primary">查看医生</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <% } %>
                    </div>
                </div>
            </section>

            <!-- 特色服务 -->
            <section class="features py-8 bg-white">
                <div class="container">
                    <h2 class="text-center mb-8">特色服务</h2>
                    <div class="row justify-content-center">
                        <div class="col-lg-3 col-md-4 col-sm-6 mb-5">
                            <div class="card features-card feature-card-1 text-center h-100">
                                <div class="card-body">
                                    <div class="feature-icon mb-5">
                                        <i class="fas fa-calendar-check fa-5x"></i>
                                    </div>
                                    <h5 class="card-title h3 mb-4">在线预约</h5>
                                    <p class="card-text text-muted">24小时在线预约挂号，无需排队等候</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-4 col-sm-6 mb-5">
                            <div class="card features-card feature-card-2 text-center h-100">
                                <div class="card-body">
                                    <div class="feature-icon mb-5">
                                        <i class="fas fa-user-md fa-5x"></i>
                                    </div>
                                    <h5 class="card-title h3 mb-4">专家团队</h5>
                                    <p class="card-text text-muted">汇聚各科室顶级专家，提供专业诊疗服务</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-4 col-sm-6 mb-5">
                            <div class="card features-card feature-card-3 text-center h-100">
                                <div class="card-body">
                                    <div class="feature-icon mb-5">
                                        <i class="fas fa-notes-medical fa-5x"></i>
                                    </div>
                                    <h5 class="card-title h3 mb-4">电子病历</h5>
                                    <p class="card-text text-muted">便捷的电子病历管理，就医记录一目了然</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- 引入页脚 -->
            <%@ include file="common/footer.jsp" %>

                <!-- JavaScript -->
        <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
        
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