<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page import="model.*"%>

<!DOCTYPE html>
<html>
<head>
<%
	Member member = (Member)request.getAttribute("member");
	
%>
<title>test</title>
</head>
<body>

<%=(String) session.getAttribute("sessionId") %>
<%=member.getId() %>
<%=member.getEmail() %>
	
</body>
</html>