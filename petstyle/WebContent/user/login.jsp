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
	<link href="${conPath }/css/login.css" rel="stylesheet">
</head>
<body>
	<c:if test="${not empty  result}">
		<script>
			alert('${result}');
		</script>
	</c:if>
	<c:if test="${not empty  loginErrorMsg}">
		<script>
			alert('${loginErrorMsg}');
			history.back();
		</script>
	</c:if>
	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
		<form action="${conPath }/login.do" method="post">
			<input type="hidden" name="next" value="${param.next }">
			<table>
				<caption>사용자님 로그인</caption>
				<tr>
					<th>ID</th><td><input type="text" name="userId" value="${userId }" required="required"></td>
				</tr>
				<tr>
					<th>PW</th><td><input type="password" name="userPw" required="required"></td>
				</tr>
				<tr>
					<td colspan="2">
						<p>
							<input type="submit" value="로그인" class="btn">
							<input type="button" value="회원가입" class="btn"
									onclick="location='${conPath}/joinView.do'">
						</p>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>