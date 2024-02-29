<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/signUp.css">
<script>
	$(document).ready(function() {
		function formatPhoneNumber(phoneNumber) {
			const phoneNumberLength = phoneNumber.length;
			let formattedNumber = "";

	        if (phoneNumberLength < 4) {
	            return phoneNumber;
	        } else if (phoneNumberLength < 7) {
	            return phoneNumber.slice(0, 3) + "-" + phoneNumber.slice(3);
	        } else {
	            return phoneNumber.slice(0, 3) + "-" + phoneNumber.slice(3, 6) + "-" + phoneNumber.slice(6, 10);
	        }
		}
		
		$("#phoneNum").on("input", function() {
			const phoneNumber = $("#phoneNum").val()
			console.log("phoneNum: " + phoneNumber)
			console.log(typeof phoneNumber)
			
			const inputPhoneNumber = phoneNumber.replace(/\D/g,'');
			console.log("num only digits: " + inputPhoneNumber)
	        const formattedPhoneNumber = formatPhoneNumber(inputPhoneNumber);
	        $("#phoneNum").val(formattedPhoneNumber)
		})
	})
</script>
</head>
<body>
<%@ include file="navBar.jsp" %>
<div class="container">
	<div class="mainContainer signUpContainer">
		<div class="main signUp">
			<img src="${pageContext.request.contextPath}/img/logoipsum-265.svg" alt="logo" id="logo" />
			<h1>Register</h1>
				<core:forEach items="${errors}" var="error">
					<p class="errorMessage">${error}</p>
				</core:forEach>
			<form:form action="register" method="POST">
				<div class="formSection">
					<input type="number" path="userId" value="${user.getUserId()}" placeholder="user id" hidden="true"/>
				</div>
				<div class="formSection labeledInputSection">
					<label for="username"><img src="${pageContext.request.contextPath}/img/signature-solid.svg" />
						<input type="text" name="username" path="username" placeholder="Username" />
					</label>
				</div>
				<br>
				<br>
				<div class="formSection labeledInputSection">
					<label for="password"><img src="${pageContext.request.contextPath}/img/key-solid.svg" />
						<input type="password" onfocus="(this.type='text')" onblur="(this.type='password')" name="password" path="password" placeholder="Password" />
					</label>
				</div>
				<br>
				<br>
				<div class="formSection labeledInputSection">
					<label for="email"><img src="${pageContext.request.contextPath}/img/envelope-solid.svg" id="emailIcon" />
						<input type="text" name="email" path="email" placeholder="Email" />
					</label>
				</div>
				<br>
				<br>
				<div class="formSection labeledInputSection">
					<label for="phoneNum"><img src="${pageContext.request.contextPath}/img/phone-flip-solid.svg" />
						<input type="text" name="phoneNum" path="phoneNum" id="phoneNum" value="${user.getPhoneNum()}" placeholder="123-456-7890" />
					</label>
				</div>
				<br>
				<br>
				<p>Already registered? <a href='login'>Sign In!</a></p>
				<input type="submit" value="Sign Up" />
			</form:form>
		</div>
	</div>
</div>
</body>
</html>