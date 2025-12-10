<%@ page import="it.lacascia.apsw.jsp.*" %>
<%@ page contentType="text/xml" %>
<%
String[] title = {"Songs", "Ballads", "New hits", "Greatest Hits", "Dance"};
String[] artist = {"John Smith", "Jane Doe", "Lisa Simpson"};
%>

<CATALOG>
<% for(int i=0; i<5; ++i){ %>
<CD>
<TITLE><%= title[RanUtilities.randomInt(title.length)-1] %></TITLE>
<ARTIST><%= artist[RanUtilities.randomInt(artist.length)-1] %></ARTIST>
<PRICE>EUR <%= RanUtilities.randomInt(6)+4 %></PRICE>
</CD>
<% } %>
</CATALOG>

