<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<meta charset="UTF-8">
<title>Flight Reservation</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservationForm.css">
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
		<div class="main reservationForm">
			<h1>Make Reservation</h1>
			<div class="flightDetails">
				<div class="flightAirline">
					<h3>${flight.getAirlines().getAirlinesName()}</h3>
					<p>${flight.getFlightNumber()}</p>
				</div>
				<div class="departureInfo">
					<h4>Depart:</h4>
					<p>${flight.getDepartureCity()}</p>
					<p>${flight.getDepartureTime()}</p>
				</div>
				<div class="departureInfo">
					<h4>Arrive:</h4>
					<p>${flight.getArrivalCity()}</p>
					<p>${flight.getArrivalTime()}</p>
				</div>
				<div class="resultBook">
					<h2 class="price">
						$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${flight.getTicketPrice()}"/>
					</h2>
					<p>${flight.getCapacity() - flight.getSeatsBooked()}/${flight.getCapacity()} seats</p>
				</div>
			</div>
			<div class="passengerInfo">
				<h3 class="formHeader">Passenger Information:</h3>
				<p>Name: ${passenger.getFirstName()} ${passenger.getLastName()}</p>
				<p>Email: ${passenger.getEmail()}</p>
				<p>Phone: ${passenger.getPhoneNum()}</p>
				<p>Gender: ${passenger.getGender()}</p>
				<p>DOB: ${passenger.getDob()}</p>
				<h4 class="addressHeader">Address:</h4>
				<p>${passenger.getAddress().getAddressLine1()} ${passenger.getAddress().getAddressLine2()}</p>
				<p>${passenger.getAddress().getCity()}, ${passenger.getAddress().getState()}</p>
				<p>${passenger.getAddress().getCountry()}<p>
				<p>${passenger.getAddress().getZipCode()}<p>
			</div>
			<form:form action="saveReservation" method="POST" modelAttribute="reservation">
				<form:input type="number" path="reservationNumber" value="${reservation.getReservationNumber()}" placeholder="reservation number" hidden="true"/>
				<form:input type="text" path="passenger" value="${passenger.getPassengerId()}" placeholder="passenger" hidden="true"/>
				<form:input type="text" path="flight" value="${flight.getFlightId()}" placeholder="flight" hidden="true"/>
				<form:input type="number" path="checkedBags" value="0" placeholder="checked bags" hidden="true"/>
				<form:input type="text" path="checkedIn" value="false" placeholder="checked in" hidden="true"/>
				<input type="submit" value="Confirm Booking" />
			</form:form>
		</div>
		<%@ include file="footer.jsp" %>
	</div>
</div>
</body>
</html>