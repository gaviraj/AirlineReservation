<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Administrator</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
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
		<div class="main admin">
			<%@ include file="breadcrumbs.jsp" %>
			<h1>Administrator</h1>
			<h2>User management</h2>
			<a class="" href="/roles"><button class="btnDefault">Roles</button></a>
			<a class="" href="/users"><button class="btnDefault">Users</button></a>
			<h2>Reservation management</h2>
			<a class="" href="/passengers"><button class="btnDefault">Passengers</button></a>
			<a class="" href="/reservations"><button class="btnDefault">Reservations</button></a>
			<h2>Airlines/flight management</h2>
			<a class="" href="/airlines"><button class="btnDefault">Airlines</button></a>
			<a class="" href="/flightForm"><button class="btnDefault">Flights</button></a>
			<a class="" href="/airports"><button class="btnDefault">Airports</button></a>
		</div>
		<%@ include file="footer.jsp" %>
	</div>
</div>
</body>
</html>