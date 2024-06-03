<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>

<!DOCTYPE html>
<html>
<head>
<%
	String test = request.getServletContext().getRealPath("/resources/images");
	String imgroot = request.getContextPath() + "/resources/images";
	
%>
<title>test</title>
</head>
<body>
	UserBoard Write test Page.
	<%=test %>
	<td colspan="6"><br><br><img src = "<%= imgroot%>/step1-25.PNG"  width="300px" height="300px"><br><br>
	<form method="post" encType = "multipart/form-data" action="test_process.jsp">
			<input type="file" name="fileName"></td>
			<input type="submit" class="btn-primary pull-right" value="글쓰기">
	</form>
	
</body>
</html>