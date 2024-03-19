<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Airlines</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/airlines.css">
<script src="${pageContext.request.contextPath}/js/formPage.js"></script>
</head>
<body>
<%@ include file="navBar.jsp" %>
<div class="container">
	<%@ include file="sideNav.jsp" %>
	<div class="mainContainer">
		<div class="main airlines">
			<%@ include file="breadcrumbs.jsp" %>
			<h1>Airlines</h1>
			<form:errors path="airlines.*" cssStyle="color:red;" />
			<form:form action="saveAirline" method="POST" modelAttribute="airlines">
				<div class="formSection">
					<form:input type="number" path="airlinesId" value="${airlines.getAirlinesId()}" placeholder="airlines id" hidden="true"/>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="airlinesName"><img src="${pageContext.request.contextPath}/img/signature-solid.svg" />
						<form:input path="airlinesName" value="${airlines.getAirlinesName()}" placeholder="Airline name" />
					</form:label>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="airlinesCode"><img src="${pageContext.request.contextPath}/img/list-ol-solid.svg" />
						<form:input path="airlinesCode" value="${airlines.getAirlinesCode()}" placeholder="Airline code" />
					</form:label>
				</div>
				<core:if test="${!isUpdate}">
					<input type="submit" value="Submit" />
				</core:if>
				<core:if test="${isUpdate}">
					<input type="submit" value="Update" class="btnUpdate" />
					<a href="deleteAirlines?airlinesId=${airlines.getAirlinesId()}"><button type="button" class="btnDefault btnDelete">Delete</button></a>
					<a href="/airlines"><button type="button" class="btnDefault btnCancel">Cancel</button></a>
				</core:if>
			</form:form>
			
			<div class="listBodyContainer">
				<!-- <div class="listHeaderContainer">
					<table class="dbList">
						<thead>
							<tr>
								<th>Airlines id</th>
								<th>Airlines name</th>
								<th>Airlines code</th>
								<th>Flights</th>
								<th></th>
							</tr>
						</thead>
					</table>
				</div> -->
				<table class="dbList dbListData">
				<thead>
					<tr>
						<th><a href="airlines?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=airlinesId" class="sortHeader ${sortedBy == 'airlinesId' ? 'active' : ''}">Airlines id</a></th>
						<th><a href="airlines?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=airlinesName" class="sortHeader ${sortedBy == 'airlinesName' ? 'active' : ''}">Airlines name</a></th>
						<th><a href="airlines?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=airlinesCode" class="sortHeader ${sortedBy == 'airlinesCode' ? 'active' : ''}">Airlines code</a></th>
						<th>Flights</th>
						<th></th>
					</tr>
				</thead>
					<tbody>
						<core:forEach items="${allAirlines}" var="airline">
							<tr>
								<td>${airline.getAirlinesId()}</td>
								<td>${airline.getAirlinesName()}</td>
								<td>${airline.getAirlinesCode()}</td>
								<td>
									<core:forEach items="${airline.getFlights()}" var="flight"  varStatus="status">
										<span>${flight.getFlightNumber()}${not status.last ? ', ' : ''}</span>
									</core:forEach>
								</td>
								<td><a href="updateAirlines?airlinesId=${airline.getAirlinesId()}"><img src="${pageContext.request.contextPath}/img/pen-to-square-solid.svg" /></a></td>
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