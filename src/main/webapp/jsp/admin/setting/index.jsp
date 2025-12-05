<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%-- 重定向到实际的系统设置Servlet --%>
        <% response.sendRedirect(request.getContextPath() + "/admin/setting?action=edit" ); %>