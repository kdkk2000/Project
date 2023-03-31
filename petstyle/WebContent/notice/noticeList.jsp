<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="conPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>공지사항</title>
	<link href="${conPath}/css/list.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
	<script>
		$(document).ready(function(){
			$('tr').click(function(){
				var noticeId = Number($(this).children().eq(0).text()); // 0번째 td안의 있는 text;
				//alert(noticeId);
				if(!isNaN(noticeId)){
					location.href = '${conPath}/noticeContent.do?noticeId='+noticeId+'&pageNum=${pageNum}';
				}
			});
		});
	</script>
</head>
<body>
	<c:if test="${not empty noticeResult }">
		<script>alert('${noticeResult}');</script>
	</c:if>
	<jsp:include page="../main/header.jsp"/>
		<div id="content_form">
		<table>
			<tr>
			    <td>
			        <c:choose>
			            <c:when test="${not empty admin}">
			                <a href="${conPath}/noticeWriteView.do">글쓰기</a>
			            </c:when>
			            <c:otherwise>
			                <a> "글쓰기는 관리자만 가능합니다."</a>
			            </c:otherwise>
			        </c:choose>
			    </td>
			</tr>
		</table>
		<br>
		<table>
			<tr><th>글번호</th><th>작성자</th><th>글제목</th><th>조회수</th>
					<th>날짜</th><th>ip</th></tr>
			<c:if test="${totCnt==0 }">
				<tr><td colspan="6">등록된 글이 없습니다</td></tr>
			</c:if>
			<c:if test="${totCnt!=0 }">
				<c:forEach items="${noticeList }" var="notice">
					<tr><td>${notice.noticeId }</td>
							<td>${notice.adminName }</td>
							<td class="left">
								<c:forEach var="i" begin="1" end="${notice.noticeIndent }">
									<c:if test="${i==notice.noticeIndent }">└─</c:if>
									<c:if test="${i!=notice.noticeIndent }"> &nbsp; &nbsp; </c:if>
								</c:forEach>
								${notice.noticeTitle } <!-- 글제목에 a태그를 걸지 말고 query로 tr을 클릭하면 상세보기 페이지로 가기 -->
								<c:if test="${not empty notice.noticeFileName }">
									<img src="https://cdn-icons-png.flaticon.com/512/5088/5088374.png" width="10">
								</c:if>
							</td>
							<td>${notice.noticeHit }</td>
							<td><fmt:formatDate value="${notice.noticeDate }" type="date" dateStyle="short"/></td>
							<td>${notice.noticeIp }</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		<div class="paging">
						<c:if test="${startPage > BLOCKSIZE }">
				[ <a href="${conPath }/noticeList.do?pageNum=${startPage-1}"> 이전 </a> ]
			</c:if>
			<c:forEach var="i" begin="${startPage }" end="${endPage }">
				<c:if test="${i == pageNum }">
					<b> [ ${i } ] </b>
				</c:if>
				<c:if test="${i != pageNum }">
					[ <a href="${conPath }/noticeList.do?pageNum=${i}"> ${i } </a> ]
				</c:if>
			</c:forEach>
			<c:if test="${endPage<pageCnt }">
			  [ <a href="${conPath }/noticeList.do?pageNum=${endPage+1}"> 다음 </a> ]
			</c:if>
		</div>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>