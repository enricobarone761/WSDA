<%@ page import="it.lacascia.apsw.jsp.*" %>
<!DOCTYPE html>
<html>
<head>
<title>Random numbers</title>
</head>
<body>
<h1>Random Numbers</h1>
<ul>
<li><%= RanUtilities.randomInt(10) %></li>
<li><%= RanUtilities.randomInt(10) %></li>
<li><%= RanUtilities.randomInt(10) %></li>
<li><%= RanUtilities.randomInt(10) %></li>
<li><%= RanUtilities.randomInt(10) %></li>
</ul>

</body>
</html>
