<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
	List searchlist = (List) request.getAttribute("searchlist");	
	int total_record = ((Integer)request.getAttribute("total_record")).intValue();
	int pageNum = ((Integer)request.getAttribute("pageNum")).intValue();
	int total_page = ((Integer)request.getAttribute("total_page")).intValue();
	int items  = ((Integer)request.getAttribute("items")).intValue();
	String text = (String) request.getAttribute("text");
	
	if(session.getAttribute("researchLogList") == null){
		ArrayList<SearchLog> researchLogList = new ArrayList<SearchLog>();
		session.setAttribute("researchLogList", researchLogList);
	}
	
	SearchLog log = new SearchLog();
	log.setPageNum(pageNum);
	log.setItems(items);
	log.setText(text);
	log.setType((String)request.getAttribute("type"));
	
	List logList = (List) session.getAttribute("researchLogList");
	logList.add(0, log);
	
	for(int i = 0; i < logList.size(); i++){
		log = (SearchLog) logList.get(i);
		System.out.println(log);
	}
	session.setAttribute("researchLogList", logList);
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
				<h1 class="diplay-5 fw-bold">전체 게시글</h1>
				<p class="col-md-8 fs-4">Total Main Board</p>
			</div>
		</div>
		<!-- 글 작성 버튼 -->
		<div>
			<a href =<%=route +  writeForm%> class = "btn btn-secondary" role="button">게시글 작성</a>
			<a href ="<%=route%>/BoardListAction.userdo?type=common" class = "btn btn-secondary" role="button">게시판만 보기</a>
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
						<th>번호</th>
						<th>제목</th>
						<th>작성일</th>
						<th>좋아요 수</th>
						<th>조회</th>
						<th>글쓴이</th>
						<th>MBTI</th>
					</tr>
					<%
					if(searchlist != null){
						for (int j = 0; j < searchlist.size() ; j++){
							
							Board board = (Board) searchlist.get(j);
							String date = board.getRegdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					%>
					<tr>
						<td><%=board.getBID()%></td>
						<td><a href="./userBoardView.userdo?BID=<%=board.getBID()%>&name=<%=name %>&type=user_search"><%=board.getTitle()%></td>
						<td><%=date %></td>
						<td><%=board.getGoohit()%></td>
						<td><%=board.getHit()%></td>
						<td><%=board.getId()%></td>
						<td><%=board.getMbti() %></td>
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
				<a href="./SearchListAction.userdo?pageNum=<%=i %>&items=<%=items %>&text=<%=text %>&type=<%=log.getType() %>" />
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