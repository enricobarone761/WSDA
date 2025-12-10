<!DOCTYPE html>
<html>
<head>
<title>JSP Expressions</title>
</head>
<body>
	<h2>JSP Expressions</h2>
	<ul>
		<li>Current time: <jsp:expression> new java.util.Date() </jsp:expression></li>
		<li>Server: <jsp:expression> application.getServerInfo() </jsp:expression></li>
<%-- 		<li>Current time: <%= new java.util.Date() %></li> --%>
<%-- 		<li>Server: <%= application.getServerInfo() %></li> --%>
		<li>Session ID: <%= session.getId() %></li>
		<li>The <CODE>testParam</CODE> form parameter: <%= request.getParameter("testParam") %></li>
	</ul>
</body>
</html>