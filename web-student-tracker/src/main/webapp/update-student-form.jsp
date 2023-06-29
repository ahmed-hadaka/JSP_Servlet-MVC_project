<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Student Form</title>

<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>FooBar University</h2>
		</div>
	</div>

	<div id="container">
		<h3>Update Student</h3>

		<form action="StudentControllerServlet" method="POST">

			<input type="hidden" name="command" value="UPDATE" /> <input
				type="hidden" name="studentId" value="${STUDENT_UPDATE.id}" />

			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName"
							value="${STUDENT_UPDATE.firstName}" /></td>
					</tr>

					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName"
							value="${STUDENT_UPDATE.lastName}" /></td>
					</tr>

					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email"
							value="${STUDENT_UPDATE.email}" /></td>
					</tr>

					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>

				</tbody>
			</table>
		</form>

		<div style="clear: both;"></div>

		<p>
			<a href="StudentControllerServlet">Back to List</a>
		</p>
	</div>

</body>
</html>