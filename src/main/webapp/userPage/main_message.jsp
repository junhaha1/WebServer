<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
	if(request.getAttribute("maillist") == null){
		System.out.println("null check");
		response.sendRedirect("./AllBoardListAction.userdo");
	}
	/*일반용 게시판 관련*/
	List maillist = (List) request.getAttribute("maillist");	 //메일 불러오기
	int total_record = ((Integer)request.getAttribute("total_record")).intValue(); //총 게시글 수
	int pageNum = ((Integer)request.getAttribute("pageNum")).intValue(); //현재 페이지 번호
	int total_page = ((Integer)request.getAttribute("total_page")).intValue(); //총 페이지 나눈거 
	
	int option = 0;
	if(request.getAttribute("option")!=null)
		option = ((Integer)request.getAttribute("option")).intValue();
%>
<html>
<head>
	<%	
		if(session.getAttribute("sessionId") == null) //세션 아이디가 없으면 로그인 안됐다는 뜻
			response.sendRedirect("../welcome.jsp");
		String name = (String)session.getAttribute("sessionId"); 
		String writeForm = "/board/userMessageWriteForm.jsp";
		String style = "/resources/css/bootstrap.min.css";
	%>
	<link rel="stylesheet" href=<%=request.getContextPath() + style %> />
	<title>userMainPage</title>
</head>
<body>
	<div class="container py-4">
		<%@ include file = "../menu.jsp" %>
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="diplay-5 fw-bold"><%=name %>님의 수신 메일</h1>
				<p class="col-md-8 fs-4"><%=name %>'s Receive Mails</p>
			</div>
		</div>
		<!-- 글 작성 버튼 -->
		<div>
			<a href =<%=route +  writeForm%> class = "btn btn-secondary" role="button">메일 작성</a>
			<a href ="./AllBoardListAction.userdo" class = "btn btn-secondary" role="button">메인으로</a>
		</div>
	<div class="row align-items-md-stretch   text-center">	 	
		<form name="search" action= "<%=request.getContextPath() %>/SearchListAction.userdo" method="post">	
				<div class="text-end"> 
					<span class="badge text-bg-success">게시글 전체 <%=total_record%>건	</span>
				</div>
		
			<div style="padding-top: 20px">
				<table class="table table-hover text-center">
					<tr>
						<th>보낸 사람</th>
						<th>제목</th>
						<th>보낸 날짜</th>
						<th>읽음 여부</th>
					</tr>
					<%
					if(maillist != null){
						for (int j = 0; j < maillist.size() ; j++){
							
							Message msg = (Message) maillist.get(j);
							String date = msg.getSENDDATE().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					%>
					<tr>
						<td><%=msg.getUID()%></td>
						<td><a href="./userMailView.userdo?MID=<%=msg.getMID()%>&&type=msg&&name="<%=name%>><%=msg.getTITLE()%></td>
						<td><%=date %></td>
						<%if(msg.getRDCHECK() == 1) {%>
						<td style="color:green">읽음</td>
						<%}else if(msg.getRDCHECK() == 0) {%>
						<td style="color:red">안읽음</td>
						<%} %>
					</tr>
					<%
						}
					}
					%>
				</table>
			</div>
			<div align="center">
			<%
				for(int i = 1; i <= total_page; i++){
			%>
				<a href="./requestUserMail.userdo?pageNum=<%=i %>&type=msg&name=<%=name %>&option=<%=option %>" />
			<%		if(pageNum == i){ %>
						<font color='4C5317'><b> [<%=i %>]</b></font></a>
			<%
					}else{
			%>
						<font color='4C5317'> [<%=i %>]</font></a>
			<%
					}
			%>
			<%
				}
			%>					
			</div>
		</form>			
	</div>
		<%@ include file = "../footer.jsp" %>
	</div>
</body>
</html>