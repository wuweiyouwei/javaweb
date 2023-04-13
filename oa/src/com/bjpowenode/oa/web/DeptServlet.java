package com.bjpowenode.oa.web;

import com.bjpowenode.oa.bean.Dept;
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
import java.util.ArrayList;

/**
 * @author Mv
 * @date 2023/4/11 9:32
 */
@WebServlet({"/dept/list","/dept/detail","/dept/delete","/dept/update","/dept/modify","/dept/add"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session != null && session.getAttribute("username") == null) {
            // 获取请求路径
            String servletPath = request.getServletPath();

            if ("/dept/list".equals(servletPath)) {
                doList(request,response);
            }

            if ("/dept/detail".equals(servletPath)) {
                doDetail(request,response);
            }
            if ("/dept/delete".equals(servletPath)) {
                doDel(request, response);
            }
            if ("/dept/update".equals(servletPath)) {
                doUpdate(request, response);
            }
            if ("/dept/modify".equals(servletPath)) {
                doModify(request, response);
            }
            if ("/dept/add".equals(servletPath)) {
                doAdd(request, response);
            } else {
                // 重定向到登陆页面
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        }


    }


    /**
     * 新增部门
     * @param request
     * @param response
     */
    private void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取部门信息
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");

        // 连接数据库，进行更新
        Connection conn = null;
        PreparedStatement ps = null;

        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into dept (dname,loc,deptno) VALUE (?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,dname);
            ps.setString(2,loc);
            ps.setString(3,deptno);
            // 查询部门信息
            count = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.Close(conn, ps ,null);
        }

        if (count == 1) {
            response.sendRedirect(""+request.getContextPath()+"/dept/list");
        }
    }

    /**
     * 修改部门信息
     * @param request
     * @param response
     */
    private void doModify(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 获取部门信息
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");

        // 连接数据库，进行更新
        Connection conn = null;
        PreparedStatement ps = null;

        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "update dept set dname = ?,loc = ? WHERE deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,dname);
            ps.setString(2,loc);
            ps.setString(3,deptno);
            // 查询部门信息
            count = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.Close(conn, ps ,null);
        }

        if (count == 1) {
            response.sendRedirect(""+request.getContextPath()+"/dept/list");
        }
    }

    private void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 获取 deptno
        String deptno = request.getParameter("deptno");
        // 连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Dept dept = new Dept();

        try {
            conn = DBUtil.getConnection();
            String sql = "select deptno,dname,loc from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            // 查询部门信息
            rs = ps.executeQuery();

            if (rs.next()) {
                // 将部门信息封装成部门实体类
                dept = new Dept();
                dept.setDeptno(rs.getString("deptno"));
                dept.setDname(rs.getString("dname"));
                dept.setLoc(rs.getString("loc"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.Close(conn, ps ,rs);
        }

        // 将存储部门信息的集合存储到request域
        request.setAttribute("dept",dept);

        // 将请求转发到 jsp(相当于servlet)
        request.getRequestDispatcher("/update.jsp").forward(request, response);

    }

    private void doDel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        // 获取 deptno
        String deptno = request.getParameter("deptno");
        // 连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "delete from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            // 查询部门信息
            count = ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.Close(conn, ps ,rs);
        }
        if (count == 1) {
            // 删除成功，返回部门列表(重定型)
            response.sendRedirect(""+request.getContextPath()+"/dept/list");
        } else {
            // 删除失败

        }
    }

//    private void Delete(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException{
//
//        // 获取 deptno
//        String deptno = request.getParameter("deptno");
//        // 连接数据库
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        int count = 0;
//
//        try {
//            conn = DBUtil.getConnection();
//            String sql = "delete from dept where deptno = ?";
//            ps = conn.prepareStatement(sql);
//            ps.setString(1,deptno);
//            // 查询部门信息
//            count = ps.executeUpdate();
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DBUtil.Close(conn, ps ,rs);
//        }
//        if (count == 1) {
//            // 删除成功，返回部门列表(重定型)
//            response.sendRedirect(""+request.getContextPath()+"/dept/list");
//        } else {
//            // 删除失败
//
//        }
//
//    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        // 获取 deptno
        String deptno = request.getParameter("deptno");
        // 连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Dept dept = new Dept();

        try {
            conn = DBUtil.getConnection();
            String sql = "select deptno,dname,loc from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            // 查询部门信息
            rs = ps.executeQuery();

            if (rs.next()) {
                // 将部门信息封装成部门实体类
                dept = new Dept();
                dept.setDeptno(rs.getString("deptno"));
                dept.setDname(rs.getString("dname"));
                dept.setLoc(rs.getString("loc"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.Close(conn, ps ,rs);
        }

        // 将存储部门信息的集合存储到request域
        request.setAttribute("dept",dept);

        // 将请求转发到 jsp(相当于servlet)
        request.getRequestDispatcher("/detail.jsp").forward(request, response);
    }

    private void doList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Dept> deptList = new ArrayList<>();

        // 连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "select deptno,dname,loc from dept";
            ps = conn.prepareStatement(sql);
            // 查询部门信息
            rs = ps.executeQuery();

            // 遍历部门信息
            while (rs.next()) {
                // 将部门信息封装成部门实体类
                Dept dept = new Dept();
                dept.setDeptno(rs.getString("deptno"));
                dept.setDname(rs.getString("dname"));
                dept.setLoc(rs.getString("loc"));

                // 将放入集合容器中
                deptList.add(dept);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.Close(conn, ps ,rs);
        }

        // 将存储部门信息的集合存储到request域
        request.setAttribute("deptList",deptList);

        // 将请求转发到 jsp(相当于servlet)
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }
}
