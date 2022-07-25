<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LimCRUD</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/bootstrap5/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap5/css/bootstrap.min.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<script type="text/javascript">
// ajax함수
function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
	    	
			console.log(jqXHR.responseText);
		}
	});
}


// 회원가입시
function memberOk() {
	const f = document.memberForm;
	let str;
	let mode = "${mode}";
	
	// 회원가입일경우
	str = f.UserId.value;
	if(!str) {
		str = "아이디를 입력하세요.";
		document.querySelector(".UserIdCheck").innerHTML = str;
		f.UserId.focus();
		return;
	}
	document.querySelector(".UserIdCheck").innerHTML = "";
	
	str = f.UserName.value;
	if(!str) {
		str = "이름을 입력하세요.";
		document.querySelector(".UserNameCheck").innerHTML = str;
		f.UserName.focus();
		return;
	}
	document.querySelector(".UserNameCheck").innerHTML = "";
	
	str = f.UserEmail.value;
	if(!str) {
		str = "이메일을 입력하세요.";
		document.querySelector(".UserEmailCheck").innerHTML = str;
		f.UserEmail.focus();
		return;
	}
	document.querySelector(".UserEmailCheck").innerHTML = "";
	
	str = f.UserPwd.value;
	if(!str) {
		str = "비밀번호를 입력하세요.";
		document.querySelector(".UserPwdCheck").innerHTML = str;
		f.UserPwd.focus();
		return;
	}
	document.querySelector(".UserPwdCheck").innerHTML = "";
	
	str = f.UserPwd2.value;
	if(!str) {
		str = "비밀번호를 입력하세요.";
		document.querySelector(".UserPwdReCheck").innerHTML = str;
		f.UserPwd2.focus();
		return;
	} else if(f.UserPwd.value != str) {
		str = "비밀번호를 확인하세요.";
		document.querySelector(".UserPwdReCheck").innerHTML = str;
		f.UserPwd2.focus();
		return;
	}
	
	if(mode == "other")	
		f.action = "${pageContext.request.contextPath}/member/signUp";
	else
		f.action = "${pageContext.request.contextPath}/member/${mode}";
		
	f.submit();
}

// 회원 탈퇴
function deleteOk() {
	const f = document.memberForm;
	
	if(! confirm("회원 탈퇴하시겠습니까??")) {
		return;
	}
	
	f.action = "${pageContext.request.contextPath}/member/delete";
	f.submit();
}

// 로그인
function sendModalLogin() {
	const f = document.modalLoginForm;
	
	f.action = "${pageContext.request.contextPath}/member/login";
	f.submit();
}

//카카오 로그인
function loginWithKakao() {
	location.href = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=본일발급키&redirect_uri=본인발급주소";
}

// 아이디 중복확인(ajax)
function Idcheck() {
	let id = document.querySelector("#UserId").value;
	let url = "${pageContext.request.contextPath}/member/idcheck";
	let query = "id=" + id;
	
	const fn = function(data) {
		let res = data.res;
		
		if(res === "true") {	// 중복되지 않은경우 -> 사용가능
			let str = id + "는 사용가능합니다.";
			document.querySelector(".UserIdCheck").style.color = 'blue';
			document.querySelector(".UserIdCheck").innerHTML = str;
			
			document.querySelector("#buttonOne").innerHTML = '중복확인 완료';
			document.querySelector("#buttonOne").disabled = true;
			
		} else {				// 중복된 경우 -> 사용불가능
			let str = id + "는 이미 등록된 아이디입니다.";
			document.querySelector(".UserIdCheck").innerHTML = str;
			return false;
		}
	};
	
	ajaxFun(url, "post", query, "json", fn);
}

</script>
</head>

<body>
<!-- 헤더 -->
<header class="mb-5">
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<div class="container-fluid">
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
		<div class="collapse navbar-collapse" id="navbarCollapse">
			<ul class="navbar-nav me-auto mb-2 mb-md-0">
				<li class="nav-item">
					<a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/board/main">LimCRUD</a>
            	</li>
			</ul>
        
			<div class="d-grid gap-2 d-md-flex justify-content-md-end">
				<c:choose>
					<c:when test="${empty sessionScope.member}">
						<button class="btn btn-light me-md-2" type="button" data-bs-toggle="modal" data-bs-target="#loginModal">로그인</button>
					</c:when>
					
					<c:when test="${sessionScope.member.userRole == 1}">
						<p style="color: white; margin:auto;">${sessionScope.member.userName}님 반갑습니다.</p>
					</c:when>
					
					<c:otherwise>
						<p style="color: white; margin:auto;">${sessionScope.member.userName}님 반갑습니다.</p>
						<button class="btn btn-light" type="button" onclick="location.href='${pageContext.request.contextPath}/member/update';">회원정보수정</button>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${empty sessionScope.member}">
						<button class="btn btn-light" type="button" onclick="location.href='${pageContext.request.contextPath}/member/signUp';">회원가입</button>
					</c:when>
					
					<c:otherwise>
						<button class="btn btn-light" type="button" onclick="location.href='${pageContext.request.contextPath}/member/logout';">로그아웃</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		</div>
	</nav>
</header>


