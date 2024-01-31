<%@page import="com.spring.coffee.member.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	MemberVO member = (MemberVO)session.getAttribute("member");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script type="text/javascript" src="/SE2/js/HuskyEZCreator.js" charset="utf-8"></script>
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	</head>
	<body style="text-align: center;">
		<h1>게시글 작성</h1>
		<form id="frm" action="/coffee/board/addDailyBoard" method="post" enctype="multipart/form-data">
			<input type="hidden" name="nickname" value="<%=member.getNickname()%>">
			<label for="title">제목</label>
			<input type="text" id="title" name="title" style="width: 70%;">
			<hr>
			<div id="smarteditor">
				<textarea name="content" id="content" placeholder="Insert the contents." rows="20" cols="50" style="overflow: scroll; width: 100%;"></textarea>
			</div> 
			<input type="submit" value="submit" id="submit" />
		</form>
		
		<script>
			let oEditors = [];
			
			smartEditor = function() {
				console.log("NaverSmartEditor");
				nhn.husky.EZCreator.createInIFrame({
					oAppRef: oEditors,
					elPlaceHolder: "content",
					sSkinURI: "/SE2/SmartEditor2Skin.html",
					fCreator: "createSEditor2",
					htParams: {
						bUseToolbar: true,
						bUseVerticalResizer: false,
						bUseModeChanger: false
					}
				})
			};
			
			$(document).ready(function() {
				smartEditor();
			})
			
			$(function() {
				$("#submit").click(function() {
					oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
					
					$("#frm").submit();
				})
			})
			
			
		</script>
	</body>
</html>