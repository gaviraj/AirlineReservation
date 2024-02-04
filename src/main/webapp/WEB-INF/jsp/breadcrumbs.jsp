<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
</head>
<body>
	<ul class="breadcrumb">
			<li><a href="/">Home</a></li>
		  	<li><a class="${activeAdmin}" href="/admin">Admin</a></li>
		  	<core:if test="${activeAdmin != 'active'}">
		  		<core:if test="${activeRoles == 'active'}">
			  		<li><a class="${activeRoles}" href="/roles">Roles</a></li>
		  		</core:if>
		  		<core:if test="${activeUsers == 'active'}">
		  			<li><a class="${activeUsers}" href="/users">Users</a></li>
		  		</core:if>
		  		<core:if test="${activeAirlines == 'active'}">
		  			<li><a class="${activeAirlines}" href="/airlines">Airlines</a></li>
		  		</core:if>
		  		<core:if test="${activeFlightForm == 'active'}">
		  			<li><a class="${activeFlightForm}" href="/flightForm">Flights</a></li>
		  		</core:if>
		  		<core:if test="${activeAirports == 'active'}">
		  			<li><a class="${activeAirports}" href="/airports">Airports</a></li>
		  		</core:if>
		  		<core:if test="${activePassengers == 'active'}">
		  			<li><a class="${activePassengers}" href="/passengers">Passengers</a></li>
		  		</core:if>
		  		<core:if test="${activeReservations == 'active'}">
		  			<li><a class="${activeReservations}" href="/reservations">Reservations</a></li>
		  		</core:if>
		  	</core:if>
		</ul>
</body>
</html>