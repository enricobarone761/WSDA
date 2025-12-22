<!DOCTYPE html>
<!-- 
Example of including files at page translation time, rather
than including output at request time as with jsp:include.
-->
<html>
<head>
<title>Some Random Page</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>Some Random Page</th>
		</tr>
	</table>
	<p>Information about our products and services.
	<p>Blah, blah, blah.
	<p>
		Yadda, yadda, yadda.
		<%@ include file="/WEB-INF/includes/ContactSection.jsp"%>
</body>
</html>