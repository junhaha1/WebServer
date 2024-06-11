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
	
	String menu = "../menu.jsp";
	if(name.equals("admin"))
		menu = "../adminPage/adminmenu.jsp";
	System.out.println(menu);

	Message msg = (Message) request.getAttribute("msg");
	String date = msg.getSENDDATE().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	
%>
<html>
<head>
<link rel="stylesheet" href=<%=route + style %> />
<title>Board</title>
</head>
<body>
<div class="container py-4">
	<jsp:include page="<%=menu %>"/>
	 <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold">메세지 상세 보기</h1>
        <p class="col-md-8 fs-4">Board</p>      
      </div>
    </div>
	<div class="row">	 
		<form name="newUpdate" action=""  method="post">
			<table class ="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3" style="background-color: #eeeeee; text-align: center;">메세지 보기</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 20%;">보낸 사람</td>
						<td colspan="2"><%=msg.getUID() %></td>
					</tr>
					<tr>
						<td style="width: 20%;">글 제목</td>
						<td colspan="2"><%=msg.getTITLE() %></td>
					</tr>
					<tr>
						<td>작성일자</td>
						<td colspan="2"><%=date %></td>
					</tr>
					<tr>
						<td>내용</td>
						<td colspan="2">
							<div  style="min-height: 200px; text-align: left">
								<%=msg.getCONTENT() %>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
			<a href =<%=route + home %> class = "btn btn-primary" role="button">이전</a>
	</div>
	<jsp:include page="../footer.jsp" />
	</div>
</body>
</html>


