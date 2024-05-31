<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="java.util.*"%>
<%@ page import="model.Board"%>
<%
	/*일반용 게시판 관련*/
	List boardList = (List) request.getAttribute("boardlist");	 //일반 게시판 가져오기
	int total_record = ((Integer)request.getAttribute("total_record")).intValue(); //총 게시글 수
	int pageNum = ((Integer)request.getAttribute("pageNum")).intValue(); //현재 페이지 번호
	int total_page = ((Integer)request.getAttribute("total_page")).intValue(); //총 페이지 나눈거 
	
	/*공지용 게시판 관련*/ 
	List noiceboardList = (List) request.getAttribute("noiceboardList"); 
	int total_record_notice = ((Integer)request.getAttribute("total_record_notice")).intValue();
	int pageNum_notice = ((Integer)request.getAttribute("pageNum_notice")).intValue();
	int total_page_notice = ((Integer)request.getAttribute("total_page_notice")).intValue(); 
%>
<html>
<head>
	<%	
		if(session.getAttribute("sessionId") == null) //세션 아이디가 없으면 로그인 안됐다는 뜻
			response.sendRedirect("../welcome.jsp");
		String name = (String)session.getAttribute("sessionId"); 
		String writeForm = "/board/userWriteForm.jsp";
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
				<h1 class="diplay-5 fw-bold"><%=name %>님 메인 페이지</h1>
				<p class="col-md-8 fs-4"><%=name %>'s Main Page</p>
			</div>
		</div>
		<!-- 글 작성 버튼 -->
		<div>
			<a href =<%=route +  writeForm%> class = "btn btn-secondary" role="button">게시글 작성</a>
		</div>
		<div class="row align-items-md-stretch   text-center">	 	
		<form name="board" action="../AllBoardListAction.userdo" method="post">
				<div class="text-end"> 
					<span class="badge text-bg-success">공지 전체 <%=total_record_notice%>건	</span>
				</div>
		
			<div style="padding-top: 20px">
				<table class="table table-hover text-center">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성일</th>
						<th>조회</th>
					</tr>
					<%
					if(noiceboardList != null){
						for (int j = 0; j < noiceboardList.size() ; j++){
							
							Board notice = (Board) noiceboardList.get(j);
					%>
					<tr>
						<td><%=notice.getBID()%></td>
						<td><%=notice.getTitle()%></td>
						<td><%=notice.getRegdate()%></td>
						<td><%=notice.getHit()%></td>
					</tr>
					<%
						}
					}
					%>
				</table>
			</div>
			<div align="center">
			<%
				for(int i = 1; i <= total_page_notice; i++){
			%>
				<a href="./AllBoardListAction.userdo?pageNum_notice=<%=i %>" />
			<%		if(pageNum_notice == i){ %>
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
	<div class="row align-items-md-stretch   text-center">	 	
		<form name="board" action="../AllBoardListAction.userdo" method="post">
				<div class="text-end"> 
					<span class="badge text-bg-success">게시글 전체 <%=total_record%>건	</span>
				</div>
		
			<div style="padding-top: 20px">
				<table class="table table-hover text-center">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성일</th>
						<th>조회</th>
						<th>글쓴이</th>
						<th>MBTI</th>
					</tr>
					<%
					if(boardList != null){
						for (int j = 0; j < boardList.size() ; j++){
							
							Board board = (Board) boardList.get(j);
					%>
					<tr>
						<td><%=board.getBID()%></td>
						<td><%=board.getTitle()%></td>
						<td><%=board.getRegdate()%></td>
						<td><%=board.getHit()%></td>
						<td><%=board.getId()%></td>
						
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
				<a href="./AllBoardListAction.userdo?pageNum=<%=i %>" />
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