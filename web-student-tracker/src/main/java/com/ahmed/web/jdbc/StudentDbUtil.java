package com.ahmed.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	DataSource dataSource;

	public StudentDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Student> getStudents() throws SQLException {
		List<Student> students = new ArrayList<>();

		Connection con = null;
		Statement stat = null;
		ResultSet result = null;
		try {
			// setup connection
			con = dataSource.getConnection();

			// setup statement
			String query = "SELECT * FROM student;";
			stat = con.createStatement();

			// setup result set
			result = stat.executeQuery(query);

			// add data to our list
			while (result.next()) {
				int id = result.getInt("id");
				String fName = result.getString("first_name");
				String lName = result.getString("last_name");
				String email = result.getString("email");

				Student student = new Student(id, fName, lName, email);
				students.add(student);
			}

			return students;
		} finally {
			close(con, result, stat);
		}
	}

	public void addStudent(Student student) throws SQLException {

		Connection connection = null;
		PreparedStatement preStatement = null;

		try {
			connection = dataSource.getConnection();

			String query = "INSERT INTO student (first_name, last_name, email) VALUES(?, ?, ?)";

			preStatement = connection.prepareStatement(query);

			preStatement.setString(1, student.getFirstName());
			preStatement.setString(2, student.getLastName());
			preStatement.setString(3, student.getEmail());

			preStatement.execute();

		} finally {
			close(connection, null, preStatement);
		}
	}

	public Student getStudent(String studentId) throws SQLException {
		// set variables
		Connection connection = null;
		PreparedStatement preStatement = null;
		ResultSet resultSet = null;
		Student student = null;
		int id;

		try {

			id = Integer.parseInt(studentId);

			// get connection pool
			connection = dataSource.getConnection();

			// write query
			String querey = "SELECT * FROM student WHERE id = ?";

			// get statement
			preStatement = connection.prepareStatement(querey);
			preStatement.setInt(1, id);

			// execute query
			resultSet = preStatement.executeQuery();

			// get our student from resultSet
			if (resultSet.next()) {
				int sId = id;
				String fName = resultSet.getString("first_name");
				String lName = resultSet.getString("last_name");
				String email = resultSet.getString("email");

				student = new Student(sId, fName, lName, email);
			}

			return student;

		} finally {
			// close our connection
			close(connection, resultSet, preStatement);
		}
	}

	public void updateStudent(Student student) throws SQLException {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();

			// create SQL update statement
			String sql = "UPDATE student " + "SET first_name=?, last_name=?, email=? " + " WHERE id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, student.getFirstName());
			myStmt.setString(2, student.getLastName());
			myStmt.setString(3, student.getEmail());
			myStmt.setInt(4, student.getId());

			// execute SQL statement
			myStmt.execute();

		} finally {
			close(myConn, null, myStmt);
		}
	}

	public void deleteStudent(int id) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();

			// create SQL update statement
			String sql = "DELETE FROM student " + " WHERE id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, id);

			// execute SQL statement
			myStmt.execute();

		} finally {
			close(myConn, null, myStmt);
		}

	}

	public List<Student> searchStudents(String name) throws SQLException {
		// initialize necessary objects
		Connection connection = null;
		PreparedStatement preStatement = null;
		ResultSet resultSet = null;
		List<Student> res = new ArrayList<>();
		try {
			// establish connection pool
			connection = dataSource.getConnection();

			if (name != null && name.trim().length() > 0) {
				// write our query to fetch the matched students
				String query = "SELECT * FROM student WHERE LOWER(first_name) LIKE ? OR LOWER(last_name) LIKE ? ";

				preStatement = connection.prepareStatement(query);

				preStatement.setString(1, "%" + name.toLowerCase() + "%");
				preStatement.setString(2, "%" + name.toLowerCase() + "%");
			} else {
				String query = "SELECT * FROM student ORDER BY last_name ";

				preStatement = connection.prepareStatement(query);
			}

			// execute query, add them to a list, return the list
			resultSet = preStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String fName = resultSet.getString("first_name");
				String lName = resultSet.getString("last_name");
				String email = resultSet.getString("email");

				Student student = new Student(id, fName, lName, email);
				res.add(student);
			}
			return res;

		} finally {
			close(connection, resultSet, preStatement);
		}
	}

	private void close(Connection con, ResultSet result, Statement stat) {
		try {
			if (con != null)
				con.close();
			if (result != null)
				result.close();
			if (stat != null)
				stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
