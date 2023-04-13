<%@ page import="com.bjpowenode.oa.bean.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>详情页面</title>
</head>
<body>
<h1>详情部门</h1>
<hr>

<%
    Dept dept = (Dept) request.getAttribute("dept");
%>

部门编号：<%=dept.getDeptno()%><br>
部门名称：<%=dept.getDname()%><br>
位置：<%=dept.getLoc()%><br>
<input type="button" value="后退" onclick="window.history.back()">
</body>
</html>