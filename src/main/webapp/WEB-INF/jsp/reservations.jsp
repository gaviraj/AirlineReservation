<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reservations</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservations.css">
<script src="${pageContext.request.contextPath}/js/formPage.js"></script>
<script>
	$(document).ready(function() {
		$(".sortHeader").click(function() {
			$(".sortHeader").parent().siblings.children().removeClass("active")
			$(this).addClass("active")
			
		})
	})
</script>
</head>
<body>
<body>
<%@ include file="navBar.jsp" %>
<div class="container">
	<%@ include file="sideNav.jsp" %>
	<div class="mainContainer">
		<div class="main reservations">
			<%@ include file="breadcrumbs.jsp" %>
			<h1>Reservations</h1>
			<div class="listBodyContainer">
				<core:if test="${errors != null}">
					<p id="errors">${errors}</p>
				</core:if>
				<table class="dbList dbListData">
				<thead>
					<tr>
						<th><a href="reservations?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=reservationNumber" class="sortHeader ${sortedBy == 'reservationNumber' ? 'active' : ''}">Reservation Number</a></th>
						<th><a href="reservations?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=passenger.firstName" class="sortHeader ${sortedBy == 'passenger.firstName' ? 'active' : ''}">Passenger</a></th>
						<th><a href="reservations?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=flight.flightNumber" class="sortHeader ${sortedBy == 'flight.flightNumber' ? 'active' : ''}">Flight</a></th>
						<th><a href="reservations?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=flight.departureDate" class="sortHeader ${sortedBy == 'flight.departureDate' ? 'active' : ''}">Departure</a></th>
						<th><a href="reservations?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=flight.arrivalDate" class="sortHeader ${sortedBy == 'flight.arrivalDate' ? 'active' : ''}">Arrival</a></th>
						<th>Checked Bags</th>
						<th><a href="reservations?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=checkedIn" class="sortHeader ${sortedBy == 'checkedIn' ? 'active' : ''}">Checked In</a></th>
						<th></th>
					</tr>
				</thead>
					<tbody>
						<core:forEach items="${reservations}" var="reservation">
							<tr>
								<form:form action="checkInFlightAdmin" method="POST" modelAttribute="reservation">
									<td>${reservation.getReservationNumber()}</td>
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
			<div class="pageList">
				<core:set var="noOfPages" value="${totalPages}"></core:set>
				<core:set var="sortedBy" value="${sortedBy}"></core:set>
				<core:set var="pageSize" value="${pageSize}"></core:set>
				<%
				for (int i = 0; i < (int)pageContext.getAttribute("noOfPages"); i++) {
					//pageContext, out, request, response are some of jsp implicit objects
					if (i > 0) {
						out.println("&middot;");
					}
					out.println("<a class=\"page\" href=\"reservations?pageNo="+i+"&pageSize="+request.getAttribute("pageSize")+"&sortedBy="+request.getAttribute("sortedBy")+"\">"+(i+1)+"</a>");
				}
				%>
			</div>
		</div>
		<%@ include file="footer.jsp" %>
	</div>
</div>
</body>
</html>