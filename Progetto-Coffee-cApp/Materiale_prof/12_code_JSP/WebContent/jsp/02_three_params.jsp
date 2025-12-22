<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reading three request parameters</title>
</head>
<body>
	<h1>Reading three request parameters</h1>
	<ul>
		<li>param1: <%=request.getParameter("param1")%></li>
		<li>param2: <%=request.getParameter("param2")%></li>
		<li>param3: <%=request.getParameter("param3")%></li>
	</ul>
</body>
</html>