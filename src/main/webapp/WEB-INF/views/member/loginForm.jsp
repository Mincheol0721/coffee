<%@page import="java.security.SecureRandom"%>
<%@page import="java.math.BigInteger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="/css/login.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<c:choose>
		<c:when test="${result eq 'loginFailed'}">
			<script type="text/javascript">
				$(function() {
					alert('회원 정보가 틀립니다. 다시 입력해주세요.');
				});
			</script>
		</c:when>
		<c:when test="${result eq 'recovery'}">
<%--             <c:set var="member" value="${recovery}" /> --%>
            <script type="text/javascript">
                $(function() {
                	var id = '${recovery.id}';
                	var password = '${recovery.password}';
                	console.log(id);
                    if(confirm('삭제 요청하신 계정입니다. \n계정을 복구하시겠습니까?')) {
                    	$.ajax({
                    		url: '/member/recoveryMemberInfo',
                    		type: 'POST',
                    		data: {id: id, password: password},
                    		success: function(data) {
                    		    location.href = '/member/loginForm'
                    		}
                    	});
                    	alert('계정 복구가 완료되었습니다. \n다시 로그인을 시도해주세요.');
                    }
                });
            </script>
		</c:when>
	</c:choose>
</head>
<body>
    <c:set var="temp" value='<%=request.getParameter("temp")%>' />
	<div class="form-container">
		<p class="title">로그인</p>
		<form class="form" action="/member/login" method="post">
			<c:if test="${temp}">
	            <input type="hidden" id="temp" name="temp" value="true">
			</c:if>
			<div class="input-group">
				<label for="username">아이디</label>
				<input type="text"	name="id" id="id" placeholder="">
			</div>
			<div class="input-group">
				<label for="password">비밀번호</label>
				<input type="password" name="password" id="password" placeholder="">
				<div class="forgot">
					<a rel="noopener noreferrer" href="/member/pwCertificationForm">비밀번호를 잊으셨나요?</a>
				</div>
			</div>
			<button class="sign" type="submit">로그인</button>
		</form>
		<div class="social-message">
			<div class="line"></div>
			<p class="message">소셜네트워크 계정으로 로그인하기</p>
			<div class="line"></div>
		</div>
		<div class="social-icons">
			<a href="https://accounts.google.com/o/oauth2/v2/auth?client_id=1870458841-rqbmjhnqhn43rk2ecv1gppvhvhj2i4tp.apps.googleusercontent.com&redirect_uri=http://localhost:8090/member/oauth2google&response_type=code&scope=profile">
				<button aria-label="Log in with Google" class="icon">
					<img src="/images/google.png" class="loginIcon">
				</button>
			</a>
			<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=6f333d17c0737d247630d8be3aeead82&redirect_uri=http://localhost:8090/member/kakaoLogin&prompt=select_account">
				<button aria-label="Log in with kakao" class="icon">
					<img src="/images/kakao.png" class="loginIcon">
				</button>
			</a>
			<a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=LqbceHaqklXQzbcgejR8&redirect_uri=http://localhost:8090/member/naverLogin&state=<%=new BigInteger(130, new SecureRandom()).toString()%>">
				<button aria-label="Log in with naver" class="icon">
					<img src="/images/naver.png" class="loginIcon">
				</button>
			</a>
		</div>
		<p class="signup">
			계정이 없으신가요?
			<a rel="noopener noreferrer" href="/member/regForm" class="">회원가입하기</a>
		</p>
	</div>
</body>
</html>