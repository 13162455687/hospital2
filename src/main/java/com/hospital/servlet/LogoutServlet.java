package com.hospital.servlet;

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
 * 退出登录Servlet
 */
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取当前会话
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 移除用户信息
            session.removeAttribute("user");
            // 使会话失效
            session.invalidate();
            logger.info("用户退出登录成功");
        }
        // 重定向到首页
        response.sendRedirect(request.getContextPath() + "/jsp/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}