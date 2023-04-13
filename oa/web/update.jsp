<%@ page import="com.bjpowenode.oa.bean.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>修改页面</title>
</head>
<body>
<h1>修改部门</h1>
<hr>
<form action="<%=request.getContextPath()%>/dept/modify" method="post">
    <%
        Dept dept = (Dept) request.getAttribute("dept");
    %>
    部门编号<input type="text" name="deptno" value="<%=dept.getDeptno()%>"><br>
    部门名称<input type="text" name="dname" value="<%=dept.getDname()%>"><br>
    位置<input type="text" name="loc" value="<%=dept.getLoc()%>"><br>
    <input type="submit" value="修改"><br>
</form>
</body>
</html>
