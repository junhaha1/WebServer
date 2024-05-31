<%@ page import="database.DBconfig" %>
<%@ page import="model.Member" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import= "java.time.LocalDateTime" %>

<%
	request.setCharacterEncoding("utf-8");
	Member member = new Member();
	
	String id = request.getParameter("id");
	String pw = request.getParameter("passwd");
	String email = request.getParameter("email");
	String name = request.getParameter("name"); 
	LocalDateTime registerDateTime = LocalDateTime.now();
	String mbti = request.getParameter("mbti");
	
	member.setId(id);
	member.setPassword(pw);
	member.setEmail(email);
	member.setName(name);
	member.setRegisterDateTime(registerDateTime);
	member.setMbti(mbti.toUpperCase());
	
	Connection con = DBconfig.makeConnection();
	PreparedStatement stmt = null;
	try{
		String sql = "INSERT INTO Member(ID, PASSWORD, EMAIL, NAME, REGDATE, MBTI) VALUES(?, ?, ?, ?, ?, ?)";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, member.getId());
		stmt.setString(2, member.getPassword());
		stmt.setString(3, member.getEmail());
		stmt.setString(4, member.getName());
		stmt.setTimestamp(5, Timestamp.valueOf(member.getRegisterDateTime()));
		stmt.setString(6, member.getMbti());
		
		stmt.executeUpdate();
		out.println("Insert Sucess");
		
		response.sendRedirect("./userPage/login_user.jsp");
	} catch(SQLException e){
		out.println("Member Insert Failed");
		out.println("SQLException: " + e.getMessage());
		response.sendRedirect("./userPage/addmember.jsp?error=1");
	} finally{
		if(stmt != null)
			stmt.close();
		if(con != null)
			con.close();
	}
%>