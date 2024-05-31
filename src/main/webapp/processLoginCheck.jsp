<%
	if(request.getParameter("check").equals("admin"))
		response.sendRedirect("./adminPage/adminMain.jsp");
	else
		response.sendRedirect("./userPage/login_user.jsp");
%>