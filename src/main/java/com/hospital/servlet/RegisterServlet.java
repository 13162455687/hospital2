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
import java.io.IOException;

/**
 * 注册Servlet
 */
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(RegisterServlet.class);
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 转发到注册页面
        request.getRequestDispatcher("/jsp/user/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            // 获取请求参数
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            // 创建用户对象
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setPhone(phone);
            user.setEmail(email);

            // 调用服务层进行注册
            userService.register(user);

            // 注册成功，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login?success=注册成功，请登录");
        } catch (Exception e) {
            logger.error("注册失败", e);
            // 注册失败，返回注册页面并显示错误信息
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/jsp/user/register.jsp").forward(request, response);
        }
    }
}