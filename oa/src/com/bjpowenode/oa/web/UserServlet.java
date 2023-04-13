package com.bjpowenode.oa.web;

import com.bjpowenode.oa.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mv
 * @date 2023/4/12 16:39
 */
@WebServlet("/user/login")
public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        boolean success = false;

        String username = request.getParameter("username");
        String password = request.getParameter("password");




        // 连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "select id from t_user where username = ? and password=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            // 查询部门信息
            rs = ps.executeQuery();

            if (rs.next()) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.Close(conn, ps, rs);
        }

        // 登录成功
        if (success) {
            // 获取session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            // 登录成功，跳转到 list
            response.sendRedirect(request.getContextPath() + "/dept/list");
        } else {
            // 登陆失败，重新登录
            response.sendRedirect(request.getContextPath() + "/errorLogin.jsp");
        }
    }

}
