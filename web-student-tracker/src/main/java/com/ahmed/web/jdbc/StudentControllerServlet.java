package com.ahmed.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	StudentDbUtil studentDbUtil;

	// setup our connection pool
	@Resource(name = "jdbc/web_student_tracker")
	DataSource detaSource;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			studentDbUtil = new StudentDbUtil(detaSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String command = request.getParameter("command");

			if (command == null)
				command = "LIST";

			switch (command) {
			case "ADD":
				addStudent(request, response);
				break;

			case "LIST":
				listStudentData(request, response);
				break;

			case "LOAD":
				loadStudent(request, response);
				break;

			case "UPDATE":
				updateStudent(request, response);
				break;

			case "DELETE":
				deleteStudent(request, response);
				break;

			case "SEARCH":
				searchStudents(request, response);
				break;

			default:
				listStudentData(request, response);
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	private void searchStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get the name to search
		String name = request.getParameter("searchByName");

		// fetch the matched names from DB
		List<Student> students = studentDbUtil.searchStudents(name);

		request.setAttribute("STUDENT_LIST", students);

		// return these matched names to the view page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// specify the student
		int id = Integer.parseInt(request.getParameter("studentId"));

		// delete student from DB
		studentDbUtil.deleteStudent(id);

		// return to the view page
		listStudentData(request, response);

	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get form data
		int id = Integer.parseInt(request.getParameter("studentId"));
		String fName = request.getParameter("firstName");
		String lName = request.getParameter("lastName");
		String email = request.getParameter("email");

		// create new student
		Student student = new Student(id, fName, lName, email);

		// update in DB
		studentDbUtil.updateStudent(student);

		// back to view with the updated data
		// SEND AS REDIRECT to avoid multiple-browser reload issue
		// (Post/Redirect/Get)Web Design pattern
		response.sendRedirect(request.getContextPath() + "/StudentControllerServlet?command=LIST");

	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		// get student id
		String studentId = request.getParameter("studentId");

		// load student from DB
		Student student = studentDbUtil.getStudent(studentId);

		// set it as request's attribute
		request.setAttribute("STUDENT_UPDATE", student);

		// get dispatcher and forward it to the (view)update-student-form.jsp page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// fetch the attributes
		String fName = request.getParameter("firstName");
		String lName = request.getParameter("lastName");
		String email = request.getParameter("email");

		Student student = new Student(fName, lName, email);

		// update DB
		studentDbUtil.addStudent(student);

		// back to view with the updated data
		// SEND AS REDIRECT to avoid multiple-browser reload issue
		// (Post/Redirect/Get)Web Design pattern
		response.sendRedirect(request.getContextPath() + "/StudentControllerServlet?command=LIST");
	}

	private void listStudentData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get students data
		List<Student> students = studentDbUtil.getStudents();

		// add it to our request object as an attribute
		request.setAttribute("STUDENT_LIST", students);

		// get request dispatcher and forward the data to the view
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

}
