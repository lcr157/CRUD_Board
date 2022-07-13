<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
</head>

<body>
<div class="conten
	tBody container col-11 my-5">
	<div id="login">
		<div class="modal-dialog">
			<div class="modal-content rounded-5 shadow">
				<div class="modal-header  p-5 pb-4 border-bottom-0">
					<h2 class="fw-bold mb-0 login-brand">DEV Learn</h2>
				</div>
				<div class="modal-body p-5 pt-0">
					
					<form name="LoginForm" method="post">
					<div class="form-floating mb-3">
						<input type="email" class="form-control rounded-4" id="floatingInput" name="memberEmail" placeholder="name@example.com">
						<label for="floatingInput">Email address</label>
					</div>
					<div class="form-floating mb-3">
						<input type="password" class="form-control rounded-4" id="floatingPassword" name="memberPwd" placeholder="Password">
						<label for="floatingPassword">Password</label>
					</div>
					<button class="w-100 mb-2 btn btn-lg rounded-4" type="button" style="background-color:#87CE00; color:#fff; font-weight:600;" onclick="sendLogin();">Log In</button>
					<small class="text-muted text-center"><span onclick="findPwd();" style="cursor: pointer;">비밀번호 찾기</span> | <span onclick="signUp();" style="cursor: pointer;">회원가입</span></small>
					<hr class="mt-3">
					<small class="fw-bold mb-3 text-secondary text-center">간편 로그인</small>
					<div>&nbsp;</div>
					<button class="w-100 py-2 mb-2 btn btn-outline-warning rounded-4" type="submit">
						Sign up with Kakao
					</button>
					<button class="w-100 py-2 mb-2 btn btn-outline-primary rounded-4" type="submit">
						Sign up with Facebook
					</button>
					<button class="w-100 py-2 mb-2 btn btn-outline-secondary rounded-4" type="submit">
						Sign up with GitHub
					</button>
					</form>
					
				</div>
			</div>
		</div>
	</div>	 
</div>
</body> </html>