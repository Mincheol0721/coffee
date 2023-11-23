<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  <link rel="stylesheet" href="/css/main.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
  <script src="/js/main.js"></script>
  <script src="http://code.jquery.com/jquery-latest.min.js"></script>
  <script src="https://kit.fontawesome.com/3c365b85f4.js" crossorigin="anonymous"></script>
</head>
<body>
	<!-- 헤더 -->
    <div class="header">
      <header class="p-3 mb-3">
        <div class="container">
          <div class="justify-content-lg-start logoBox">
            <a href="/coffee/main" class="mb-2 mb-lg-0 link-body-emphasis logoLink">
              <img src="/images/logo.png" alt="" class="header-logo">
            </a>
    		
            <!-- <div class="dropdown bars">
              <a href="#" class="dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
              	<i class="fa-solid fa-bars"></i>
              </a>
              <ul class="dropdown-menu text-small toggleNav">
            	<li><a href="/coffee/main" class="nav-link px-2 link-secondary"><img src="/images/home.png" alt="HOME" ><br> <span class="nav-span">홈</span></a></li>
             	<li><a href="#" class="nav-link px-2 link-body-emphasis"><img src="/images/coffee.png" alt="COFFEE"> <br> <span class="nav-span">일상공유</span></a></li>
              	<li><a href="#" class="nav-link px-2 link-body-emphasis"><img src="/images/coffee_machine.png" alt="BUY"> <br> <span class="nav-span">용품구매</span></a></li>
              </ul>
    		</div> -->
    		
            <ul class="nav navbar">
              <li><a href="/coffee/main" class="nav-link px-2 link-secondary"><img src="/images/home.png" alt="HOME" ><br> <span class="nav-span">홈</span></a></li>
              <li><a href="#" class="nav-link px-2 link-body-emphasis"><img src="/images/coffee.png" alt="COFFEE"> <br> <span class="nav-span">일상공유</span></a></li>
              <li><a href="#" class="nav-link px-2 link-body-emphasis"><img src="/images/coffee_machine.png" alt="BUY"> <br> <span class="nav-span">용품구매</span></a></li>
            </ul>
    
            <form role="search">
              <input type="search" class="form-control" placeholder="Search..." aria-label="Search">
            </form>
    		
            <div class="dropdown text-end users">
              <a href="#" class="dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
              <c:if test="${isLogOn ne true}">
                <img src="/images/login_image.png" alt="user" width="32" height="32" class="rounded-circle">
              </c:if>
              <c:if test="${isLogOn eq true}">
                <img src="/coffee/member/download?id=${member.id }" alt="user" width="32" height="32" class="rounded-circle">
              </c:if>
              </a>
   			<c:choose>
    			<c:when test="${isLogOn ne true}">
		              <ul class="dropdown-menu text-small">
		                <li><a class="dropdown-item" href="/coffee/member/loginForm">로그인</a></li>
		                <li><a class="dropdown-item" href="/coffee/member/regForm">회원가입</a></li>
		              </ul>
    			</c:when>
    			<c:when test="${isLogOn eq true}">
				<!-- 로그인 시 화면-->
				<ul class="dropdown-menu text-small">
				  <li><a class="dropdown-item" href="#">마이페이지</a></li>
				  <li><a class="dropdown-item" href="#">회원정보수정</a></li>
				  <li><hr class="dropdown-divider"></li>
				  <li>
				  <c:if test="${isOwnMember eq true}">
				  	<a class="dropdown-item" href="/coffee/member/logout">로그아웃</a>
				  </c:if>
				  <c:if test="${isKakao eq true}">
				  	<a class="dropdown-item" href="https://kauth.kakao.com/oauth/logout?client_id=6f333d17c0737d247630d8be3aeead82&logout_redirect_uri=http://localhost:8090/coffee/member/kakaoLogout">로그아웃</a>
				  </c:if>
				  <c:if test="${isGoogle eq true}">
				  	<a class="dropdown-item" href="https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://localhost:8090/coffee/member/googleLogout">로그아웃</a>
				  </c:if>
				  </li>
				</ul>
    			</c:when>
    		</c:choose>
            </div>
          </div>
        </div>
      </header>
    </div>
</body>
</html>