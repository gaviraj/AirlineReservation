<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Airports</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/airports.css">
<script src="${pageContext.request.contextPath}/js/formPage.js"></script>
</head>
<body>
<%@ include file="navBar.jsp" %>
<div class="container">
	<%@ include file="sideNav.jsp" %>
	<div class="mainContainer">
		<div class="main airports">
			<%@ include file="breadcrumbs.jsp" %>
			<h1>Airports</h1>
			<form:errors path="airport.*" cssStyle="color:red;" />
			<form:form action="saveAirport" method="POST" modelAttribute="airport">
				<div class="formSection">
					<form:input type="number" path="airportId" value="${airport.getAirportId()}" placeholder="airport id" hidden="true"/>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="airportCode"><img src="${pageContext.request.contextPath}/img/list-ol-solid.svg" />
						<form:input path="airportCode" value="${airport.getAirportCode()}" placeholder="Airport code" />
					</form:label>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="airportName"><img src="${pageContext.request.contextPath}/img/signature-solid.svg" />
						<form:input path="airportName" value="${airport.getAirportName()}" placeholder="Airport name" />
					</form:label>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="city"><img src="${pageContext.request.contextPath}/img/city-solid.svg" />
						<form:input path="city" value="${airport.getCity()}" placeholder="City" />
					</form:label>
				</div>
				<core:if test="${!isUpdate}">
					<input type="submit" value="Submit" />
				</core:if>
				<core:if test="${isUpdate}">
					<input type="submit" value="Update" class="btnUpdate" />
					<a href="deleteAirport?airportId=${airport.getAirportId()}"><button type="button" class="btnDefault btnDelete">Delete</button></a>
					<a href="/airports"><button type="button" class="btnDefault btnCancel">Cancel</button></a>
				</core:if>
			</form:form>
			
			<div class="listBodyContainer">
				<!-- <div class="listHeaderContainer">
					<table class="dbList">
						<thead>
							<tr>
								<th>Airport id</th>
								<th>Airport code</th>
								<th>Airport name</th>
								<th>City</th>
								<th>Arrival Flights</th>
								<th>Departure Flights</th>
								<th></th>
							</tr>
						</thead>
					</table>
				</div> -->
				<table class="dbList dbListData">
				<thead>
					<tr>
						<th><a href="airports?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=airportId" class="sortHeader ${sortedBy == 'airportId' ? 'active' : ''}">Airport id</a></th>
						<th><a href="airports?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=airportCode" class="sortHeader ${sortedBy == 'airportCode' ? 'active' : ''}">Airport code</a></th>
						<th><a href="airports?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=airportName" class="sortHeader ${sortedBy == 'airportName' ? 'active' : ''}">Airport name</a></th>
						<th><a href="airports?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=city" class="sortHeader ${sortedBy == 'city' ? 'active' : ''}">City</a></th>
						<th><a href="airports?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=arrivalFlights" class="sortHeader ${sortedBy == 'arrivalFlights' ? 'active' : ''}">Arrival Flights</a></th>
						<th><a href="airports?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=departureFlights" class="sortHeader ${sortedBy == 'departureFlights' ? 'active' : ''}">Departure Flights</a></th>
						<th></th>
					</tr>
				</thead>
					<tbody>
						<core:forEach items="${airports}" var="airport">
							<tr>
								<td>${airport.getAirportId()}</td>
								<td>${airport.getAirportCode()}</td>
								<td>${airport.getAirportName()}</td>
								<td>${airport.getCity()}</td>
								<td>
									<!--<core:forEach items="${airport.getArrivalFlights()}" var="flight"  varStatus="status">
										<span>${flight.getFlightNumber()}${not status.last ? ', ' : ''}</span>
									</core:forEach>-->
									<core:forEach items="${flights}" var="flight"  varStatus="status">
										<core:if test="${flight.getArrivalCity() == airport.getCity()}">
											<span>${flight.getFlightNumber()}${not status.last ? ', ' : ''}</span>
										</core:if>
									</core:forEach>
								</td>
								<td>
									<!--<core:forEach items="${airport.getDepartureFlights()}" var="flight"  varStatus="status">
										<span>${flight.getFlightNumber()}${not status.last ? ', ' : ''}</span>
									</core:forEach>-->
									<core:forEach items="${flights}" var="flight"  varStatus="status">
										<core:if test="${flight.getDepartureCity() == airport.getCity()}">
											<span>${flight.getFlightNumber()}${not status.last ? ', ' : ''}</span>
										</core:if>
									</core:forEach>
								</td>
								<td><a href="updateAirport?airportId=${airport.getAirportId()}"><img src="${pageContext.request.contextPath}/img/pen-to-square-solid.svg" /></a></td>
							</tr>
						</core:forEach>
					</tbody>
				</table>
			</div>
			<div class="pageList">
				<core:set var="noOfPages" value="${totalPages}"></core:set>
				<core:set var="sortedBy" value="${sortedBy}"></core:set>
				<core:set var="pageSize" value="${pageSize}"></core:set>
				<core:set var="pageNo" value="${pageNo}"></core:set>
				<%
				for (int i = 0; i < (int)pageContext.getAttribute("noOfPages"); i++) {
					//pageContext, out, request, response are some of jsp implicit objects
					if (i > 0) {
						out.println("&middot;");
					}
					if ((int)pageContext.getAttribute("pageNo") == i) {
						out.println("<p>" + (i+1) + "</p>");
					} else {
						out.println("<a class=\"page\" href=\"flightForm?pageNo="+i+"&pageSize="+request.getAttribute("pageSize")+"&sortedBy="+request.getAttribute("sortedBy")+"\">"+(i+1)+"</a>");						
					}
				}
				%>
			</div>
		</div>
		<%@ include file="footer.jsp" %>
	</div>
</div>
</body>
</html>