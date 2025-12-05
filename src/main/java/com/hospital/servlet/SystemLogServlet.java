package com.hospital.servlet;

import com.hospital.entity.SystemLog;
import com.hospital.service.SystemLogService;
import com.hospital.service.impl.SystemLogServiceImpl;
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
 * 系统日志管理Servlet
 */
@WebServlet(name = "SystemLogServlet", urlPatterns = "/admin/log")
public class SystemLogServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(SystemLogServlet.class);
    private SystemLogService systemLogService = new SystemLogServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listSystemLogs(request, response);
                break;
            case "delete":
                deleteSystemLog(request, response);
                break;
            default:
                listSystemLogs(request, response);
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
            case "deleteBatch":
                deleteBatchSystemLogs(request, response);
                break;
            default:
                listSystemLogs(request, response);
                break;
        }
    }

    /**
     * 列出所有系统日志
     */
    private void listSystemLogs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<SystemLog> systemLogs = systemLogService.findAll();
            request.setAttribute("systemLogs", systemLogs);
            request.getRequestDispatcher("/jsp/admin/log/list.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("列出系统日志失败", e);
            request.setAttribute("error", "列出系统日志失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/log/list.jsp").forward(request, response);
        }
    }

    /**
     * 删除系统日志
     */
    private void deleteSystemLog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            systemLogService.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/log?action=list");
        } catch (Exception e) {
            logger.error("删除系统日志失败", e);
            request.setAttribute("error", "删除系统日志失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/log/list.jsp").forward(request, response);
        }
    }

    /**
     * 批量删除系统日志
     */
    private void deleteBatchSystemLogs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String[] idStrs = request.getParameterValues("ids");
            if (idStrs != null && idStrs.length > 0) {
                Integer[] ids = new Integer[idStrs.length];
                for (int i = 0; i < idStrs.length; i++) {
                    ids[i] = Integer.parseInt(idStrs[i]);
                }
                systemLogService.deleteBatch(ids);
            }
            response.sendRedirect(request.getContextPath() + "/admin/log?action=list");
        } catch (Exception e) {
            logger.error("批量删除系统日志失败", e);
            request.setAttribute("error", "批量删除系统日志失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/log/list.jsp").forward(request, response);
        }
    }
}