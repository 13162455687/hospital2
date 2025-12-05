package com.hospital.servlet;

import com.hospital.entity.Appointment;
import com.hospital.entity.User;
import com.hospital.exception.HospitalException;
import com.hospital.service.AppointmentService;
import com.hospital.service.UserService;
import com.hospital.service.impl.AppointmentServiceImpl;
import com.hospital.service.impl.UserServiceImpl;
import com.hospital.util.ExceptionUtil;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 患者Servlet
 */
@WebServlet(name = "PatientServlet", urlPatterns = "/patient/*")
public class PatientServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(PatientServlet.class);
    private AppointmentService appointmentService = new AppointmentServiceImpl();
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }

        try {
            switch (pathInfo) {
                case "/":
                case "/index":
                    showPatientHome(request, response);
                    break;
                case "/appointments":
                    showMyAppointments(request, response);
                    break;
                case "/appointment/cancel":
                    cancelAppointment(request, response);
                    break;
                case "/profile":
                    showProfile(request, response);
                    break;
                case "/profile/save":
                    saveProfile(request, response);
                    break;
                default:
                    showPatientHome(request, response);
                    break;
            }
        } catch (Exception e) {
            logger.error("处理患者请求失败", e);
            request.setAttribute("error", ExceptionUtil.handleException(e, "处理患者请求"));
            request.getRequestDispatcher("/jsp/error/exception.jsp").forward(request, response);
        }
    }

    /**
     * 显示患者首页
     */
    private void showPatientHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 检查用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("error", "请先登录后再访问患者中心");
            request.getRequestDispatcher("/jsp/user/login.jsp").forward(request, response);
            return;
        }

        // 获取患者的预约历史
        List<Appointment> appointments = appointmentService.findByUserId(user.getId());
        request.setAttribute("appointments", appointments);

        // 转发到患者首页
        request.getRequestDispatcher("/jsp/patient/index.jsp").forward(request, response);
    }

    /**
     * 显示我的预约
     */
    private void showMyAppointments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 检查用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("error", "请先登录后再查看预约");
            request.getRequestDispatcher("/jsp/user/login.jsp").forward(request, response);
            return;
        }

        // 获取患者的预约历史
        List<Appointment> appointments = appointmentService.findByUserId(user.getId());
        request.setAttribute("appointments", appointments);

        // 转发到我的预约页面
        request.getRequestDispatcher("/jsp/patient/appointments.jsp").forward(request, response);
    }

    /**
     * 取消预约
     */
    private void cancelAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("error", "请先登录后再取消预约");
            request.getRequestDispatcher("/jsp/user/login.jsp").forward(request, response);
            return;
        }

        try {
            Integer appointmentId = Integer.parseInt(request.getParameter("id"));
            if (appointmentId == null) {
                throw new HospitalException("无效的预约ID");
            }

            // 调用PatientAppointmentServlet的取消预约方法
            response.sendRedirect(request.getContextPath() + "/appointment?action=cancel&id=" + appointmentId);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "无效的预约ID");
            showMyAppointments(request, response);
        } catch (HospitalException e) {
            request.setAttribute("error", e.getMessage());
            showMyAppointments(request, response);
        } catch (Exception e) {
            logger.error("取消预约失败", e);
            request.setAttribute("error", ExceptionUtil.handleException(e, "取消预约"));
            request.getRequestDispatcher("/jsp/error/exception.jsp").forward(request, response);
        }
    }
    
    /**
     * 显示个人信息
     */
    private void showProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("error", "请先登录后再查看个人信息");
            request.getRequestDispatcher("/jsp/user/login.jsp").forward(request, response);
            return;
        }
        
        // 获取最新的用户信息
        User updatedUser = userService.findById(user.getId());
        if (updatedUser != null) {
            // 更新session中的用户信息
            request.getSession().setAttribute("user", updatedUser);
        }
        
        // 转发到个人信息页面
        request.getRequestDispatcher("/jsp/patient/profile.jsp").forward(request, response);
    }
    
    /**
     * 保存个人信息
     */
    private void saveProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("error", "请先登录后再修改个人信息");
            request.getRequestDispatcher("/jsp/user/login.jsp").forward(request, response);
            return;
        }
        
        try {
            // 获取表单数据
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            
            // 验证必填字段
            if (name == null || name.trim().isEmpty()) {
                throw new HospitalException("姓名不能为空");
            }
            
            if (phone == null || phone.trim().isEmpty()) {
                throw new HospitalException("手机号不能为空");
            }
            
            // 更新用户信息
            user.setName(name);
            user.setPhone(phone);
            user.setEmail(email);
            
            // 保存更新
            userService.update(user);
            
            // 更新session中的用户信息
            request.getSession().setAttribute("user", user);
            
            // 记录系统日志
            LogUtil.recordSystemLog(request, user.getUsername(), "修改个人信息");
            
            // 显示成功信息
            request.setAttribute("success", "个人信息更新成功");
            
            // 重新显示个人信息页面
            showProfile(request, response);
        } catch (HospitalException e) {
            request.setAttribute("error", e.getMessage());
            showProfile(request, response);
        } catch (Exception e) {
            logger.error("保存个人信息失败", e);
            request.setAttribute("error", ExceptionUtil.handleException(e, "保存个人信息"));
            showProfile(request, response);
        }
    }
}