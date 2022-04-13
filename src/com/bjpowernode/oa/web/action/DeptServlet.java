package com.bjpowernode.oa.web.action;

import com.bjpowernode.oa.bean.Dept;
import com.bjpowernode.oa.utils.DBUtil;
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
import java.util.List;

@WebServlet({"/dept/list","/dept/detail","/dept/edit" , "/dept/save","/dept/delete","/dept/modify"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("username") != null) {
            String servletPath = request.getServletPath();
            if("/dept/list".equals(servletPath)){
                doList(request , response);
            }
            else if("/dept/detail".equals(servletPath)){
                doDetail(request , response);
            }
            else if("/dept/edit".equals(servletPath)){
                doEdit(request, response);
            }
            else if("/dept/save".equals(servletPath)){
                doSave(request , response);
            }
            else if("/dept/delete".equals(servletPath)){
                doDel(request , response);
            }
            else if("/dept/modify".equals(servletPath)){
                doModify(request , response);
            }
        }
        else{
            response.sendRedirect(  request.getContextPath());
        }
    }

    private void doSave(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String contextPath = request.getContextPath();
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");

        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "insert into dept(deptno ,dname ,loc) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            ps.setString(2,dname);
            ps.setString(3,loc);

            count = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn , ps , null);
        }
        if(count == 1){
            response.sendRedirect(contextPath + "/dept/list");
        }else{
            response.sendRedirect(contextPath + "/error.html");
        }
    }

    private void doDel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String deptno = request.getParameter("deptno");
        String contextPath = request.getContextPath();
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "delete from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            count = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DBUtil.close(conn , ps , null);
        }
        if(count == 1){
            response.sendRedirect(contextPath + "/dept/list");
        }else{
            response.sendRedirect(contextPath + "/error.html");
        }
    }

    private void doModify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");
        String contextPath = request.getContextPath();
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0 ;

        try {
            conn = DBUtil.getConnection();
            String sql = "update dept set dname = ? , loc = ?  where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,dname );
            ps.setString(2,loc);
            ps.setString(3,deptno);

            count = ps.executeUpdate();

            if(count == 1){
                response.sendRedirect(contextPath + "/dept/list");
            }else{
                response.sendRedirect(contextPath + "/error.html");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DBUtil.close(conn , ps , null);
        }

    }

    private void doEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String deptno = request.getParameter("deptno");

        try {
            conn = DBUtil.getConnection();
            String sql = "select dname , loc from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1 , deptno);
            rs = ps.executeQuery();
            if(rs.next()){
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                Dept dept = new Dept(deptno , dname , loc);
                request.setAttribute("dept" , dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            DBUtil.close(conn , ps , rs);
        }
        request.getRequestDispatcher("/edit.jsp").forward(request , response);
    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String deptno = request.getParameter("deptno");
        try {
            conn = DBUtil.getConnection();
            String sql = "select dname , loc from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,deptno);
            rs = ps.executeQuery();
            if(rs.next()){
                Dept dept = new Dept(deptno , rs.getString("dname") , rs.getString("loc"));
                request.setAttribute("dept",dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            DBUtil.close(conn , ps , rs);
        }
        request.getRequestDispatcher("/detail.jsp").forward(request, response);
    }

    /**
     * 连接数据库 查询所有部门信息，跳转到jsp做页面展示
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        List<Dept> depts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select deptno , dname , loc from dept ";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                Dept dept = new Dept(deptno , dname , loc);
                depts.add(dept);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            DBUtil.close(conn , ps , rs);
        }
        request.setAttribute("deptList" , depts);
        request.getRequestDispatcher("/list.jsp").forward(request,response);
//        request.getRequestDispatcher("/error.jsp").forward(request,response);
//        response.sendRedirect();
    }
}
