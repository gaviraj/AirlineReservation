<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Successfully Booked</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservationSuccess.css">
<script>
	$(document).ready(function() {
		$(".container").css("height",$('#sideNav').height());
	})
</script>
</head>
<body>
<%@ include file="navBar.jsp" %>
<div class="container">
	<%@ include file="sideNav.jsp" %>
	<div class="mainContainer">
		<div class="main reservationSuccess">
			<div class="reservationInfo">
				<h1>Successfully Booked!</h1>
				<h2>Reservation number: ${reservation.getReservationNumber()}</h2>
				<h3>Flight number: ${reservation.getFlight().getFlightNumber()}</h3>
				<div class="flightInfo">
					<div>
						<h3>Depart:</h3>
						<p>${reservation.getFlight().getDepartureCity()}</p>
						<p>${reservation.getFlight().getDepartureDate()} ${reservation.getFlight().getDepartureTime()}</p>
					</div>
					<div>
						<h3>Arrive:</h3>
						<p>${reservation.getFlight().getArrivalCity()}</p>
						<p>${reservation.getFlight().getArrivalDate()} ${reservation.getFlight().getArrivalTime()}</p>
					</div>
				</div>
			</div>
			<div class="passengerInfo">
				<h3>Passenger: </h3>
				<p>Name: ${reservation.getPassenger().getFirstName()} ${reservation.getPassenger().getLastName()}</p>
				<p>Email: ${reservation.getPassenger().getEmail()}</p>
				<p>Phone: ${reservation.getPassenger().getPhoneNum()}</p>
				<h4>Address:</h4>
				<p>${reservation.getPassenger().getAddress().getAddressLine1()} ${reservation.getPassenger().getAddress().getAddressLine2()}</p>
				<p>${reservation.getPassenger().getAddress().getCity()}, ${reservation.getPassenger().getAddress().getState()}</p>
				<p>${reservation.getPassenger().getAddress().getCountry()}</p>
				<p>${reservation.getPassenger().getAddress().getZipCode()}</p>
			</div>
			<h2 class="totalPrice">Total: $<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${reservation.getFlight().getTicketPrice()}"/></h2>
		</div>
		<%@ include file="footer.jsp" %>
	</div>
</div>
</body>
</html>