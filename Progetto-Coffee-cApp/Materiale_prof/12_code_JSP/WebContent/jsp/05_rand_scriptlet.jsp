<%@page import="it.lacascia.apsw.jsp.*" %>
<!DOCTYPE html>
<html>
<head>
<title>Random numbers</title>
</head>
<body>
<h1>Random List (Version 2)</h1>
<ul>

<%
int numEntries = RanUtilities.randomInt(10);
for(int i=0; i<numEntries; i++) {
	out.print("<LI>" + RanUtilities.randomInt(10));
}
%>
</UL>

</body>
</html>
