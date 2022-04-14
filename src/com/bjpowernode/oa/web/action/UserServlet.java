package com.bjpowernode.oa.web.action;

import com.bjpowernode.oa.bean.User;
import com.bjpowernode.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet({"/user/login","/user/exit"})
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if("/user/exit".equals(servletPath)){
            doExit(request , response);
        }
        else if("/user/login".equals(servletPath)){
            doLogin(request,response);
        }
    }

    private void doExit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("user");
            session.invalidate();
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                cookie.setMaxAge(0);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
            }
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }


    private void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from t_user where username = ? and password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);

            rs = ps.executeQuery();
            if(rs.next()) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            DBUtil.close(conn , ps , rs);
        }
        if(success){
            HttpSession session = request.getSession();
            User user = new User(username , password);
            session.setAttribute("user" , user);
//            session.setAttribute("username",username);
            String f = request.getParameter("f");
            if("1".equals(f)){
                Cookie cookie1 = new Cookie("username" , username);
                Cookie cookie2 = new Cookie("password" , password);
                cookie2.setMaxAge(60 * 60 * 24 * 10);
                cookie1.setMaxAge(60 * 60 * 24 * 10);
                cookie1.setPath(request.getContextPath());
                cookie2.setPath(request.getContextPath());
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }
            response.sendRedirect(request.getContextPath() + "/dept/list");
        }
        else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
