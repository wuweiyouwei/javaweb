package com.bjpowenode.oa.web;

import com.bjpowenode.oa.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Mv
 * @date 2023/4/9 9:43
 */
public class ModifyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取应用的根路径
        String contextPath = request.getContextPath();

        // 设置相应的内容类型以及字符集，防止中文乱码
        response.setContentType("text/html;charset=UTF-8");

        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");

        // 添加到数据库
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "update dept set dname = ?,loc = ? WHERE deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(3, deptno);
            ps.setString(2, dname);
            ps.setString(1, loc);

            count = ps.executeUpdate();

            if (count == 1) {
                // 添加成功
                //request.getRequestDispatcher("/dept/list").forward(request, response);
                // 使用重定向即可
                response.sendRedirect(contextPath + "/dept/list");
            } else {
                //request.getRequestDispatcher("/error.html").forward(request, response);
                response.sendRedirect(contextPath + "/error.html");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.Close(conn, ps, null);
        }

    }
}
