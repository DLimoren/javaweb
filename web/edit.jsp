<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.bjpowernode.oa.bean.Dept" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>部门</title>
	</head>
	<body>
		<h3>欢迎[<%=session.getAttribute("username")%>]</h3>
		<a href="<%=request.getContextPath()%>/user/exit">[退出登录]</a>
		<h1>修改部门</h1>
		<hr >
		<% Dept dept = (Dept)request.getAttribute("dept"); %>
		<form action="<%=request.getContextPath()%>/dept/modify" method="post">
			部门编号 <input type="text" name="deptno" value="<%=dept.getDeptno()%>" readonly="true"/> <br>
			部门名称 <input type="text" name="dname"  value="<%=dept.getDname()%>"/><br>
			部门位置 <input type="text" name="loc"    value="<%=dept.getLoc()%>"/><br>
			<input type="submit" name="" id="" value="修改" /><br>
		</form>
	</body>
</html>
