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
		
		$(".phoneNum").on("input", function() {
			const phoneNumber = $(this).val()
			console.log("phoneNum: " + phoneNumber)
			console.log(typeof phoneNumber)
			
			const inputPhoneNumber = phoneNumber.replace(/\D/g,'');
			console.log("num only digits: " + inputPhoneNumber)
	        const formattedPhoneNumber = formatPhoneNumber(inputPhoneNumber);
	        $(this).val(formattedPhoneNumber)
		})
		
		$("#next").click(function() {
			let allPassengers = [];
			const numOfPassengers = $("#numOfPass").text()
			for (i = 0; i < numOfPassengers; i++) {
				let fNameId = "#"+i+"-firstName"
				let lNameId = "#"+i+"-lastName"
				let emailId = "#"+i+"-email"
				let phoneNumId = "#"+i+"-phoneNum"
				let dobId = "#"+i+"-dob"
				let genderId = "#"+i+"-gender"

				let firstName = $(fNameId).val()
				let lastName = $(lNameId).val()
				let email = $(emailId).val()
				let phoneNum = $(phoneNumId).val()
				let dob = $(dobId).val()
				let gender = $(genderId+" option:selected").val()
				
				if (firstName == "" || lastName == "" || email == "" || phoneNum == "" || dob == "" || gender == "") {
					$("#errors").empty()
					$("#errors").append("<p>Please enter all passenger(s) details.</p>")
					return;
				}
				
				const newPassenger = {
					firstName: firstName,
					lastName: lastName,
					email: email,
					phoneNum: phoneNum,
					gender: gender,
					dob: dob
				}
				allPassengers.push(newPassenger)
			}
			
			const flightId = $("#flightId").text()
			
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: "/savePassengers?flightId="+flightId,
				data: JSON.stringify(allPassengers),
				dataType: "json",
				success: function(passengers) {
					console.log("saved passengers", passengers)
					let redirectString = "/reservationForm2?flightId=" + flightId;
					$.each(passengers, function(index, passenger) {
							redirectString = redirectString + "&passengerIds=" + passenger.passengerId;							
					})
					console.log("redirect: " + redirectString)
					window.location.replace(redirectString);
					
				},
				error: function(e) {
				}
			})
			
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
				<p hidden="true" id="flightId">${flight.getFlightId()}</p>
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
						<core:if test="${passengerList != null}">
							<span class="highlight">${passengerList.size()}</span> x
						</core:if>
						$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${flight.getTicketPrice()}"/>
						
					</h2>
					<p>${flight.getCapacity() - flight.getSeatsBooked()} seats left</p>
				</div>
			</div>
			
			<p hidden="true" id="redirect">${redirect}</p>
			<core:if test="${passengerList == null}">
			<h3>Number of passengers:</h3>
			<form action="addPassengers?flightId=${flight.getFlightId()}" method="POST">
				<div class="formSection labeledInputSection">
					<label for="numOfPassengers"><img src="${pageContext.request.contextPath}/img/people-group-solid.svg" id="numPassengers" />
						<input type="number" placeholder="number of passengers" min="1" value="1" id="numOfPassengers" name="numOfPassengers" />
					</label>
				</div>
				<input type="submit" value="Add" />
			</form>
			</core:if>
			
			<core:if test="${passengerList != null}">
			<h3 class="formHeader">Passenger Information:</h3>
			<p hidden="true" id="numOfPass">${passengerList.size()}</p>
			<form:errors path="passenger.*" cssStyle="color:red;" />		
			<form action="savePassenger?flightId=${flight.getFlightId()}" method="POST" modelAttribute="passenger">
				<core:forEach items="${passengerList}" var="passenger" varStatus="status">
					<p>Passenger ${status.index+1}</p>
					<div class="formSection">
						<input type="number" class="passengerId" value="${passenger.getPassengerId()}" placeholder="passenger id" hidden="true"/>
					</div>
					<div class="formSection labeledInputSection">
						<label for="firstName"><img src="${pageContext.request.contextPath}/img/signature-solid.svg" />
							<input type="text" id="${status.index}-firstName" value="${passenger.getFirstName()}" placeholder="First Name" />
						</label>
					</div>
					<div class="formSection labeledInputSection">
						<label path="lastName"><img src="${pageContext.request.contextPath}/img/signature-solid.svg" />
							<input type="text" id="${status.index}-lastName" value="${passenger.getLastName()}" placeholder="Last Name" />
						</label>
					</div>
					<br>
					<br>
					<div class="formSection labeledInputSection">
						<label for="email"><img src="${pageContext.request.contextPath}/img/envelope-solid.svg" id="emailIcon" />
							<input type="email" id="${status.index}-email" value="${passenger.getEmail()}" placeholder="Email" />
						</label>
					</div>
					<div class="formSection labeledInputSection">
						<label for="phoneNum"><img src="${pageContext.request.contextPath}/img/phone-flip-solid.svg" />
							<input type="text" class="phoneNum" id="${status.index}-phoneNum" value="${passenger.getPhoneNum()}" placeholder="123-456-7890" />
						</label>
					</div>
					<br>
					<br>
					<div class="formSection labeledInputSection">
						<label for="dob"><img src="${pageContext.request.contextPath}/img/cake-candles-solid.svg" id="dobIcon" />
							<input type="date" id="${status.index}-dob" value="${passenger.getDob()}" />
						</label>
					</div>
					<select id="${status.index}-gender">
						<option value="" disabled="true" selected="true">--- Select Gender ---</option>
						<core:forEach items="${genders}" var="gender">
							<option value="${gender}" label="${gender}" />
						</core:forEach>
					</select>
				</core:forEach>
				<br>
				<div id="errors"></div>
				<br>
				<!-- <input type="submit" value="Next" /> -->
				<a href="/passengerForm?flightId=${flight.getFlightId()}"><button type="button" id="back" class='btnDefault'>Back</button></a>
				<button type='button' id="next" class='btnDefault'>Next</button>
			</form>
			</core:if>
		</div>
		<%@ include file="footer.jsp" %>
	</div>
</div>
</body>
</html>