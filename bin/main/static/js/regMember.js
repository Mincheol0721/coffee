//닉네임 중복 확인 유효성 검사
var nickname = $('#nickname');
nickname.on('keyup', function() {
//	console.log('nickname: ' + nickname.val());
	$('.nickValidate').css('font-size', 'small');
	
	if(nickname.val().length >= 4 && nickname.val().length <=16) {
		$.ajax({
			url: '/coffee/member/nickValidate',
			type: 'POST',
			data: {nickname: nickname.val()},
			success: function(data) {
				console.log('data: ' + data);
				if(data == '사용가능') {
					$('.nickValidate').text(' | 사용 가능한 닉네임입니다.');
					$('.nickValidate').css('color', 'cornflowerblue');
				} else {
					$('.nickValidate').text(' | 이미 사용중인 닉네임입니다.');
					$('.nickValidate').css('color', 'red');
				}
			}
		});
	} else {
		$('.nickValidate').text(' | 닉네임은 4자 ~ 16자 사이로 입력해주세요.');
		$('.nickValidate').css('color', '#9CA3AF');
	}
})

//정규식
var reg = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{4,20}$/;
//패스워드 유효성 검사
var password = $('#password');
var passwordConfirm = $('#passwordConfirm');

$('.passwordConfirmValidate').css('font-size', 'small');
$('.passwordValidate').css('font-size', 'small');

password.on('keyup', function() {
	var validate = reg.test(password.val());
	console.log('password: ' + password.val());
	console.log('validate: ' + validate);
	
	if(!validate) {
		$('.passwordValidate').text(' | 비밀번호는 4자 이상 20자 이하, 숫자, 영문자, 특수문자를 모두 포함해야합니다.');
		$('.passwordValidate').css('color', 'red');
	} else {
		$('.passwordValidate').text('');
	}
	
	if(password.val().search(" ") != -1) {
		$('.passwordValidate').text(' | 비밀번호는 공백을 포함할 수 없습니다.');
		$('.passwordValidate').css('color', 'red');
	} 
	
});

passwordConfirm.on('keyup', function() {
	if(password.val() != passwordConfirm.val()) {
		$('.passwordConfirmValidate').text(' | 비밀번호가 일치하지 않습니다.');
		$('.passwordConfirmValidate').css('color', 'red');
	}  else {
		$('.passwordConfirmValidate').text('');
	}
});

//id 유효성 검사
var id = $('#id');
$('.idValidate').css('font-size', 'small');

id.on('blur', function(){	
	console.log('id: ' + id.val());
	if(id.val().length < 4 || id.val().length > 25) {
		$('.idValidate').text(' | 아이디는 4자 이상 25자 이하로 작성해주세요.');
		$('.idValidate').css('color', '#9CA3AF');
	} else {
		$.ajax({
			url: '/coffee/member/idValidate',
			type: 'POST',
			data: {id: id.val()},
			success: function(data) {
				console.log('data: ' + data);
				if(data == '사용가능') {
					$('.idValidate').text(' | 사용 가능한 아이디입니다.');
					$('.idValidate').css('color', 'conrflowerblue');
				} else {
					$('.idValidate').text(' | 이미 사용중인 아이디입니다.');
					$('.idValidate').css('color', 'red');
				}
			}
		});
	}
});