<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.Board"%>
<%
	String sessionId = (String) session.getAttribute("sessionId");
	List boardList = (List) request.getAttribute("boardlist");	
	int total_record = ((Integer)request.getAttribute("total_record")).intValue();
	int pageNum = ((Integer)request.getAttribute("pageNum")).intValue();
	int total_page = ((Integer)request.getAttribute("total_page")).intValue();
	
	String title = "";
	String subTitle = "";
	
	title = "공지용 게시판 목록";
	subTitle = "Notic Board";
%>
<html>
<head>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
<title>Board</title>
<script type="text/javascript">
</script>
</head>
<body>
<div class="container py-4">
	<jsp:include page="./adminmenu.jsp" />
	
	 <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold"><%=title %></h1>
        <p class="col-md-8 fs-4"><%=subTitle %></p>      
      </div>
    </div>
	
	<div class="row align-items-md-stretch   text-center">	 	
		<form name="board" action="../NoticeListAction.do" method="post">
				<div class="text-end"> 
					<span class="badge text-bg-success">전체 <%=total_record%>건	</span>
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
							
							Board notice = (Board) boardList.get(j);
					%>
					<tr>
						<td><%=notice.getBID()%></td>
						<td><a href="../BoardViewAction.do?num=<%=notice.getBID()%>&pageNum=<%=pageNum%>" /><%=notice.getTitle()%></a></td>
						<td><%=notice.getRegdate()%></td>
						<td><%=notice.getHit()%></td>
						<td><%=notice.getId()%></td>
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
				<a href="./NoticeListAction.do?pageNum=<%=i %>" />
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
			
			<div class="py-3" align="right">							
				<a href="./NoticeWriteForm.do" class="btn btn-primary">&laquo;공지용 글쓰기</a>				
			</div>			
			<div align="left">				
				<select name="items" class="txt">
					<option value="subject">제목에서</option>
					<option value="content">본문에서</option>
					<option value="name">글쓴이에서</option>
				</select> <input name="text" type="text" /> <input type="submit" id="btnAdd" class="btn btn-primary " value="검색 " />				
			</div>
		</form>			
	</div>
	<jsp:include page="../footer.jsp" />
</div>
</body>
</html>





