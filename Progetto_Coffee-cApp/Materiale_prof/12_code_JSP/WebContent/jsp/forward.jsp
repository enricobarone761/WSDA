<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setAttribute("user", "MLC");
%>
<%-- <jsp:forward page="destination.jsp?bgColor=lavender" /> --%>
<jsp:forward page="destination.jsp">
	<jsp:param name="bgColor" value="lavender" />
</jsp:forward>

