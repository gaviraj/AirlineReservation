<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Profile</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/userProfile.css">
<script src="${pageContext.request.contextPath}/js/formPage.js"></script>
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
		<div class="main userProfile">
			<h1>My Profile</h1>
			<h2>Upcoming flights:</h2>
			<core:if test="${upcomingReservations.isEmpty()}">
				<p>You have no upcoming reservations</p>
			</core:if>
			<core:if test="${!upcomingReservations.isEmpty()}">
				<div class="listBodyContainer">
					<core:if test="${errors != null}">
						<p id="errors">${errors}</p>
					</core:if>
					<table class="dbList dbListData">
					<thead>
						<tr>
							<th>Reservation #</th>
							<th>Passenger</th>
							<th>Flight</th>
							<th>Departure:</th>
							<th>Arrival:</th>
							<th>Checked Bags</th>
							<th>Checked In</th>
							<th></th>
						</tr>
					</thead>
						<tbody>
							<core:forEach items="${upcomingReservations}" var="reservation">
								<tr>
									<form:form action="checkInFlight" method="POST" modelAttribute="reservation">
										<td class="reservationNum">${reservation.getReservationNumber()}</td>
										<td>${reservation.getPassenger().getFirstName()} ${reservation.getPassenger().getLastName()}</td>
										<td>${reservation.getFlight().getFlightNumber()}</td>
										<td>
											${reservation.getFlight().getDepartureCity()}<br>
											${reservation.getFlight().getDepartureDate()}<br>
											${reservation.getFlight().getDepartureTime()}
										</td>
										<td>
											${reservation.getFlight().getArrivalCity()}<br>
											${reservation.getFlight().getArrivalDate()}<br>
											${reservation.getFlight().getArrivalTime()}
										</td>
										<core:if test="${reservation.isCheckedIn()}">
											<td class="trueCheckedBags">${reservation.getCheckedBags()}</td>
										</core:if>
										<core:if test="${!reservation.isCheckedIn()}">
											<td>
												<form:input type="number" path="reservationNumber" value="${reservation.getReservationNumber()}" placeholder="reservationNumber" hidden="true"/>
												<form:input type="number" path="checkedBags" value="${reservation.getCheckedBags()}" placeholder="0 bags" />
											</td>
										</core:if>
										<td align="center">
											<core:if test="${reservation.isCheckedIn()}">
												<img src="${pageContext.request.contextPath}/img/square-check-solid.svg" alt="checked" class="checkedIcon" />
											</core:if>
											<core:if test="${!reservation.isCheckedIn()}">
												<img src="${pageContext.request.contextPath}/img/square-regular.svg" alt="checked" class="checkedIcon" />
											</core:if>
										</td>
										<td>
											<core:if test="${!reservation.isCheckedIn()}">
												<input type="submit" value="Check In" />
											</core:if>
										</td>
									</form:form>
								</tr>
							</core:forEach>
						</tbody>
					</table>
				</div>
			</core:if>
			
			<h2>Complete flights:</h2>
			<core:if test="${completeReservations.isEmpty()}">
				<p>You have not completed any reservations</p>
			</core:if>
			<core:if test="${!completeReservations.isEmpty()}">
				<div class="listBodyContainer">
					<table class="dbList dbListData">
					<thead>
						<tr>
							<th>Reservation #</th>
							<th>Passenger</th>
							<th>Flight</th>
							<th>Departure:</th>
							<th>Arrival:</th>
							<th>Checked Bags</th>
							<th>Checked In</th>
							<th></th>
						</tr>
					</thead>
						<tbody>
							<core:forEach items="${completeReservations}" var="reservation">
								<tr>
									<td class="reservationNum">${reservation.getReservationNumber()}</td>
									<td>${reservation.getPassenger().getFirstName()} ${reservation.getPassenger().getLastName()}</td>
									<td>${reservation.getFlight().getFlightNumber()}</td>
									<td>
										${reservation.getFlight().getDepartureCity()}<br>
										${reservation.getFlight().getDepartureDate()}<br>
										${reservation.getFlight().getDepartureTime()}
									</td>
									<td>
										${reservation.getFlight().getArrivalCity()}<br>
										${reservation.getFlight().getArrivalDate()}<br>
										${reservation.getFlight().getArrivalTime()}
									</td>
									<td class="trueCheckedBags">${reservation.getCheckedBags()}</td>
									<td align="center">
										<core:if test="${reservation.isCheckedIn()}">
											<img src="${pageContext.request.contextPath}/img/square-check-solid.svg" alt="checked" class="checkedIcon" />
										</core:if>
										<core:if test="${!reservation.isCheckedIn()}">
											<img src="${pageContext.request.contextPath}/img/square-regular.svg" alt="checked" class="checkedIcon" />
										</core:if>
									</td>
								</tr>
							</core:forEach>
						</tbody>
					</table>
				</div>
			</core:if>
		</div>
		<%@ include file="footer.jsp" %>
	</div>
</div>
</body>
</html>