<%
	System.out.println(request.getParameter("check"));
	if(request.getParameter("check").equals("null") || request.getParameter("check").equals(""))
		session.removeAttribute("check");
	else
		session.setAttribute("check", request.getParameter("check"));
	response.sendRedirect("usermanage_admin.jsp");
%>