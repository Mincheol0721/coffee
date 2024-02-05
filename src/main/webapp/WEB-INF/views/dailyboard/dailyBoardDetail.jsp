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
		<style type="text/css">
			#whole {
				width: 80%;
				margin: 0 auto;
			}
			#commenBox {
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
				border-radius: 60%;
				border: 1px solid gray;
				margin: 0.5em;
			}
			#commentList {
			}
		</style>
	</head>
	<body>
		<div id="whole">
			<div id="title">
				<h1 style="font-size: 80px; font-weight: bold; margin-bottom: 20px;">${vo.title}</h1>
				<span style="padding-left: 10px;">
					<img src="/coffee/member/download?id=${owner.id}" width="50px" height="50px" style="border-radius: 60%; object-fit: cover; border: 1px solid lightgray;">
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
							<div>
								<img alt="profile" src="/coffee/member/download?nickname=${list.nickname}" style="width: 30px; height: 30px; border-radius: 60%; float: left; object-fit: scale-down; border: 1px solid lightgray;">
								<b>${list.nickname }</b> | <small>작성일자: ${list.writeDate }</small>
								<c:if test="${not empty member}">
									<small>
										<a href="javascript:recommentForm(${loop.index}, ${list.no})">&nbsp;<i class="fa-regular fa-comment"></i>&nbsp;</a>
									</small>
								</c:if>
								<c:if test="${list.nickname eq member.nickname}">
									<small><a href="javascript:modForm(${loop.index})"><i class="fa-regular fa-pen-to-square"></i></a></small> &nbsp;
									<small><a href="${path}/trade/delComment.do?no=${list.no}&boardNo=${vo.no}"><i class="fa-regular fa-trash-can"></i></a></small>
								</c:if>
								<c:choose>
									<c:when test="${list.level > 1}">
										<c:forEach begin="1" end="${list.level}" step="1">
											<span style="padding-left: 20px"></span>
										</c:forEach>
										└ 
									</c:when>
								</c:choose>
								<span class="content" style="display: inline-block;">&nbsp;${list.content}</span>
								<span class="comment" style="display: none; width: 100%;">
									<input type="text" name="content" value="${list.content}" style="width: calc(100% - 150px); float: left; height: 2.3em; border: 1px solid lightgray; padding-left: 15px; color:black;">
									<button class="signupBtn" type="button" onclick="javascript: modComment(${list.no}, ${vo.no}, ${loop.index})">
									  	댓글수정
									 	<span class="arrow">
									    	<svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 320 512" fill="rgb(183, 128, 255)"><path d="M278.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-160 160c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L210.7 256 73.4 118.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l160 160z"></path></svg>
									 	</span>
									</button>
								</span>
							</div>
						</c:forEach>
					</c:if>
				</div>
				<hr>
				<div id="input-wrapper" style="height: 125px; border: 1px solid lightsteelblue;">
					<c:if test="${member ne null}">
						<img alt="profile" src="/coffee/member/download?id=${member.id}" class="profile">
						<c:out value="${member.nickname}" />
					</c:if>
					<c:if test="${member eq null}">
						<img alt="profile" src="/images/login_image.png" class="profile">
						<span>로그인이 필요한 기능입니다.</span>
					</c:if>
					<form action="/coffee/dailyboardComment/regComment" method="post"style="display: flex;justify-content: space-between;flex-wrap: nowrap;">
						<input type="hidden" name="boardNo" value="${vo.no}">
						<input type="hidden" name="nickname" value="${member.nickname}">
						<input class="input-box" type="text" placeholder="댓글을 입력하세요." name="content">
						<div style="display: flex; justify-content: flex-end;">
							<button class="button">
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
	</body>
</html>