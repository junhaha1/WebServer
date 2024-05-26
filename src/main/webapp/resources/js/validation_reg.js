function CheckMember(){
	
	var id = document.getElementById("id");
	var passwd = document.getElementById("passwd");
	var name = document.getElementById("name");
	var email = document.getElementById("email");
	var mbti = document.getElementById("mbti");
	
	var regExpId = /^[a-zA-Z0-9]*$/;
	var regExpPw = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
	//최소 8 자, 하나 이상의 대문자, 하나의 소문자, 하나의 숫자 및 하나의 특수 문자
	var regExpName = /^[가-힣]*$/;
	var regExpEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	var Mbtis = ['INTJ', 'INTP', 'ENTJ', 'ENTP', 
				'INFJ', 'INFP', 'ENFJ', 'ENFP', 
				'ISTJ', 'ISFJ', 'ESTJ', 'ESFJ', 
				'ISTP', 'ISFP', 'ESTP', 'ESFP'];
	
	//아이디 길이 수
	if(id.value.length < 4 || id.value.length > 50){
		alert("[ID]\n최소 4자에서 최대 50자까지 입력하세요.");
		id.focus();
		return false;
	}
	//아이디
	if(!check(regExpId, id, "영문과 숫자만 입력해주십시오.")) return false;
	//비밀번호
	if(!check(regExpPw, passwd, "최소 8 자, 하나 이상의 대문자, 하나의 소문자, 하나의 숫자 및 하나의 특수 문자를 입력해주십시오.")) return false;
	//이름
	if(name.value.length < 1){
		alert("[이름]\n이름을 입력해주십시오.");
		name.focus();
		return false;
	}
	if(!check(regExpName, name, "한글만 입력해주십시오.")) return false;
	//이메일
	if(!check(regExpEmail, email, "이메일 다시 입력해주십시오.")) return false;
	//mbti
	if(!Mbtis.includes(mbti.value.toUpperCase()))
	{
		alert("[MBTI]\nMBTI를 다시 한번 확인해주십시오.");
		mbti.focus();
		return false;
	}
	
	function check(regExp, e, msg){
		if(regExp.test(e.value)){
			return true;
		}
		alert(msg);
		e.focus();
		return false;
	}
	
	document.Member.submit();
	
}