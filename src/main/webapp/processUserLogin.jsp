<%@ page import="controller.DBconfig" %>
<%@ page import="model.Member" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import= "java.time.LocalDateTime" %>

<%
	String id = request.getParameter("id");
	String pw = request.getParameter("passwd");
	int code = 1;
	
	Connection con = DBconfig.makeConnection();
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	try{
		String sql = "SELECT ID FROM MEMBER WHERE ID = ? AND PASSWORD = ?";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, id);
		stmt.setString(2, pw);
		rs = stmt.executeQuery();
		
		if(rs.next()){
			code = 0;
			sql = "UPDATE MEMBER SET ACTIVITY = 1 WHERE ID=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.executeUpdate();
		}
		else //매칭 되는 아이디가 없을 경우
			code = 1;
		
	
		if(code == 0)
			response.sendRedirect("mainpage.jsp"); //메인 페이지 이동
		else
			response.sendRedirect("login_user.jsp?error=" + code); //error = 1
		
	} catch(SQLException e){
		code = 2;
		out.println("Member Select Failed");
		out.println("SQLException: " + e.getMessage());
		response.sendRedirect("login_user.jsp?error=" + code); //error = 2
	} finally{
		if(stmt != null)
			stmt.close();
		if(con != null)
			con.close();
	}
%>