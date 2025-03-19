<%@page import="com.spring.coffee.member.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script type="text/javascript" src="/SE2/js/HuskyEZCreator.js" charset="utf-8"></script>
		<script type="text/javascript" src="/SE2/sample/photo_uploader/jindo.min.js" charset="utf-8"></script>
		<script type="text/javascript" src="/SE2/sample/photo_uploader/jindo.fileuploader.js" charset="utf-8"></script>
		<script type="text/javascript" src="/SE2/sample/photo_uploader/attach_photo.js" charset="utf-8"></script>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
		<style type="text/css">
			#btn {
				width: 80%;
				margin: auto;
			}
			.bn59 {
			    margin-top: 0.5rem;
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
			#smarteditor {
			    margin: 0 auto;
			    width: 80%;
			}
			#smarteditor.hover {
			    border-color: pink;
			}
			table {
			    margin: 0.5rem;
			    padding: 0.5rem;
			    width: 100%;
			}
			table tr {
			    border-bottom: 1px #ccc solid;
			    vertical-align: middle;
			}
			th {
			    width: 15%;
			}
			.table_content {
			    text-align: left;
			    padding: 0.5rem;
			    margin: 1rem;
			}
			.table_content input {
			    width: 100%;
			}
			#dropzone {
			    height: 100px;
			    align-content: center;
			    padding-left: 0.5rem;
			}
			#dropzone.before {
			    text-align: center;
			}
			#dropzone.hover {
			    background: #ccc;
                border: 2px #666 dashed;
			}
		</style>
	</head>
	<body style="text-align: center;">
		<h1>게시글 작성</h1>
		<hr style="width: 80%; margin: 1.2em auto;">
		<form id="frm" action="/dailyBoard/insertDailyBoard" method="post" enctype="multipart/form-data">
<%-- 		    <% MemberVO member = (MemberVO)session.getAttribute("member"); %> --%>
<%--             <input type="hidden" name="id" value="<%=member.getId()%>"> --%>
			<input type="hidden" name="id" value="test">
			<div id="smarteditor">
			    <table>
			        <tr>
			            <th>글 제목</th>
			            <td class="table_content">
			                <input type="text" name="title" id="title" placeholder="제목을 입력해주세요." required="required" />
			            </td>
			        </tr>
			        <tr>
			            <th>파일 첨부</th>
			            <td class="table_content">
			                <div id="dropzone" class="before" style="overflow-y: scroll;">
			                    여기에 업로드할 이미지를 드래그 해주세요.
			                </div>
			                <input type="file" name="files" id="files" multiple="multiple" style="display:none;" />
			            </td>
			        </tr>
			        <tr>
			            <th>내용</th>
			            <td class="table_content">
			                <textarea name="content" id="content" placeholder="내용을 입력해주세요." rows="20" cols="50" style="overflow-y: scroll; width: 100%;" required="required"></textarea>
			            </td>
			        </tr>
			    </table>
			</div>
			<div id="btn">
				<input type="submit" value="글 작성" id="submit"  class="bn59" />
            </div>
		</form>


		<script type="text/javascript" src="/js/imgUpload.js" charset="utf-8"></script>
	</body>
</html>