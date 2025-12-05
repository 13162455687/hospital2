package com.hospital.servlet;

import com.hospital.entity.SystemSetting;
import com.hospital.service.SystemSettingService;
import com.hospital.service.impl.SystemSettingServiceImpl;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统设置管理Servlet
 */
@WebServlet(name = "SystemSettingServlet", urlPatterns = "/admin/setting")
public class SystemSettingServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(SystemSettingServlet.class);
    private SystemSettingService systemSettingService = new SystemSettingServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "edit";
        }

        switch (action) {
            case "edit":
                editSetting(request, response);
                break;
            default:
                editSetting(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "update";
        }

        switch (action) {
            case "update":
                updateSetting(request, response);
                break;
            default:
                editSetting(request, response);
                break;
        }
    }

    /**
     * 编辑系统设置
     */
    private void editSetting(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SystemSetting systemSetting = systemSettingService.getSystemSetting();
            request.setAttribute("systemSetting", systemSetting);
            request.getRequestDispatcher("/jsp/admin/setting/index.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("编辑系统设置失败", e);
            request.setAttribute("error", "编辑系统设置失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/setting/index.jsp").forward(request, response);
        }
    }

    /**
     * 更新系统设置
     */
    private void updateSetting(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            SystemSetting systemSetting = systemSettingService.findById(id);

            systemSetting.setSystemName(request.getParameter("systemName"));
            systemSetting.setContactPhone(request.getParameter("contactPhone"));
            systemSetting.setContactEmail(request.getParameter("contactEmail"));
            systemSetting.setAddress(request.getParameter("address"));
            systemSetting.setCopyright(request.getParameter("copyright"));
            // logo_path暂时不处理文件上传，留待后续扩展
            // systemSetting.setLogoPath(request.getParameter("logoPath"));

            boolean result = systemSettingService.update(systemSetting);
            if (result) {
                request.setAttribute("success", "系统设置更新成功");
            } else {
                request.setAttribute("error", "系统设置更新失败");
            }
            editSetting(request, response);
        } catch (Exception e) {
            logger.error("更新系统设置失败", e);
            request.setAttribute("error", "更新系统设置失败：" + e.getMessage());
            editSetting(request, response);
        }
    }
}