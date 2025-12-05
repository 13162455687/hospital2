package com.hospital.servlet;

import com.hospital.entity.Doctor;
import com.hospital.service.DoctorService;
import com.hospital.service.impl.DoctorServiceImpl;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 处理医生列表的AJAX请求
 */
@WebServlet(name = "DoctorListServlet", urlPatterns = "/doctor/list")
public class DoctorListServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(DoctorListServlet.class);
    private DoctorService doctorService = new DoctorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            // 记录请求参数
            String deptIdStr = request.getParameter("deptId");
            logger.info("接收到获取医生列表请求，科室ID：" + deptIdStr);
            
            if (deptIdStr == null || deptIdStr.isEmpty()) {
                logger.info("科室ID为空，返回空列表");
                out.write("[]");
                return;
            }

            Integer deptId = Integer.parseInt(deptIdStr);
            logger.info("转换科室ID为整数：" + deptId);

            // 获取医生列表
            List<Doctor> doctors = doctorService.findByDeptId(deptId);
            logger.info("获取到医生列表，数量：" + (doctors != null ? doctors.size() : 0));

            // 转换为JSON格式
            StringBuilder json = new StringBuilder("[");
            if (doctors != null && !doctors.isEmpty()) {
                for (int i = 0; i < doctors.size(); i++) {
                    Doctor doctor = doctors.get(i);
                    json.append("{");
                    json.append("\"id\":");
                    json.append(doctor.getId());
                    json.append(",\"name\":");
                    json.append("\"").append(doctor.getUser() != null ? doctor.getUser().getName() : "").append("\"");
                    json.append(",\"title\":");
                    json.append("\"").append(doctor.getTitle()).append("\"");
                    json.append("}");
                    if (i < doctors.size() - 1) {
                    json.append(",");
                    }
                    }
            }
            json.append("]");
            logger.info("生成JSON数据：" + json.toString());

            // 返回JSON数据
            out.write(json.toString());
        } catch (NumberFormatException e) {
            logger.error("科室ID格式错误：" + request.getParameter("deptId"), e);
            out.write("[]");
        } catch (Exception e) {
            logger.error("获取医生列表失败", e);
            out.write("[]");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}