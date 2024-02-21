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
  <link rel="stylesheet" href="/css/main.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
  <script src="/js/main.js"></script>
  <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<!-- 메인 -->
    <div class="main">
      <h1>Coffee Finder</h1>
      <div class="InputContainer">
        <input type="text" name="text" class="input" id="input" placeholder="Search">
        <label for="input" class="labelforsearch">
          <a href="#">
            <svg viewBox="0 0 512 512" class="searchIcon"><path d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"></path></svg>
          </a>
        </label>
        <div class="border"></div>
        <button class="micButton">
          <svg viewBox="0 0 384 512" class="micIcon"><path d="M192 0C139 0 96 43 96 96V256c0 53 43 96 96 96s96-43 96-96V96c0-53-43-96-96-96zM64 216c0-13.3-10.7-24-24-24s-24 10.7-24 24v40c0 89.1 66.2 162.7 152 174.4V464H120c-13.3 0-24 10.7-24 24s10.7 24 24 24h72 72c13.3 0 24-10.7 24-24s-10.7-24-24-24H216V430.4c85.8-11.7 152-85.3 152-174.4V216c0-13.3-10.7-24-24-24s-24 10.7-24 24v40c0 70.7-57.3 128-128 128s-128-57.3-128-128V216z"></path></svg>
        </button>
      </div>
      <div class="options">
        <table>
          <tr>
            <th>원두 종류</th>
            <td>
              <div class="radio-buttons-container">
                <div class="radio-button">
                  <input name="beans" id="radio2" class="radio-button__input" type="radio" value="sanmiBean">
                  <label for="radio2" class="radio-button__label">
                    <span class="radio-button__custom"></span>
                    산미 / 향미
                  </label>
                </div>
                <div class="radio-button">
                  <input name="beans" id="radio1" class="radio-button__input" type="radio" value="bodyBean">
                  <label for="radio1" class="radio-button__label">
                    <span class="radio-button__custom"></span>
                    단맛 / 바디
                  </label>
                </div>
                <div class="radio-button">
                  <input name="beans"  id="radio3" class="radio-button__input" type="radio" value="decafBean">
                  <label for="radio3" class="radio-button__label">
                    <span class="radio-button__custom"></span>
                    디카페인
                  </label>
                </div>
                </div>
            </td>
          </tr>
          <tr>
            <th>내고싶은 맛</th>
            <td>
              <input id="myRange" class="slider" value="50" max="100" min="0" step="25" type="range">
              <span class="rangeStr">
                <font size="1.5">강한 산미</font> 
                <font size="1.5">약한 산미</font> 
                <font size="1.5">밸런스</font> 
                <font size="1.5">약한 바디</font> 
                <font size="1.5">강한 바디</font>
              </span>
            </td>
          </tr>
        </table>
      </div>
    </div>
	
</body>
</html>