<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="java.util.*"%>
<%@ page import="model.Board"%>
<%

	String name = (String) session.getAttribute("sessionId");
	String route = request.getContextPath();
	String style = "/resources/css/bootstrap.min.css";
	String userWrite = "/BoardWriteAction.userdo";
	String home = "/AllBoardListAction.userdo";

	Board board = (Board) request.getAttribute("board");
	String BID = (String) request.getAttribute("BID");
%>
<html>
<head>
<link rel="stylesheet" href=<%=route + style %> />
<title>Board</title>
</head>
<body>

<div class="container py-4">
	<jsp:include page="../menu.jsp"/>
	 <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold">게시판</h1>
        <p class="col-md-8 fs-4">Board</p>      
      </div>
    </div>
	<div class="row">	 
		<form name="newUpdate" action=""  method="post">
			<table class ="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3" style="background-color: #eeeeee; text-align: center;">게시판 글 보기</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 20%;">글 번호</td>
						<td colspan="2"><%=board.getBID() %></td>
					</tr>
					<tr>
						<td style="width: 20%;">글 제목</td>
						<td colspan="2"><%=board.getTitle() %></td>
					</tr>
					<tr>
						<td>작성자</td>
						<td colspan="2"><%=board.getId() %></td>
					</tr>
					<tr>
						<td>작성일자</td>
						<td colspan="2"><%=board.getRegdate() %></td>
					</tr>
					<tr>
						<td>내용</td>
						<td colspan="2">
						<div  style="min-height: 200px; text-align: left"><%=board.getContent() %></div></td>
					</tr>
				</tbody>
			</table>
		</form>
			<a href =<%=route + home %> class = "btn btn-primary" role="button">이전</a>
	</div>
	<jsp:include page="../footer.jsp" />
	</div>
</body>
</html>


