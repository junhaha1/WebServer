<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
	String name = (String) session.getAttribute("sessionId");
	String route = request.getContextPath();
	String style = "/resources/css/bootstrap.min.css";
	String mailSend = "/sendMail.userdo";
	String checkId = "/checkId.userdo";
	String home = "/AllBoardListAction.userdo";
	
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
		if (!document.newMail.rid.value) {
			alert("보내는 사람을 입력하세요.");
			return false;
		}
		if (!document.newMail.title.value) {
			alert("제목을 입력하세요.");
			return false;
		}
		if (!document.newMail.content.value) {
			alert("내용을 입력하세요.");
			return false;
		}		
		return true;
	}
	
</script>
<body>
<div class="container py-4">
	<jsp:include page="../menu.jsp" />
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
					<input name="rid" type="text" class="form-control">
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >제목</label>
				<div class="col-sm-5">
					<input name="title" type="text" class="form-control" placeholder="subject">
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 control-label" >내용</label>
				<div class="col-sm-8">
					<textarea name="content" cols="50" rows="5" class="form-control"placeholder="content"></textarea>
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
				<label class="col-sm-2 control-label" >내용</label>
				<div class="col-sm-8">
					<textarea name="content" cols="50" rows="5" class="form-control"placeholder="content"><%=msg.getCONTENT() %></textarea>
				</div>
			</div>
			<%} %>
			<div class="mb-3 row">
				<div class="col-sm-offset-2 col-sm-10 ">
				 <a href =<%=route + home %> class = "btn btn-primary" role="button">이전</a>
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



