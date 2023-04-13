<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>oa系统首页</title>
</head>
<body>


<h1>LOGIN PAGE</h1>
<%--<a href="<%=request.getContextPath()%>/user/login">查看部门列表</a>--%>


<form action="<%=request.getContextPath()%>/user/login" method="post">
    username:<input name="username"><br>
    password:<input name="password"><br>
    <input type="submit" value="login">
</form>
</body>
</html>
