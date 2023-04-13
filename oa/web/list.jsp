<%@ page import="java.util.List" %>
<%@ page import="com.bjpowenode.oa.bean.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang='en' xmlns:th='http://www.thymeleaf.org'>
<head>
    <meta charset='UTF-8'>
    <title>部门列表页面</title>

    <script type='text/javascript'>
        function del(dno) {
            if (window.confirm('亲，删了不可恢复哦')) {
                document.location.href = '<%=request.getContextPath()%>/dept/delete?deptno='+dno
            }

        }
    </script>

</head>
<body>
<h1 align='center'>部门列表</h1>
<hr>
<table border='1px' align='center' width='50%'>
    <tr>
        <th>序号</th>
        <th>部门编号</th>
        <th>部门名称</th>
        <th>操作</th>
    </tr>

    <%
        String contextPath = request.getContextPath();
        int i = 0;
        List<Dept> deptList = (List<Dept>) request.getAttribute("deptList");
        for (Dept dept : deptList) {
            String deptno = dept.getDeptno();
            String dname = dept.getDname();
            String loc = dept.getLoc();
    %>

    <tr>
            <td><%=(++i)%></td>
            <td><%=deptno%></td>
            <td><%=dname%></td>
            <td>
                <a href='<%=contextPath%>/dept/update?deptno=<%=deptno%>'>修改</a>
                <a href='javascript:void(0)' onclick='del(<%=deptno%>)'>删除</a>
                <a href='<%=contextPath%>/dept/detail?deptno=<%=deptno%>'>详情</a>
            </td>
    </tr>

    <%
        }
    %>


</table>
<br>
<a href='<%=request.getContextPath()%>/add.jsp'>新增部门</a>
</body>
</html>
