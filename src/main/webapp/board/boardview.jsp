<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ page import="java.time.format.*" %>
<%
	String name = (String) session.getAttribute("sessionId");
	String route = request.getContextPath();
	String style = "/resources/css/bootstrap.min.css";
	String userWrite = "/BoardWriteAction.userdo";
	String home = "/AllBoardListAction.userdo";

	Board board = (Board) request.getAttribute("board");
	List comentlist = (List) request.getAttribute("comentlist");
	String date = board.getRegdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	String BID = (String) request.getAttribute("BID");
	
	String imgroot = request.getContextPath() + "/resources/images";
	
	String title = "게시판";
	if(board.getId().equals("admin"))
		title = "공지글";
	
	String type = "";
	if(request.getParameter("type") != null){
		type = request.getParameter("type");
		System.out.println("boardview test: " + type);
		if(type.equals("common"))
			home = "/BoardListAction.userdo?type=" + type;
		else if(type.equals("notice"))
			home = "/NoticeBoardListAciton.userdo?type=" + type;
		else if(type.equals("coment"))
			home = "/comentBoardListAction.userdo?type=" + type +"&&name=" + name;
	}
%>
<html>
<head>
<link rel="stylesheet" href=<%=route + style %> />
<title>Board</title>
</head>
<script type="text/javascript">
	function checkForm() {
		if (!document.insertComent.coment.value) {
			alert("댓글을 입력하세요.");
			return false;
		}
	}
</script>
<body>
<div class="container py-4">
	<jsp:include page="../menu.jsp"/>
	 <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold"><%=title %></h1>
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
						<td colspan="2"><%=date %></td>
					</tr>
					<tr>
						<td>내용</td>
						<td colspan="2">
							<div  style="min-height: 200px; text-align: left">
								<%if(board.getImage() != null) {%>
									<img src = "<%= imgroot%>/<%=board.getImage() %>"  width="300px" height="300px">
								<%} %>
								<%=board.getContent() %>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<div class="mb-3 row">
				<div class="col-sm-offset-2 col-sm-10 ">
				 <form name="insertComent" action="./insertComent.userdo?type=<%=type %>&&name=<%=name %>"  method="post" onsubmit="return checkForm()">
					 <label class="col-sm-2 control-label" >댓글 작성</label>
					 		<input name="ID" type="hidden" class="form-control" value=<%=name %>>
					 		<input name="BID" type="hidden" class="form-control" value=<%=board.getBID() %>>
						<div class="col-sm-8">
							<textarea name="coment" cols="50" rows="5" class="form-control"placeholder="coment"></textarea>
						</div>
					 <input type="submit" class="btn btn-primary " value="등록 ">				
					 <input type="reset" class="btn btn-primary " value="초기화 ">
				 </form>
				</div>
			</div>
			<table class="table table-hover text-center">
					<tr>
						<th>댓글 번호</th>
						<th>유저ID</th>
						<th>내용</th>
						<th>작성일</th>
					</tr>
					<%
					if(comentlist != null){
						for (int j = 0; j < comentlist.size() ; j++){
							
							Coment coment = (Coment) comentlist.get(j);
							String date_coment = coment.getRegdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					%>
					<tr>
						<td><%=coment.getCNUM()%></td>
						<td><%=coment.getId() %></td>
						<td><%=coment.getContent()%></td>
						<td><%=date_coment%></td>
					</tr>
					<%
						}
					}
					%>
				</table>
			</div>
			
			<a href =<%=route + home %> class = "btn btn-primary" role="button">이전</a>
	</div>
	<jsp:include page="../footer.jsp" />
	</div>
</body>
</html>


