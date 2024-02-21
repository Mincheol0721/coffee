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
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
		<style type="text/css">
			.container {
			  	width: 80%;
			}
			
			.thumbnail {
			    position: absolute;
			    top: 0;
			    left: 0;
			    max-width: 100px;
			    max-height: 100px;
			    margin: 5px;
			}
			
			.textarea {
			    width: 100%;
			/*     height: 200px; /* Adjust the height as needed */ */
			    padding: 10px;
			    box-sizing: border-box;
			}		
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
		<form id="frm" action="/coffee/coffeeboard/addCoffeeBoard" method="post" enctype="multipart/form-data">
			<input type="hidden" name="nickname" value="<%=member.getNickname()%>">
			<input type="text" id="title" name="title" style="width: 80%;" placeholder="제목을 입력해주세요.">
			<hr style="width: 80%; margin: 1.2em auto;">
			<div class="container">
				<input type="file" name="files" id="fileInput" accept="image/*" multiple="multiple" style="margin: 0.5em auto; float: left;" onchange="setThumbnail(event)">
				<textarea name="content" id="content" class="textarea" placeholder="Insert the contents." rows="20" cols="50" style="overflow: scroll; width: 100%; padding: 0.5em; resize: none;"></textarea>
				<div class="thumbnails"></div>
			</div> 
			<div id="btn">
				<input type="submit" value="글 작성" id="submit"  class="bn59" />
			</div>
		</form>
		
		<script>
			function setThumbnail(e) {
				var thumbnails = document.getElementById('thumbnails');
				var content = document.getElementById('content');
				
				for(var image of e.target.files) {
					var reader = new FileReader();
					reader.onload = function(e) {
						var img = document.createElement('img');
						
						img.setAttribute('src', e.target.result);
						img.style.maxWidth = '200px';
						img.style.maxHeight = '200px';
						
						console.log(image);
						/*
						img.addEventListener('click', function() {
							img.setAttribute('data-filename', image.name);
							var filename = this.getAttribute('data-filename');
							
							var link = document.createElement('a');
							link.href = e.target.result;
							link.download = 'image.png';
							link.click();
						});
						*/
						console.log(img);
						var span = document.createElement('span');
						span.appendChild(img);
						console.log(span);
						content.appendChild(span);
						console.log(content)
					}
					reader.readAsDataURL(image);
				}
			}
		</script>
	</body>
</html>