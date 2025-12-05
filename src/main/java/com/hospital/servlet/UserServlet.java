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
import java.util.List;

/**
 * 用户管理Servlet
 */
@WebServlet(name = "UserServlet", urlPatterns = "/admin/user")
public class UserServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(UserServlet.class);
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listUsers(request, response);
                break;
            case "add":
                // 直接跳转到添加页面，无需准备额外数据
                request.getRequestDispatcher("/jsp/admin/user/add.jsp").forward(request, response);
                break;
            case "edit":
                editUser(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            default:
                listUsers(request, response);
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
            case "add":
                addUser(request, response);
                break;
            case "update":
                updateUser(request, response);
                break;
            default:
                listUsers(request, response);
                break;
        }
    }

    /**
     * 列出所有用户
     */
    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<User> users = userService.findAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/jsp/admin/user/list.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("列出用户失败", e);
            request.setAttribute("error", "列出用户失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/user/list.jsp").forward(request, response);
        }
    }

    /**
     * 添加用户
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = new User();
            user.setUsername(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
            user.setName(request.getParameter("name"));
            user.setPhone(request.getParameter("phone"));
            user.setEmail(request.getParameter("email"));
            user.setRoleId(Integer.parseInt(request.getParameter("roleId")));
            user.setStatus(Integer.parseInt(request.getParameter("status")));

            // 密码加密
            userService.register(user);
            response.sendRedirect(request.getContextPath() + "/admin/user?action=list");
        } catch (Exception e) {
            logger.error("添加用户失败", e);
            request.setAttribute("error", "添加用户失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/user/add.jsp").forward(request, response);
        }
    }

    /**
     * 编辑用户
     */
    private void editUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                Integer id = Integer.parseInt(idStr);
                User user = userService.findById(id);
                request.setAttribute("user", user);
                request.getRequestDispatcher("/jsp/admin/user/edit.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("编辑用户失败", e);
            request.setAttribute("error", "编辑用户失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/user/list.jsp").forward(request, response);
        }
    }

    /**
     * 更新用户
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            User user = userService.findById(id);
            user.setName(request.getParameter("name"));
            user.setPhone(request.getParameter("phone"));
            user.setEmail(request.getParameter("email"));
            user.setRoleId(Integer.parseInt(request.getParameter("roleId")));
            user.setStatus(Integer.parseInt(request.getParameter("status")));

            // 如果密码不为空，则更新密码
            String password = request.getParameter("password");
            if (password != null && !password.isEmpty()) {
                user.setPassword(password);
            }

            userService.update(user);
            response.sendRedirect(request.getContextPath() + "/admin/user?action=list");
        } catch (Exception e) {
            logger.error("更新用户失败", e);
            request.setAttribute("error", "更新用户失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/user/edit.jsp").forward(request, response);
        }
    }

    /**
     * 删除用户
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            userService.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/user?action=list");
        } catch (Exception e) {
            logger.error("删除用户失败", e);
            request.setAttribute("error", "删除用户失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/user/list.jsp").forward(request, response);
        }
    }
}