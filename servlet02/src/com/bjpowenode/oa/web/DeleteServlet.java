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
 * @date 2023/4/6 19:53
 */
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 获取部门编号
        String deptNo = request.getParameter("deptno");

        Connection conn = null;
        PreparedStatement ps = null;

        int count = 0;
        // 连接数据库删除数据
        try {
            conn = DBUtil.getConnection();
            // 开启事务
            conn.setAutoCommit(false);
            String sql = "delete  from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptNo);
            count = ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.Close(conn,ps,null);
        }

        // 删除成功
        if (count == 1) {
            // 跳转到list页面
            request.getRequestDispatcher("/dept/list").forward(request,response);
        } else {
            // 删除失败
            request.getRequestDispatcher("/error.html").forward(request,response);
        }

    }
}
