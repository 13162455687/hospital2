package com.hospital.servlet;

import com.hospital.entity.Appointment;
import com.hospital.entity.Schedule;
import com.hospital.entity.User;
import com.hospital.exception.HospitalException;
import com.hospital.service.AppointmentService;
import com.hospital.service.ScheduleService;
import com.hospital.service.impl.AppointmentServiceImpl;
import com.hospital.service.impl.ScheduleServiceImpl;
import com.hospital.util.DBUtil;
import com.hospital.util.ExceptionUtil;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 患者预约提交Servlet
 */
@WebServlet(name = "PatientAppointmentServlet", urlPatterns = "/appointment")
public class PatientAppointmentServlet extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(PatientAppointmentServlet.class);
    private AppointmentService appointmentService = new AppointmentServiceImpl();
    private ScheduleService scheduleService = new ScheduleServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "submit";
        }

        try {
            switch (action) {
                case "submit":
                    submitAppointment(request, response);
                    break;
                case "cancel":
                    cancelAppointment(request, response);
                    break;
                default:
                    submitAppointment(request, response);
                    break;
            }
        } catch (Exception e) {
            logger.error("处理预约请求失败", e);
            request.setAttribute("error", ExceptionUtil.handleException(e, "处理预约请求"));
            request.getRequestDispatcher("/jsp/error/exception.jsp").forward(request, response);
        }
    }

    /**
     * 提交预约
     */
    private void submitAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("error", "请先登录后再进行预约");
            request.getRequestDispatcher("/jsp/user/login.jsp").forward(request, response);
            return;
        }

        try {
            // 开始事务
            DBUtil.beginTransaction();
            logger.info("开始事务，准备保存预约");

            // 获取预约信息
            String deptIdStr = request.getParameter("deptId");
            String doctorIdStr = request.getParameter("doctor");
            String dateStr = request.getParameter("date");
            String timeSlot = request.getParameter("time");
            String symptom = request.getParameter("symptom");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String idCard = request.getParameter("idCard");
            
            logger.info("获取预约信息：deptId=" + deptIdStr + ", doctorId=" + doctorIdStr + ", dateStr=" + dateStr + ", timeSlot=" + timeSlot);

            // 验证必填字段
            if (deptIdStr == null || deptIdStr.isEmpty() || doctorIdStr == null || doctorIdStr.isEmpty() || dateStr == null || dateStr.isEmpty() || timeSlot == null || timeSlot.isEmpty() || name == null || name.isEmpty() || phone == null || phone.isEmpty() || idCard == null || idCard.isEmpty()) {
                throw new HospitalException("请填写完整的预约信息");
            }
            
            Integer deptId = Integer.parseInt(deptIdStr);
            Integer doctorId = Integer.parseInt(doctorIdStr);
            logger.info("验证通过，转换科室ID和医生ID成功");

            // 解析日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date appointmentDate = sdf.parse(dateStr);
            logger.info("解析日期成功：" + appointmentDate);

            // 检查排班是否存在
            Schedule schedule = scheduleService.findScheduleByDoctorDateAndTime(doctorId, appointmentDate, timeSlot);
            logger.info("查询到排班信息：" + (schedule != null ? "存在" : "不存在"));
            
            // 如果排班不存在，自动创建一条排班记录
            if (schedule == null) {
                logger.info("排班不存在，自动创建排班记录");
                schedule = new Schedule();
                schedule.setDoctorId(doctorId);
                schedule.setDate(appointmentDate);
                schedule.setTimeSlot(timeSlot);
                schedule.setMaxNum(10); // 默认最大预约数为10
                schedule.setCurrentNum(0); // 当前预约数为0
                schedule.setStatus(1); // 状态为有效
                
                // 保存排班记录
                boolean scheduleSaved = scheduleService.save(schedule);
                if (!scheduleSaved) {
                    throw new HospitalException("创建排班记录失败，请稍后重试");
                }
                logger.info("创建排班记录成功");
                
                // 重新查询排班信息，获取ID
                schedule = scheduleService.findScheduleByDoctorDateAndTime(doctorId, appointmentDate, timeSlot);
                if (schedule == null) {
                    throw new HospitalException("获取排班记录失败，请稍后重试");
                }
            }

            // 检查排班是否已满
            logger.info("排班当前预约数：" + schedule.getCurrentNum() + "，最大预约数：" + schedule.getMaxNum());
            if (schedule.getCurrentNum() >= schedule.getMaxNum()) {
                throw new HospitalException("该时间段的预约已经满了，请选择其他时间段");
            }

            // 检查患者是否有冲突的预约
            boolean hasConflict = appointmentService.checkAppointmentConflict(user.getId(), appointmentDate, timeSlot);
            logger.info("检查预约冲突结果：" + (hasConflict ? "有冲突" : "无冲突"));
            if (hasConflict) {
                throw new HospitalException("您在该时间段已经有其他预约，请选择其他时间段");
            }

            // 创建预约对象
            Appointment appointment = new Appointment();
            appointment.setUserId(user.getId());
            appointment.setDoctorId(doctorId);
            appointment.setScheduleId(schedule.getId());
            appointment.setAppointmentDate(appointmentDate);
            appointment.setTimeSlot(timeSlot);
            appointment.setStatus(1); // 1-待就诊
            logger.info("创建预约对象成功");

            // 保存预约
            boolean saved = appointmentService.save(appointment);
            logger.info("保存预约结果：" + (saved ? "成功" : "失败"));
            if (!saved) {
                throw new HospitalException("预约保存失败，请稍后重试");
            }

            // 更新排班表的当前预约数
            schedule.setCurrentNum(schedule.getCurrentNum() + 1);
            logger.info("更新排班当前预约数为：" + schedule.getCurrentNum());
            boolean updated = scheduleService.update(schedule);
            logger.info("更新排班结果：" + (updated ? "成功" : "失败"));
            if (!updated) {
                throw new HospitalException("更新排班信息失败，请稍后重试");
            }

            // 提交事务
            DBUtil.commitTransaction();
            logger.info("提交事务成功");

            // 记录系统日志
            LogUtil.recordSystemLog(request, user.getUsername(), "提交预约，医生ID：" + doctorId + "，日期：" + dateStr + "，时间段：" + timeSlot);

            // 预约成功，跳转到成功页面
            request.setAttribute("success", "预约成功！请记住您的预约信息，按时就诊");
            request.setAttribute("appointment", appointment);
            request.getRequestDispatcher("/jsp/appointment/success.jsp").forward(request, response);
        } catch (ParseException e) {
            DBUtil.rollbackTransaction();
            request.setAttribute("error", "日期格式错误，请重新输入");
            request.getRequestDispatcher("/jsp/appointment/register.jsp").forward(request, response);
        } catch (HospitalException e) {
            DBUtil.rollbackTransaction();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/jsp/appointment/register.jsp").forward(request, response);
        } catch (Exception e) {
            DBUtil.rollbackTransaction();
            logger.error("提交预约失败", e);
            request.setAttribute("error", ExceptionUtil.handleException(e, "提交预约"));
            request.getRequestDispatcher("/jsp/error/exception.jsp").forward(request, response);
        }
    }

    /**
     * 取消预约
     */
    private void cancelAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("error", "请先登录后再取消预约");
            request.getRequestDispatcher("/jsp/user/login.jsp").forward(request, response);
            return;
        }

        try {
            // 开始事务
            DBUtil.beginTransaction();

            // 获取预约ID
            Integer appointmentId = Integer.parseInt(request.getParameter("id"));

            // 查找预约
            Appointment appointment = appointmentService.findById(appointmentId);
            if (appointment == null) {
                throw new HospitalException("预约不存在");
            }

            // 检查预约是否属于当前用户
            if (!appointment.getUserId().equals(user.getId())) {
                throw new HospitalException("您没有权限取消该预约");
            }

            // 检查预约状态
            if (appointment.getStatus() != 1) {
                throw new HospitalException("该预约已无法取消");
            }

            // 获取排班信息
            Schedule schedule = scheduleService.findById(appointment.getScheduleId());
            if (schedule == null) {
                throw new HospitalException("排班信息不存在");
            }

            // 更新预约状态为已取消
            appointment.setStatus(3); // 3-已取消
            boolean updated = appointmentService.update(appointment);
            if (!updated) {
                throw new HospitalException("取消预约失败，请稍后重试");
            }

            // 更新排班表的当前预约数
            schedule.setCurrentNum(schedule.getCurrentNum() - 1);
            boolean scheduleUpdated = scheduleService.update(schedule);
            if (!scheduleUpdated) {
                throw new HospitalException("更新排班信息失败，请稍后重试");
            }

            // 提交事务
            DBUtil.commitTransaction();

            // 记录系统日志
            LogUtil.recordSystemLog(request, user.getUsername(), "取消预约，预约ID：" + appointmentId);

            // 取消成功，跳转到成功页面
            request.setAttribute("success", "预约已成功取消");
            request.getRequestDispatcher("/jsp/patient/index.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            DBUtil.rollbackTransaction();
            request.setAttribute("error", "无效的预约ID");
            request.getRequestDispatcher("/jsp/patient/index.jsp").forward(request, response);
        } catch (HospitalException e) {
            DBUtil.rollbackTransaction();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/jsp/patient/index.jsp").forward(request, response);
        } catch (Exception e) {
            DBUtil.rollbackTransaction();
            logger.error("取消预约失败", e);
            request.setAttribute("error", ExceptionUtil.handleException(e, "取消预约"));
            request.getRequestDispatcher("/jsp/error/exception.jsp").forward(request, response);
        }
    }
}