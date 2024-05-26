<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.Date" %>
<html>
<head>
	<link 04 href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel = "stylesheet">
	<title>Welcome</title>
</head>
<body>
<div class="container py-4">
	<%@ include file = "menu.jsp" %>
	<%! 
		String greeting= "맛집 추천 리스트에 오신 것을 환영합니다.";
		String tagline = "현재 시각";
	%>
	
	<div class = "p-5 mb-4 bg-body-tertiary rounded-3">
		<div class="container-fluid py-5">
			<h1 class="diplay-5 fw-bold"><%=greeting %></h1>
			<p class="col-md-8 fs-4">WebServer</p>
		</div>
	</div>
	
	<div class = "row align-items-md-stretch  text-center">
		<div class="col-md-12">
			<div class="h-100 p-5">
				<h3><%=tagline %></h3>
				<%
					//response.setIntHeader("Refresh", 1);
					Date day = new java.util.Date();
					String am_pm;
					int hour = day.getHours();
					int minute = day.getMinutes();
					int second = day.getSeconds();
					if (hour / 12 == 0){
						am_pm = "AM";
					}else{
						am_pm = "PM";
						hour = hour - 12;
					}
					
					String hour_s; //시간 출력 관련
					String minute_s;
					String second_s; //초 출력 관련
					
					if(hour < 10)
						hour_s = "0" + hour;
					else
						hour_s = ""+hour;
					
					if(minute< 10)
						minute_s = "0" + minute;
					else
						minute_s = "" + minute;
					
					if(second < 10)
						second_s = "0" + second;
					else
						second_s = ""+second;
					
					String CT = hour_s + ":" + minute_s + ":" + second_s + " " + am_pm;
					out.println("현재 접속 시각: " + CT + "\n");
				%>
				<form name="loginCheck" action="./processLoginCheck.jsp">
					<input type='radio' name='check' value='admin' />관리자
  					<input type='radio' name='check' value='user' checked/>일반 유저
					<input type="submit" value="로그인">
				</form>
			</div>
		</div>
	</div>
	<%@ include file = "footer.jsp" %>
</div>
</body>
</html>