<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" href="/css/board.css">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
	</head>
		<body class="is-preload">
<!-- 		dailyboardlist 들어옴 -->
		<!-- Home -->
			<h1 style="text-align: center; padding-top:1em;">일상 게시판
			<c:if test="${(category ne null) or (not empty category)}">
				 - <c:out value="${category}" />
			</c:if></h1>
			<hr>
				<c:if test="${not empty member}">
					<button class="cssbuttons-io-button writeBtn" onclick="location.href='/coffee/board/dailyBoardForm'">
					  글작성
					  <div class="icon">
					    <svg
					      height="24"
					      width="24"
					      viewBox="0 0 24 24"
					      xmlns="http://www.w3.org/2000/svg"
					    >
					      <path d="M0 0h24v24H0z" fill="none" />
					      <path d="M16.172 11l-5.364-5.364 1.414-1.414L20 12l-7.778 7.778-1.414-1.414L16.172 13H4v-2z" fill="currentColor" />
					    	</svg>
						</div>
					</button>
				</c:if>

					<table class="table table-striped">
						<thead>
							<tr style="text-align: center; background-color: darkgray; ">
								<th style="width: 7%;">글번호</th>
								<th style="width: 10%;">이미지</th>
								<th style="width: 43%;">제목</th>
								<th style="width: 15%;">닉네임</th>
								<th style="width: 15%;">작성일</th>
								<th style="width: 10%;">조회수</th>
							</tr>
						</thead>
						<tbody>
						<c:if test="${empty list.vo}">
							<tr height="40px">
								<td colspan="6" style="text-align:center;"><font size="3" color="black">작성된 내용이 없습니다.</font></td>
							</tr>
						</c:if>
				<c:if test="${not empty list.vo}">
					<c:set var="no" value="${list.no}" />
						<c:forEach var="vo" items="${list.vo}" varStatus="loop">
						<c:set var="index" value="${loop.index + 1}" />
							<tr class="boardArticles" onclick="location.href='${path}/coffee/board/dailyBoardDetail?no=${vo.no}'">
								<td class="boardNo">${index + ((list.currentPage-1) * 10)}</td>
								<td class="boardImg">
									<img src="${path}/coffee/board/thumbnail.do?no=${vo.no}">
								</td>
								<td class="boardTitle">${vo.title}</td>
								<td class="boardUser">${vo.nickname}</td>
								<td class="boardWriteDate">${vo.writeDate}</td>
								<td class="boardReadCount">${vo.readCount}</td>
							</tr>
						</c:forEach>
						</c:if>
						</tbody>
					</table>
					
				<nav aria-label="Page navigation example">
					<c:if test="${not empty list.vo}">
					<fmt:parseNumber var="pageCount" value="${list.count/list.pageSize + (list.count % list.pageSize eq 0 ? 0 : 1)}" integerOnly="true" />
						<ul class="pagination" style="float: right; margin-right: 20px">
						<!-- 글이 존재 한다면 -->
						<c:if test="${not empty list.vo}">
							<fmt:parseNumber var="pageCount" value="${list.count/list.pageSize + (list.count % list.pageSize eq 0 ? 0 : 1)}" integerOnly="true" />
							<c:set var="startPage" value="${1}" />
						</c:if>
						
						<!-- pageSize로 설정한 수보다 글이 더 많으면 -->
						<c:if test="${(list.currentPage % list.pageBlock) ne 0}">
							<fmt:parseNumber var="result" value="${list.currentPage / list.pageBlock}" integerOnly="true" />
							<c:set var="startPage" value="${result * list.pageBlock + 1}" />
						</c:if>
						
						<!-- pageSize보다 글 개수가 더 적으면 -->
						<c:if test="${list.currentPage % list.pageSize eq 0}">
							<c:set var="startPage" value="${(result - 1) * list.pageSize + 1}" />
						</c:if>
						
						<c:set var="pageBlock" value="${list.pageBlock}" />
						<c:set var="endPage" value="${startPage + pageBlock - 1}" />
						
						<!-- 끝 페이지 -->
						<c:if test="${endPage > pageCount}">
							<c:set var="endPage" value="${pageCount}" />
						</c:if>
						
						<!-- 시작페이지가 pageSize보다 크면 -->
						<c:if test="${startPage > list.pageBlock}">
							<li class="page-item">
								<a class="page-link" href="${path}/coffee/board/dailyBoardList?<c:if test="${not empty category}">category=${category}&</c:if>pageNum=${startPage - list.pageBlock}" aria-label="Previous">
						      		<span aria-hidden="true">&laquo;</span>
						    	</a>
							</li>
						</c:if>
						
						<!-- 시작페이지부터 끝페이지까지 노출되도록 반복문 사용 -->
						<c:forEach var="n" begin="${startPage}" end="${endPage}">
							<c:choose>
								<c:when test="${n == list.currentPage}">
									<li class="page-item active"><a class="page-link" href="${path}/coffee/board/dailyBoardList?<c:if test="${not empty category}">category=${category}&</c:if>pageNum=${list.currentPage}">${list.currentPage}</a></li>
								</c:when>
								<c:otherwise>
									<li class="page-item"><a class="page-link" href="${path}/coffee/board/dailyBoardList?<c:if test="${not empty category}">category=${category}&</c:if>pageNum=${n}">${n}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<!-- 끝페이지 이후 다음 글 존재하면 -->
						<c:if test="${endPage < pageCount}">
							<li class="page-item">
								<a class="page-link" href="${path}/coffee/board/dailyBoardList?<c:if test="${not empty category}">category=${category}&</c:if>pageNum=${startPage + pageBlock}">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>
						</c:if>
						
						</ul>					
					</c:if>
					<c:if test="${empty list.vo}">
						
					</c:if>
				</nav>
				 
	</body>
</html>