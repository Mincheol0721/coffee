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
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	</head>
	<body class="is-preload">
		<main style="width: 80%; margin: 0 auto;">
			<!-- Home -->
			<div style="text-align: center; margin: 0 auto;">
				<h1 style="padding-top:1rem;">일상 게시판
				<c:if test="${(category ne null) or (not empty category)}">
					 - <c:out value="${category}" />
				</c:if></h1>
				<hr>
			</div>
			<div style="width: 100%; height: 3rem;">
	    		<form action="/coffee/dailyboard/dailyBoardList" method="post">
	    			<select name="category" style="display: inline-block;">
	    				<option value="">------------</option>
	    				<option value="title">제목</option>
	    				<option value="content">내용</option>
	    				<option value="nickname">작성자</option>
	    			</select>
					<div class="search">
					  	<div class="search-box">
					    	<div class="search-field">
						      		<input placeholder="Search..." class="input keyword" type="text" name="keyword">
						      		<div class="search-box-icon">
						        		<button class="btn-icon-content search">
						          			<i class="search-icon">
						            			<svg xmlns="://www.w3.org/2000/svg" version="1.1" viewBox="0 0 512 512"><path d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z" fill="#000"></path></svg>
						          			</i>
						        		</button>
						      		</div>
					    	</div>
					  	</div>
					</div>
	    		</form>
			</div>
			<br>
			<table class="table table-striped" style="margin: 0 auto;">
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
						<tr class="boardArticles" onclick="location.href='${path}/coffee/dailyboard/dailyBoardDetail?no=${vo.no}'">
							<td class="boardNo">${index + ((list.currentPage-1) * 10)}</td>
							<td class="boardImg">
								<img src="${path}/coffee/dailyboard/thumbnail?no=${vo.no}">
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
			<div style="display: inline-block; width: 100%; margin: 1em 0 -1em;">
				<c:if test="${not empty member}">
					<button class="cssbuttons-io-button writeBtn" onclick="location.href='/coffee/dailyboard/dailyBoardForm'">
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
			</div>
			<nav aria-label="Page navigation example" style="margin: 1rem auto;">
				<c:if test="${not empty list.vo}">
					<fmt:parseNumber var="pageCount" value="${list.count/list.pageSize + (list.count % list.pageSize eq 0 ? 0 : 1)}" integerOnly="true" />
					<ul class="pagination" style="float: right; margin-right: 20px">
						<!-- 글이 존재 한다면 -->
						<c:if test="${not empty list.vo}">
							<c:set var="startPage" value="${1}" />
						</c:if>
						
						<c:set var="pageBlock" value="${list.pageBlock}" />
						
						<!-- pageSize로 설정한 수보다 글이 더 많으면 -->
						<fmt:parseNumber var="result" value="${list.currentPage / pageBlock}" integerOnly="true" />
						<c:if test="${list.currentPage > pageBlock}">
							<c:set var="startPage" value="${result * pageBlock + 1}" />
						</c:if>
						<c:out value="result: ${result}" />
						
						<!-- pageSize보다 글 개수가 더 적으면 -->
						<c:if test="${list.currentPage % pageBlock eq 0}">
							<c:choose>
								<c:when test="${result >= 1}">
									<c:set var="startPage" value="${(result - 1) * list.pageSize + 1}" />
								</c:when>
								<c:otherwise>
									<c:set var="startPage" value="1" />
								</c:otherwise>
							</c:choose>
						</c:if>
						
						<!-- 끝 페이지 -->
						<c:set var="endPage" value="${startPage + pageBlock - 1}" />
						<c:if test="${endPage >= pageCount}">
							<c:set var="endPage" value="${pageCount}" />
						    <c:set var="startPage" value="${pageCount - pageBlock + 1}" />
						</c:if>
						
						<!-- 시작페이지가 pageBlock보다 크면 -->
						<c:if test="${startPage > pageBlock}">
							<li class="page-item">
								<a class="page-link" href="${path}/coffee/dailyboard/dailyBoardList?<c:if test="${not empty category}">category=${category}&</c:if>pageNum=${startPage - list.pageBlock}" aria-label="Previous">
						      		<span aria-hidden="true">&laquo;</span>
						    	</a>
							</li>
						</c:if>
						
						<!-- 시작페이지부터 끝페이지까지 노출되도록 반복문 사용 -->
						<c:forEach var="n" begin="${startPage}" end="${endPage}">
							<c:choose>
								<c:when test="${n == list.currentPage}">
									<li class="page-item active"><a class="page-link" href="${path}/coffee/dailyboard/dailyBoardList?<c:if test="${not empty category}">category=${category}&</c:if>pageNum=${list.currentPage}">${list.currentPage}</a></li>
								</c:when>
								<c:otherwise>
									<li class="page-item"><a class="page-link" href="${path}/coffee/dailyboard/dailyBoardList?<c:if test="${not empty category}">category=${category}&</c:if>pageNum=${n}">${n}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<!-- 끝페이지 이후 다음 글 존재하면 -->
						<c:if test="${endPage < pageCount}">
							<li class="page-item">
								<a class="page-link" href="${path}/coffee/dailyboard/dailyBoardList?<c:if test="${not empty category}">category=${category}&</c:if>pageNum=${startPage + pageBlock}">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>
						</c:if>
						
<%-- 						<c:out value="endPage: ${endPage}/ pageCount: ${pageCount}/ pageBlock: ${pageBlock}/ startPage: ${startPage}" /> --%>
						
					</ul>					
				</c:if>
				<c:if test="${empty list.vo}">
					
				</c:if>
			</nav>
		</main>		
		 
	</body>
</html>