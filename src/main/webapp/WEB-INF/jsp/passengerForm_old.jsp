<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Flight Reservation</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/passengerForm.css">
<script>
	$(document).ready(function() {
		$(".container").css("height",$('#sideNav').height());
		
		function formatPhoneNumber(phoneNumber) {
			const phoneNumberLength = phoneNumber.length;
			let formattedNumber = "";
	
	        if (phoneNumberLength < 4) {
	            return phoneNumber;
	        } else if (phoneNumberLength < 7) {
	            return phoneNumber.slice(0, 3) + "-" + phoneNumber.slice(3);
	        } else {
	            return phoneNumber.slice(0, 3) + "-" + phoneNumber.slice(3, 6) + "-" + phoneNumber.slice(6, 10);
	        }
		}
		
		$("#phoneNum").on("input", function() {
			const phoneNumber = $("#phoneNum").val()
			console.log("phoneNum: " + phoneNumber)
			console.log(typeof phoneNumber)
			
			const inputPhoneNumber = phoneNumber.replace(/\D/g,'');
			console.log("num only digits: " + inputPhoneNumber)
	        const formattedPhoneNumber = formatPhoneNumber(inputPhoneNumber);
	        $("#phoneNum").val(formattedPhoneNumber)
		})
	})
</script>
</head>
<body>
<%@ include file="navBar.jsp" %>
<div class="container">
	<%@ include file="sideNav.jsp" %>
	<div class="mainContainer">
		<div class="main passengerForm">
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
			
			<h3 class="formHeader">Passenger Information:</h3>
			<form:errors path="passenger.*" cssStyle="color:red;" />		
			<form:form action="savePassenger?flightId=${flight.getFlightId()}" method="POST" modelAttribute="passenger">
				<div class="formSection">
					<form:input type="number" path="passengerId" value="${passenger.getPassengerId()}" placeholder="passenger id" hidden="true"/>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="firstName"><img src="${pageContext.request.contextPath}/img/signature-solid.svg" />
						<form:input path="firstName" value="${passenger.getFirstName()}" placeholder="First Name" />
					</form:label>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="lastName"><img src="${pageContext.request.contextPath}/img/signature-solid.svg" />
						<form:input path="lastName" value="${passenger.getLastName()}" placeholder="Last Name" />
					</form:label>
				</div>
				<br>
				<br>
				<div class="formSection labeledInputSection">
					<form:label path="email"><img src="${pageContext.request.contextPath}/img/envelope-solid.svg" id="emailIcon" />
						<form:input path="email" value="${passenger.getEmail()}" placeholder="Email" />
					</form:label>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="phoneNum"><img src="${pageContext.request.contextPath}/img/phone-flip-solid.svg" />
						<form:input path="phoneNum" value="${passenger.getPhoneNum()}" placeholder="123-456-7890" />
					</form:label>
				</div>
				<br>
				<br>
				<div class="formSection labeledInputSection">
					<form:label path="phoneNum"><img src="${pageContext.request.contextPath}/img/cake-candles-solid.svg" id="dobIcon" />
						<form:input type="date" path="dob" value="${passenger.getDob()}" />
					</form:label>
				</div>
				<form:select path="gender">
					<form:option value="" disabled="true" selected="true">--- Select Gender ---</form:option>
					<core:forEach items="${genders}" var="gender">
						<form:option value="${gender}" label="${gender}" />
					</core:forEach>
				</form:select>
				<br>
				<br>
				<h4 class="addressHeader">Address:</h4>
				<div class="formSection">
					<form:input class="addressInput" type="text" path="address.addressLine1" value="${passenger.getAddress().getAddressLine1()}" placeholder="Address line 1" />
					<form:input class="addressInput" type="text" path="address.addressLine2" value="${passenger.getAddress().getAddressLine2()}" placeholder="Address line 2" />
				</div>
				<br>
				<div class="formSection">
					<form:input class="addressInput" type="text" path="address.city" value="${passenger.getAddress().getCity()}" placeholder="City" />
					<form:input class="addressInput" type="text" path="address.state" value="${passenger.getAddress().getState()}" placeholder="State" />
				</div>
				<br>
				<div class="formSection">
					<form:input class="addressInput" type="text" path="address.country" value="${passenger.getAddress().getCountry()}" placeholder="Country" />
				</div>
				<br>
				<div class="formSection">
					<form:input class="addressInput" type="text" path="address.zipCode" value="${passenger.getAddress().getZipCode()}" placeholder="Zip code" />
				</div>
				<br>
				<input type="submit" value="Add passenger" />
			</form:form>
		</div>
		<%@ include file="footer.jsp" %>
	</div>
</div>
</body>
</html>