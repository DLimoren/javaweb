package com.bjpowernode.oa.utils;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * JDBC的工具类
 */
public class DBUtil {
    private static ResourceBundle boundle = ResourceBundle.getBundle("resources.jdbc");
    private static String driver = boundle.getString("driver") ;
    private static String url = boundle.getString("url");
    private static String user = boundle.getString("user");
    private static String password = boundle.getString("password");

    static {
       try{
           // 注册驱动
           Class.forName(driver);
       }catch(ClassNotFoundException e){
           e.printStackTrace();
       }
    }

    /**
     * 获取数据库连接对象
     * @return conn 连接对象
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        // 获取连接
        Connection conn = DriverManager.getConnection(url , user , password);
        return conn;
    }

    /**
     * 释放资源
     * @param conn 连接对象
     * @param ps  数据库操作对象
     * @param rs 数据结果集对象
     */
    public static void close(Connection conn , Statement ps , ResultSet rs){
        if (rs != null) {
            try{
                rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try{
                ps.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
