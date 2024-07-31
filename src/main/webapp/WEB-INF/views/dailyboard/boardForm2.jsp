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
		    body {
                font-family: D2Coding;
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
			#contentDiv {
			    margin: 1em auto;
			    width: 80%;
			    height: 30rem;
			    border: 1px solid darkgray;
			    text-align: left;
			    padding: 0.5em;
			    overflow-y: scroll;
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
			    <div id="contentDiv" contenteditable="true">
			    </div>
			    <textarea id="contentArea" name="content" style="display: none"></textarea>
                <input type="file" name="file" id="file" multiple="multiple" style="display: none;">
			</div>
			<div id="btn">
				<input type="submit" value="글 작성" id="submit"  class="bn59" />
			</div>
		</form>

		<script>
		    const contentDiv = $('#contentDiv');
		    const content = $('#contentArea');
		    const fileInput = $('#file');
		    const fileList= $('#fileList')
		    var formData = new FormData();

		    // 드래그 앤 드롭 이벤트 처리
		    contentDiv.on("dragover", function(e){
// 		    	console.log('드래그 오버~');
		    	e.preventDefault();
		    });
		    contentDiv.on("dragleave", function(e){
// 		    	console.log("드래그 리브~");
		    	e.preventDefault();
		    })
		    contentDiv.on("drop", function(e){
// 		    	console.log('드롭~');
		    	e.preventDefault();
		    	// e.originalEvent를 통해 dataTransfer 객체를 확인
		        const dataTransfer = e.originalEvent ? e.originalEvent.dataTransfer : null;
		        if (dataTransfer && dataTransfer.files) {
		            const files = dataTransfer.files;
		            // 파일을 처리하는 함수 호출
		            handleFiles(files);
		        } else {
		            console.log('No files detected or dataTransfer is undefined.');
		        }
		    });

		    function handleFiles(files) {
				const fileInputDataTransfer = new DataTransfer();
				
		        // 파일 처리 로직
		        for (const file of files) {
		        	if(file && file.type.startsWith("image")) {
		        		fileInputDataTransfer.items.add(file);
		        		displayImage(file);
		        	}
		        }
		        
		        fileInput.files = fileInputDataTransfer.files;
		    }

		    function displayImage(file) {
		    	const reader = new FileReader();
		    	reader.onload = () => {
		            const div_tag = document.createElement('div')
		            div_tag.style.maxWidth = '80%';
		            div_tag.style.maxHeight = '320px';
		            div_tag.style.padding = '0.5em';
		    		var content = contentDiv.val();
		    		const img = document.createElement('img');
		    		img.src = reader.result;
		    		img.style.width= '320px';
		    		img.style.height = 'auto';

		    		div_tag.append(img);
                    contentDiv.append(div_tag);
		    	};
		    	reader.readAsDataURL(file);
		    }

		    contentDiv.on('keyup', function(){
				content.innerHTML = contentDiv;
				console.log(content.innerHTML);
		    });
		</script>
	</body>
</html>