<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="conPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Admin Join</title>
	<link href="${conPath }/css/join.css" rel="stylesheet">
	<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
	<script>
		$(function(){
			$('input[name="adminId"]').keyup(function(){
				var adminId = $(this).val();
				if(adminId.length<2){
					$('#adminIdConfirmResult').text('아이디는 2글자 이상');
				}else{
					$.ajax({
						url : '${conPath}/adminIdConfirm.do',
						type : 'get',
						data : 'adminId='+adminId,
						dataType : 'html',
						success : function(data){
							$('#adminIdConfirmResult').html(data);
						},
					});// ajax함수
				}//if
			});// keyup event
			$('input[name="adminPw"], input[name="adminPwChk"]').keyup(function(){
				var pw = $('input[name="adminPw"]').val();
				var pwChk = $('input[name="adminPwChk"]').val();
				if(pw == pwChk){
					$('#adminPwChkResult').text('비밀번호 일치');
				}else{
					$('#adminPwChkResult').text('비밀번호 불일치');
				}
			});// keyup event(비밀번호 일치 확인용)
			$('form').submit(function(){
				var adminIdConfirmResult = $('#adminIdConfirmResult').text().trim();
				var adminPwChkResult = $('#adminPwChkResult').text().trim();
				if(adminIdConfirmResult != '사용 가능한 ID입니다'){
					alert('사용 불가한 아이디입니다');
					$('input[name="adminId"]').focus();
					return false; // submit 제한
				}else if(adminPwChkResult != '비밀번호 일치'){
					alert('비밀번호를 확인하세요');
					$('input[name="adminPw"]').focus();
					return false;
				}
			});
		});
		$(document).ready(function(){
			// confirm 창 출력
			$("#join-form").submit(function() {
				var confirmMsg = "회원가입 하시겠습니까?";
				return confirm(confirmMsg);
			});
		});
	</script>
</head>
<body>
	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
		<form id="join-form" action="${conPath}/adminJoin.do" method="post">
			<table>
				<caption>관리자 회원가입</caption>
				<tr>
				    <th>아이디</th>
				    <td>
				        <input type="text" name="adminId" required="required" autofocus="autofocus">
				        <div id="adminIdConfirmResult"> &nbsp; </div>
				    </td>
				</tr>
				<tr>
				    <th>비밀번호</th>
				    <td><input type="password" name="adminPw" required="required"></td>
				</tr>
				<tr>
				    <th>비밀번호 확인</th>
				    <td>
				        <input type="password" name="adminPwChk" required="required">
				        <div id="adminPwChkResult"> &nbsp; </div>
				    </td>
				</tr>
				<tr>
				    <th>이름</th>
				    <td><input type="text" name="adminName" required="required"></td>
				</tr>
				<tr>
				    <th>메일</th>
				    <td>
				        <input type="text" name="adminEmail" required="required">
				        <div id="adminEmailConfirmResult"> &nbsp; </div>
				    </td>
				</tr>
				<tr>
				    <th>전화번호</th>
				    <td><input type="text" name="adminPhone"></td>
				</tr>
				<tr>
					<td colspan="2">
						<p>
							<input type="submit" value="회원가입" class="btn">
							<input type="button" value="로그인" class="btn" onclick="location='${conPath}/adminLoginView.do'">
						</p>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>