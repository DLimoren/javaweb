<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="com.bjpowernode.oa.bean.Dept , java.util.*" %>
<%String contextPath = request.getContextPath();%>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>部门列表页面</title>
		<script type='text/javascript'>
			  function del(dno){
				   if(window.confirm('亲 ， 删了不可恢复哦！')){
						document.location.href="<%=contextPath%>/dept/delete?deptno="+dno;
				   }
			  }
		</script>
	</head>
	<body>
		<h3>欢迎[用户]</h3>
		<a href="<%=request.getContextPath()%>/user/exit">[退出登录]</a>
		<h1 align="center">部门列表</h1>
		<hr>
		<table border="1px" align="center" width=50%>
			<tr>
				<th>序号</th>
				<th>部门编号</th>
				<th>部门名称</th>
				<th>操作</th>

			</tr>
			<% List<Dept> deptList = (List<Dept>)request.getAttribute("deptList");
				for(int i = 0 ; i < deptList.size() ; i ++){
			%>
			<tr>
				<td><%=i%></td>
				<td><%=deptList.get(i).getDeptno()%></td>
				<td><%=deptList.get(i).getDname()%></td>
				<td>
					<a href="javascript:void(0)" onclick= "del(<%=deptList.get(i).getDeptno()%>)">删除</a>
					<a href="<%=request.getContextPath()%>/dept/edit?deptno=<%=deptList.get(i).getDeptno()%>">修改</a>
					<a href="<%=request.getContextPath()%>/dept/detail?deptno=<%=deptList.get(i).getDeptno()%>">详情</a>
				</td>
			</tr>
			<%
				}
			%>
			<hr >
		</table> 
		<a href="<%=request.getContextPath()%>/add.jsp">新增部门</a>
	</body>
</html>