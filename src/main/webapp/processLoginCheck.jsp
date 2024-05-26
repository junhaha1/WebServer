<%
	if(request.getParameter("check").equals("admin"))
		response.sendRedirect("adminMain.jsp");
	else
		response.sendRedirect("login_user.jsp");
%>