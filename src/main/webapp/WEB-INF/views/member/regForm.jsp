<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="map" value="${map}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/regMember.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	$(function() {


	});
</script>
</head>
<body>
	<div class="form-container">
		<p class="title">회원가입</p>
		<c:if test="${map eq null}">
			<form class="form" action="/member/insertMemberInfo" method="post" enctype="multipart/form-data">
		</c:if>
		<c:if test="${kakaoReg eq true}">
			<form class="form" action="/member/insertKakaoMember" method="post" enctype="multipart/form-data">
		</c:if>
		<c:if test="${googleReg eq true}">
			<form class="form" action="/member/insertGoogleMember" method="post" enctype="multipart/form-data">
		</c:if>
		<c:if test="${naverReg eq true}">
			<form class="form" action="/member/insertNaverMember" method="post" enctype="multipart/form-data">
		</c:if>
		<input type="hidden" id="socailId" name="socialId" value="${map.socialId}">
			<div class="profile">
				<input type="file" name="file" id="file" onchange="setThumbnail(event);">
				<label for="file">
				<c:if test="${map eq null}">
					<img src="/images/profile.png" id="thumbnail">
				</c:if>
				<c:if test="${map ne null}">
					<img src="${map.fileName}" id="thumbnail">
					<input type="hidden" name="fileName" value="${map.fileName}" >
				</c:if>
				</label>
			</div>
			<c:if test="${map ne null}">
			<div class="input-group">
				<label for="id">아이디<span class="idValidate valMsg"></span> </label>
				<input type="text"	name="id" id="id" placeholder="" required>
			</div>
			<div class="input-group">
				<label for="password">비밀번호<span class="passwordValidate valMsg"></span> </label>
				<input type="password" name="password" id="password" placeholder="" required>
			</div>
			<div class="input-group">
				<label for="passwordConfirm">비밀번호 확인 <span class="passwordConfirmValidate valMsg"></span></label>
				<input type="password" name="passwordConfirm" id="passwordConfirm" placeholder="" required>
			</div>
				<input type="hidden" name="id" id="id" value="${map.id}">
			<div class="input-group">
				<label for="name">이름</label>
				<input type="text" name="name" id="name" value="${map.name}" placeholder="" required>
			</div>
			<div class="input-group">
				<label for="nickname">주민등록번호</label>
				<input type="text" name="ssn" id="ssn" placeholder="하이픈(-)을 포함하여 입력해주세요. 예)930101-1234567" required>
			</div>
			<div class="input-group">
				<label for="nickname">닉네임 <span class="nickValidate valMsg"></span> </label>
				<input type="text" name="nickname" id="nickname" placeholder="" required>
			</div>

			<div class="input-group">
				<label for="nickname">이메일</label>
				<input type="email" name="email" id="email" value="${map.email}" placeholder="" required>
			</div>
			<div class="input-group">
				<label for="mobile">전화번호</label>
				<input type="tel" name="mobile" id="mobile" placeholder="" required>
			</div>
			<div class="input-group">
				<label for="zipcode">주소</label>
				<input type="text" id="sample6_postcode" id="zipcode" name="zipcode" placeholder="우편번호" style="width: 30%; border-radius: 0.375rem;" required>
				<input type="button" onclick="sample6_execDaumPostcode()" id="findZipcode" value="우편번호 찾기" style="width: 30%; margin-left:2rem; border-radius: 0.375rem;">
			</div>
			<div class="input-group">
				<input type="text" id="sample6_address" name="roadAddr" placeholder="주소" required>
			</div>
			<div class="input-group">
				<input type="text" id="sample6_detailAddress" name="detailAddr" placeholder="상세주소를 입력해주세요" required>
			</div>
			<div class="input-group">
				<input type="text" id="sample6_extraAddress" name="jibunAddr" placeholder="참고항목(OO동)" required>
			</div>
			</c:if>
			<c:if test="${map eq null}">
				<div class="input-group">
					<label for="id">아이디<span class="idValidate valMsg"></span> </label>
					<input type="text"	name="id" id="id" placeholder="" required>
				</div>
				<div class="input-group">
					<label for="password">비밀번호<span class="passwordValidate valMsg"></span> </label>
					<input type="password" name="password" id="password" placeholder="" required>
				</div>
				<div class="input-group">
					<label for="passwordConfirm">비밀번호 확인 <span class="passwordConfirmValidate valMsg"></span></label>
					<input type="password" name="passwordConfirm" id="passwordConfirm" placeholder="" required>
				</div>
				<div class="input-group">
				<label for="nickname">이름</label>
				<input type="text" name="name" id="name" placeholder="" required>
				</div>
				<div class="input-group">
					<label for="nickname">주민등록번호</label>
					<input type="text" name="ssn" id="ssn" placeholder="하이픈(-)을 포함하여 입력해주세요. 예)930101-1234567" required>
				</div>
				<div class="input-group">
					<label for="nickname">닉네임 <span class="nickValidate valMsg"></span> </label>
					<input type="text" name="nickname" id="nickname" placeholder="" required>
				</div>
				<div class="input-group">
					<label for="nickname">이메일</label>
					<input type="email" name="email" id="email" placeholder="" required>
				</div>
				<div class="input-group">
					<label for="mobile">전화번호</label>
					<input type="tel" name="mobile" id="mobile" placeholder="" required>
				</div>
				<div class="input-group">
					<label for="zipcode">주소</label>
					<input type="text" id="sample6_postcode" id="zipcode" name="zipcode" placeholder="우편번호" style="width: 30%; border-radius: 0.375rem;" required>
					<input type="button" onclick="sample6_execDaumPostcode()" id="findZipcode" value="우편번호 찾기" style="width: 30%; margin-left:2rem; border-radius: 0.375rem; background-color: #FEE500; color: black;">
				</div>
				<div class="input-group">
					<input type="text" id="sample6_address" name="roadAddr" placeholder="주소" required>
				</div>
				<div class="input-group">
					<input type="text" id="sample6_detailAddress" name="detailAddr" placeholder="상세주소를 입력해주세요" required>
				</div>
				<div class="input-group">
					<input type="text" id="sample6_extraAddress" name="jibunAddr" placeholder="참고항목(OO동)" required>
				</div>
			</c:if>

			<br>
			<button class="sign">회원가입</button>
		</form>
		<br>
		<p class="signup">
			계정이 있으신가요?
			<a rel="noopener noreferrer" href="/member/loginForm" class="">로그인하기</a>
		</p>
	</div>

	<script type="text/javascript" src="/js/regMember.js"></script>
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