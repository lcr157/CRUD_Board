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
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/paginate.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap5/css/bootstrap.min.css" type="text/css">


<script type="text/javascript">
function deleteBoard() {
	if( !confirm("게시글 삭제하시겠습니까?")) {
		return false;		
	}
	
	location.href = "${pageContext.request.contextPath}/board/delete?num=${dto.num}";
}

//로그인
function sendModalLogin() {
	const f = document.modalLoginForm;
	
	f.action = "${pageContext.request.contextPath}/member/login";
	f.submit();
}

//카카오 로그인
function loginWithKakao() {
	location.href = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=본일발급키&redirect_uri=본인발급주소";
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


<!-- 메인단 -->
<main class="flex-shrink-0">
	<div class="container" style="padding-top: 30px; max-width: 800px;">
		<h2 style="padding-top: 10px; padding-bottom: 10px;"> 게시판</h2>			
			<div class="body-main">
					<table class="table mt-3 write-form">
						<tr>
							<td colspan="2" align="center">
								${dto.subject}
							</td>
						</tr>
					
						<tr>
							<td width="50%">
								이름 : ${dto.userName}
							</td>
							<td align="right">
								${dto.regDate} | 조회 ${dto.hitCount}
							</td>
						</tr>
						
						<tr>
							<td colspan="2" valign="top" height="200">
								${dto.content}
							</td>
						</tr>
				
					<tr>
						<td width="50%">
							<c:choose>
								<c:when test="${sessionScope.member.userId == dto.userId}">
									<!-- 게시글 작성자로 로그인 -> 수정, 삭제 둘다 가능 -->
									<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/board/update?num=${dto.num}&page=${page}';">수정</button>
									<button type="button" class="btn" onclick="deleteBoard();">삭제</button>
								</c:when>
								
								<c:when test="${sessionScope.member.userRole == 1}">
									<!-- 관리자로 로그인 -> 수정 불가능, 삭제 가능 -->
									<button type="button" disabled="disabled" class="btn" onclick="location.href='${pageContext.request.contextPath}/board/update?num=${dto.num}&page=${page}';">수정</button>
									<button type="button" class="btn" onclick="deleteBoard();">삭제</button>
								</c:when>
								
								<c:otherwise>
									<!-- 비로그인 -> 수정, 삭제 불가능 -->
									<button type="button" disabled="disabled" class="btn" onclick="location.href='${pageContext.request.contextPath}/board/update?num=${dto.num}&page=${page}';">수정</button>
									<button type="button" disabled="disabled" class="btn" onclick="deleteBoard();">삭제</button>
								</c:otherwise>
							</c:choose>
						</td>
						<td align="right">
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/board/main?${query}';">리스트</button>
						</td>
					</tr>
				</table>
			
		</div>
	</div>
</main>


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

</body>
</html>