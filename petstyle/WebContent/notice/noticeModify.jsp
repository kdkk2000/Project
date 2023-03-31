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
	<link href="${conPath}/css/writeModify.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
	<script>
	$(document).ready(function(){
		// confirm 창 출력
		$("#modify-form").submit(function() {
			var confirmMsg = "공지사항을 수정하시겠습니까?";
			return confirm(confirmMsg);
		});
	});
	</script>
</head>
<body>
	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
	<form id="modify-form" action="${conPath }/noticeModify.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="pageNum" value="${param.pageNum }">
		<input type="hidden" name="noticeId" value="${notice.noticeId }">
		<input type="hidden" name="dbFileName" value="${notice.noticeFileName }">
		<table>
			<caption>${notice.noticeId }번 글 수정</caption>
			<tr><th>작성자</th>
					<td><input type="text" required="required"
								value="${notice.adminName }(${notice.adminId })" readonly="readonly"></td>
			</tr>
			<tr><th>제목</th>
					<td><input type="text" name="noticeTitle" required="required"
								value="${notice.noticeTitle }"></td>
			</tr>
			<tr><th>본문</th>
					<td><textarea rows="5" 
							name="noticeContent">${notice.noticeContent }</textarea></td>
			</tr>
			<tr><th>첨부파일</th>
					<td><input type="file" name="noticeFileName" class="btn"> 원첨부파일:
							<c:if test="${not empty notice.noticeFileName }">
						 		<a href="${conPath }/fileBoardUp/${notice.noticeFileName}" target="_blank">${notice.noticeFileName}</a>
						 	</c:if>
						 	<c:if test="${empty notice.noticeFileName }">
						 		첨부파일없음
						 	</c:if>
					</td>
			</tr>
			<tr><td colspan="2">
						<input type="submit" value="수정" class="btn">
						<input type="reset" value="이전" class="btn"
						  onclick="history.back()">
						<input type="button" value="목록"  class="btn"
							onclick="location='${conPath}/noticeList.do?pageNum=${param.pageNum }'">
					</td>
			</tr>
		</table>
	</form>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>