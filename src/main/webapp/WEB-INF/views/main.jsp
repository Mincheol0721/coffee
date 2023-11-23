<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<title>Coffee Finder</title>
  	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
  	<script src="/js/main.js"></script>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<c:set var="center" value="${center}" />
	<section class="layout">
		<header>
			<jsp:include page="${path}/WEB-INF/views/common/header.jsp" />
		</header>

		<main>
			<jsp:include page="${center}" />
		</main>

		<footer>
			<jsp:include page="${path}/WEB-INF/views/common/footer.jsp" />
		</footer>
	</section>
</body>
</html>