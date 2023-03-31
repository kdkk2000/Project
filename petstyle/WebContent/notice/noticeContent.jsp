<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="conPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="${conPath}/css/content.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
</head>
<body>
	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
		<table>
			<caption>${notice.noticeId }번 글 상세보기</caption>
			<tr><td>작성자</td><td>${notice.adminName} (${notice.adminId}) 님</td>	</tr>
			<tr><td>제목</td>	 <td>${notice.noticeTitle }</td></tr>
			<tr><td>본문</td>	 <td><pre>${notice.noticeContent}</pre></td></tr>
			<tr><th>조회수</th><td>${notice.noticeHit }</td></tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<c:if test="${not empty notice.noticeFileName }">
						<a href="${conPath }/fileBoardUp/${notice.noticeFileName}" target="_blank">${notice.noticeFileName}</a>
					</c:if>
					<c:if test="${empty notice.noticeFileName }">
						첨부파일없음
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<c:if test="${admin.adminId eq notice.adminId }">
				 		<button onclick="location='${conPath}/noticeModifyView.do?noticeId=${notice.noticeId }&pageNum=${param.pageNum }'">수정</button>
				 	</c:if>
				 	<c:if test="${admin.adminId eq notice.adminId or not empty admin}">
						<button onclick="location='${conPath}/noticeDelete.do?noticeId=${notice.noticeId }&pageNum=${param.pageNum }'">삭제</button>
			 		</c:if>
				 	<button onclick="location='${conPath}/noticeList.do?pageNum=${param.pageNum }'">목록</button>
		</table>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>