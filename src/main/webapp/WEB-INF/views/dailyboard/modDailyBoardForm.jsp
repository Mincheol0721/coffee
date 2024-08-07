<%@page import="com.spring.coffee.member.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	MemberVO member = (MemberVO)session.getAttribute("member");
%>
<c:set var="vo" value="${vo}" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script type="text/javascript" src="/SE2/js/HuskyEZCreator.js" charset="utf-8"></script>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	</head>
	<body style="text-align: center;">
        <h1>게시글 수정</h1>
        <form id="frm" action="/dailyBoard/updateDailyBoard" method="post" enctype="multipart/form-data">
            <input type="hidden" name="no" value="${vo.no}">
            <input type="hidden" name="id" value="<%=member.getId()%>">
            <input type="text" id="title" name="title" style="width: 80%;" value="${vo.title}">
            <hr>
            <div id="smarteditor">
                <textarea name="content" id="content" placeholder="Insert the contents." rows="20" cols="50" style="overflow: scroll; width: 100%;">
                    ${vo.content}
                </textarea>
            </div>
            <input type="submit" value="submit" id="submit" />
        </form>

        <script>
            let oEditors = [];

            smartEditor = function() {
//              console.log("NaverSmartEditor");
                nhn.husky.EZCreator.createInIFrame({
                    oAppRef: oEditors,
                    elPlaceHolder: "content",
                    sSkinURI: "/SE2/SmartEditor2Skin.html?no=" + ${vo.no},
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

                <%--
                // textArea에 이미지 첨부
                function pasteHTML(filepath){
                    console.log('filePath: ' + filepath);
                    var sHTML = '<img src="<%=request.getContextPath()%>/src/main/resources/static/dailyBoard/temp/'+filepath+'">';
                    var sHTML = '<img src="<%=request.getContextPath()%>/'+filepath+'">';
                    oEditors.getById["content"].exec("PASTE_HTML", [sHTML]);
                }
                 --%>
            })

        </script>
	</body>
</html>