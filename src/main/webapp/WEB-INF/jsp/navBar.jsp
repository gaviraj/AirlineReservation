<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/navBar.css">
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
	$(document).ready(function() {
		$("#menuBtn").click(function() {
			if ($(".menuText").is(":hidden")) {
				$(".menuText").show(200)
				$("#sideNav").addClass("openSideNav")
				//$(".main").css("width",$('.container').width() - $("#sideNav").width() - $(".menuText").width() -  1 +"px");
				$(".main").css("padding", "50px 35px")
				$(".accessDenied").css("padding-bottom", "200px")
				$(".footer").css("padding", "50px 35px 20px")
			} else {
				$(".menuText").hide(200)
				$("#sideNav").removeClass("openSideNav")
				//$(".main").css("width",$('.container').width() + $("#sideNav").width() -  1 +"px");
				$(".main").css("padding", "50px 60px")
				$(".accessDenied").css("padding-bottom", "200px")
				$(".footer").css("padding", "50px 60px 20px")
			}
		})
		
		$(".container").css("max-height",$('.container').height() - $("#navbar").height() -  1 +"px");
	})
</script>
</head>
<body>
<nav id="navbar">
	<div class="navbarLeft">
		<button id="menuBtn"><img src="${pageContext.request.contextPath}/img/bars-solid.svg" alt="menu icon" id="menu"/></button>
		<a href="/" id="navHome"><img src="${pageContext.request.contextPath}/img/logoipsum-265.svg" alt="logo" id="logo" /></a>
	</div>
	<div class="navbarRight">
		<sec:authorize access="hasAuthority('Admin') || hasAuthority('DBA')">
			<a class="${activeAdmin} ${activeRoles} ${activeUsers} ${activeAirlines} ${activeFlightForm} ${activeAirports} ${activePassengers} ${activeReservations}" href="/admin"><button id="adminBtn"><img src="${pageContext.request.contextPath}/img/user-tie-solid.svg" alt="user tie" id="admin" /></button></a>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<a class="${activeProfile}" href="/userProfile"><button id="adminBtn"><img src="${pageContext.request.contextPath}/img/user_icon.svg" alt="user tie" id="user" /></button></a>
		</sec:authorize>
		<button id="trip"><img src="${pageContext.request.contextPath}/img/heart-solid.svg" alt="heart" id="heart" /></button>
		<sec:authorize access="!isAuthenticated()">
			<a href="/login"><button id="signIn"><img src="${pageContext.request.contextPath}/img/user_icon.svg" alt="user" id="userIcon" /><p id="signInText">Sign in</p></button></a>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<a href="/login?logout"><button id="signIn"><img src="${pageContext.request.contextPath}/img/right-from-bracket-solid.svg" alt="user" id="userIcon" /><p id="signInText">Logout</p></button></a>
		</sec:authorize>
	</div>
</nav>
</body>
</html>