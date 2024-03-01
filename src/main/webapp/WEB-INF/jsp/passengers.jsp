<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Passengers</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/passengers.css">
<script src="${pageContext.request.contextPath}/js/formPage.js"></script>
</head>
<body>
<body>
<%@ include file="navBar.jsp" %>
<div class="container">
	<%@ include file="sideNav.jsp" %>
	<div class="mainContainer">
		<div class="main passengers">
			<%@ include file="breadcrumbs.jsp" %>
			<h1>Passengers</h1>
			<form:errors path="passenger.*" cssStyle="color:red;" />
			<form:form action="savePassengerAdmin" method="POST" modelAttribute="passenger">
				<div class="formSection">
					<form:input type="number" path="passengerId" value="${passenger.getPassengerId()}" placeholder="Passenger id" hidden="true"/>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="firstName"><img src="${pageContext.request.contextPath}/img/signature-solid.svg" />
						<form:input path="firstName" value="${passenger.getFirstName()}" placeholder="First name" />
					</form:label>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="lastName"><img src="${pageContext.request.contextPath}/img/signature-solid.svg" />
						<form:input path="lastName" value="${passenger.getLastName()}" placeholder="Last name" />
					</form:label>
				</div>
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
				<form:select path="gender">
					<form:option value="" disabled="true" selected="true">--- Select Gender ---</form:option>
					<core:forEach items="${genders}" var="gender">
						<form:option value="${gender}" label="${gender}" />
					</core:forEach>
				</form:select>
				<form:input type="date" path="dob" value="${passenger.getDob()}" />
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
				<core:if test="${!isUpdate}">
					<input type="submit" value="Submit" />
				</core:if>
				<core:if test="${isUpdate}">
					<input type="submit" value="Update" class="btnUpdate" />
					<a href="deletePassenger?passengerId=${passenger.getPassengerId()}"><button type="button" class="btnDefault btnDelete">Delete</button></a>
					<a href="/passengers"><button type="button" class="btnDefault btnCancel">Cancel</button></a>
				</core:if>
			</form:form>
			<div class="listBodyContainer">
				<!-- <div class="listHeaderContainer">
					<table class="dbList">
						<thead>
							<tr>
								<th>Passenger id</th>
								<th>Name</th>
								<th>Email</th>
								<th>Phone</th>
								<th>Gender</th>
								<th>DOB</th>
								<th>Address</th>
								<th></th>
							</tr>
						</thead>
					</table>
				</div> -->
				<table class="dbList dbListData">
				<thead>
					<tr>
						<th>Passenger id</th>
						<th>Name</th>
						<th>Email</th>
						<th>Phone</th>
						<th>Gender</th>
						<th>DOB</th>
						<th>Address</th>
						<th></th>
					</tr>
				</thead>
					<tbody>
						<core:forEach items="${passengers}" var="passenger">
							<tr>
								<td>${passenger.getPassengerId()}</td>
								<td>${passenger.getFirstName()} ${passenger.getLastName()}</td>
								<td>${passenger.getEmail()}</td>
								<td>${passenger.getPhoneNum()}</td>
								<td>${passenger.getGender()}</td>
								<td>${passenger.getDob()}</td>
								<td>
									${passenger.getAddress().getAddressLine1()} ${passenger.getAddress().getAddressLine2()}<br>
									${passenger.getAddress().getCity()}, ${passenger.getAddress().getState()}<br>
									${passenger.getAddress().getCountry()}<br>
									${passenger.getAddress().getZipCode()}
								</td>
								<td><a href="updatePassenger?passengerId=${passenger.getPassengerId()}"><img src="${pageContext.request.contextPath}/img/pen-to-square-solid.svg" /></a></td>
							</tr>
						</core:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<%@ include file="footer.jsp" %>
	</div>
</div>
</body>
</html>