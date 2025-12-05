<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%-- 重定向到实际的用户管理Servlet --%>
        <% response.sendRedirect(request.getContextPath() + "/admin/user?action=list" ); %>