package com.hospital.servlet;

import com.hospital.entity.User;
import com.hospital.service.UserService;
import com.hospital.service.impl.UserServiceImpl;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录Servlet
 */
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(LoginServlet.class);
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取success参数
        String success = request.getParameter("success");
        if (success != null && !success.trim().isEmpty()) {
            request.setAttribute("success", success);
        }
        // 转发到登录页面
        request.getRequestDispatcher("/jsp/user/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String username = null;
        try {
            // 获取请求参数
            username = request.getParameter("username");
            String password = request.getParameter("password");

            logger.info("接收到登录请求，用户名: " + username + ", 密码长度: " + (password != null ? password.length() : 0));

            // 参数验证
            if (username == null || username.trim().isEmpty()) {
                logger.warn("用户名不能为空，登录请求被拒绝");
                throw new RuntimeException("用户名不能为空");
            }
            if (password == null || password.trim().isEmpty()) {
                logger.warn("密码不能为空，登录请求被拒绝，用户: " + username);
                throw new RuntimeException("密码不能为空");
            }

            username = username.trim();
            logger.info("用户尝试登录: " + username);

            // 调用服务层进行登录
            User user = userService.login(username, password);

            // 登录成功，将用户信息存入会话
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            logger.info("用户登录成功: " + username + ", 角色ID: " + user.getRoleId() + ", 用户ID: " + user.getId());

            // 根据用户角色跳转到不同页面
            if (user.getRoleId() == 1) { // 管理员
                logger.info("管理员登录，跳转到管理后台");
                response.sendRedirect(request.getContextPath() + "/jsp/admin/index.jsp");
            } else if (user.getRoleId() == 2) { // 医生
                logger.info("医生登录，跳转到医生页面");
                response.sendRedirect(request.getContextPath() + "/jsp/doctor/index.jsp");
            } else { // 患者
                logger.info("患者登录，跳转到患者页面");
                response.sendRedirect(request.getContextPath() + "/jsp/patient/index.jsp");
            }
        } catch (Exception e) {
            logger.error("登录失败，用户名: " + username, e);
            // 登录失败，返回登录页面并显示友好错误信息
            String errorMessage;
            if (e.getMessage() != null) {
                logger.error("登录错误详情: " + e.getMessage());
                if (e.getMessage().contains("用户名或密码错误") || 
                    e.getMessage().contains("用户已禁用") || 
                    e.getMessage().contains("用户名不能为空") || 
                    e.getMessage().contains("密码不能为空")) {
                    errorMessage = e.getMessage();
                } else {
                    // 对于系统异常，不直接暴露给用户，但记录详细日志
                    logger.error("系统异常: " + e.getMessage(), e);
                    errorMessage = "登录过程中发生系统异常，请稍后重试或联系管理员";
                }
            } else {
                logger.error("未知登录异常", e);
                errorMessage = "登录过程中发生未知异常，请稍后重试或联系管理员";
            }
            request.setAttribute("error", errorMessage);
            logger.info("返回登录页面，错误信息: " + errorMessage);
            request.getRequestDispatcher("/jsp/user/login.jsp").forward(request, response);
        }
    }
}