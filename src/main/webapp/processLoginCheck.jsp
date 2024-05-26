<%
	if(request.getParameter("check").equals("admin"))
		response.sendRedirect("admin_manage.jsp");
	else
		response.sendRedirect("login_user.jsp");
%>