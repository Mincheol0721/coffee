<%@page import="java.security.SecureRandom"%>
<%@page import="java.math.BigInteger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="message" value="${message}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="/css/login.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript">
		var message = '${message}';
		if(message == 'failed') {
			alert('현재 비밀번호가 틀립니다.\r\n다시 시도해주세요.');
		}
	</script>
</head>
<body>
	<div class="form-container">
<%-- 	<c:out value="message: ${message }" /> --%>
		<p class="title">비밀번호 변경</p>
		<form class="form" action="/member/modPassword" method="post">
<%-- 		<c:out value="id: ${member.id}" /> --%>
		<input type="hidden" name="id" value="${member.id}">
			<div class="input-group">
				<label for="curPassword">현재 비밀번호</label>
				<input type="password" name="curPassword" id="curPassword" placeholder="">
			</div>
			<div class="input-group">
				<label for="curPassword">비밀번호 입력</label>
				<input type="password" name="password" id="password" placeholder="">
			</div>
			<div class="input-group">
				<label for="curPassword">현재 비밀번호</label>
				<input type="password" name="passwordConfirm" id="passwordConfirm" placeholder="">
			</div>
			<br>
			<button class="sign" type="submit">변경하기</button>
		</form>
	</div>
</body>
</html>