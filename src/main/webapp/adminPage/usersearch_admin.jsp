<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="controller.*" %>
<%@ page import="model.Member" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" />
<title>관리자용 일반 사용자 조회</title>
</head>
<body>
<% 
    int pageNumber = 1; 
	int count = 8;
	int total_user;
	String check = ""; //검색 조건
	 //만약에 파라미터로 pageNumber가 넘어왔다면 해당 파라미터의 값을 넣어주도록 한다.
    if (request.getParameter("pageNumber") != null)
        pageNumber = Integer.parseInt(request.getParameter("pageNumber")); 
  		//파라미터는 항상 정수형으로 바꿔주는 parseInt를 사용해야 한다.
  	if(request.getParameter("check")!= null) //mbti 검색 조건
  		check = " where MBTI = " + request.getParameter("check");
  		
  	memberDao dao = new memberDao();
  	ArrayList<Member> members = dao.getList(pageNumber, count, check);
  	total_user = dao.allcount();
%>
<div class="container py-4">
		<%@ include file = "../menu.jsp" %>
		
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="diplay-5 fw-bold">관리자용 일반 사용자 조회</h1>
				<p class="col-md-8 fs-4">Admin Common User Search</p>
				<p class="col-md-8 fs-4">Current Total User: <%=total_user %></p>
			</div>
		</div>
		<label>MBTI 검색</label>
		<select id="check" name="check" size = 1>
			<option value = "">없음</option>
			<option value = "INTJ">INTJ</option>
			<option value = "INTP">INTP</option>
			<option value = "ENTJ">ENTJ</option>
			<option value = "ENTP">ENTP</option>
			<option value = "INFJ">INFJ</option>
			<option value = "INFP">INFP</option>
			<option value = "ENFJ">ENFJ</option>
			<option value = "ENFP">ENFP</option>
			<option value = "ISTJ">ISTJ</option>
			<option value = "ISFJ">ISFJ</option>
			<option value = "ESTJ">ESTJ</option>
			<option value = "ESFJ">ESFJ</option>
			<option value = "ISTP">ISTP</option>
			<option value = "ISFP">ISFP</option>
			<option value = "ESTP">ESTP</option>
			<option value = "ESFP">ESFP</option>
		</select>
		<div style="padding-top: 20px">
		<table class="table table-hover text-center">
		<thead>
			<tr>
				<th>ID</th>
				<th>이름</th>
				<th>이메일</th>
				<th>가입날짜</th>
				<th>MBTI</th>
				<th>마지막 접속</th>
				<th>활동 상태</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(int i = 0; i < members.size(); i++){
					Member member = members.get(i);
					String activity;
			%>
			<tr>
				<td><%=member.getId() %></td>
				<td><%=member.getName() %></td>
				<td><%=member.getEmail() %></td>
				<td><%=member.getRegisterDateTime() %></td>
				<td><%=member.getMbti() %></td>
				<td><%=member.getLastDateTime() %></td>
				<%
				if(member.getActivity() == 0){
					activity = "비활동";
				%>
				<td style="color:red"><strong><%=activity %></strong></td>
				<%
				}
				%>
				<%
				if(member.getActivity() == 1){
					activity = "현재 활동중";
				%>
				<td style="color:green"><strong><%=activity %></strong></td>
				<%
				}
				%>
			</tr>
			<%
				}			
			%>
		</tbody>
		</table>
		<%
			if(pageNumber != 1){
    	 %>
    	 	<a href = "usersearch_admin.jsp?pageNumber=<%=pageNumber -1 %>" class="btn btn-success btn-arraw-left">이전</a>
    	 <%
			}
    	 %>
    	 <%
			if(total_user > pageNumber * count){
    	 %>
    	 	<a href = "usersearch_admin.jsp?pageNumber=<%=pageNumber + 1 %>" class="btn btn-success btn-arraw-left">다음</a>
    	 <%
			}
    	 %>
		</div>
		<%@ include file = "../footer.jsp" %>
	</div>
</body>
</html>