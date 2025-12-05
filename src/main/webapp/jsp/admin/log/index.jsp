<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%-- 重定向到实际的系统日志Servlet --%>
        <% response.sendRedirect(request.getContextPath() + "/admin/systemlog?action=list" ); %>