package com.project;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loginstep1")
public class LoginAuthenticationStep1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Long account_number = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		/*
		 * This interface with all methods for contacting a database.
		 * The connection object represents communication context, i.e., all communication with database
		   is through connection object only.
		 */
		Connection connection = null;
		/*
		 * You use objects created from this interface to submit the SQL statements to the database.
		 * Some derived interfaces accept parameters in addition to executing stored procedures.
		 */
		Statement statement = null; 
		/*
		 * ResultSet objects hold data retrieved from a database after you execute an SQL query using Statement objects.
		 * It acts as an iterator to allow you to move through its data.
		 */
		ResultSet rs = null;
		
		String query = "SELECT account_number FROM customer_login_info where username = '" + username + "' and password = SHA1('" + password + "')";
		
		try {			
			connection = JDBCMySQLConnection.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			if (rs.next()) {
				account_number = rs.getLong("account_number");
			}
			else
			{
				response.sendRedirect("login_fail.html");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					/*
					 * Closing the Connection object will also close Statement object as well.
					 * However, we should always explicitly close the Statement object to ensure proper cleanup.
					 */
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		response.sendRedirect("customerhome");
	}
}