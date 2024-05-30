<%@ page import="database.memberDao" %>
<%@ page import="model.Member" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import= "java.time.LocalDateTime" %>

<%
	memberDao dao = memberDao.getInstance();
	if(session.getAttribute("sessionId") != null){
		if(dao.logoutUser((String)session.getAttribute("sessionId")) > 0){ // 로그아웃 성공
			session.invalidate();
			response.sendRedirect("welcome.jsp");
		} else { //로그아웃 실패
			System.out.println("로그아웃 실패");
			response.sendRedirect("mainpage.jsp");
		}
	} else{ //세션 오류
		System.out.println("세션 값이 null");
	}
		
%>