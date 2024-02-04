<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search Flights</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"> --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/flights.css">
<script>
	$(document).ready(function() {
		$(".container").css("height",$('#sideNav').height());
		//$(".flights").css("height",$('#sideNav').height());
		
		$(".switch").click(function() {
			const fromCity = $("#fromCity").val()
			const toCity = $("#toCity").val()
			$("#fromCity").val(toCity)
			$("#toCity").val(fromCity)
		})
	})
</script>
</head>
<body>
<%@ include file="navBar.jsp" %>
<div class="container">
	<%@ include file="sideNav.jsp" %>
	<div class="mainContainer">
		<div class="main flights">
			<!-- <h1>Compare flights from 100s of sites.</h1> -->
			<h1>Book flights from 100s of airlines.</h1>
			<core:if test="${dateInputError != null}">
				<p class="errorMessage">${dateInputError}</p>
			</core:if>
			<form:form action="searchFlights" method="GET">
				<div class="formSection labeledInputSection">
					<label for="fromCity"><img src="${pageContext.request.contextPath}/img/plane-departure-solid.svg" />
						<input type="text" name="fromCity" id="fromCity" value="${searchFromCity}" placeholder="From?" />
					</label>
				</div>
				<button type="button" class="switch"><img src="${pageContext.request.contextPath}/img/arrow-right-arrow-left-solid.svg" /></button>
				<div class="formSection labeledInputSection">
					<label for="toCity"><img src="${pageContext.request.contextPath}/img/plane-arrival-solid.svg" />
						<input type="text" name="toCity" id="toCity" value="${searchToCity}" placeholder="To?" />
					</label>
				</div>
				<input type="date" name="fromDate" value="${searchFromDate}" />
				<input type="date" name="toDate" value="${searchToDate}" />
				<button id="searchSubmit"><img src="${pageContext.request.contextPath}/img/magnifying-glass.png" /></button>
			</form:form>
			<core:if test="${searchResults.size() == null}">
				<core:forEach items="${flights}" var="flight">
				<div class="result">
					<div class="resultAirline">
						<h3>${flight.getAirlines().getAirlinesName()}</h3>
						<p>${flight.getFlightNumber()}</p>
					</div>
					<div class="resultDetails">
						<div>
							<h4>Depart:</h4>
							<p>${flight.getDepartureCity()}</p>
							<p>${flight.getDepartureDate()} ${flight.getDepartureTime()}</p>
							<br>
							<h4>Arrive:</h4>
							<p>${flight.getArrivalCity()}</p>
							<p>${flight.getArrivalDate()} ${flight.getArrivalTime()}</p>
						</div>
						<div class="resultBook">
							<h2 class="price">
								$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${flight.getTicketPrice()}"/>
							</h2>
							<p>${flight.getCapacity() - flight.getSeatsBooked()}/${flight.getCapacity()} seats</p>
							<a href="/passengerForm?flightId=${flight.getFlightId()}"><button class="btnDefault">Book Now</button></a>
						</div>
					</div>
				</div>
			</core:forEach>			
			</core:if>
			<core:if test="${searchResults.size() > 0}">
				<h3>${searchResults.size()} flight(s) found:</h3>
			</core:if>
			<core:if test="${searchResults.size() == 0}">
				<h3>No flights found matching search criteria</h3>
			</core:if>
			<core:forEach items="${searchResults}" var="result">
				<div class="result">
					<div class="resultAirline">
						<h3>${result.getAirlines().getAirlinesName()}</h3>
						<p>${result.getFlightNumber()}</p>
					</div>
					<div class="resultDetails">
						<div>
							<h4>Depart:</h4>
							<p>${result.getDepartureCity()}</p>
							<p>${result.getDepartureTime()}</p>
							<br>
							<h4>Arrive:</h4>
							<p>${result.getArrivalCity()}</p>
							<p>${result.getArrivalTime()}</p>
						</div>
						<div class="resultBook">
							<h2 class="price">
								$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${result.getTicketPrice()}"/>
							</h2>
							<p>${result.getCapacity() - result.getSeatsBooked()}/${result.getCapacity()} seats</p>
							<a href="/passengerForm?flightId=${result.getFlightId()}"><button class="btnDefault">Book Now</button></a>
						</div>
					</div>
				</div>
			</core:forEach>
		</div>
		<%@ include file="footer.jsp" %>
	</div>
</div>
</body>
</html>