<!DOCTYPE html>
<html>
<head>
<title>Testing import attribute</title>
</head>
<body>
	<h2>The import Attribute</h2>
	<%@ page import="java.util.*, it.lacascia.apsw.*"%>
	<%!private String randomID() {
		int num = (int) (Math.random() * 10000000.0);
		return ("id" + num);
	}

	private final String NO_VALUE = "<I>No Value</I>";%>
	<%
		String oldID = CookieUtilities.getCookieValue(request, "userID", NO_VALUE);
		if (oldID.equals(NO_VALUE)) {
			String newID = randomID();
			Cookie cookie = new LongLivedCookie("userID", newID);
			response.addCookie(cookie);
		}
	%>
	This page was accessed on
	<%=new Date()%>
	with a userID cookie of
	<%=oldID%>.
</body>
</html>
