<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script>
			let oEditors = [];
			
			smartEditor = function() {
				console.log("NaverSmartEditor");
				nhn.husky.EZCreator.createInIFrame({
					oAppRef: oEditors,
					elPlaceHolder: "editorTxt",
					sSkinURI: "/SE2/SmartEditor2Skin.html",
					fCreator: "createSEditor2"
				})
			};
			
			$(document).ready(function() {
				smartEditor();
			})
			
			oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []);
		</script>
		<script type="text/javascript" src="/SE2/js/HuskyEZCreator.js" charset="utf-8"></script>
	</head>
	<body style="text-align: center;">
		<h1>게시글 작성</h1>
		<form action="/coffee/board/addDailyBoard" method="post" enctype="multipart/form-data">
			<label for="title">제목</label>
			<input type="text" style="width: 70%;" id="title">
			<hr>
			<div id="smarteditor">
				<textarea name="editorTxt" id="editorTxt" placeholder="Insert the contents." rows="20" cols="50" style="width: 70%;"></textarea>
			</div> 
			<input type="submit" value="submit" />
		</form>
		
		
	</body>
</html>