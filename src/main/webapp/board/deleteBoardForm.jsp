<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="model.*"%>
<html>
<head>
	<%
		int code = -1;
		if(request.getAttribute("code") != null)
			code = ((Integer)request.getAttribute("code")).intValue();
	
		String name = (String)session.getAttribute("sessionId");
		String title = request.getParameter("title");
		int bid = Integer.parseInt(request.getParameter("bid"));
		
	%>
	<link rel="stylesheet" href= "<%=request.getContextPath() %>/resources/css/bootstrap.min.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/validation_reg.js"></script>
	<title>회원가입</title>
</head>
<script type="text/javascript">
	function checkPasswdForm() {
		if (document.Board.passwd.value != document.Board.passwd_confirm.value) {
			alert("비밀번호를 동일하게 입력하세요.");
			return false;
		}
		document.Board.submit();
	}
</script>
<body>
<div class="container py-4">
		<%@ include file = "../menu.jsp" %>
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="diplay-5 fw-bold"><%=name %>님 '<%=title %>' 게시글 삭제 </h1>
				<p class="col-md-8 fs-4"><%=name %>'s delete board</p>
			</div>
			<% if(code == 0){ %>
				<div>회원 정보가 일치하지 않습니다. 게시글 삭제 오류! 정보를 다시 확인해주십시오. </div>
			<% } else if(code == 2) {%>
				<div>회원 정보와 비밀번호가 일치하지 않습니다. </div>
			<% } %> 
		</div>
		<form class="form-signin" action="<%=request.getContextPath() %>/requestBoardDelete.userdo" name= "Board" method="post">
			<input type="hidden" class="form-control" name="name" value=<%=name%>>
			<input type="hidden" class="form-control" name="bid" value=<%=bid%>>
			<input type="hidden" class="form-control" name="title" value=<%=title%>>
			글 번호 : <%=bid %></br>
			글 제목 : <%=title %></br>
			<div>
				비밀번호<input type="text" class="form-control" id="passwd" name="passwd">
			</div>
			<div>
				비밀번호 확인<input type="text" class="form-control" id="passwd_confirm" name="passwd_confirm">
			</div>
			<div class = "mb-3 row">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="button" class = "btn btn-primary" value="글 삭제" onclick="checkPasswdForm()">
					<a href = "<%=request.getContextPath() %>/MyBoardListAction.userdo?name=<%=name %>" class = "btn btn-secondary" role="button">이전</a>
				</div>
			</div>
		</form>
		
		<%@ include file = "../footer.jsp" %>
	</div>
</body>
</html>