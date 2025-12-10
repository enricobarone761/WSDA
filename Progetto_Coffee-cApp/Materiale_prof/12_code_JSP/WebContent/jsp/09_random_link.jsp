<%@page contentType="application/json; charset=UTF-8"%>
<%
	String title, link;
	if(Math.random()>0.5){
		title = "APSW course page";
		link = "http://www.lacascia.it/apsw/2018-19/";
	} else {
		title = "Netflix";
		link = "https://www.netflix.com/";		
	}
%>
{
	"title": "<%=title%>",
	"link": "<%=link%>"
}
