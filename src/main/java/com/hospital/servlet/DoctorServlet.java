package com.hospital.servlet;

import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.User;
import com.hospital.service.DepartmentService;
import com.hospital.service.DoctorService;
import com.hospital.service.UserService;
import com.hospital.service.impl.DepartmentServiceImpl;
import com.hospital.service.impl.DoctorServiceImpl;
import com.hospital.service.impl.UserServiceImpl;
import com.hospital.util.LogUtil;
import com.hospital.util.PasswordUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 医生管理Servlet
 */
@WebServlet(name = "DoctorServlet", urlPatterns = "/admin/doctor")
public class DoctorServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(DoctorServlet.class);
    private DoctorService doctorService = new DoctorServiceImpl();
    private UserService userService = new UserServiceImpl();
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
                listDoctors(request, response);
                break;
            case "add":
                showAddDoctorPage(request, response);
                break;
            case "edit":
                editDoctor(request, response);
                break;
            case "delete":
                deleteDoctor(request, response);
                break;
            default:
                listDoctors(request, response);
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
                addDoctor(request, response);
                break;
            case "update":
                updateDoctor(request, response);
                break;
            default:
                listDoctors(request, response);
                break;
        }
    }

    /**
     * 列出所有医生
     */
    private void listDoctors(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Doctor> doctors = doctorService.findAll();
            request.setAttribute("doctors", doctors);
            request.getRequestDispatcher("/jsp/admin/doctor/list.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("列出医生失败", e);
            request.setAttribute("error", "列出医生失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/doctor/list.jsp").forward(request, response);
        }
    }
    
    /**
     * 跳转到添加医生页面，携带科室列表
     */
    private void showAddDoctorPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 获取所有科室列表，用于下拉选择
            DepartmentService departmentService = new DepartmentServiceImpl();
            List<Department> departments = departmentService.findAll();
            request.setAttribute("departments", departments);
            request.getRequestDispatcher("/jsp/admin/doctor/add.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("跳转到添加医生页面失败", e);
            request.setAttribute("error", "跳转到添加医生页面失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/doctor/list.jsp").forward(request, response);
        }
    }

    /**
     * 添加医生
     */
    private void addDoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. 创建用户记录
            User user = new User();
            user.setName(request.getParameter("name"));
            user.setPhone(request.getParameter("phone"));
            // 生成默认用户名（基于手机号）
            user.setUsername("doctor_" + request.getParameter("phone").substring(request.getParameter("phone").length() - 6));
            // 生成默认密码（基于手机号后6位）
            String defaultPassword = request.getParameter("phone").substring(request.getParameter("phone").length() - 6);
            user.setPassword(PasswordUtil.md5(defaultPassword));
            // 设置角色为医生
            user.setRoleId(2);
            // 设置状态
            user.setStatus(Integer.parseInt(request.getParameter("status")));
            // 保存用户
            userService.register(user);
            // 获取保存后的用户（需要从数据库重新查询，因为register方法没有返回生成的ID）
            User savedUser = userService.findByPhone(user.getPhone());
            
            // 2. 创建医生记录
            Doctor doctor = new Doctor();
            doctor.setUserId(savedUser.getId());
            doctor.setDeptId(Integer.parseInt(request.getParameter("deptId")));
            doctor.setTitle(request.getParameter("title"));
            doctor.setSpecialty(request.getParameter("specialty"));
            doctor.setAvatar(request.getParameter("avatar"));
            doctor.setStatus(Integer.parseInt(request.getParameter("status")));

            doctorService.save(doctor);
            response.sendRedirect(request.getContextPath() + "/admin/doctor?action=list");
        } catch (Exception e) {
            logger.error("添加医生失败", e);
            request.setAttribute("error", "添加医生失败：" + e.getMessage());
            // 重新获取科室列表，以便返回添加页面时显示
            List<Department> departments = departmentService.findAll();
            request.setAttribute("departments", departments);
            request.getRequestDispatcher("/jsp/admin/doctor/add.jsp").forward(request, response);
        }
    }

    /**
     * 编辑医生
     */
    private void editDoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                Integer id = Integer.parseInt(idStr);
                Doctor doctor = doctorService.findById(id);
                request.setAttribute("doctor", doctor);
                // 获取所有科室列表，用于下拉选择
                List<Department> departments = departmentService.findAll();
                request.setAttribute("departments", departments);
                request.getRequestDispatcher("/jsp/admin/doctor/edit.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("编辑医生失败", e);
            request.setAttribute("error", "编辑医生失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/doctor/list.jsp").forward(request, response);
        }
    }

    /**
     * 更新医生
     */
    private void updateDoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Doctor doctor = doctorService.findById(id);
            
            // 1. 更新关联的用户信息
            User user = userService.findById(doctor.getUserId());
            user.setName(request.getParameter("name"));
            user.setPhone(request.getParameter("phone"));
            user.setStatus(Integer.parseInt(request.getParameter("status")));
            userService.update(user);
            
            // 2. 更新医生信息
            doctor.setDeptId(Integer.parseInt(request.getParameter("deptId")));
            doctor.setTitle(request.getParameter("title"));
            doctor.setSpecialty(request.getParameter("specialty"));
            doctor.setAvatar(request.getParameter("avatar"));
            doctor.setStatus(Integer.parseInt(request.getParameter("status")));

            doctorService.update(doctor);
            response.sendRedirect(request.getContextPath() + "/admin/doctor?action=list");
        } catch (Exception e) {
            logger.error("更新医生失败", e);
            request.setAttribute("error", "更新医生失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/doctor/edit.jsp").forward(request, response);
        }
    }

    /**
     * 删除医生
     */
    private void deleteDoctor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            doctorService.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/doctor?action=list");
        } catch (Exception e) {
            logger.error("删除医生失败", e);
            request.setAttribute("error", "删除医生失败：" + e.getMessage());
            request.getRequestDispatcher("/jsp/admin/doctor/list.jsp").forward(request, response);
        }
    }
}