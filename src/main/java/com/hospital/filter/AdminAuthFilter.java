package com.hospital.filter;

import com.hospital.entity.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 管理员权限过滤器
 * 用于拦截所有管理员后台请求，确保只有管理员才能访问
 */
@WebFilter(filterName = "AdminAuthFilter", urlPatterns = "/jsp/admin/*")
public class AdminAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        // 获取当前用户
        User user = (User) session.getAttribute("user");

        // 检查用户是否已登录
        if (user == null) {
            // 未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 检查用户是否为管理员（roleId = 1）
        if (user.getRoleId() != 1) {
            // 非管理员，根据角色重定向到对应首页
            if (user.getRoleId() == 2) {
                // 医生，重定向到医生首页
                response.sendRedirect(request.getContextPath() + "/jsp/doctor/index.jsp");
            } else if (user.getRoleId() == 3) {
                // 患者，重定向到患者首页
                response.sendRedirect(request.getContextPath() + "/jsp/patient/index.jsp");
            } else {
                // 其他角色，重定向到登录页面
                response.sendRedirect(request.getContextPath() + "/login");
            }
            return;
        }

        // 管理员，放行请求
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 过滤器销毁
    }
}
