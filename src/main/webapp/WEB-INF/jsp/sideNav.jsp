<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sideNav.css">
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
$(document).ready(function() {
	$("#sideNav").css("height",$('#sideNav').height() - $("#navbar").height() -  1 +"px");
	
	$("#sideNav").hover(function() {
		if ($(".menuText").is(":hidden")) {
			if (!$("#sideNav").hasClass("openSideNav")) {
				setTimeout(function() {
					//$(".menuText").delay(500).show(200)
					$(".menuText").show(200)
					//$(".main").css("width",$('.container').width() - $("#sideNav").width() - $(".menuText").width() -  1 +"px");
					$(".main").css("padding", "50px 35px")
					$(".footer").css("padding", "50px 35px")
					
				}, 500)
			}
		}
	}, function() {
		if (!$("#sideNav").hasClass("openSideNav")) {
			setTimeout(function() {
				//$(".menuText").delay(300).hide(200)
				$(".menuText").hide(200)
				//$(".main").css("width",$('.container').width() + $("#sideNav").width() -  1 +"px");
				$(".main").css("padding", "50px 80px")
				$(".footer").css("padding", "50px 80px")
			}, 300)
			
		}
	})
})
</script>
</head>
<body>
<nav id="sideNav" class="openSideNav">
<!-- <nav id="sideNav"> -->
	<sec:authorize access="!isAuthenticated()">
		<a class="" href="/login">
			<button id="signIn">
				<img src="${pageContext.request.contextPath}/img/user_icon.svg" alt="user" id="sideNavUserIcon" />
				<span class="menuText">Sign In</span>
			</button>
		</a>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
		<sec:authorize access="hasAuthority('Admin') || hasAuthority('DBA')">
			<a href="/admin" class="${activeAdmin} ${activeRoles} ${activeUsers} ${activeAirlines} ${activeFlightForm} ${activeAirports} ${activePassengers} ${activeReservations}">
				<button id="signIn">
					<img src="${pageContext.request.contextPath}/img/user_icon.svg" alt="user" id="sideNavUserIcon" />
					<span class="menuText"><sec:authentication property="principal.username"/></span>
				</button>
			</a>
		</sec:authorize>
		<sec:authorize access="!hasAuthority('Admin') && !hasAuthority('DBA')">
			<a class="${activeProfile}" href="/userProfile">
				<button id="signIn">
					<img src="${pageContext.request.contextPath}/img/user_icon.svg" alt="user" id="sideNavUserIcon" />
					<span class="menuText"><sec:authentication property="principal.username"/></span>
				</button>
			</a>
		</sec:authorize>
	</sec:authorize>
	<div class="line"></div>
	<a class="${activeFlights}" href="/flights">
		<button id="flights">
			<img src="${pageContext.request.contextPath}/img/plane-solid.svg" alt="plane" id="plane" />
			<span class="menuText">Flights</span>
		</button>
	</a>
	<a class="" href="/underConstruction">
		<button id="stays">
			<img src="${pageContext.request.contextPath}/img/bed-solid.svg" alt="bed" id="bed" />
			<span class="menuText">Stays</span>
		</button>
	</a>
	<a class="" href="/underConstruction">
		<button id="cars">
			<img src="${pageContext.request.contextPath}/img/car-solid.svg" alt="car" id="car" />
			<span class="menuText">Cars</span>
		</button>
	</a>
	<a class="" href="/underConstruction">
		<button id="packages">
			<img src="${pageContext.request.contextPath}/img/tree-city-solid.svg" alt="tree and building" id="package" />
			<span class="menuText">Packages</span>
		</button>
	</a>
	<a class="" href="/underConstruction">
		<button id="trainsBuses">
			<img src="${pageContext.request.contextPath}/img/train-solid.svg" alt="train" id="train" />
			<span class="menuText">Trains and buses</span>
		</button>
	</a>
	<div class="line"></div>
	<a class="" href="/underConstruction">
		<button id="explore">
			<img src="${pageContext.request.contextPath}/img/globe-solid.svg" alt="globe" id="explore" />
			<span class="menuText">Explore</span>
		</button>
	</a>
	<a class="" href="/underConstruction">
		<button id="tracker">
			<img src="${pageContext.request.contextPath}/img/location-crosshairs-solid.svg" alt="location crosshairs" id="tracker" />
			<span class="menuText">Flight Tracker</span>
		</button>
	</a>
	<a class="" href="/underConstruction">
		<button id="tips">
			<img src="${pageContext.request.contextPath}/img/newspaper-solid.svg" alt="newspaper" id="tips" />
			<span class="menuText">Travel tips</span>
		</button>
	</a>
	<div class="line"></div>
	<a class="" href="#">
		<button id="sideNavTrips">
			<img src="${pageContext.request.contextPath}/img/heart-solid.svg" alt="heart" id="sideNavHeart" />
			<span class="menuText">Trips</span>
		</button>
	</a>
</nav>
</body>
</html>