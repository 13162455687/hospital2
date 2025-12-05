<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>添加排班 - 医院预约挂号系统</title>
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
                        <li class="nav-item active">
                            <a class="nav-link"
                                href="${pageContext.request.contextPath}/admin/schedule?action=list">排班管理 <span
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
            <h2 class="mb-4">添加排班</h2>

            <!-- 错误信息显示 -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>

            <!-- 添加排班表单 -->
            <div class="card">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/schedule?action=add" method="post">
                        <div class="form-group">
                            <label for="deptId">科室</label>
                            <select class="form-control" id="deptId" name="deptId" required>
                                <option value="">请选择科室</option>
                                <!-- 动态加载科室列表 -->
                                <c:forEach var="dept" items="${departments}">
                                    <option value="${dept.id}">${dept.deptName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="doctorId">医生</label>
                            <select class="form-control" id="doctorId" name="doctorId" required>
                                <option value="">请先选择科室</option>
                                <!-- 这里需要通过AJAX动态加载医生列表 -->
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="date">排班日期</label>
                            <input type="date" class="form-control" id="date" name="date" required>
                        </div>
                        <div class="form-group">
                            <label for="timeSlot">时间段</label>
                            <select class="form-control" id="timeSlot" name="timeSlot" required>
                                <option value="">请选择时间段</option>
                                <option value="上午">上午</option>
                                <option value="下午">下午</option>
                                <option value="全天">全天</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="maxNum">最大预约数</label>
                            <input type="number" class="form-control" id="maxNum" name="maxNum" min="1" max="50"
                                required placeholder="请输入最大预约数">
                        </div>
                        <div class="form-group">
                            <label for="status">状态</label>
                            <select class="form-control" id="status" name="status" required>
                                <option value="1">启用</option>
                                <option value="0">禁用</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary mr-2">添加</button>
                            <a href="${pageContext.request.contextPath}/admin/schedule?action=list"
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
        <script>
            // 科室选择变化时，动态加载医生列表
            $(document).ready(function() {
                $('#deptId').change(function() {
                    var deptId = $(this).val();
                    var doctorSelect = $('#doctorId');
                    
                    // 清空医生列表
                    doctorSelect.empty();
                    
                    if (deptId === '') {
                        doctorSelect.append('<option value="">请先选择科室</option>');
                        return;
                    }
                    
                    // 显示加载中
                    doctorSelect.append('<option value="">加载中...</option>');
                    
                    // 发送AJAX请求获取医生列表
                    $.ajax({
                        url: '${pageContext.request.contextPath}/doctor/list',
                        type: 'GET',
                        data: { deptId: deptId },
                        dataType: 'json',
                        success: function(doctors) {
                            // 清空医生列表
                            doctorSelect.empty();
                            
                            if (doctors.length === 0) {
                                doctorSelect.append('<option value="">该科室暂无医生</option>');
                                return;
                            }
                            
                            // 添加医生选项
                            doctorSelect.append('<option value="">请选择医生</option>');
                            $.each(doctors, function(index, doctor) {
                                doctorSelect.append('<option value="' + doctor.id + '">' + doctor.name + ' - ' + doctor.title + '</option>');
                            });
                        },
                        error: function() {
                            // 清空医生列表
                            doctorSelect.empty();
                            doctorSelect.append('<option value="">获取医生列表失败</option>');
                        }
                    });
                });
            });
        </script>
    </body>

    </html>