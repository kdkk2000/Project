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
	<link href="${conPath }/css/adminUserModify.css" rel="stylesheet">
	<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
  	<script>
	  	$(function(){
		  	  var patternUserEmail = /^[a-zA-Z0-9_\.]+@[a-zA-Z0-9_]+(\.\w+){1,2}$/;
		  	  var inputValid = true; 
		  	  	  
		  	  $('input[name="userEmail"]').keyup(function(){
		  	    let userEmail = $(this).val();
		  	    if( (!userEmail) || (userEmail == '${user.userEmail}')){
		  	      $('#userEmailConfirmResult').html(' &nbsp; ');
		  	      inputValid = true; 
		  	    }else if(patternUserEmail.test(userEmail)){
		  	      $.ajax({
		  	        url : '${conPath}/userEmailConfirm.do',
		  	        type : 'get',
		  	        data : 'userEmail='+userEmail,
		  	        dataType : 'html',
		  	        success : function(data){
		  	          $('#userEmailConfirmResult').html(data);
		  	          inputValid = true;
		  	        },
		  	      });
		  	    }else if(!patternUserEmail.test(userEmail)){
		  	      $('#userEmailConfirmResult').html('<b>메일 형식을 지켜 주세요</b>');
		  	      inputValid = false;
		  	    }
		  	  });
		
		  	  $('form').submit(function(){
		  	    var userPw = $('input[name="userPw"]').val();
		  	    var userEmailConfirmResult = $('#userEmailConfirmResult').text().trim();
		  	    if(userPw != '${user.userPw}'){ 
		  	      alert('현재 비밀번호를 확인하세요');
		  	      $('input[name="userPw"]').focus();
		  	      return false;
		  	    }else if(!inputValid){ 
		  	      alert('메일을 확인하세요');
		  	      $('input[name="userEmail"]').focus();
		  	      return false;
		  	    }else{
		  	      var confirmMsg = "개인 회원정보를 수정하시겠습니까?";
		  	      return confirm(confirmMsg);
		  	    }
		  	  });
		  	});
	</script>
</head>
<body>
	<c:if test="${empty user }">
		<script>location.href='${conPath}/loginView.do?next=modifyView.do';</script>
	</c:if>
	<jsp:include page="../main/header.jsp"/>
	<div id="content_form">
		<form id="form" action="${conPath}/modify.do" method="post">
		<input type="hidden" name="dbUserPw" value="${user.userPw}">

			<table>
				<caption>정보수정</caption>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="userId" value="${user.userId }" readonly="readonly" size="3">
					</td>
				</tr>
				<tr>
					<th>현재 비밀번호</th>
					<td>
						<input type="password" name="userPw" required="required">
					 </td>
				</tr>
				<tr>
					<th>새 비밀번호</th>
					<td>
						<input type="password" name="newUserPw">
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<input type="text" name="userName" value="${user.userName }" required="required" size="3">
					</td>
				</tr>
				<tr>
					<th>메일</th>
					<td colspan="2">
						<input type="email" name="userEmail" value="${user.userEmail }">
						<div id="userEmailConfirmResult"> &nbsp; </div>
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td colspan="2">
						<input type="text" name="userPhone"  value="${user.userPhone }">
					</td>
				</tr>
				<tr>
					<th>주소</th>
					<td colspan="2">
						<input type="text" name="userAddress" value="${user.userAddress }">
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<input type="submit" value="정보수정" class="btn">
						<input type="reset" value="초기화" class="btn">
						<input type="reset" value="이전" onclick="history.back()" class="btn">
						<input type="button" value="회원탈퇴" class="btn"
							onclick="location.href='${conPath}/withdrawal.do'">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<jsp:include page="../main/footer.jsp"/>
</body>
</html>




