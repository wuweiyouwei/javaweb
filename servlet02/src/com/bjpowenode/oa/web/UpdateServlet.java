package com.bjpowenode.oa.web;

import com.bjpowenode.oa.util.DBUtil;
import jakarta.servlet.ServletException;
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
 * @date 2023/4/9 9:29
 */
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();


out.print("        <!DOCTYPE html>");
out.print("<html lang='en' xmlns:th='http://www.thymeleaf.org'>");
out.print("<head>");
out.print("    <meta charset='UTF-8'>");
out.print("    <title>修改页面</title>");
out.print("</head>");
out.print("<body>");
out.print("<h1>修改部门</h1>");
out.print("<hr>");




        String contextPath = request.getContextPath();


        // 获取deptno
        String deptNo = request.getParameter("deptno");

        // 查询数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "select dname,loc from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptNo);
            rs = ps.executeQuery();
            if (rs.next()) {
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                // 将信息展示
                out.print("<form action='"+contextPath+"/dept/modify' method='post'>");
                out.print("                部门编号<input type='text' name='deptno' value='"+deptNo+"'><br>");
                out.print("                部门名称<input type='text' name='dname' value='"+dname+"'><br>");
                out.print("                位置<input type='text' name='loc' value='"+loc+"'><br>");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.Close(conn, ps, rs);
        }


        out.print("    <input type='submit' value='修改'><br>");
        out.print("</form>");
        out.print("</body>");
        out.print("</html>");

    }



}
