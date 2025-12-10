<%@ page import="java.util.Date"%>
<%-- The following become fields in each servlet that
     results from a JSP page that includes this file. --%>
<%!private int accessCount = 0;
	private Date accessDate = new Date();
	private String accessHost = "<em>No previous access</em>";%>
<p>
<hr>
This page has been accessed
<%=++accessCount%>
times since server reboot. It was most recently accessed from
<%=accessHost%>
at
<%=accessDate%>.
<%
	accessHost = request.getRemoteHost();
	accessDate = new Date();
%>

