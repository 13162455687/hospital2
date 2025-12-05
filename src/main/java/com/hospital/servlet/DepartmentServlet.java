package com.hospital.servlet;

import com.hospital.entity.Department;
import com.hospital.service.DepartmentService;
import com.hospital.service.impl.DepartmentServiceImpl;
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
 * 科室管理Servlet
 */
@WebServlet(name = "DepartmentServlet", urlPatterns = "/admin/department")
public class DepartmentServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(DepartmentServlet.class);
    private DepartmentService departmentService = new DepartmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listDepartments(request, response);
                break;
            case "add":
                // 直接跳转到添加页面，无需准备额外数据
                request.getRequestDispatcher("/jsp/admin/department/add.jsp").forward(request, response);
                break;
            case "edit":
                editDepartment(request, response);
                break;
            case "delete":
                deleteDepartment(request, response);
                break;
            default:
                listDepartments(request, response);
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
                addDepartment(request, response);
                break;
            case "update":
                updateDepartment(request, response);
                break;
            default:
                listDepartments(request, response);
                break;
        }
    }

    /**
     * 列出所有科室
     */
    private void listDepartments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Department> departments = departmentService.findAll();
            request.setAttribute("departments", departments);
            request.getRequestDispatcher("/jsp/admin/department/list.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("列出科室失败", e);
            request.setAttribute("error", "列出科室失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/department/list.jsp").forward(request, response);
        }
    }

    /**
     * 添加科室
     */
    private void addDepartment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Department department = new Department();
            department.setDeptName(request.getParameter("name"));
            department.setDescription(request.getParameter("description"));
            department.setImageUrl(request.getParameter("imageUrl"));
            department.setStatus(Integer.parseInt(request.getParameter("status")));

            departmentService.save(department);
            response.sendRedirect(request.getContextPath() + "/admin/department?action=list");
        } catch (Exception e) {
            logger.error("添加科室失败", e);
            request.setAttribute("error", "添加科室失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/department/add.jsp").forward(request, response);
        }
    }

    /**
     * 编辑科室
     */
    private void editDepartment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                Integer id = Integer.parseInt(idStr);
                Department department = departmentService.findById(id);
                request.setAttribute("department", department);
                request.getRequestDispatcher("/jsp/admin/department/edit.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("编辑科室失败", e);
            request.setAttribute("error", "编辑科室失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/department/list.jsp").forward(request, response);
        }
    }

    /**
     * 更新科室
     */
    private void updateDepartment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Department department = departmentService.findById(id);
            department.setDeptName(request.getParameter("name"));
            department.setDescription(request.getParameter("description"));
            department.setImageUrl(request.getParameter("imageUrl"));
            department.setStatus(Integer.parseInt(request.getParameter("status")));

            departmentService.update(department);
            response.sendRedirect(request.getContextPath() + "/admin/department?action=list");
        } catch (Exception e) {
            logger.error("更新科室失败", e);
            request.setAttribute("error", "更新科室失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/department/edit.jsp").forward(request, response);
        }
    }

    /**
     * 删除科室
     */
    private void deleteDepartment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            departmentService.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/department?action=list");
        } catch (Exception e) {
            logger.error("删除科室失败", e);
            request.setAttribute("error", "删除科室失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/department/list.jsp").forward(request, response);
        }
    }
}