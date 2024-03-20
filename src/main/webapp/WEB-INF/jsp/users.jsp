<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Users</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/users.css">
<script src="${pageContext.request.contextPath}/js/formPage.js"></script>
<script>
	$(document).ready(function() {
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
		<div class="main users">
			<%@ include file="breadcrumbs.jsp" %>
			<h1>Users</h1>
			<form:errors path="user.*" cssStyle="color:red;" />
			<form:form action="saveUser" method="POST" modelAttribute="user">
				<div class="formSection">
					<form:input type="number" path="userId" value="${user.getUserId()}" placeholder="user id" hidden="true"/>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="username"><img src="${pageContext.request.contextPath}/img/signature-solid.svg" />
						<form:input path="username" value="${user.getUsername()}" placeholder="Username" />
					</form:label>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="password"><img src="${pageContext.request.contextPath}/img/key-solid.svg" />
						<form:input path="password" value="${user.getPassword()}" placeholder="Password" />
					</form:label>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="email"><img src="${pageContext.request.contextPath}/img/envelope-solid.svg" id="emailIcon" />
						<form:input path="email" value="${user.getEmail()}" placeholder="Email" />
					</form:label>
				</div>
				<div class="formSection labeledInputSection">
					<form:label path="phoneNum"><img src="${pageContext.request.contextPath}/img/phone-flip-solid.svg" />
						<form:input path="phoneNum" value="${user.getPhoneNum()}" placeholder="123-456-7890" />
					</form:label>
				</div>
				<div class="formSection rolesSection">
					<core:forEach items="${roles}" var="role" >
						<core:if test="${userRoles.contains(role)}">
							<div class="checkboxContainer">
								<form:checkbox path="roles" value="${role.roleId}" checked="true"/>
								<div><span>${role.roleName}</span></div>
							</div>
						</core:if>
						<core:if test="${!userRoles.contains(role)}">
							<div class="checkboxContainer">
								<form:checkbox path="roles" value="${role.roleId}" />
								<div><span>${role.roleName}</span></div>
							</div>
						</core:if>
					</core:forEach>
				</div>
				<core:if test="${!isUpdate}">
					<input type="submit" value="Submit" />
				</core:if>
				<core:if test="${isUpdate}">
					<input type="submit" value="Update" class="btnUpdate" />
					<a href="deleteUser?userId=${user.getUserId()}"><button type="button" class="btnDefault btnDelete">Delete</button></a>
					<a href="/users"><button type="button" class="btnDefault btnCancel">Cancel</button></a>
				</core:if>
			</form:form>
			
			<div class="listBodyContainer">
				<!-- <div class="listHeaderContainer">
					<table class="dbList">
						<thead>
							<tr>
								<th>User id</th>
								<th>Username</th>
								<th>Email</th>
								<th>Phone</th>
								<th>Roles</th>
								<th></th>
							</tr>
						</thead>
					</table>
				</div> -->
				<table class="dbList dbListData">
				<thead>
					<tr>
						<th><a href="users?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=userId" class="sortHeader ${sortedBy == 'userId' ? 'active' : ''}">User id</a></th>
						<th><a href="users?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=username" class="sortHeader ${sortedBy == 'username' ? 'active' : ''}">Username</a></th>
						<th><a href="users?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=email" class="sortHeader ${sortedBy == 'email' ? 'active' : ''}">Email</a></th>
						<th><a href="users?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=phoneNum" class="sortHeader ${sortedBy == 'phoneNum' ? 'active' : ''}">Phone</a></th>
						<th><a href="users?pageNo=${pageNo}&pageSize=${pageSize}&sortedBy=roles" class="sortHeader ${sortedBy == 'roles' ? 'active' : ''}">Roles</a></th>
						<th></th>
					</tr>
				</thead>
					<tbody>
						<core:forEach items="${users}" var="user">
							<tr>
								<td>${user.getUserId()}</td>
								<td>${user.getUsername()}</td>
								<td>${user.getEmail()}</td>
								<td>${user.getPhoneNum()}</td>
								<td>
									<core:forEach items="${user.getRoles()}" var="role"  varStatus="status">
										<span>${role.getRoleName()}${not status.last ? ', ' : ''}</span>
									</core:forEach>
								</td>
								<td><a href="updateUser?userId=${user.getUserId()}"><img src="${pageContext.request.contextPath}/img/pen-to-square-solid.svg" /></a></td>
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
						out.println("<a class=\"page\" href=\"reservations?pageNo="+i+"&pageSize="+request.getAttribute("pageSize")+"&sortedBy="+request.getAttribute("sortedBy")+"\">"+(i+1)+"</a>");						
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