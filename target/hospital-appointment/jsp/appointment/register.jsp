<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>预约挂号 - 医院预约挂号系统</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <!-- 导航栏 -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <a class="navbar-brand d-flex align-items-center"
                    href="${pageContext.request.contextPath}/jsp/index.jsp">
                    <div class="hospital-logo me-3">🏥</div>
                    <span>医院预约挂号系统</span>
                </a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/jsp/index.jsp">首页</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/jsp/doctor/list.jsp">医生列表</a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link"
                                href="${pageContext.request.contextPath}/jsp/appointment/register.jsp">预约挂号 <span
                                    class="sr-only">(current)</span></a>
                        </li>
                        <% // 检查用户是否登录 if (session==null || session.getAttribute("user")==null) { %>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/jsp/user/login.jsp">登录</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link"
                                    href="${pageContext.request.contextPath}/jsp/user/register.jsp">注册</a>
                            </li>
                            <% } else { // 登录状态，显示用户信息 com.hospital.entity.User user=(com.hospital.entity.User)
                                session.getAttribute("user"); %>
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

        <!-- 主要内容 -->
        <div class="container mt-5">
            <% // 获取当前日期，用于日期选择器的min属性 java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy-MM-dd");
                String today=sdf.format(new java.util.Date()); // 获取URL中的doctorId参数 String
                doctorIdStr=request.getParameter("doctorId"); Integer doctorId=null; com.hospital.entity.Doctor
                selectedDoctor=null; if (doctorIdStr !=null && !doctorIdStr.isEmpty()) { try {
                doctorId=Integer.parseInt(doctorIdStr); // 获取医生信息 com.hospital.service.DoctorService doctorService=new
                com.hospital.service.impl.DoctorServiceImpl(); selectedDoctor=doctorService.findById(doctorId); } catch
                (Exception e) { e.printStackTrace(); // 异常处理：设置为null，不影响页面显示 selectedDoctor=null; doctorId=null; } } //
                获取URL中的日期和时间段参数 String preSelectedDate=request.getParameter("date"); String
                preSelectedTimeSlot=request.getParameter("timeSlot"); %>
                <h2 class="mb-4">预约挂号</h2>

                <% // 检查用户是否登录 if (session==null || session.getAttribute("user")==null) { %>
                    <div class="alert alert-warning text-center p-5" role="alert">
                        <h4 class="alert-heading">请先登录！</h4>
                        <p>预约挂号功能需要登录后才能使用，请先登录您的账号。</p>
                        <hr>
                        <div class="d-flex justify-content-center">
                            <a href="${pageContext.request.contextPath}/jsp/user/login.jsp"
                                class="btn btn-primary btn-lg mx-2">立即登录</a>
                            <a href="${pageContext.request.contextPath}/jsp/user/register.jsp"
                                class="btn btn-secondary btn-lg mx-2">注册账号</a>
                        </div>
                    </div>
                    <% } else { // 检查用户角色是否为患者 com.hospital.entity.User user=(com.hospital.entity.User)
                        session.getAttribute("user"); if (user.getRoleId() !=3) { %>
                        <div class="alert alert-danger text-center p-5" role="alert">
                            <h4 class="alert-heading">权限不足！</h4>
                            <p>只有患者角色才能进行预约挂号操作。</p>
                            <hr>
                            <div class="d-flex justify-content-center">
                                <a href="${pageContext.request.contextPath}/jsp/index.jsp"
                                    class="btn btn-primary btn-lg">返回首页</a>
                            </div>
                        </div>
                        <% } else { %>
                            <div class="card">
                                <div class="card-body">
                                    <form action="${pageContext.request.contextPath}/appointment?action=submit"
                                        method="post">
                                        <% if (selectedDoctor==null || selectedDoctor.getUser()==null) { %>
                                            <div class="alert alert-danger" role="alert">
                                                无效的医生信息，请从医生列表页面进入预约。
                                            </div>
                                            <% } else { %>
                                                <input type="hidden" id="doctor" name="doctorId"
                                                    value="<%= selectedDoctor.getId() %>">
                                                <div class="mb-3">
                                                    <label class="form-label">选择医生</label>
                                                    <div class="alert alert-info" role="alert">
                                                        <strong>
                                                            <%= selectedDoctor.getUser().getName() %> - <%=
                                                                    selectedDoctor.getTitle() %>
                                                        </strong>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-6 mb-3">
                                                        <label for="date" class="form-label">选择日期</label>
                                                        <input type="date" class="form-control" id="date" name="date"
                                                            min="<%= today %>"
                                                            value="<%= preSelectedDate != null ? preSelectedDate : "" %>"
                                                            required>
                                                    </div>

                                                    <div class="col-md-6 mb-3">
                                                        <label for="time" class="form-label">选择时间</label>
                                                        <select class="form-control" id="time" name="time">
                                                            <option value="">请选择时间</option>
                                                            <option value="上午" <%=preSelectedTimeSlot !=null &&
                                                                preSelectedTimeSlot.equals("上午") ? "selected" : "" %>>上午
                                                            </option>
                                                            <option value="下午" <%=preSelectedTimeSlot !=null &&
                                                                preSelectedTimeSlot.equals("下午") ? "selected" : "" %>>下午
                                                            </option>
                                                            <option value="晚上" <%=preSelectedTimeSlot !=null &&
                                                                preSelectedTimeSlot.equals("晚上") ? "selected" : "" %>>晚上
                                                            </option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="mb-3">
                                                    <label for="symptom" class="form-label">症状描述</label>
                                                    <textarea class="form-control" id="symptom" name="symptom" rows="3"
                                                        placeholder="请简要描述您的症状"></textarea>
                                                </div>

                                                <div class="mb-3">
                                                    <label for="name" class="form-label">就诊人姓名</label>
                                                    <input type="text" class="form-control" id="name" name="name"
                                                        placeholder="请输入就诊人姓名" required>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-6 mb-3">
                                                        <label for="phone" class="form-label">联系电话</label>
                                                        <input type="tel" class="form-control" id="phone" name="phone"
                                                            placeholder="请输入联系电话" required>
                                                    </div>

                                                    <div class="col-md-6 mb-3">
                                                        <label for="idCard" class="form-label">身份证号</label>
                                                        <input type="text" class="form-control" id="idCard"
                                                            name="idCard" placeholder="请输入身份证号" required>
                                                    </div>
                                                </div>

                                                <div class="form-check mb-3">
                                                    <input class="form-check-input" type="checkbox" id="agreement"
                                                        name="agreement" required>
                                                    <label class="form-check-label" for="agreement">
                                                        我已阅读并同意 <a href="#">《预约挂号须知》</a> 和 <a href="#">《隐私政策》</a>
                                                    </label>
                                                </div>

                                                <button type="submit" class="btn btn-primary btn-block">提交预约</button>
                                                <% } %>
                                    </form>
                                </div>
                            </div>
                            <% } %>
                                <% } %>
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
            $(document).ready(function () {
                // 添加错误提示容器
                if ($('#ajax-error-alert').length === 0) {
                    $('.container').prepend('<div id="ajax-error-alert" class="alert alert-danger fade show" role="alert" style="display: none;"></div>');
                }

                // 日期选择变化时，加载可用时间段
                $('#date').change(function () {
                    var doctorId = $('#doctor').val();
                    var date = $(this).val();
                    if (doctorId && date) {
                        loadAvailableTimeSlots(doctorId, date);
                    } else {
                        resetTimeSlots();
                    }
                });

                // 页面加载时，如果已经选择了医生和日期，自动加载可用时间段
                setTimeout(function () {
                    var doctorId = $('#doctor').val();
                    var date = $('#date').val();
                    if (doctorId && doctorId !== '' && date) {
                        loadAvailableTimeSlots(doctorId, date);
                    }
                }, 100);
            });

            // 加载可用时间段
            function loadAvailableTimeSlots(doctorId, date) {
                var timeSelect = $('#time');

                // 清空时间选择
                timeSelect.empty();

                // 显示加载状态
                timeSelect.append('<option value="">加载中...</option>');

                // 通过AJAX从服务器获取可用时间段
                $.ajax({
                    url: '${pageContext.request.contextPath}/schedule/available',
                    type: 'GET',
                    data: { doctorId: doctorId, date: date },
                    dataType: 'json',
                    success: function (response) {
                        // 清空加载状态
                        timeSelect.empty();

                        if (response && response.availableTimeSlots && response.availableTimeSlots.length > 0) {
                            timeSelect.append('<option value="">请选择时间</option>');
                            $.each(response.availableTimeSlots, function (index, slot) {
                                var preSelected = '<%= preSelectedTimeSlot != null ? preSelectedTimeSlot : "" %>';
                                var selected = preSelected === slot.timeSlot ? "selected" : "";
                                var disabled = slot.isFull ? "disabled" : "";
                                var text = slot.timeSlot + (slot.isFull ? " (已满)" : " (剩余" + slot.remainingNum + "个号)");
                                timeSelect.append("<option value=\"" + slot.timeSlot + "\" " + selected + " " + disabled + ">" + text + "</option>");
                            });
                        } else {
                            timeSelect.append("<option value=\"\">该日期暂无排班</option>");
                        }
                    },
                    error: function () {
                        resetTimeSlots();
                        // 显示错误提示
                        $('#ajax-error-alert').text('获取可用时间段失败，请刷新页面重试或稍后再试。');
                        $('#ajax-error-alert').show();
                        // 3秒后自动隐藏错误提示
                        setTimeout(function () {
                            $('#ajax-error-alert').hide();
                        }, 3000);
                    }
                });
            }

            // 重置时间段选择
            function resetTimeSlots() {
                var timeSelect = $("#time");
                timeSelect.empty();
                timeSelect.append("<option value=\"\">请选择时间</option>");
                timeSelect.append("<option value=\"上午\">上午</option>");
                timeSelect.append("<option value=\"下午\">下午</option>");
                timeSelect.append("<option value=\"晚上\">晚上</option>");
            }

            // 表单提交验证，确保不能预约过去的时间
            $("form").submit(function (e) {
                var selectedDate = $("#date").val();
                var today = new Date();
                today.setHours(0, 0, 0, 0);

                var appointmentDate = new Date(selectedDate);
                appointmentDate.setHours(0, 0, 0, 0);

                if (appointmentDate < today) {
                    e.preventDefault();
                    alert("不能预约过去的时间，请选择今天或未来的日期！");
                    return false;
                }

                // 验证时间是否已选择
                var timeSlot = $("#time").val();

                if (!timeSlot || timeSlot === "") {
                    e.preventDefault();
                    alert("请选择时间段！");
                    return false;
                }
            });
        </script>
    </body>

    </html>