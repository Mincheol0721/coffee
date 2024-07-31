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
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
		<style type="text/css">
			#btn {
				width: 80%;
				margin: auto;
			}
			.bn59 {
				background-color: #141414;
				float: right;
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
		</style>
	</head>
	<body style="text-align: center;">
		<h1>게시글 작성</h1>
		<hr style="width: 80%; margin: 1.2em auto;">
		<form id="frm" action="/dailyBoard/insertDailyBoard" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="<%=member.getId()%>">
			<input type="text" id="title" name="title" style="width: 80%;" placeholder="제목을 입력해주세요.">
			<hr style="width: 80%; margin: 1.2em auto;">
			<div id="smarteditor">
				<textarea name="content" id="content" placeholder="Insert the contents." rows="20" cols="50" style="overflow: scroll; width: 100%;">
				</textarea>
			</div>
			<div id="btn">
				<input type="submit" value="글 작성" id="submit"  class="bn59" />
			</div>
		</form>

		<script>
			let oEditors = [];

			smartEditor = function() {
// 				console.log("NaverSmartEditor");
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
			});

			$(function() {
				$("#submit").click(function() {
					oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);

					$("#frm").submit();
				})

			});

		</script>
	</body>
</html>