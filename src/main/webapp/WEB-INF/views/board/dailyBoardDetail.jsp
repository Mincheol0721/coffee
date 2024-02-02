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
			#comment {
				margin-bottom: 20px;
			}
			#content img {
				max-width: 80%;
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
				<button onclick="location.href='/coffee/board/dailyBoardList'">글 목록</button>
				<c:if test="${member.id eq owner.id}">
					<button onclick="location.href='/coffee/board/modDailyBoardForm?no=${vo.no}'">글 수정</button>
					<button onclick="location.href='/coffee/board/delDailyBoard?no=${vo.no}'">글 삭제</button>
				</c:if>
			</div>
			<hr>
			<div id="comment">
				comment block
			</div>
		</div>
	</body>
</html>