<%@ page contentType="text/html; charset=utf-8"  %>
<%@ page import="model.*"%>
<html>
<head>
	<%
		String name = (String)session.getAttribute("sessionId");
		Member member = (Member)request.getAttribute("member");
		
		int code = -1;
		if(request.getAttribute("code") != null){
			code = ((Integer)request.getAttribute("code")).intValue();
		}
	%>
	<link rel="stylesheet" href= "<%=request.getContextPath() %>/resources/css/bootstrap.min.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/validation_reg.js"></script>
	<title>회원가입</title>
</head>
<script type="text/javascript">
	function btnClick() {

	  const mydiv = document.getElementById('showInfo');
	  const todiv = document.getElementById('updateShow');
	  
	  if(mydiv.style.display === 'none') {
		todiv.style.display = 'none';
	    mydiv.style.display = 'block';

	  }else {
		todiv.style.display = 'block';
	    mydiv.style.display = 'none';
	  }
	}
	
	function checkPasswdForm() {
		if (document.Member.password_input.value == "" || document.Member.password_input.value != document.Member.password_check.value) {
			alert("비밀번호를 동일하게 입력하세요.");
			return false;
		}else{
			alert("비밀번호 일치!");
			 const pwbtn = document.getElementById('pwcheck');
			 pwbtn.style.display = 'none';
			 const pwcancel = document.getElementById('pwcancel');
			 pwcancel.style.display = 'block';
			 
			 document.getElementById('password_input').style.display = 'none';
			 document.getElementById('password_check').style.display = 'none';
			 document.getElementById('updateBtn').disabled = false;
			 
		}
	}
	function updatePasswdForm() {
		
		const pwbtn = document.getElementById('pwcheck');
		pwbtn.style.display = 'block';
		const pwcancel = document.getElementById('pwcancel');
		pwcancel.style.display = 'none';
		
		document.getElementById('password_input').style.display = 'block';
		document.getElementById('password_check').style.display = 'block';
		document.getElementById('updateBtn').disabled = true;
	}
</script>
<body>
<div class="container py-4">
		<%@ include file = "../menu.jsp" %>
		<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="diplay-5 fw-bold"><%=name %>님 정보 </h1>
				<p class="col-md-8 fs-4"><%=name %>'s Information</p>
				<%if(code == 2) {%>
					<p style="color:red" class="col-md-8 fs-4"><strong>이미 존재하는 아이디입니다!!</strong></p>
				<%}else if(code == 0) {%>
					<p style="color:red" class="col-md-8 fs-4"><strong>업데이트 실패 다시 시도해주십시오!</strong></p>
				<%} %>
			</div>
		</div>
		
		
		<div id = "showInfo">
			<div>
				ID<input type="text" class="form-control" id="id" name="id" value=<%=member.getId() %> disabled>
			</div>
			<div>
				name<input type="text" class="form-control" id="name" name="name" value=<%=member.getName() %> disabled> 
			</div>
			<div>
				Email<input type="text" class="form-control" id="email" name="email" value=<%=member.getEmail() %> disabled>
			</div>
			<div>
				MBTI<input type="text" class="form-control" id="mbti" name="mbti" value=<%=member.getMbti() %> disabled>
			<input type="button" id= "updateDo" class = "btn btn-primary" value="내 정보 수정하기" onclick="btnClick()">
			</div>
		</div>
		<div id = "updateShow" style="display:none;">
			<form class="form-signin" action="<%=request.getContextPath() %>/updateUserInfo.userdo?name=<%=name %>" name= "Member" method="post">
				<div>
					ID<input type="text" class="form-control" id="id" name="id" value=<%=member.getId() %> >
				</div>
				<div>
					name<input type="text" class="form-control" id="name" name="name" value=<%=member.getName() %> > 
				</div>
				<div>
					Email<input type="text" class="form-control" id="email" name="email" value=<%=member.getEmail() %> >
				</div>
				<div>
					MBTI<input type="text" class="form-control" id="mbti" name="mbti" value=<%=member.getMbti() %> >
				</div>
				<div>
					새 PW<input type="text" class="form-control" id="password_input" name="password" >
				</div>
				<div>
					새 PW 확인<input type="text" class="form-control" id="password_check" name="password_check" >
				</div>
					<input type="button" id= "pwcheck" class = "btn btn-primary" value="비밀번호 확인" onclick="checkPasswdForm()">
					<input type="button" id= "pwcancel" class = "btn btn-primary" value="비밀번호 수정" onclick="updatePasswdForm()" style="display:none;">
				<div class = "mb-3 row">
					<div class="col-sm-offset-2 col-sm-10">
						<input type="button" id= "updateCancle" class = "btn btn-primary" value="내 정보 수정 취소" onclick="btnClick()">
						<input type="submit" id="updateBtn" class="btn btn-primary " value="내 정보 수정하기" onclick="CheckMember()" disabled/>
					</div>
				</div>
			</form>
		</div>
		<a href ="userPage/deletemember.jsp" class = "btn btn-secondary" role="button">회원 탈퇴</a>
		<%@ include file = "../footer.jsp" %>
	</div>
</body>
</html>