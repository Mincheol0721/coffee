<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="vo" value="${vo}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/memberDetail.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body>
	<div class="form-container">
		<p class="title">마이페이지</p>
			<div class="profile">
				<img src="/member/download?nickname=${member.nickname}" id="thumbnail">
			</div>
			<div class="input-group">
				<label for="name">이름</label>
				<input type="text" name="name" id="name" class="detail" value="${vo.name}" placeholder="" disabled>
			</div>
			<div class="input-group">
				<label for="nickname">주민등록번호</label>
				<input type="text" name="ssn" id="ssn" class="detail" value="${fn:substring(vo.ssn, 0, 8)}******" disabled>
			</div>
			<div class="input-group">
				<label for="nickname">닉네임</label>
				<input type="text" name="nickname" id="nickname" class="detail" value="${vo.nickname}" placeholder="" disabled>
			</div>
			<div class="input-group">
				<label for="nickname">이메일</label>
				<input type="email" name="email" id="email" class="detail" value="${vo.email}" placeholder="" disabled>
			</div>
			<div class="input-group">
				<label for="mobile">전화번호</label>
				<input type="tel" name="mobile" id="mobile" class="detail" value="${vo.mobile}" placeholder="" disabled>
			</div>
			<div class="input-group">
				<label for="zipcode">주소</label>
				<input type="text" id="sample6_postcode" id="zipcode" class="detail" name="zipcode" placeholder="우편번호" style="width: 30%; border-radius: 0.375rem;" value="${vo.zipcode}" disabled>
			</div>
			<div class="input-group">
				<input type="text" id="sample6_address" name="roadAddr" class="detail" placeholder="주소" value="${vo.roadAddr}" disabled>
			</div>
			<div class="input-group">
				<input type="text" id="sample6_detailAddress" name="detailAddr" class="detail" placeholder="상세주소를 입력해주세요" value="${vo.detailAddr}" disabled>
			</div>
			<div class="input-group">
				<input type="text" id="sample6_extraAddress" name="jibunAddr" class="detail" placeholder="참고항목(OO동)" value="${vo.jibunAddr}" disabled>
			</div>
			<br>
			<button class="sign" onclick="location.href='/member/modForm'">회원정보 수정</button>
			<button class="sign" onclick="delMember();">회원탈퇴</button>
		<br>
	</div>
	<script type="text/javascript">
		function delMember() {
			confirm('계정정보를 삭제하시겠습니까?');
			$.ajax({
				url: '/member/delMember',
				type: 'POST',
				dataType: 'text',
				data: '${vo.id}',
				success: function(data) {
					alert('계정이 삭제되었습니다.');
					location.href='/main';
				}
			});
// 			location.href='/member/delMember?id=${vo.id}';
		}
	</script>
</body>
</html>