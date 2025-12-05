package com.hospital.servlet;

import com.hospital.entity.Schedule;
import com.hospital.service.ScheduleService;
import com.hospital.service.impl.ScheduleServiceImpl;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 排班相关Servlet
 */
@WebServlet(name = "ScheduleServlet", urlPatterns = "/schedule/*")
public class ScheduleServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(ScheduleServlet.class);
    private ScheduleService scheduleService = new ScheduleServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String pathInfo = request.getPathInfo();
        logger.info("处理排班请求，pathInfo: " + pathInfo);

        try {
            if ("/available".equals(pathInfo)) {
                getAvailableTimeSlots(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("处理排班请求失败", e);
            PrintWriter out = response.getWriter();
            out.write("{\"error\":\"处理请求失败\"}");
            out.flush();
        }
    }

    /**
     * 获取医生在指定日期的可用时间段
     */
    private void getAvailableTimeSlots(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ParseException {
        // 获取请求参数
        String doctorIdStr = request.getParameter("doctorId");
        String dateStr = request.getParameter("date");

        logger.info("获取可用时间段，doctorId: " + doctorIdStr + ", date: " + dateStr);

        // 验证参数
        if (doctorIdStr == null || doctorIdStr.isEmpty() || dateStr == null || dateStr.isEmpty()) {
            PrintWriter out = response.getWriter();
            out.write("{\"error\":\"参数错误\"}");
            out.flush();
            return;
        }

        Integer doctorId = Integer.parseInt(doctorIdStr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr);

        // 预定义可能的时间段
        List<String> allTimeSlots = new ArrayList<>();
        allTimeSlots.add("上午");
        allTimeSlots.add("下午");
        allTimeSlots.add("晚上");

        // 查询排班信息
        // 转换日期类型，从java.util.Date转换为java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        List<Schedule> schedules = scheduleService.findByDoctorIdAndDate(doctorId, sqlDate);

        // 构建可用时间段响应
        StringBuilder responseJson = new StringBuilder("{\"availableTimeSlots\":[");

        for (int i = 0; i < allTimeSlots.size(); i++) {
            String timeSlot = allTimeSlots.get(i);
            Schedule schedule = null;

            // 查找对应时间段的排班
            for (Schedule s : schedules) {
                if (s.getTimeSlot().equals(timeSlot)) {
                    schedule = s;
                    break;
                }
            }

            int remainingNum = 0;
            boolean isFull = true;

            if (schedule != null) {
                remainingNum = schedule.getMaxNum() - schedule.getCurrentNum();
                isFull = remainingNum <= 0;
            } else {
                // 如果排班不存在，默认剩余0个号
                remainingNum = 0;
                isFull = true;
            }

            // 添加到响应JSON
            responseJson.append("{\"timeSlot\":\"")
                    .append(timeSlot)
                    .append("\",\"remainingNum\":")
                    .append(remainingNum)
                    .append(",\"isFull\":")
                    .append(isFull)
                    .append("}");

            if (i < allTimeSlots.size() - 1) {
                responseJson.append(",");
            }
        }

        responseJson.append("]}");

        logger.info("返回可用时间段: " + responseJson.toString());

        // 返回JSON响应
        PrintWriter out = response.getWriter();
        out.write(responseJson.toString());
        out.flush();
    }
}