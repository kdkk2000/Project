<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="conPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
<style>
	footer {
		position: fixed;
		bottom: 0;
		height: 100px;
		background-color: black;
		width: 100%;
	}
	
	#footer_conts {
		color: gray;
		font-size: 0.9em;
		text-align: center;
		margin: 0 auto;
		width: 80%;
	}
	
	#footer_conts p:first-child {
		height: 30px;
		line-height: 30px;
	}
</style>
</head>
<body>
	<footer>
		<div id="footer_conts">
			<p>(주)PetStyle</p> 
			<p>서울특별시 서대문구 신촌로 9 Acro 5F | <b><a href="${conPath }/adminLoginView.do">관리자 모드</a></b></p>
			<p>Copyright© 2023 tj . All rights reserved.</p>
		</div>
	</footer>
</body>
</html>