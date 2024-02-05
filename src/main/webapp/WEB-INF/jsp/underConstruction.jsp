<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Under Construction</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/underConstruction.css">
</head>
<script>
	$(document).ready(function() {
		$(".container").css("height",$('#sideNav').height());
	})
</script>
<body>
<%@ include file="navBar.jsp" %>
<div class="container">
	<%@ include file="sideNav.jsp" %>
	<div class="mainContainer">
		<div class="main underConstruction">
			<h1>Comming soon...</h1>
			<h4>This feature is currently in the works.</h4>
			<p>Go back <a href="${pageContext.request.contextPath}/">Home</a></p>
		</div>
		<%@ include file="footer.jsp" %>
	</div>
</div>
</body>
</html>