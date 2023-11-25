<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="map" value="${map}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/modMember.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body>
	<div class="form-container">
		<p class="title">회원정보 수정</p>
			<form class="form" action="/coffee/member/modMember" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${member.id}">
			<div class="profile">
				<input type="file" name="file" id="file" onchange="setThumbnail(event);">
				<label for="file"> 
					<img src="/coffee/member/download?id=${member.id}" id="thumbnail">
					<input type="hidden" name="fileName" value="${member.fileName}" >
				</label>
			</div>
			<div class="input-group">
				<label for="name">이름</label> 
				<input type="text" name="name" id="name" value="${member.name}" placeholder="" required>
			</div>
			<div class="input-group">
				<label for="nickname">주민등록번호</label> 
				<input type="text" name="ssn" id="ssn" value="${fn:substring(member.ssn, 0, 8)}******" disabled>
			</div>
			<div class="input-group">
				<label for="nickname">닉네임</label> 
				<input type="text" name="nickname" id="nickname" value="${member.nickname}" placeholder="" required>
			</div>
			<div class="input-group">
				<label for="nickname">이메일</label> 
				<input type="email" name="email" id="email" value="${member.email}" placeholder="" required>
			</div>
			<div class="input-group">
				<label for="mobile">전화번호</label> 
				<input type="tel" name="mobile" id="mobile" value="${member.mobile}" placeholder="" required>
			</div>
			<div class="input-group">
				<label for="zipcode">주소</label>
				<input type="text" id="sample6_postcode" id="zipcode" name="zipcode" placeholder="우편번호" style="width: 30%; border-radius: 0.375rem;" value="${member.zipcode}" required>
				<input type="button" onclick="sample6_execDaumPostcode()" id="findZipcode" value="우편번호 찾기" style="width: 30%; margin-left:2rem; border-radius: 0.375rem; background-color: #FEE500; color: black;">
			</div>
			<div class="input-group">
				<input type="text" id="sample6_address" name="roadAddr" placeholder="주소" value="${member.roadAddr}" required>
			</div>
			<div class="input-group">
				<input type="text" id="sample6_detailAddress" name="detailAddr" placeholder="상세주소를 입력해주세요" value="${member.detailAddr}" required>
			</div>
			<div class="input-group">
				<input type="text" id="sample6_extraAddress" name="jibunAddr" placeholder="참고항목(OO동)" value="${member.jibunAddr}" required>
			</div>
			
			<br>
			<button class="button" type="button" onclick="location.href='/coffee/member/modPasswordForm'">
			    <p>비밀번호 변경</p>
			    <svg stroke-width="4" stroke="currentColor" viewBox="0 0 24 24" fill="none" class="h-6 w-6" xmlns="http://www.w3.org/2000/svg">
			        <path d="M14 5l7 7m0 0l-7 7m7-7H3" stroke-linejoin="round" stroke-linecap="round"></path>
			    </svg>
			</button>
			<button class="sign" type="submit">수정</button>
		</form>
		<br>
	</div>
	
	
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
	    function sample6_execDaumPostcode() {
	        new daum.Postcode({
	            oncomplete: function(data) {
	                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
	                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                var addr = ''; // 주소 변수
	                var extraAddr = ''; // 참고항목 변수
	
	                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                    addr = data.roadAddress;
	                } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                    addr = data.jibunAddress;
	                }
	
	                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	                if(data.userSelectedType === 'R'){
	                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                        extraAddr += data.bname;
	                    }
	                    // 건물명이 있고, 공동주택일 경우 추가한다.
	                    if(data.buildingName !== '' && data.apartment === 'Y'){
	                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                    }
	                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                    if(extraAddr !== ''){
	                        extraAddr = ' (' + extraAddr + ')';
	                    }
	                    // 조합된 참고항목을 해당 필드에 넣는다.
	                    document.getElementById("sample6_extraAddress").value = extraAddr;
	                
	                } else {
	                    document.getElementById("sample6_extraAddress").value = '';
	                }
	
	                // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                document.getElementById('sample6_postcode').value = data.zonecode;
	                document.getElementById("sample6_address").value = addr;
	                // 커서를 상세주소 필드로 이동한다.
	                document.getElementById("sample6_detailAddress").focus();
	            }
	        }).open();
	    }
	    
	    function setThumbnail(e) {
			var input = e.target;
			var reader = new FileReader();
			var thumbnail = document.getElementById('thumbnail');
			
			reader.onload = function(e) {
				thumbnail.setAttribute('src', e.target.result);
			}
			reader.readAsDataURL(e.target.files[0]);
		}
	</script>
</body>
</html>