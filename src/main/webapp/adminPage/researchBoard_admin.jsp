<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
	String sessionId = (String) session.getAttribute("sessionId");
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
	
	
	String title = "";
	String subTitle = "";
	
	title = "게시판 검색 결과";
	subTitle = "Board Search Result";
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
					</tr>
					<%
					if(searchlist != null){
						for (int j = 0; j < searchlist.size() ; j++){
							
							Board board = (Board) searchlist.get(j);
							String date = board.getRegdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					%>
					<tr>
						<td><%=board.getBID()%></td>
						<td><a href="./BoardViewAction.do?BID=<%=board.getBID()%>&name=<%=sessionId %>&type=admin_search" /><%=board.getTitle()%></a></td>
						<td><%=date%></td>
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
				<a href="./SearchListAction.do?pageNum=<%=i %>&items=<%=items %>&text=<%=text %>&type=<%=log.getType() %>" />
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
	<jsp:include page="../footer.jsp" />
</div>
</body>
</html>





