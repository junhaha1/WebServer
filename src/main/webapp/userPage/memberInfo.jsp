<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="model.*"%>
<html>
<head>
	<%
		String name = (String)session.getAttribute("sessionId");
		Member member = (Member)request.getAttribute("member");
	%>
	<link rel="stylesheet" href= "<%=request.getContextPath() %>/resources/css/bootstrap.min.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/validation_reg.js"></script>
	<title>회원가입</title>
</head>
<script type="text/javascript">
	function userInfoDisabled() {
	      const user_id = document.getElementById('id');
	      const user_name = document.getElementById('name');
	      const user_email = document.getElementById('email');
	      const user_mbti = document.getElementById('mbti');
	     
	      user_id.disabled = false;
	      user_name.disabled = false;
	      user_email.disabled = false;
	      user_mbti.disabled = false;
	  
	 }
	function checkForm() {
		if (!document.Member.id.value) {
			alert("아이디를 입력하세요.");
			return false;
		}
		if (!document.Member.password.value) {
			alert("비밀번호를 입력하세요.");
			return false;
		}
		if (document.Member.password.value != document.Member.password_confirm.value) {
			alert("비밀번호를 동일하게 입력하세요.");
			return false;
		}
	}
</script>
<body>
<div class="container py-4">
		<%@ include file = "../menu.jsp" %>
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="diplay-5 fw-bold"><%=name %>님 정보 </h1>
				<p class="col-md-8 fs-4"><%=name %>'s Information</p>
			</div>
		</div>
		<form class="form-signin" action="" name= "Member" method="post">
			<div>
				ID<input type="text" class="form-control" id="id" name="id" value=<%=member.getId() %>>
			</div>
			<div>
				name<input type="text" class="form-control" id="name" name="name" value=<%=member.getName() %>>
			</div>
			<div>
				Email<input type="text" class="form-control" id="email" name="email" value=<%=member.getEmail() %>>
			</div>
			<div>
				MBTI<input type="text" class="form-control" id="mbti" name="mbti" value=<%=member.getMbti() %>>
			</div>
			<div class = "mb-3 row">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="button" class = "btn btn-primary" value="내 정보 수정" onclick="checkForm()">
					<a href ="userPage/deletemember.jsp" class = "btn btn-secondary" role="button">회원 탈퇴</a>
				</div>
			</div>
		</form>
		<%@ include file = "../footer.jsp" %>
	</div>
</body>
</html>