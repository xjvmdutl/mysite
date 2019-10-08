<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath}/assets/js/jquery/jquery-1.9.0.js" type="text/javascript" ></script>
<script>
	$(function(){
		$("#input-email").change(function(){
			$("#btn-check-email").show();
			$("#img-checked").hide()();
		});
		
		$("#btn-check-email").click(function(){
			var email=$("#input-email").val();
			if(email==""){
				return;
			}
			//AJAX 통신
			$.ajax({
				url:"${pageContext.servletContext.contextPath}/api/user/checkemail?email="+email,
				type:"get",
				dataType:"json",
				data:"",
				success:function(response){
					if(response.result =="fail"){
						console.error(response.message);
						return
					}
					if(response.data==true){
						alert("이미 존재하는 메일입니다");
						$("#input-email").val("");
						$("#input-email").focus();
						return;
					}
					$("#btn-check-email").hide();
					$("#img-checked").show();
				},
				error:function(xhr,error){
					console.err("error"+error);
				}
				
			});
			//http://localhost:8088/mysite3/api/user/checkemail?email=widn45@naver.com
		});
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<div id="content">
			<div id="user">
				<form:form
				 modelAttribute="userVo"
				 id="join-form" 
				 name="joinForm" 
				 method="post" 
				 action="${pageContext.servletContext.contextPath}/user/join">
					<label class="block-label" for="name">이름</label>
					<form:input path="name" />
					<spring:hasBindErrors name="userVo">
					<br>
						<c:if test='${errors.hasFieldErrors("name") }'>
						<p style="font-weight:bold; color:red; text-align:left; padding:2px 0 0 0">
							<spring:message code='${errors.getFieldError("name").codes[0] }' text='${errors.getFieldError("name").defaultMessage }'></spring:message>
						</p>
						</c:if>
					</spring:hasBindErrors>
					<label class="block-label" for="email">이메일</label>
					<form:input path="email" />
					<input id="btn-check-email" type="button" value="중복 체크">
					<img id="img-checked" style='width:20px; display:none' src='${pageContext.servletContext.contextPath}/assets/images/check.png' />
					<p style="font-weight:bold; color:red; text-align:left; padding:2px 0 0 0">
						<form:errors path="email" />
					</p>
					<label class="block-label">패스워드</label>
					<form:password path="password" />
					<!--  
					<fieldset>
						<legend><spring:message code="user.joinform.gendertext"></spring:message></legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					-->
					<form:radiobuttons path="gender" items="${userVo.genders }" />
					
	
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					<input type="submit" value="가입하기">
					
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
</body>
</html>