package com.hospital.servlet;

import com.hospital.entity.Appointment;
import com.hospital.service.AppointmentService;
import com.hospital.service.impl.AppointmentServiceImpl;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 预约管理Servlet
 */
@WebServlet(name = "AppointmentServlet", urlPatterns = "/admin/appointment")
public class AppointmentServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(AppointmentServlet.class);
    private AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listAppointments(request, response);
                break;
            case "edit":
                editAppointment(request, response);
                break;
            case "delete":
                deleteAppointment(request, response);
                break;
            default:
                listAppointments(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "update":
                updateAppointment(request, response);
                break;
            default:
                listAppointments(request, response);
                break;
        }
    }

    /**
     * 列出所有预约
     */
    private void listAppointments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Appointment> appointments = appointmentService.findAll();
            request.setAttribute("appointments", appointments);
            request.getRequestDispatcher("/jsp/admin/appointment/list.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("列出预约失败", e);
            request.setAttribute("error", "列出预约失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/appointment/list.jsp").forward(request, response);
        }
    }

    /**
     * 编辑预约
     */
    private void editAppointment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                Integer id = Integer.parseInt(idStr);
                Appointment appointment = appointmentService.findById(id);
                request.setAttribute("appointment", appointment);
                request.getRequestDispatcher("/jsp/admin/appointment/edit.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("编辑预约失败", e);
            request.setAttribute("error", "编辑预约失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/appointment/list.jsp").forward(request, response);
        }
    }

    /**
     * 更新预约
     */
    private void updateAppointment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Appointment appointment = appointmentService.findById(id);
            appointment.setStatus(Integer.parseInt(request.getParameter("status")));

            appointmentService.update(appointment);
            response.sendRedirect(request.getContextPath() + "/admin/appointment?action=list");
        } catch (Exception e) {
            logger.error("更新预约失败", e);
            request.setAttribute("error", "更新预约失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/appointment/edit.jsp").forward(request, response);
        }
    }

    /**
     * 删除预约
     */
    private void deleteAppointment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            appointmentService.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/appointment?action=list");
        } catch (Exception e) {
            logger.error("删除预约失败", e);
            request.setAttribute("error", "删除预约失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/appointment/list.jsp").forward(request, response);
        }
    }
}