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
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/paginate.js"></script>


<style type="text/css">
.page-box { clear: both; padding: 20px 0; text-align: center; }
.paginate {
	text-align: center;
	white-space: nowrap;
	font-size: 14px;	
}
.paginate a {
	border: 1px solid #ccc;
	color: #000;
	font-weight: 600;
	text-decoration: none;
	padding: 3px 7px;
	margin-left: 3px;
	vertical-align: middle;
}
.paginate a:hover, .paginate a:active {
	color: #6771ff;
}
.paginate span {
	border: 1px solid #e28d8d;
	color: #cb3536;
	font-weight: 600;
	padding: 3px 7px;
	margin-left: 3px;
	vertical-align: middle;
}
.paginate :first-child {
	margin-left: 0;
}

a { color: #222; text-decoration: none; cursor: pointer; }
a:active, a:hover { color: #f28011; text-decoration: underline; }

</style>

<!-- c:url은 ${pageContext.request.contextPath}를 자동으로 추가해줌 -->
<c:url var="listUrl" value="/board/main">
	<c:if test="${not empty keyword}">
		<c:param name="keyword" value="${keyword}"/>
	</c:if>
</c:url>


<script type="text/javascript">
//  모든 페이지 로드 됨과 동시에 실행되는 이벤트
window.addEventListener('load', function() {
	let message = "${message}";
	if(message != "")
		alert(message);
	
	let page = ${page};
	let pageSize = ${rows};
	let dataCount = ${dataCount};
	let url = "${listUrl}";
	
	let total_page = pageCount(dataCount, pageSize);
	let paging = pagingUrl(page, total_page, url);
	
	document.querySelector(".dataCount").innerHTML = 
		dataCount + "개 (" + page + " / " + total_page + "페이지)";
	
	document.querySelector(".page-box").innerHTML = 
		dataCount == 0 ? "등록된 게시물이 없습니다." : paging;
	
});

// 로그인
function sendModalLogin() {
	const f = document.modalLoginForm;
	
	f.action = "${pageContext.request.contextPath}/member/login";
	f.submit();
}

// 검색
function searchList() {
	const f = document.searchForm;
	
	f.action = "${pageContext.request.contextPath}/board/main";
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
  <div class="container" style="padding-top: 30px; max-width: 1000px;">
    <h2 style="padding-top: 10px; padding-bottom: 10px;"> 게시판</h2>
	    <div class="body-container">			
			<div class="body-main">
				<table class="table">
		        	<tr>
		        		<td width="50%"><span class="dataCount"></span></td>
		        		<td align="right">&nbsp;</td>
		        	</tr>			
		        </table>
				
				<table class="table table-hover board-list">
					<thead class="table-light">
						<tr style="text-align:center;">
							<th>번호</th>
							<th style="width: 400px;">제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회수</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr style="text-align:center;">
								<td>${dataCount - (page-1) * rows - status.index}</td>
								<td>
									<c:url var="url" value="/board/article">
										<c:param name="num" value="${dto.num}"></c:param>
										<c:param name="page" value="${page}"></c:param>
										<c:if test="${not empty keyword}">
											<c:param name="keyword" value="${keyword}"/>
										</c:if>
									</c:url>
									<a href="${url}">${dto.subject}</a>
								</td>
								<td>${dto.userName}</td>
								<td>${dto.regDate}</td>
								<td>${dto.hitCount}</td>
							</tr>
						</c:forEach>
					</tbody>
					
				</table>
				
				<div class="page-box"></div>
	
				<div class="row board-list-footer">
					<div class="col">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/board/main';">새로고침</button>
					</div>
					<div class="col-6 text-center">
						<form class="row" name="searchForm" method="post">
							<div class="col-6 p-1" style="float:left;">
								<input type="text" name="keyword" value="${keyword}" class="form-control">
							</div>
							<div class="col-auto p-1" style="float:right;">
								<button type="button" class="btn btn-light" onclick="searchList()">검색</button>
							</div>
						</form>
					</div>
					<div class="col text-end">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/board/write';">글올리기</button>
					</div>
				</div>
	
			</div>
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

</body> </html>