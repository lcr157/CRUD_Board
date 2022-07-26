# CRUD_Board - 토이프로젝트
> CRUD의 기본이 되는 게시판을 만들어본 프로젝트입니다.

![image](https://user-images.githubusercontent.com/44182633/180828961-c3bbc80e-f781-4fc0-b0ab-8ffdfcec2127.png)


## 목차
- [기획](#기획)
  - [프로젝트 소개](#1-프로젝트-소개)
  - [주요기능](#2-주요기능)
  - [기술 스택](#3-기술-스택)
  - [요구사항 분석](#4-요구사항-분석)
- [구조 및 설계](#구조-및-설계)
  - [테이블 구조](#5-테이블-구조)
  - [사이트맵](#6-사이트맵)
- [개발내용](#개발내용)
  - [메인페이지](#1-메인페이지)
  - [회원 관련기능](#2-회원-관련기능)
  - [게시판 관련기능](#3-게시판-관련기능)
- [프로젝트 마치며](#프로젝트-마치며)
<br>

## 기획
### 1. 프로젝트 소개

웹 프로그래밍의 기본소양인 게시판을 만들어보며 CRUD에 대해 익히고 spring mvc패턴에 대한 이해와 현업에서 자주 사용하는 spring boot와 jpa에 익숙해지며 한번도 사용해보지 못했던 Oauth2.0를 직접 써보는것에 목표를 둔 프로젝트입니다.
<br><br>

### 2. 주요기능

기능은 크게 3개의 분류로 나뉩니다.<br>
- **회원** - 회원가입, 로그인, 회원정보 수정, 회원탈퇴가 가능
- **게시판** - CRUD기능, 조회수 관리, 페이징 및 검색 기능
- **카카오로그인(Oauth2.0)** - 로그인 시 카카오 계정으로 연동하여 회원가입 후 로그인 가능
<br>

### 3. 기술 스택
#### 개발 언어
- Java 8

#### 개발 환경
- sts4
- JAP(spring data JPA)

#### Build Tool
- Maven

#### DataBase
- Oracle 18c

#### 형상관리
- GitHub

#### api
- Oauther 2.0(kakao developer)
<br>

### 4. 요구사항 분석
**로그인** <br>
- 로그인 시 아이디와 비밀번호 체크한다. 잘못 입력 시 메인페이지로 이동<br>
- OAuth2.0추가로 카카오 계정으로 연동기능 추가<br>
  처음 연동되었다면 회원 가입 페이지로 이동하여 회원가입 진행<br>
  이미 연동된 경우 메인페이지로 이동하여 로그인하기 <br><br>


**회원가입** <br>
- <b>유효성검사</b>
  - 반드시 이름, 이메일, 비밀번호, 비밀번호 확인에 값 입력<br>
  - 비밀번호 확인은 비밀번호와 동일하게 입력<br>

- <b>아이디중복확인</b>
  - '중복확인'버튼을 통해 회원 테이블의 아이디 검사하여 중복확인가능<br><br>


**게시판** <br>
- 로그인 했을 때만 게시글 작성 가능<br>
- 관리자와 글쓴이만 게시글 수정 및 삭제 가능<br>
- 페이징처리는 게시글 10개 기준이고 검색은 제목+내용 키워드로 검색<br><br>


## 구조 및 설계
### 5. 테이블 구조
<details>
  <summary>ERD 다이어그램</summary>
  
  ![image](https://user-images.githubusercontent.com/44182633/180821230-7d253fb9-4f52-4634-aa9d-ccf17476f7d7.JPG)
</details>

### 6. 사이트맵
<details>
  <summary>사이트맵</summary>
  
  ![image](https://user-images.githubusercontent.com/44182633/180826375-768f4581-09ff-401d-81b6-761d6a280b9a.JPG)
</details>


## 개발내용
### 1. 메인페이지
<h4>메인페이지</h4>

![image](https://user-images.githubusercontent.com/44182633/180924018-f0ca12b6-fdaa-4f69-a087-bb99b978507e.JPG)

![image](https://user-images.githubusercontent.com/44182633/180924006-0fe9874b-6eaa-48eb-912f-f9451dc2794b.JPG)

- <b>작성된 게시글 리스트</b>를 보여줍니다.<br>
- <b>제목, 작성자, 작성일, 조회수 </b>순으로 게시글 정보를 보여줍니다.<br>
- <b>페이징처리 및 검색(제목+내용으로 키워드검색)</b>기능을 추가했습니다.<br>
- <b>글올리기</b>를 통해 <b>게시글 작성이 가능</b>합니다.<br><br>

![메인페이지_3](https://user-images.githubusercontent.com/44182633/180932445-aba0dc2c-9d33-499f-9943-96659cf0f30e.JPG)

- <b>검색(제목+내용으로 키워드검색)</b>하면 검색키워드가 포함된 게시글 리스트가 나옵니다.
<hr><br>


<h4>메인페이지 alert창</h4>

![메인페이지_실패시1](https://user-images.githubusercontent.com/44182633/180927308-abf888a3-87f8-466a-beb3-b6c10a651c8e.JPG)

![메인페이지_실패시2](https://user-images.githubusercontent.com/44182633/180927319-5cb8e523-ca02-40b8-b647-39644b7e219d.JPG)

- 로그인실패, 회원정보수정, 회원탈퇴 등 <b>로그인 관련 처리</b>를하고나면 메인페이지로 돌아와 alert창으로 문구를 띄워줍니다.
  - <b>RedirectAttributes클래스</b> 이용하여 <b>addFlashAttribute("message", "문구")로 redirect시킬때 값을 넘겨</b>주었습니다.
<hr><br><br>


### 2. 회원 관련기능
<h4>로그인 모달창</h4>

![image](https://user-images.githubusercontent.com/44182633/180927672-6377c741-ea8f-44b1-925d-3b2bd08abde9.png)

- <b>로그인</b>은 헤더 우측 상단 "로그인"버튼을 누르면 모달창으로 띄워주며 로그인, 회원가입, 간편 로그인(카카오 연동)이 가능합니다.
<hr><br>


<h4>로그인 - 로그인<h4>

![로그인_로그인1](https://user-images.githubusercontent.com/44182633/180928373-2c5b50a2-b72b-440b-99b3-8c50edfbe572.JPG)

- <b>로그인</b>은 <b>로그인JSP페이지와 로그인 모달창 두 가지</b>로 가능합니다.<br><br>

![로그인_로그인2](https://user-images.githubusercontent.com/44182633/180928380-22e1372d-c186-425c-a00e-352dc065db56.JPG)

- 아이디, 비밀번호 정상 입력 시, 로그인이 됩니다.<br><br>

![메인페이지_실패시1](https://user-images.githubusercontent.com/44182633/180935404-5618f7aa-6f65-4912-9d30-2e02d52bc154.JPG)

- 아이디, 비밀번호 잘못 입력 시, 메인페이지로 이동 후 문구 띄워줍니다.
<hr><br>


<h4>로그인 - 간편로그인</h4>

![image](https://user-images.githubusercontent.com/44182633/180929966-3507b378-a33c-4169-8f67-f9e61d438d1f.png)

- 간편 로그인은 oAuth 2.0을 이용하여 카카오 계정과 연동해주었습니다.<br><br>

![로그인_로그인_간편로그인1](https://user-images.githubusercontent.com/44182633/180930613-02d4ec3f-edc6-40ef-9e83-9f85e1a3b52b.JPG)

- 카카오 아이디로 로그인하여 <b>인가된 코드와 토큰 정보들을 받아와 접속 가능여부를 판단</b>합니다.<br><br>

![로그인_로그인_간편로그인2](https://user-images.githubusercontent.com/44182633/180930938-224a075a-fbf7-4ed9-96b0-2e99aca00c18.JPG)

- <b>처음 연동</b>했다면 <b>회원가입 창으로 이동하여 회원가입 진행</b>합니다.<br><br>

![로그인_로그인_간편로그인3](https://user-images.githubusercontent.com/44182633/180930976-8922442f-6d58-4edc-84df-9f6ecc2286d7.JPG)

- <b>이미 연동</b>되어있다면 <b>메인페이지로 이동 후 "이미 가입된 회원입니다"라는 문구</b>를 띄워줍니다.
<hr><br>


<h4>회원가입</h4>

![로그인_회원가입](https://user-images.githubusercontent.com/44182633/180928545-87b96ef3-ae56-44ba-945f-b75b496d91fe.JPG)

- 회원가입은 <b>아이디중복확인, 유효성 검사</b>가 이루어집니다.<br>
  두 기능은 <b>Ajax를 이용하여 Jsp -> Controller -> Jsp의 흐름</b>으로 제어하였습니다.<br><br>


![로그인_회원가입_아이디중복확인](https://user-images.githubusercontent.com/44182633/180928724-303e44eb-0096-4eb0-8542-fca9ec731602.JPG)

![로그인_회원가입_아이디중복확인2](https://user-images.githubusercontent.com/44182633/180928837-52e1ebfb-4ac3-4de6-86bd-550fc0bfaf4c.JPG)

- <b>아이디중복확인</b>은 이미 가입된 아이디가 있다면 빨간 문구로 알려주고, 존재하지 않는 아이디라면 사용가능하다고 알려줍니다.<br><br>

![로그인_회원가입_유효성검사](https://user-images.githubusercontent.com/44182633/180929381-7b53222a-ae22-4e30-958a-fc1fd501bdac.JPG)

- <b>유효성검사</b>는 <b>아이디, 이메일 입력안하면 빨간 문구</b>로 알려줍니다. <br><br>

![로그인_회원가입_유효성검사2](https://user-images.githubusercontent.com/44182633/180929390-3dd17bf3-fe37-438e-81af-bf5d1343ecc9.JPG)

- <b>비밀번호 확인</b>는 <b>비밀번호와 같지 않거나, 아무런 값 입력되지 않으면 빨간 문구</b>로 알려줍니다.
<hr><br>


<h4>회원정보수정</h4>

![회원_정보수정](https://user-images.githubusercontent.com/44182633/180931405-b3a45c05-25a9-4873-b45d-1a0a4499b25b.JPG)

- <b>회원정보수정</b>는 이미 로그인 되어있는 회원의 정보를 수정합니다.<br><br>

![회원_회원탈퇴](https://user-images.githubusercontent.com/44182633/180931590-a284a3dc-1c84-4068-aa06-11bec2076ee8.JPG)

- <b>이름, 이메일, 비밀번호 변경이 가능</b>하고, <b>회원 탈퇴도 가능</b>합니다.
<hr><br><br>


### 3. 게시판 관련기능

<h4>게시글 올리기(create)</h4>

![게시판_글올리기](https://user-images.githubusercontent.com/44182633/180931878-ff0ed281-de04-455a-b24c-6d26d85f418e.JPG)

![게시판_글올리기2](https://user-images.githubusercontent.com/44182633/180932026-3cfb44ae-7e41-4850-8919-0a1079135688.JPG)

- 메인페이지 우측하단에 <b>글올리기</b>를 통해 게시글을 등록합니다.
- 만일, <b>로그인 되어있지 않으면 로그인 페이지로 이동</b>합니다.<br><br>

![게시판_글올리기3](https://user-images.githubusercontent.com/44182633/180932118-13d8eda1-410a-424e-b1d5-d9e177c73b56.JPG)

- <b>게시글 작성</b>는 제목, 내용을 입력하여 등록 가능합니다.
<hr><br>


<h4>게시글 상세(read)</h4>

![게시글_게시글상세](https://user-images.githubusercontent.com/44182633/180933769-a6bbeb1a-380b-4147-b3d5-702fb3385a6d.JPG)

- <b>게시글 상세</b>를 통해 게시글 내용을 확인가능합니다.
- <b>작성날짜, 조회수, 내용</b>을 볼 수 있습니다.
- <b>게시글 수정</b>은 게시글 작성자만 가능하고, <b>게시글 삭제</b>는 게시글 작성자와 관리자가 가능합니다.
<hr><br>


<h4>게시글 수정(update)</h4>

![게시글_게시글수정](https://user-images.githubusercontent.com/44182633/180934222-7b0a150a-89b4-4a56-9a31-5eac3f23809e.JPG)

- <b>게시글 수정</b>은 게시글 제목과 게시글 내용을 수정할 수 있습니다.
<br><hr>


<h4>게시글 삭제(delete)</h4>

![게시글_게시글삭제](https://user-images.githubusercontent.com/44182633/180934240-80c8bd62-a1b8-404f-aaf6-ff7af6d73d64.JPG)

- <b>게시글 삭제</b>는 게시글에 대한 정보를 삭제할 수 있습니다.
<br><hr>


## 프로젝트 마치며

