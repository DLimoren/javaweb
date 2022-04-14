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

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = null, password = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for(Cookie cookie:cookies){
                String name = cookie.getName();
                if("username".equals(name)) username = cookie.getValue();
                if("password".equals(name)) password = cookie.getValue();
            }
        }

        if(username != null && password != null){
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            boolean success = false;
            try {
                conn = DBUtil.getConnection();
                String sql = "select * from t_user where username = ? and password = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1,username);
                ps.setString(2 , password);

                rs = ps.executeQuery();
                if(rs.next()){
                    success = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally{
                DBUtil.close(conn , ps , rs);
            }
            if(success){
                HttpSession session = request.getSession();
                session.setAttribute("username" , username);
                User user = new User(username , password);
                session.setAttribute("user" , user);
//                session.setAttribute("username",username);
                response.sendRedirect(request.getContextPath() + "/dept/list");
            }else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        }
        else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
