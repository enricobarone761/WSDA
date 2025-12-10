<!DOCTYPE html>
<html>
<head>
<title>JSP declaration</title>
</head>
<body>
	<h1>JSP Declarations</h1>
	<%!private int accessCount = 0;%>
	<h2>
		Accesses to page since server reboot:
		<%=++accessCount%></h2>
</body>
</html>