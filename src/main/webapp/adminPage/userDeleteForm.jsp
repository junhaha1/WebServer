<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
	String name = (String) session.getAttribute("sessionId");
	String id = request.getParameter("id");
	
	System.out.println("탈퇴 아이디: " + id);
	String route = request.getContextPath();
	String style = "/resources/css/bootstrap.min.css";
	String mailSend = "/sendDeleteMail.do";
	String adminhome = "/adminPage/adminMain.jsp";
	
	Message msg = null;
	
	if(request.getAttribute("msg") != null){
		msg = (Message)request.getAttribute("msg");
	}
%>
<html>
<head>
<link rel="stylesheet" href="<%=route+style %>" />
<title>Message</title>
</head>
<script type="text/javascript">
	function checkForm() {
		if (!document.newMail.content.value) {
			alert("내용을 입력하세요.");
			return false;
		}		
	}
	
</script>
<body>
<div class="container py-4">
	<jsp:include page="adminmenu.jsp" />
	 <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold">메세지 작성</h1>
        <p class="col-md-8 fs-4">Message Write</p>      
      </div>
    </div>

	<div class="row align-items-md-stretch text-center">	 	

		<form name="newMail" action="<%=route + mailSend %>" method="post" onsubmit="return checkForm()" autocomplete="off">
			<%if(msg == null) {%>
			<div class="mb-3 row">
				<input name="uid" type="hidden" class="form-control" value=<%=name %>>
				<label class="col-sm-2 control-label" >보내는 사람</label>
				<div class="col-sm-3">
					<input name="uid" type="text" class="form-control" value=<%=name %> disabled>
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >받는 사람</label>
				<div class="col-sm-3">
					<input name="rid" type="hidden" class="form-control" value=<%=id %>>
					<input name="rid" type="text" class="form-control" value=<%=id %> disabled>
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >제목</label>
				<div class="col-sm-5">
					<input name="title" type="hidden" class="form-control" value="!유저 강제 탈퇴 안내 메일!">
					<input name="title" type="text" class="form-control" value="!유저 강제 탈퇴 안내 메일!" disabled>
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >강제 탈퇴 사유</label>
				<div class="col-sm-8">
					<select id="content" name="content" size = 1>
						<option value = "">없음</option>
						<option value = "부적절한 게시판 사용" selected>부적절한 게시판 사용</option>
						<option value = "부적절한 댓글 및 게시글 작성">부적절한 댓글 및 게시글 작성</option>
						<option value = "올바르지 못한 사이트 이용">올바르지 못한 사이트 이용</option>
					</select>
				</div>
			</div>
			<%} else if(msg != null){ %>
			<div class="mb-3 row">
				<input name="uid" type="hidden" class="form-control" value=<%=msg.getUID() %>>
				<label class="col-sm-2 control-label" >보내는 사람</label>
				<div class="col-sm-3">
					<input name="uid" type="text" class="form-control" value=<%=msg.getUID() %> disabled>
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >받는 사람</label>
				<div class="col-sm-3">
					<input name="rid" type="text" class="form-control" value=<%=msg.getRID() %> >
					<p style="color:red" class="col-md-8 fs-4">존재하지 않는 아이디입니다!</p>
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >제목</label>
				<div class="col-sm-5">
					<input name="title" type="text" class="form-control" placeholder="subject" value=<%=msg.getTITLE() %>>
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >강제 탈퇴 사유</label>
				<div class="col-sm-8">
					<select id="content" name="content" size = 1>
						<option value = "">없음</option>
						<option value = "부적절한 게시판 사용" selected>부적절한 게시판 사용</option>
						<option value = "부적절한 댓글 및 게시글 작성">부적절한 댓글 및 게시글 작성</option>
						<option value = "올바르지 못한 사이트 이용">올바르지 못한 사이트 이용</option>
					</select>
				</div>
			</div>
			<%} %>
			<div class="mb-3 row">
				<div class="col-sm-offset-2 col-sm-10 ">
				 <a href =<%=route + adminhome %> class = "btn btn-primary" role="button">홈으로</a>
				 <input type="submit" class="btn btn-primary " value="등록 ">				
				 <input type="reset" class="btn btn-primary " value="초기화 ">
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="../footer.jsp" />
	</div>
</body>
</html>



