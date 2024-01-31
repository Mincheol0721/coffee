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
            	<ul class="nav navbar">
              		<li><a href="/coffee/main" class="nav-link px-2 link-secondary"><img src="/images/home.png" alt="HOME" ><br> <span class="nav-span">홈</span></a></li>
              		<li class="nav-item dropdown">
		          		<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
		            		<img src="/images/coffee.png" alt="COFFEE" style="margin-right: 1.5rem;"> <br> 
		            		<span class="nav-span dropdown text-end users">게시판</span>
		          		</a>
		          		<ul class="dropdown-menu">
				            <li><a class="dropdown-item" href="/coffee/board/dailyBoardList">일상 게시판</a></li>
				            <li><a class="dropdown-item" href="#">커피 게시판</a></li>
				            <li><a class="dropdown-item" href="#">질의응답 게시판</a></li>
		          		</ul>
					</li>
              		<li class="nav-item dropdown">
		          		<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
		            		<img src="/images/coffee_machine.png" alt="BUY" style="margin-right: 1.8rem;"> <br> 
		            		<span class="nav-span">커피용품</span>
		          		</a>
		          		<ul class="dropdown-menu">
				            <li><a class="dropdown-item" href="#">커피 원두</a></li>
                			<li><a class="dropdown-item" href="#">커피 추출 용품</a></li>
                			<li><a class="dropdown-item" href="#">입점 문의</a></li>
		          		</ul>
					</li>
           		</ul>
            	<form role="search">
              		<input type="search" class="form-control" placeholder="Search..." aria-label="Search">
           	 	</form>
    		
            	<div class="dropdown text-end users">
              		<a href="#" class="dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
	              		<c:if test="${empty member}">
	               		 	<img src="/images/login_image.png" alt="user" width="32" height="32" class="rounded-circle">
	              		</c:if>
	              		<c:if test="${not empty member}">
	                		<img src="/coffee/member/download?id=${member.id }" alt="user" width="32" height="32" class="rounded-circle">
	              		</c:if>
              		</a>
   					<c:choose>
    					<c:when test="${empty member}">
		              		<ul class="dropdown-menu text-small">
		                		<li><a class="dropdown-item" href="/coffee/member/loginForm">로그인</a></li>
		                		<li><a class="dropdown-item" href="/coffee/member/regForm">회원가입</a></li>
	              			</ul>
    					</c:when>
    					<c:when test="${not empty member}">
							<!-- 로그인 시 화면-->
							<ul class="dropdown-menu text-small">
				  				<li><a class="dropdown-item" href="/coffee/member/memberDetail">마이페이지</a></li>
				  				<li><a class="dropdown-item" href="/coffee/member/modForm">회원정보수정</a></li>
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
				  					<c:if test="${isNaver eq true}">
				  						<a class="dropdown-item" href="/coffee/member/naverLogout">로그아웃</a>
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