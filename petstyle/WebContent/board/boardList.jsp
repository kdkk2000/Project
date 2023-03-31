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
	<link href="${conPath}/css/list.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
	<script>
		$(document).ready(function(){
			$('tr').click(function(){
				var boardId = Number($(this).children().eq(0).text()); // 0번째 td안의 있는 text;
				//alert(boardId);
				if(!isNaN(boardId)){
					location.href = '${conPath}/boardContent.do?boardId='+boardId+'&pageNum=${pageNum}';
				}
			});
		});
	</script>
</head>
<body>
	<c:if test="${not empty boardResult }">
		<script>alert('${boardResult}');</script>
	</c:if>
	<jsp:include page="../main/header.jsp"/>
		<div id="content_form">
		<table>
			<tr>
			    <td>
			        <c:choose>
			            <c:when test="${not empty user}">
			                <a href="${conPath}/boardWriteView.do">글쓰기</a>
			            </c:when>
			            <c:otherwise>
			                <a href="${conPath}/loginView.do?next=boardWriteView.do">글쓰기는 사용자 로그인 이후에만 가능합니다.</a>
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
				<c:forEach items="${boardList }" var="board">
					<tr><td>${board.boardId }</td>
							<td>${board.userName }</td>
							<td class="left">
								<c:forEach var="i" begin="1" end="${board.boardIndent }">
									<c:if test="${i==board.boardIndent }">└─</c:if>
									<c:if test="${i!=board.boardIndent }"> &nbsp; &nbsp; </c:if>
								</c:forEach>
								${board.boardTitle } <!-- 글제목에 a태그를 걸지 말고 query로 tr을 클릭하면 상세보기 페이지로 가기 -->
								<c:if test="${not empty board.boardFileName }">
									<img src="https://cdn-icons-png.flaticon.com/512/5088/5088374.png" width="10">
								</c:if>
							</td>
							<td>${board.boardHit }</td>
							<td><fmt:formatDate value="${board.boardDate }" type="date" dateStyle="short"/></td>
							<td>${board.boardIp }</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		<div class="paging">
			<c:if test="${startPage > BLOCKSIZE }">
				[ <a href="${conPath }/boardList.do?pageNum=${startPage-1}"> 이전 </a> ]
			</c:if>
			<c:forEach var="i" begin="${startPage }" end="${endPage }">
				<c:if test="${i == pageNum }">
					<b> [ ${i } ] </b>
				</c:if>
				<c:if test="${i != pageNum }">
					[ <a href="${conPath }/boardList.do?pageNum=${i}"> ${i } </a> ]
				</c:if>
			</c:forEach>
			<c:if test="${endPage<pageCnt }">
			  [ <a href="${conPath }/boardList.do?pageNum=${endPage+1}"> 다음 </a> ]
			</c:if>
		</div>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>