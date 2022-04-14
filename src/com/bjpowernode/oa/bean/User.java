package com.bjpowernode.oa.bean;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;

public class User implements HttpSessionBindingListener {
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        ServletContext application = event.getSession().getServletContext();
        Object onlinecount = application.getAttribute("onlinecount");
        if(onlinecount == null){
            application.setAttribute("onlinecount",1);
        }else {
            Integer count = (Integer)application.getAttribute("onlinecount");
            count ++;
            application.setAttribute("onlinecount" , count);
        }

    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        ServletContext application = event.getSession().getServletContext();
        Integer onlinecount = (Integer)application.getAttribute("onlinecount");
        onlinecount--;
        application.setAttribute("onlinecount" , onlinecount);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
