<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="database.*" %>
<%@ page import="model.Member" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.format.*" %>


<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/bootstrap.min.css" />
<title>관리자용 일반 사용자 조회</title>
</head>
<body>
<% 
    int pageNumber = 1; 
	int count = 8;
	int total_user;
	String check = "where id != 'admin'"; //검색 조건
	String type = "Total";
	 //만약에 파라미터로 pageNumber가 넘어왔다면 해당 파라미터의 값을 넣어주도록 한다.
    if (request.getParameter("pageNumber") != null)
        pageNumber = Integer.parseInt(request.getParameter("pageNumber")); 
  		//파라미터는 항상 정수형으로 바꿔주는 parseInt를 사용해야 한다.
  	if(session.getAttribute("check")!= null) {//mbti 검색 조건
  		check = " where id != 'admin' AND MBTI = " + session.getAttribute("check");
  		type = (String)session.getAttribute("check");
  		System.out.println(check);
  	}
  		
  	memberDao dao = memberDao.getInstance();
  	ArrayList<Member> members = dao.getList(pageNumber, count, check);
  	total_user = dao.allcount(check);
%>
<div class="container py-4">
		<%@ include file = "./adminmenu.jsp" %>
		
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="diplay-5 fw-bold">관리자용 일반 사용자 조회</h1>
				<p class="col-md-8 fs-4">Admin Common User Search</p>
				<p class="col-md-8 fs-4">Current <%=type %> User: <%=total_user %></p>
			</div>
		</div>
		<a href = "processUserSearch_admin.jsp?check=null" class="btn btn-success btn-arraw-left">모든 사용자</a>
		<form name="board" action="processUserSearch_admin.jsp" method="post">
		<label>MBTI 검색</label>
		<select id="check" name="check" size = 1>
			<option value = "">없음</option>
			<option value = "'INTJ'">INTJ</option>
			<option value = "'INTP'">INTP</option>
			<option value = "'ENTJ'">ENTJ</option>
			<option value = "'ENTP'">ENTP</option>
			<option value = "'INFJ'">INFJ</option>
			<option value = "'INFP'">INFP</option>
			<option value = "'ENFJ'">ENFJ</option>
			<option value = "'ENFP'">ENFP</option>
			<option value = "'ISTJ'">ISTJ</option>
			<option value = "'ISFJ'">ISFJ</option>
			<option value = "'ESTJ'">ESTJ</option>
			<option value = "'ESFJ'">ESFJ</option>
			<option value = "'ISTP'">ISTP</option>
			<option value = "'ISFP'">ISFP</option>
			<option value = "'ESTP'">ESTP</option>
			<option value = "'ESFP'">ESFP</option>
		</select>
			<input type = "submit" value = "검색">
		</form>
			
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
				<th>강제 탈퇴</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(int i = 0; i < members.size(); i++){
					Member member = members.get(i);
					String activity;
					String lastdate = "";
					String date = member.getRegisterDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

					if(member.getLastDateTime() != null)
						lastdate = member.getLastDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			%>
			<tr>
				<td><%=member.getId() %></td>
				<td><%=member.getName() %></td>
				<td><%=member.getEmail() %></td>
				<td><%=date %></td>
				<td><%=member.getMbti() %></td>
				<td><%=lastdate %></td>
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
				<%if(member.getDeleteDateTime()!=null) {%>
				<td style="color:red"><strong><%=member.getDeleteDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) %></strong></td>
				<%} else {%>
				<td><a href = "<%=request.getContextPath() %>/adminPage/userDeleteForm.jsp?id=<%=member.getId() %>" class="btn btn-success btn-arraw-left">강제 탈퇴</a></td>
				<% }%>
			</tr>
			<%
				}			
			%>
		</tbody>
		</table>
		<%
			if(pageNumber != 1){
    	 %>
    	 	<a href = "usermanage_admin.jsp?pageNumber=<%=pageNumber -1 %>" class="btn btn-success btn-arraw-left">이전</a>
    	 <%
			}
    	 %>
    	 <%
			if(total_user > pageNumber * count){
    	 %>
    	 	<a href = "usermanage_admin.jsp?pageNumber=<%=pageNumber + 1 %>" class="btn btn-success btn-arraw-left">다음</a>
    	 <%
			}
    	 %>
		</div>
		<%@ include file = "../footer.jsp" %>
	</div>
</body>
</html>