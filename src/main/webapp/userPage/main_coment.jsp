<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
	if(request.getAttribute("comentlist") == null){
		System.out.println("null check");
		response.sendRedirect("./AllBoardListAction.userdo");
	}
	/*공지용 게시판 관련*/ 
	List comentList = (List) request.getAttribute("comentlist"); 
	int total_record_coment = ((Integer)request.getAttribute("total_record_coment")).intValue();
	int pageNum_coment = ((Integer)request.getAttribute("pageNum_coment")).intValue();
	int total_page_coment = ((Integer)request.getAttribute("total_page_coment")).intValue(); 
	
%>
<html>
<head>
	<%	
		if(session.getAttribute("sessionId") == null) //세션 아이디가 없으면 로그인 안됐다는 뜻
			response.sendRedirect("../welcome.jsp");
	
		String name = (String)session.getAttribute("sessionId"); 
		
		/*버튼 눌렀을 시에 옮겨갈 페이지*/
		String writeForm = "/board/userWriteForm.jsp";
		String commonShow = "./BoardListAction.userdo";
		String noticeShow = "./NoticeBoardListAciton.userdo";
		String comentShow = "./comentBoardListAction.userdo";
		
		
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
				<h1 class="diplay-5 fw-bold"><%=name %>님 댓글 보기</h1>
				<p class="col-md-8 fs-4"><%=name %>'s Coment Page</p>
			</div>
		</div>

		<div class="row align-items-md-stretch   text-center">	 	
		<form name="board" action=<%=noticeShow %> method="post">
				<div class="text-end"> 
					<span class="badge text-bg-success">댓글 전체 <%=total_record_coment%>건	</span>
				</div>
		
			<div style="padding-top: 20px">
				<table class="table table-hover text-center">
					<tr>
						<th>글번호</th>
						<th>글제목</th>
						<th>댓글 내용</th>
						<th>등록날짜</th>
					</tr>
					<%
					if(comentList != null){
						for (int j = 0; j < comentList.size() ; j++){
							Coment coment = (Coment) comentList.get(j);
							String date = coment.getRegdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					%>
					<tr>
						<td><%=coment.getBID()%></td>
						<td><a href="./userBoardView.userdo?BID=<%=coment.getBID()%>&&type=coment"><%=coment.getBtitle()%></td>
						<td><%=coment.getContent()%></td>
						<td><%=date%></td>
						<td><a href = "<%=request.getContextPath() %>/requestComentDelete.userdo?cnum=<%=coment.getCNUM()%>&&name=<%=name %>" class = "btn btn-secondary" role="button">댓글 삭제</a></td>
					</tr>
					<%
						}
					}
					%>
				</table>
			</div>
			<div align="center">
			<%
				for(int i = 1; i <= total_page_coment; i++){
			%>
				<a href="<%=comentShow %>?pageNum_coment=<%=i %>&&type=coment&&name=<%=name %>" />
			<%		if(pageNum_coment == i){ %>
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