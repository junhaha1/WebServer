<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8"  %>
<html>
<%
	String name = (String) session.getAttribute("sessionId");
	String route = request.getContextPath();
	String userWrite = "/BoardWriteAction.userdo";
	String home = "/AllBoardListAction.userdo";
%>
<head>
    <meta charset="utf-8">
    <title>home으로 </title>
    
</head>
<body>
	<a href =<%=route + home %> class = "btn btn-primary" role="button">home으로</a>
</body>
</html>