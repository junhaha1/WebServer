<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
	if(request.getAttribute("noiceboardList") == null){
		System.out.println("null check");
		response.sendRedirect("./AllBoardListAction.userdo");
	}
	/*공지용 게시판 관련*/ 
	List noiceboardList = (List) request.getAttribute("noiceboardList"); 
	int total_record_notice = ((Integer)request.getAttribute("total_record_notice")).intValue();
	int pageNum_notice = ((Integer)request.getAttribute("pageNum_notice")).intValue();
	int total_page_notice = ((Integer)request.getAttribute("total_page_notice")).intValue(); 
	
	session.removeAttribute("researchLogList");
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
				<h1 class="diplay-5 fw-bold"><%=name %>님 공지글만 보기</h1>
				<p class="col-md-8 fs-4"><%=name %>'s Main Page</p>
			</div>
		</div>
		<a href ="./AllBoardListAction.userdo" class = "btn btn-secondary" role="button">메인으로</a>
		<div class="row align-items-md-stretch   text-center">	 	
		<form name="search" action= "<%=request.getContextPath() %>/SearchListAction.userdo" method="post">	
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
							String date = notice.getRegdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					%>
					<tr>
						<td><%=notice.getBID()%></td>
						<td><a href="./userNoticeView.userdo?BID=<%=notice.getBID()%>&&type=notice"><%=notice.getTitle()%></td>
						<td><%=date%></td>
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
				<a href="<%=noticeShow %>?pageNum_notice=<%=i %>&&type=notice" />
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
			<div align="left">				
				<select name="items" class="txt">
					<option value="1">제목에서</option>
					<option value="2">본문에서</option>
					<option value="0">글쓴이에서</option>
					<option value="3">mbti에서</option>
				</select> <input name="text" type="text" /> 
				<input type="hidden" name="type" value = "notice"/> 
				<input type="submit" id="btnAdd" class="btn btn-primary " value="검색 " />				
			</div>
		</form>			
	</div>
		
		<%@ include file = "../footer.jsp" %>
	</div>
</body>
</html>