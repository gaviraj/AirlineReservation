<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<%-- <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"> --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<%@ include file="navBar.jsp" %>
<div class="container">
	<div class="mainContainer loginContainer">
		<div class="main login">
			<img src="${pageContext.request.contextPath}/img/logoipsum-265.svg" alt="logo" id="logo" />
			<h1>Sign In</h1>
			<core:if test="${message != null}">
				<p class="errorMessage">${message}</p>
			</core:if>
			<form action="login" method="POST">
				<div class="formSection labeledInputSection">
					<label for="username"><img src="${pageContext.request.contextPath}/img/signature-solid.svg" />
						<input type="text" name="username" id="username" placeholder="Username" />
					</label>
				</div>
				<br>
				<br>
				<div class="formSection labeledInputSection">
					<label for="password"><img src="${pageContext.request.contextPath}/img/key-solid.svg" />
						<input type="password" onfocus="(this.type='text')" onblur="(this.type='password')" name="password" id="password" placeholder="Password" />
					</label>
				</div>
				<br>
				<br>
				<p>Don't Have an Account? <a href='/signUp'>Create Here!</a></p>
				<input type="submit" value="LOGIN" />
			</form>
		</div>
	</div>
</div>

</body>
</html>