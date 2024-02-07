<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="vo" value="${vo}" />
<c:set var="member" value="${member}" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>일상 게시판 상세보기</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
		<style type="text/css">
			#whole {
				width: 80%;
				margin: 0 auto;
			}
			#commentBox {
				margin-bottom: 20px;
			}
			#contentBox img {
				max-width: 80%;
			}
			.bn59 {
				background-color: #141414;
				color: white;
				border: 1px solid rgba(54, 54, 54, 0.6);
				font-weight: 600;
				position: relative;
				outline: none;
				border-radius: 50px;
				display: inline-flex;
				justify-content: center;
				align-items: center;
				cursor: pointer;
				height: 45px;
				width: 130px;
				opacity: 1;		
			}
			.input-wrapper {
				display: flex;
				justify-content: space-between;
				align-items: center;
				position: relative;
/* 				width: 200px; */
				margin: 50px auto;
			}
			
			.input-box {
				font-size: 16px;
				padding: 10px 0;
				border: none;
				border-bottom: 2px solid #ccc;
/* 				color: #08AEEA; */
				width: 100%;
				background-color: transparent;
				transition: border-color 0.3s ease-in-out;
			}
			
			.underline {
				position: absolute;
				bottom: 0;
				left: 0;
				width: 100%;
				height: 2px;
				background-color: #08AEEA;
				transform: scaleX(0);
				transition: transform 0.3s ease-in-out;
			}
			
			.input-box:focus {
				border-color: #08AEEA;
				outline: none;
			}
			
			.input-box:focus + .underline {
				transform: scaleX(1);
			}
			.button {
				background-color: #ffffff00;
/* 				color: #fff; */
				width: 9em;
				height: 2.5em;
				border: #3654ff 0.2em solid;
				border-radius: 11px;
				text-align: right;
				transition: all 0.6s ease;
			}
			
			.button:hover {
				background-color: #3654ff;
				cursor: pointer;
			}
			
			.button svg {
				width: 1.2em;
				margin: 0.2em auto;
				position: absolute;
				display: flex;
				transition: all 0.6s ease;
			}
			
			.button:hover svg {
				transform: translateX(5px);
			}
			
			.text {
				margin: 0 1.5em
			}
			.profile {
				width: 50px;
				height: 50px;
				border-radius: 60%;
				border: 1px solid lightgray;
				margin: 0.5em;
				object-fit: cover;
			}
			#content img {
				max-width: 80%;
				display: block;
				margin: 0 auto;
			}
			.commentListProfile {
				display: block;
			}
			.commentListProfile img {
				margin-right: 1em;
			}
			a {
				text-decoration: none;
			}
			.commentListProfile i {
				margin-right: 0.4em;
			}
			.commentItems {
				width: 100%;
				height: 4rem;
/* 				border-bottom: 1px solid lightgray; */
				margin-top: 0.6em;
			}
		</style>
	</head>
	<body>
		<div id="whole">
			<div id="title">
				<h1 style="font-size: 80px; font-weight: bold; margin-bottom: 20px;">${vo.title}</h1>
				<span style="padding-left: 10px;">
					<img src="/coffee/member/download?nickname=${owner.nickname}" width="50px" height="50px" style="border-radius: 60%; object-fit: cover; border: 1px solid lightgray;">
					&nbsp; ${vo.nickname} | <small>${vo.writeDate}</small> 
				</span>
				<span style="float: right; font-size: small;">조회수 : ${vo.readCount}</span>
			</div>
			<hr>
			<div id="content">
				${vo.content}
			</div>
			<hr>
			<div id="btns">
				<button onclick="location.href='/coffee/dailyboard/dailyBoardList'" class="bn59">글 목록</button>
				<c:if test="${member.id eq owner.id}">
					<button onclick="location.href='/coffee/dailyboard/modDailyBoardForm?no=${vo.no}'" class="bn59">글 수정</button>
					<button onclick="location.href='/coffee/dailyboard/delDailyBoard?no=${vo.no}'" class="bn59">글 삭제</button>
				</c:if>
			</div>
			<hr>
			<div id="commentBox">
				<div id="commentList">
					<c:if test="${empty commentList}">
						<center><b>등록된 댓글이 없습니다.</b></center>
					</c:if>
					<c:if test="${not empty commentList}">
						<c:forEach var="list" items="${commentList}" varStatus="loop">
								<c:choose>
									<c:when test="${list.level > 1}">
										<span style="display: inline-flex;" class="commentItems">
										<c:forEach begin="1" end="${list.level-1}" step="1">
											<span style="padding-left: 20px"></span>
										</c:forEach>
										└ &nbsp;
									</c:when>
									<c:otherwise>
										<span style="display: inline-block;" class="commentItems">
									</c:otherwise>
								</c:choose>
								<div style="width: 100%;">
									<div class="commentListProfile">
										<img alt="profile" src="/coffee/member/download?nickname=${list.nickname}" style="width: 50px; height: 50px; border-radius: 60%; float: left; object-fit: cover; border: 1px solid lightgray;">
										<b>${list.nickname }</b>&nbsp;&nbsp;&nbsp;<small style="font-size: 11px;">${list.writeDate }</small>
										<c:if test="${not empty member}">
											<small>
												<a href="javascript:recommentForm(${loop.index}, ${list.no})" class="newFrm hide">&nbsp;<i class="fa-regular fa-comment"></i></a>
											</small>
										</c:if>
										<c:if test="${list.nickname eq member.nickname}">
											<small><a href="javascript:modCommentForm(${loop.index})"><i class="fa-regular fa-pen-to-square"></i></a></small> 
											<small><a href="/coffee/dailyboardComment/delComment?no=${list.no}&boardNo=${vo.no}"><i class="fa-regular fa-trash-can"></i></a></small>
										</c:if>
									</div>
									<span class="content" style="display: inline-block;">${list.content}</span>
									<span class="comment" style="display: none;justify-content: space-between;flex-wrap: nowrap;">
										<input class="input-box" type="text" name="content" value="${list.content}">
									 	<div style="display: flex; justify-content: flex-end;">
											<button class="button signupBtn" onclick="javascript: modComment(${list.no}, ${vo.no}, ${loop.index})" 
													style="font-size: 12px; margin: auto; ">
											  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
											    <path stroke-linecap="round" stroke-linejoin="round" d="M4.5 12h15m0 0l-6.75-6.75M19.5 12l-6.75 6.75"></path>
											  </svg>
											  <div class="text" style="margin: 0 1.2em;">
											    댓글 수정
											  </div>
											</button>
										</div>
										<span class="underline"></span>
									</span>
									<div class="recFrm hide" style="display: none;">
										<form action="/coffee/dailyboardComment/regComment" method="post">
											<input type="hidden" name="nickname" value="${member.nickname}">
											<input type="hidden" name="boardNo" value="${vo.no}"> 
											<input type="hidden" name="parentNo"> 
											<span class="comment" style="display: flex; justify-content: space-between; flex-wrap: nowrap;">
												<span style="padding-top: 0.5rem; margin-bottom: -1.2rem;">
													└&nbsp;
												</span> 
												<input class="input-box" type="text" name="content" value="">
												<div style="display: flex; justify-content: flex-end;">
													<button class="button signupBtn"  onclick="javascript: modComment(${list.no}, ${vo.no}, ${loop.index})" style="font-size: 12px; margin: auto;">
														<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
													    	<path stroke-linecap="round" stroke-linejoin="round" d="M4.5 12h15m0 0l-6.75-6.75M19.5 12l-6.75 6.75"></path>
													  	</svg>
														<div class="text" style="margin: 0 1.2em;">댓글 작성</div>
													</button>
												</div> 
												<span class="underline"></span>
											</span>
										</form>
									</div>
								</div>
							</span>
						</c:forEach>
					</c:if>
				</div>
				<hr>
				<div id="input-wrapper" style="height: 125px; border: 1px solid lightsteelblue;">
					<c:if test="${member ne null}">
						<img alt="profile" src="/coffee/member/download?nickname=${member.nickname}" class="profile">
						<b><c:out value="${member.nickname}" /></b>
					</c:if>
					<c:if test="${member eq null}">
						<img alt="profile" src="/images/login_image.png" class="profile">
						<span>로그인이 필요한 기능입니다.</span>
					</c:if>
					<form action="/coffee/dailyboardComment/regComment" method="post" style="display: flex;justify-content: space-between;flex-wrap: nowrap;">
						<input type="hidden" name="boardNo" value="${vo.no}">
						<input type="hidden" name="nickname" value="${member.nickname}">
						<input class="input-box" type="text" placeholder="댓글을 입력하세요." name="content">
						<div style="display: flex; justify-content: flex-end;">
							<button class="button signupBtn">
							  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
							    <path stroke-linecap="round" stroke-linejoin="round" d="M4.5 12h15m0 0l-6.75-6.75M19.5 12l-6.75 6.75"></path>
							  </svg>
							  <div class="text">
							    댓글 등록
							  </div>
							</button>
						</div>
						<span class="underline"></span>
					</form>
				</div>
			</div>
		</div>
		
		
		<script type="text/javascript">
			//validate
			var nickname = '${member.nickname}';
			
			if(nickname.length == 0) {
				$('.signupBtn').on('click', function(e) {
					e.preventDefault();
					
					alert('로그인이 필요한 기능입니다.');
					location.href='/coffee/member/loginForm';
				})
			}
		
			function modCommentForm(idx) {
				var content = $('.content')[idx];
				var comment = $('.comment')[idx];
				
				if(content.style.display == "none") {
					content.style.display = "inline-block";
					comment.style.display = "none";
				} else {
					content.style.display = "none";
					comment.style.display = "flex";
				}
			}
			
			function modComment(no, boardNo, idx) {
				var comment = $(".comment")[idx];
				var content = comment.children[0].value;
				var i_content = $('.content')[idx];

				$.ajax({
					url: '/coffee/dailyboardComment/modComment',
					type: 'POST',
					dataType: 'text',
					data: {no: no, boardNo: boardNo, content: content},
					success: function(data) {
						i_content.innerText = data;
						
						i_content.style.display = 'inline-block';
						comment.style.display = 'none';
					}
				});
				
			}
			
			function recommentForm(idx, parentNo) {
				
				$('input[name=parentNo]').val(parentNo);
				
				var link = document.getElementsByClassName('newFrm')[idx];
				var recFrm = document.getElementsByClassName('recFrm')[idx];
				var comments = document.getElementsByClassName("commentItems");
				
				if(recFrm.style.display == 'none' || recFrm.style.display == '') {
					recFrm.style.display = 'block';
					for(var i=idx+1; i<comments.length; i++) {
						comments[i].style.marginTop = "2em";
					}
				} else {
					recFrm.style.display = 'none';
					for(var i=idx+1; i<comments.length; i++) {
						comments[i].style.marginTop = "0.6em";
					}
				}
				
			}
		</script>
	</body>
</html>