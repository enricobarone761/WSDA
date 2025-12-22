<!DOCTYPE html>
<html>
<head>
<title>Color testing</title>
</head>
<%
	String bgColor = request.getParameter("bgColor");
	if ((bgColor == null) || (bgColor.trim().equals(""))) {
		bgColor = "white";
	}
%>
<body bgcolor="<%=bgColor%>">
	<h2>
		Testing background "<%=bgColor%>"
	</h2>
</body>
</html>