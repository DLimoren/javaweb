<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.bjpowernode.oa.bean.Dept" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>部门详情</title>
	</head>
	<body>
	<% Dept dept = (Dept)request.getAttribute("dept");%>
		<h3>欢迎[<%=session.getAttribute("username")%>]</h3>
		<h1>部门详情</h1>
		<hr >
		部门编号：<%=dept.getDeptno()%> <br>
		部门名称：<%=dept.getDname()%><br>
		部门位置：<%=dept.getLoc()%><br>
		
		<input type="button" value="后退" onclick="window.history.back()"/>
	</body>
</html>
