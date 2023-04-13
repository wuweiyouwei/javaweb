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
 * @date 2023/4/6 18:58
 * 详情查询
 */
public class DetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // 获取应用的根路径
        String contextPath = request.getContextPath();

        // 设置相应的内容类型以及字符集，防止中文乱码
        response.setContentType("text/html;character=UTF-8");
        PrintWriter out = response.getWriter();


        out.print("<!DOCTYPE html>");
        out.print("<html lang='en' xmlns:th='http://www.thymeleaf.org'>");
        out.print("<head>");
        out.print("    <meta charset='UTF-8'>");
        out.print("    <title>详情页面</title>");
        out.print("</head>");
        out.print("<body>");


        // 获取部门编号
        String deptNo = request.getParameter("deptno");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // 连接数据库
        try {
            conn = DBUtil.getConnection();
            // sql语句
            String sql = "select deptno,dname,loc from dept where deptno = ?";
            // 获取Statement
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptNo);
            // 执行Sql语句
            rs = ps.executeQuery();
            // 输出结果

            if (rs.next()) {
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                out.print("<h1>详情部门</h1>");
                out.print("<hr>");
                out.print("                部门编号：" + deptno + "<br>");
                out.print("                部门名称：" + dname + "<br>");
                out.print("        位置：" + loc + "<br>");
                out.print("<input type='button' value='后退' onclick='window.history.back()'>");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.Close(conn,ps,rs);
        }

        out.print("</body>");
        out.print("</html>");


    }
}
