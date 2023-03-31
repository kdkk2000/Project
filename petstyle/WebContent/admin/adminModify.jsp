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
	  	  var patternAdminEmail = /^[a-zA-Z0-9_\.]+@[a-zA-Z0-9_]+(\.\w+){1,2}$/;
	  	  var inputValid = true; 
	  	  	  
	  	  $('input[name="adminEmail"]').keyup(function(){
	  	    let adminEmail = $(this).val();
	  	    if( (!adminEmail) || (adminEmail == '${admin.adminEmail}')){
	  	      $('#adminEmailConfirmResult').html(' &nbsp; ');
	  	      inputValid = true; 
	  	    }else if(patternAdminEmail.test(adminEmail)){
	  	      $.ajax({
	  	        url : '${conPath}/adminEmailConfirm.do',
	  	        type : 'get',
	  	        data : 'adminEmail='+adminEmail,
	  	        dataType : 'html',
	  	        success : function(data){
	  	          $('#adminEmailConfirmResult').html(data);
	  	          inputValid = true;
	  	        },
	  	      });
	  	    }else if(!patternAdminEmail.test(adminEmail)){
	  	      $('#adminEmailConfirmResult').html('<b>메일 형식을 지켜 주세요</b>');
	  	      inputValid = false;
	  	    }
	  	  });
	
	  	  $('form').submit(function(){
	  	    var adminPw = $('input[name="adminPw"]').val();
	  	    var adminEmailConfirmResult = $('#adminEmailConfirmResult').text().trim();
	  	    if(adminPw != '${admin.adminPw}'){ 
	  	      alert('현재 비밀번호를 확인하세요');
	  	      $('input[name="adminPw"]').focus();
	  	      return false;
	  	    }else if(!inputValid){ 
	  	      alert('메일을 확인하세요');
	  	      $('input[name="adminEmail"]').focus();
	  	      return false;
	  	    }else{
	  	      var confirmMsg = "관리자 정보를 수정하시겠습니까?";
	  	      return confirm(confirmMsg);
	  	    }
	  	  });
	  	});
	</script>
</head>
<body>
	<c:if test="${empty admin }">
		<script>location.href='${conPath}/adminLoginView.do?next=adminModifyView.do';</script>
	</c:if>
    <jsp:include page="../main/header.jsp" />
    <div id="content_form">
        <form id="form" action="${conPath}/adminModifyView.do" method="post">
        <input type="hidden" name="dbAdminPw" value="${admin.adminPw}">
        
            <table>
				<caption>정보수정</caption>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="adminId" value="${admin.adminId }" readonly="readonly" size="3">
					</td>
				</tr>
				<tr>
					<th>현비밀번호</th>
					<td>
						<input type="password" name="adminPw" required="required">
					 </td>
				</tr>
				<tr>
					<th>새비밀번호</th>
					<td><input type="password" name="newAdminPw"></td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<input type="text" name="adminName" value="${admin.adminName }" required="required" size="3">
					</td>
				</tr>
				<tr>
					<th>메일</th>
					<td colspan="2">
						<input type="email" name="adminEmail" value="${admin.adminEmail }" size="3">
						<div id="adminEmailConfirmResult"> &nbsp; </div>
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td colspan="2">
						<input type="text" name="adminPhone"  value="${admin.adminPhone }">
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<input type="submit" value="정보수정" class="btn">
						<input type="reset" value="초기화" class="btn">
						<input type="reset" value="이전" onclick="history.back()" class="btn">
					</td>
				</tr>
            </table>
        </form>
    </div>
    <jsp:include page="../main/footer.jsp" />
</body>
</html>