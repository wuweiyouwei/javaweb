package com.bjpowernode;

import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @author Mv
 * @date 2023/3/29 13:45
 */
public class UserServlet implements Servlet {


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

        // 设置响应类型
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        // 连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 获取数据库连接
            String url = "jdbc:mysql://localhost:3307/yupi";
            String username = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, username, password);
            // 获取与预编译数据库对象
            String sql = "select username from user";
            ps = conn.prepareStatement(sql);
            // 执行sql语句
            rs = ps.executeQuery();
            // 处理结果集
            while (rs != null) {
                String userName = rs.getString("username");
                out.print(userName);
                System.out.println(userName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
