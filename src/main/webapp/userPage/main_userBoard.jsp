<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="java.util.*"%>
<%@ page import="model.Board"%>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
	if(request.getAttribute("boardlist") == null){
		System.out.println("null check");
		response.sendRedirect("./AllBoardListAction.userdo");
	}
	/*일반용 게시판 관련*/
	List boardList = (List) request.getAttribute("boardlist");	 //일반 게시판 가져오기
	int total_record = ((Integer)request.getAttribute("total_record")).intValue(); //총 게시글 수
	int pageNum = ((Integer)request.getAttribute("pageNum")).intValue(); //현재 페이지 번호
	int total_page = ((Integer)request.getAttribute("total_page")).intValue(); //총 페이지 나눈거 
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
				<h1 class="diplay-5 fw-bold"><%=name %>님 게시글 전체보기</h1>
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
					<span class="badge text-bg-success"><%=name %>님 게시글 전체 <%=total_record%>건	</span>
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
							String date = board.getRegdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					%>
					<tr>
						<td><%=board.getBID()%></td>
						<td><a href="./userBoardView.userdo?BID=<%=board.getBID()%>&&type=common"><%=board.getTitle()%></td>
						<td><%=date %></td>
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
				<a href="./BoardListAction.userdo?pageNum=<%=i %>&&type=common" />
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