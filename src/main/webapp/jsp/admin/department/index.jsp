<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%-- 重定向到实际的科室管理Servlet --%>
        <% response.sendRedirect(request.getContextPath() + "/admin/department?action=list" ); %>