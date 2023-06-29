<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div>
		<div>
			<h2>FooBar University</h2>
		</div>
	</div>

	<div>
		<div>
			<!-- Add student area -->
			<input type="button" value="Add Student"
				onclick="window.location.href='add-student-form.jsp';return false;"
				class="add-student-button">

			<!--Search students area  -->
			<form action="StudentControllerServlet" method="POST">
				<input type="hidden" name = "command" value="SEARCH"> Search Student: <input
					type="text" name="searchByName" /> <input type="submit"
					value="Search" class="add-student-button">
			</form>
			<table border="2">
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				<c:forEach var="student" items="${STUDENT_LIST}">
					<c:url var="updateLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="studentId" value="${student.id}"></c:param>
					</c:url>
					<c:url var="deleteLink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="studentId" value="${student.id}"></c:param>
					</c:url>
					<tr>
						<td>${student.firstName}</td>
						<td>${student.lastName}</td>
						<td>${student.email}</td>
						<td><a href="${updateLink}">Update</a> | <a
							href="${deleteLink}"
							onclick="if(!(confirm('Are you sure from deleting the student?')))return false;">Delete</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>