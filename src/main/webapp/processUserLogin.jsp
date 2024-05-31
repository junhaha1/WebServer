<%@ page import="database.DBconfig" %>
<%@ page import="model.Member" %>
<%@ page import="database.memberDao" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import= "java.time.LocalDateTime" %>

<%
	String id = request.getParameter("id");
	String pw = request.getParameter("passwd");
	String suceId = null;
	int code = 1;
	
	
	memberDao dao = memberDao.getInstance();
	if((suceId = dao.loginUser(id, pw)) != null){
		session.setAttribute("sessionId", suceId); //해당 유저 아이디로 세션 등록
		response.sendRedirect("./AllBoardListAction.userdo"); //모든 게시판 출력하도록 하여 메인 페이지 이동
	} else{
		response.sendRedirect("./userPage/login_user.jsp?error=" + code); //error = 1
	}
%>