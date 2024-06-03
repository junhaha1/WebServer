<%@ page import="java.io.File" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>

<%
	String upload= request.getServletContext().getRealPath("/resources/images");		
	String encType = "utf-8";				
	int maxSize=5*1024*1024;				
	request.setCharacterEncoding("UTF-8");		
			
	MultipartRequest multi = null;
	
	//파일업로드를 직접적으로 담당		
	multi = new MultipartRequest(request, upload, maxSize, encType);
	
%>