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
	<link href="${conPath }/css/join.css" rel="stylesheet">
	<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
  <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
   <script>
  	$(function(){
  		$('input[name="userId"]').keyup(function(){
  			var userId = $(this).val();
  			if(userId.length<2){
  				$('#userIdConfirmResult').text('아이디는 2글자 이상');
  			}else{
  				$.ajax({
  					url : '${conPath}/userIdConfirm.do',
  					type : 'get',
  					data : 'userId='+userId,
  					dataType : 'html',
  					success : function(data){
  						$('#userIdConfirmResult').html(data);
  					},
  				});// ajax함수
  			}//if
  		});// keyup event
  		
  		$('input[name="userPw"], input[name="userPwChk"]').keyup(function(){
  			var pw = $('input[name="userPw"]').val();
  			var pwChk = $('input[name="userPwChk"]').val();
  			if(pw == pwChk){
  				$('#userPwChkResult').text('비밀번호 일치');
  			}else{
  				$('#userPwChkResult').text('비밀번호 불일치');
  			}
  		});// keyup event(비밀번호 일치 확인용)
  		 
  		// macth함수 사용
  		var patternUserEmail = /^[a-zA-Z0-9_\.]+@[a-zA-Z0-9_]+(\.\w+){1,2}$/;
  		$('input[name="userEmail"]').keyup(function(){
  			let userEmail = $(this).val();
  			if(!userEmail){
  				$('#userEmailConfirmResult').html(' &nbsp; ');
  			}else if(!userEmail.match(patternUserEmail)){
  				$('#userEmailConfirmResult').html('<b>메일 형식을 지켜 주세요</b>');
  			}else{
  				$.ajax({
  					url : '${conPath}/userEmailConfirm.do',
  					type : 'get',
  					data : 'userEmail='+userEmail,
  					dataType : 'html',
  					success : function(data){
  						$('#userEmailConfirmResult').html(data);
  					},
  				});
  			}
  		});
  		
  		$('form').submit(function(){
  		// "사용 가능한 ID입니다"(#idConfirmResult), "비밀번호 일치(#pwChkResult)"가 출력되었을 경우만 submit 가능
				var userIdConfirmResult = $('#userIdConfirmResult').text().trim();
  			var userPwChkResult = $('#userPwChkResult').text().trim();
  			var userEmailConfirmResult = $('#userEmailConfirmResult').text().trim();
  			if(userIdConfirmResult != '사용 가능한 ID입니다'){
  				alert('사용 불가한 아이디입니다');
  				$('input[name="userId"]').focus();
  				return false; // submit 제한
  			}else if(userPwChkResult != '비밀번호 일치'){
  				alert('비밀번호를 확인하세요');
  				$('input[name="userPw"]').focus();
  				return false;
  			}else if(userEmailConfirmResult != '사용 가능한 메일' && userEmailConfirmResult!= ''){
  				alert('메일을 확인하세요');
  				$('input[name="userEmail"]').focus();
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
		<form id="join-form" action="${conPath }/join.do" method="post">
			<table>
				<caption>회원가입</caption>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="userId" required="required" autofocus="autofocus">
						<div id="userIdConfirmResult"> &nbsp; </div>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="userPw" required="required"></td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td>
						<input type="password" name="userPwChk" required="required">
						<div id="userPwChkResult"> &nbsp; </div>
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td><input type="text" name="userName" required="required"></td>
				</tr>
				<tr>
					<th>메일</th>
					<td>
						<input type="text" name="userEmail" required="required">
						<div id="userEmailConfirmResult"> &nbsp; </div>
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td><input type="text" name="userPhone"></td>
				</tr>
				<tr>
					<th>주소</th><td><input type="text" name="userAddress"></td>
				</tr>
				<tr>
					<td colspan="2">
						<p>
							<input type="submit" value="회원가입" class="btn">
							<input type="button" value="로그인" class="btn" onclick="location='${conPath}/loginView.do'">
						</p>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>