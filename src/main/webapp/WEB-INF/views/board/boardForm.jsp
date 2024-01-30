<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script type="text/javascript" src="/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>
	</head>
	<body>
		<h3>Naver Smart Editor 2.0</h3>
		<form action="insertNewForm" method="post">
			<div id="smarteditor">
				<textarea name="editorTxt" id="editorTxt" rows="20" cols="10" placeholder="Insert the contents." style="width: 500px;"></textarea>
			</div>
			<input type="button" value="submit" />
		</form>
		
		<script>
			let oEditors = [];
			
			smartEditor = function() {
				console.log("NaverSmartEditor");
				nhn.husky.EZCreator.createInIFrame({
					oAppRef: oEditors,
					elPlaceHolder: "editorTxt",
					sSkinURI: "/smarteditor/SmartEditor2Skin.html",
					fCreator: "createSEditor2"
				})
			};
			
			$(document).ready(function() {
				smarteditor();
			})
		</script>
	</body>
</html>