<!-- 메인 -->
<div class="container" style="width:400px;">	
	<form name="memberForm" method="post" class="mt-5 pt-5">
		<h2 class="h3 mb-4 fw-normal">${mode == "update" ? "정보수정" : "회원가입"}</h2>
		<div class="row gy-4">
			<!-- 아이디 -->
			<c:choose>
				<c:when test="${mode == 'signUp'}">
					<div class="col-8">
						<input type="text" class="form-control form-control-lg" name="UserId" id="UserId" placeholder="아이디입력">
					</div>
					<div class="col-4">
						<button id="buttonOne" type="button" class="btn btn-secondary form-control-lg" style="width:100%; height:100%;" onclick="Idcheck();">중복확인</button>
					</div>
				</c:when>
				
				<c:when test="${mode == 'other'}">
					<div class="col-12">
						<input type="text" class="form-control form-control-lg" name="UserId" id="UserId" value="${dto.userId}" readonly="readonly">
					</div>
				</c:when>
				
				<c:otherwise>
					<div class="col-2" style="font-size: 20px; line-height: 40px; padding: 0; text-align: center;">
						<p>아이디</p>
					</div>
					<div class="col-10">
						<input type="text" class="form-control form-control-lg" name="UserId" id="UserId" value="${dto.userId}" readonly="readonly">
					</div>
				</c:otherwise>
			</c:choose>
			<p class="UserIdCheck" style="margin-bottom: 0px; margin-top: 8px; margin-left: 5px; color:red;"></p>
			
			<!-- 이름 -->
			<c:choose>
				<c:when test="${mode == 'signUp' }">
					<div class="col-12">
						<input type="text" class="form-control form-control-lg" name="UserName" id="UserName" placeholder="이름">
					</div>
				</c:when>
				
				<c:when test="${mode == 'other'}">
					<div class="col-12">
						<input type="text" class="form-control form-control-lg" name="UserName" id="UserName" placeholder="이름" value="${dto.userName}" readonly="readonly">
					</div>
				</c:when>
				
				<c:otherwise>
					<div class="col-2" style="font-size: 20px; line-height: 40px; padding: 0; text-align: center;">
						<p>이름</p>
					</div>
					<div class="col-10">
						<input type="text" class="form-control form-control-lg" name="UserName" id="UserName" placeholder="이름" value="${dto.userName}">
					</div>
				</c:otherwise>
			</c:choose>
			<p class="UserNameCheck" style="margin-bottom: 0px; margin-top: 8px; margin-left: 5px; color:red;"></p>
			
			
			<!-- 이메일 -->
			<c:choose>
				<c:when test="${mode == 'update' }">
					<div class="col-2" style="font-size: 20px; line-height: 40px; padding: 0; text-align: center;">
						<p>이메일</p>
					</div>
					<div class="col-10">
						<input type="text" class="form-control form-control-lg" name="UserEmail" id="UserEmail" placeholder="이메일" value="${dto.userEmail}">
					</div>
				</c:when>
				
				<c:otherwise>
					<div class="col-12">
						<input type="text" class="form-control form-control-lg" name="UserEmail" id="UserEmail" placeholder="이메일">
					</div>
				</c:otherwise>
			</c:choose>
			<p class="UserEmailCheck" style="margin-bottom: 0px; margin-top: 8px; margin-left: 5px; color:red;"></p>
			
			
			<!-- 비밀번호 -->
			<div class="col-12">
				<input type="password" class="form-control form-control-lg" name="UserPwd" id="UserPwd" placeholder="비밀번호">
			</div>
			<p class="UserPwdCheck" style="margin-bottom: 0px; margin-top: 8px; margin-left: 5px; color:red;"></p>
			<div class="col-12">
				<input type="password" class="form-control form-control-lg" name="UserPwd2" id="UserPwd2" placeholder="비밀번호 확인">
			</div>
			<p class="UserPwdReCheck" style="margin-bottom: 0px; margin-top: 8px; margin-left: 5px; color:red;"></p>
		</div>
		
		<button type="button" class="w-100 btn btn-lg btn-secondary" style="margin-top: 20px;" onclick="memberOk();">${mode == 'update' ? '정보수정' : '회원가입' }</button>
	
		<c:if test="${mode == 'update'}">
			<button type="button" class="w-100 btn btn-lg btn-danger" style="margin-top: 20px;" onclick="deleteOk();">회원탈퇴</button>
		</c:if>
	
	</form>
</div>


<!-- 푸터 -->
<footer class="py-3 my-4">
	<ul class="nav justify-content-center border-bottom pb-3 mb-3">
		<li class="nav-item"><a href="#" class="nav-link px-2 text-muted">Home</a></li>
	</ul>
	<p class="text-center text-muted">&copy; 2022 LimCRUD</p>
</footer>

<!-- 로그인 모달창 -->
<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content rounded-5 shadow">
			<div class="modal-header  p-5 pb-4 border-bottom-0">
				<h2 class="fw-bold mb-0 login-brand">LimCRUD</h2>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body p-5 pt-0">
				<form name="modalLoginForm" method="post">
				<div class="form-floating mb-3">
					<input type="text" class="form-control rounded-4" id="floatingInput" name="UserId">
					<label for="floatingInput">아이디</label>
				</div>
				<div class="form-floating mb-3">
					<input type="password" class="form-control rounded-4" id="floatingPassword" name="UserPwd">
					<label for="floatingPassword">비밀번호</label>
				</div>
				<button class="w-100 mb-2 btn btn-lg rounded-4" type="button" style="background-color:#87CE00; color:#fff; font-weight:600;" onclick="sendModalLogin();">로그인</button>
				<small class="text-muted text-center"><span onclick="location.href='${pageContext.request.contextPath}/member/signUp';" style="cursor: pointer;">회원가입</span></small>
				<hr class="mt-3">
				<small class="fw-bold mb-3 text-secondary text-center">간편 로그인</small>
				<div>&nbsp;</div>
				<button class="w-100 py-2 mb-2 btn btn-outline-warning rounded-4" type="button" onclick="loginWithKakao()">
					Sign up with Kakao
				</button>
				</form>
			</div>
		</div>
	</div>
</div>


</body> </html>