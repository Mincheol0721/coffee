<%@page import="java.security.SecureRandom"%>
<%@page import="java.math.BigInteger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이메일 인증</title>
    <link rel="stylesheet" href="/css/login.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style type="text/css">
        .form-control {
            margin: 0.8em auto;
            background: transparent;
            border: 1px solid gray;
            color: white;
        }
        #userEmail2 {
            background: white;
            color: black;
        }
    </style>
</head>
<body>
    <div class="form-container">
<%--    <c:out value="message: ${message }" /> --%>
        <p class="title">이메일 인증</p>
        <form class="form" action="/member/modPassword" method="post">
        <label for="id">아이디</label>
        <input type="text" class="form-control" name="id" id="id">
        <div class="form-group email-form">
		    <label for="email">이메일</label>
            <div class="input-group">
			    <input type="text" class="form-control" name="userEmail1" id="userEmail1" placeholder="이메일" >
			    <select class="form-control" name="userEmail2" id="userEmail2" >
					<option>@naver.com</option>
					<option>@daum.net</option>
					<option>@gmail.com</option>
	                <option>@hanmail.com</option>
	                <option>@yahoo.co.kr</option>
	            </select>
	        </div>
			<div class="input-group-addon">
			    <button type="button" class="btn btn-primary" id="mail-Check-Btn">임시 비밀번호 발급</button>
			</div>
        </form>
    </div>
    <script type="text/javascript">
	    $('#mail-Check-Btn').click(function() {
	        const email = $('#userEmail1').val() + $('#userEmail2').val(); // 이메일 주소값 얻어오기!
	        console.log('완성된 이메일 : ' + email); // 이메일 오는지 확인
	        const checkInput = $('.mail-check-input') // 인증번호 입력하는곳
	        const id = $('#id').val();

	        $.ajax({
	            type : 'post',
	            url : '<c:url value ="/member/emailCertification"/>',
	            data: {'email': email, 'id': id},
	            success : function (data) {
	                console.log("data : " +  data.msg);
	                checkInput.attr('disabled',false);
	                if (data.msg == 'failed') {
	                	alert('입력하신 ID 또는 Email이 틀렸습니다. \n가입 시 입력했던 ID 또는 Email을 입력해주세요.');
	                } else {
	                    alert('입력하신 Email로 임시 패스워드가 발급 되었습니다. \n임시 패스워드로 로그인 후 패스워드를 변경해주세요.');
	                    location.href = '/member/loginForm?temp=' + true;
	                }
	            }
	        }); // end ajax
	    }); // end send email
    </script>
</body>
</html>