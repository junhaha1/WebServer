<%@ page import="database.memberDao" %>
<%@ page import="model.Member" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import= "java.time.LocalDateTime" %>

<%@ page contentType="text/html; charset=utf-8"  %>

<script type="text/javascript">
    alert("회원 탈퇴 성공");
</script>

<%	
	session.invalidate();
	response.sendRedirect(request.getContextPath()+"/welcome.jsp");
%>