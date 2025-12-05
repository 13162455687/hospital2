package com.hospital.servlet;

import com.hospital.entity.Registration;
import com.hospital.service.RegistrationService;
import com.hospital.service.impl.RegistrationServiceImpl;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 挂号管理Servlet
 */
@WebServlet(name = "RegistrationServlet", urlPatterns = "/admin/registration")
public class RegistrationServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(RegistrationServlet.class);
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listRegistrations(request, response);
                break;
            case "edit":
                editRegistration(request, response);
                break;
            case "delete":
                deleteRegistration(request, response);
                break;
            default:
                listRegistrations(request, response);
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
                updateRegistration(request, response);
                break;
            default:
                listRegistrations(request, response);
                break;
        }
    }

    /**
     * 列出所有挂号
     */
    private void listRegistrations(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Registration> registrations = registrationService.findAll();
            request.setAttribute("registrations", registrations);
            request.getRequestDispatcher("/jsp/admin/registration/list.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("列出挂号失败", e);
            request.setAttribute("error", "列出挂号失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/registration/list.jsp").forward(request, response);
        }
    }

    /**
     * 编辑挂号
     */
    private void editRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                Integer id = Integer.parseInt(idStr);
                Registration registration = registrationService.findById(id);
                request.setAttribute("registration", registration);
                request.getRequestDispatcher("/jsp/admin/registration/edit.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("编辑挂号失败", e);
            request.setAttribute("error", "编辑挂号失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/registration/list.jsp").forward(request, response);
        }
    }

    /**
     * 更新挂号
     */
    private void updateRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Registration registration = registrationService.findById(id);

            registration.setCost(new BigDecimal(request.getParameter("cost")));
            registration.setPaymentStatus(Integer.parseInt(request.getParameter("paymentStatus")));
            registration.setDoctorAdvice(request.getParameter("doctorAdvice"));

            registrationService.update(registration);
            response.sendRedirect(request.getContextPath() + "/admin/registration?action=list");
        } catch (Exception e) {
            logger.error("更新挂号失败", e);
            request.setAttribute("error", "更新挂号失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/registration/edit.jsp").forward(request, response);
        }
    }

    /**
     * 删除挂号
     */
    private void deleteRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            registrationService.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/registration?action=list");
        } catch (Exception e) {
            logger.error("删除挂号失败", e);
            request.setAttribute("error", "删除挂号失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/registration/list.jsp").forward(request, response);
        }
    }
}