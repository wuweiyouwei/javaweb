package com.bjpowenode.oa.web;

import com.bjpowenode.oa.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mv
 * @date 2023/4/6 17:35
 */
@WebServlet
public class ListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置相应的内容类型以及字符集，防止中文乱码
//        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // 获取应用的根路径
        String contextPath = request.getContextPath();


        out.print("<!DOCTYPE html>");
        out.print("<html lang='en' xmlns:th='http://www.thymeleaf.org'>");
        out.print("<head>");
        out.print("    <meta charset='UTF-8'>");
        out.print("    <title>部门列表页面</title>");

        out.print("<script type='text/javascript'>");
        out.print("    function del(dno) {");
        out.print("    if (window.confirm('亲，删了不可恢复哦')) {");
        out.print("        document.location.href = '" + contextPath + "/dept/delete?deptno='+ dno");
        out.print("    }");
        out.print("}");
        out.print("</script>");

        out.print("</head>");
        out.print("<body>");
        out.print("<h1 align='center'>部门列表</h1>");
        out.print("<hr>");
        out.print("<table border='1px' align='center' width='50%'>");
        out.print("   <tr>");
        out.print("        <th>序号</th>");
        out.print("        <th>部门编号</th>");
        out.print("        <th>部门名称</th>");
        out.print("        <th>操作</th>");
        out.print("    </tr>");


        // 连接数据库
        try {
            conn = DBUtil.getConnection();
            // sql语句
            String sql = "select deptno,dname,loc from dept";
            // 获取Statement
            ps = conn.prepareStatement(sql);
            // 执行Sql语句
            rs = ps.executeQuery();
            // 输出结果
            int i = 0;
            while (rs.next()) {
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                out.print("    <tr>");
                out.print("        <td>" + (++i) + "</td>");
                out.print("        <td>" + deptno + "</td>");
                out.print("        <td>" + dname + "</td>");
                out.print("        <td>");
                out.print("            <a href='"+contextPath+"/dept/update?deptno="+deptno+"'>修改</a>");
                out.print("            <a href='javascript:void(0)' onclick='del(" + deptno + ")'>删除</a>");
                out.print("            <a href='" + contextPath + "/dept/detail?deptno=" + deptno + "'>详情</a>");
                out.print("        </td>");
                out.print("    </tr>");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.Close(conn, ps, rs);
        }


        out.print("</table>");
        out.print("<br>");
        out.print("<a href='/aa/add.html'>新增部门</a>");
        out.print("</body>");
        out.print("</html>");


    }
}
