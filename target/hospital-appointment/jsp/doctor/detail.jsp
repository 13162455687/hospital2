<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>医生详情</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <!-- 引入导航栏 -->
        <%@ include file="../common/header.jsp" %>

    <!-- 医生详情 -->
    <div class="container">
        <div class="doctor-detail mt-5">
            <div class="card">
                <div class="card-header">
                    <h2>医生详情</h2>
                </div>
                <div class="card-body">
                    <div class="row">
                        <!-- 医生基本信息 -->
                        <div class="col-md-4">
                            <div class="doctor-avatar-large">
                                <img src="${pageContext.request.contextPath}/images/doctor-default.jpg" alt="医生头像"
                                    class="img-fluid rounded-circle">
                            </div>
                            <div class="text-center mt-3">
                                <h3>张医生</h3>
                                <p class="text-primary">主任医师</p>
                                <p>内科</p>
                                <a href="${pageContext.request.contextPath}/jsp/appointment/register.jsp?doctorId=1"
                                    class="btn btn-success btn-block">立即预约</a>
                            </div>
                        </div>

                        <!-- 医生详细信息 -->
                        <div class="col-md-8">
                            <div class="doctor-info-detail">
                                <h4>基本信息</h4>
                                <hr>
                                <div class="row">
                                    <div class="col-md-6">
                                        <p><strong>姓名：</strong>张医生</p>
                                        <p><strong>性别：</strong>男</p>
                                        <p><strong>年龄：</strong>50岁</p>
                                        <p><strong>职称：</strong>主任医师</p>
                                    </div>
                                    <div class="col-md-6">
                                        <p><strong>科室：</strong>内科</p>
                                        <p><strong>联系电话：</strong>13900139001</p>
                                        <p><strong>出诊时间：</strong>周一、周三、周五</p>
                                        <p><strong>挂号费：</strong>100元</p>
                                    </div>
                                </div>

                                <h4 class="mt-4">专长</h4>
                                <hr>
                                <p>心血管疾病，包括冠心病、高血压、心律失常、心力衰竭等疾病的诊断和治疗。</p>

                                <h4 class="mt-4">简介</h4>
                                <hr>
                                <p>张医生从事心血管疾病临床工作20余年，积累了丰富的临床经验，擅长各种心血管疾病的诊断和治疗，尤其在冠心病介入治疗方面有较深的造诣。曾在国内外多家知名医院进修学习，发表学术论文数十篇。
                                </p>

                                <h4 class="mt-4">出诊安排</h4>
                                <hr>
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>日期</th>
                                            <th>时间段</th>
                                            <th>可预约人数</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>2025-12-05</td>
                                            <td>上午</td>
                                            <td>10/20</td>
                                            <td><a href="${pageContext.request.contextPath}/jsp/appointment/register.jsp?doctorId=1&date=2025-12-05&timeSlot=上午"
                                                    class="btn btn-primary btn-sm">预约</a></td>
                                        </tr>
                                        <tr>
                                            <td>2025-12-05</td>
                                            <td>下午</td>
                                            <td>8/20</td>
                                            <td><a href="${pageContext.request.contextPath}/jsp/appointment/register.jsp?doctorId=1&date=2025-12-05&timeSlot=下午"
                                                    class="btn btn-primary btn-sm">预约</a></td>
                                        </tr>
                                        <tr>
                                            <td>2025-12-07</td>
                                            <td>上午</td>
                                            <td>15/20</td>
                                            <td><a href="${pageContext.request.contextPath}/jsp/appointment/register.jsp?doctorId=1&date=2025-12-07&timeSlot=上午"
                                                    class="btn btn-primary btn-sm">预约</a></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
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
</body>

</html>