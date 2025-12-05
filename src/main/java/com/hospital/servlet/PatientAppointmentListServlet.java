package com.hospital.servlet;

import com.hospital.entity.Appointment;
import com.hospital.entity.User;
import com.hospital.service.AppointmentService;
import com.hospital.service.impl.AppointmentServiceImpl;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 患者预约记录Servlet
 * 用于显示患者的预约记录
 */
public class PatientAppointmentListServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(PatientAppointmentListServlet.class);
    private AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            // 1. 获取当前登录用户
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // 2. 获取用户的预约记录
            List<Appointment> appointments = appointmentService.findByUserId(user.getId());

            // 3. 将预约记录传递给页面
            request.setAttribute("appointments", appointments);
            request.getRequestDispatcher("/jsp/patient/appointment/list.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("获取预约记录失败", e);
            request.setAttribute("error", "获取预约记录失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/patient/index.jsp").forward(request, response);
        }
    }
}
