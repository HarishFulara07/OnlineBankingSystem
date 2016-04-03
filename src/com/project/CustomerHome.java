package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/customerhome")
public class CustomerHome extends HttpServlet {
	/*
	 * This is customer's home page after he gets logged in.
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Long account_number = LoginAuthenticationStep1.account_number;
		String first_name = null;
		String last_name = null;
		String gender = null;
		Date dob = null;
		String email = null;
		Long mobile_number = null;
		String address = null;
		String district = null;
		String city = null;
		Integer pincode = null;
		String state = null;
		Long balance = null;
		String ifsc_code = null;
		
		Connection connection = null;
		Statement statement = null; 
		ResultSet rs = null;
		
		String query = "SELECT * FROM customer_personal_info natural join customer_contact_info natural join"
				+ " customer_residence_info natural join customer_account_info"
				+ " where account_number = " + account_number;
		
		try {			
			connection = JDBCMySQLConnection.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			if(rs.next()) {
				first_name = rs.getString("first_name");
				last_name = rs.getString("last_name");
				gender = rs.getString("gender");
				dob = rs.getDate("dob");
				email = rs.getString("email");
				mobile_number = rs.getLong("mobile_number");
				address = rs.getString("address");
				district = rs.getString("district");
				city = rs.getString("city");
				pincode = rs.getInt("pincode");
				state = rs.getString("state");
				balance = rs.getLong("balance");
				ifsc_code = rs.getString("ifsc_code");
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
		
		out.println("<p>" + first_name  + "</p>");
		out.println("<p>" + last_name + "</p>");
		out.println("<p>" + gender + "</p>");
		out.println("<p>" + dob + "</p>");
		out.println("<p>" + email + "</p>");
		out.println("<p>" + mobile_number + "</p>");
		out.println("<p>" + address + "</p>");
		out.println("<p>" + district + "</p>");
		out.println("<p>" + city + "</p>");
		out.println("<p>" + pincode + "</p>");
		out.println("<p>" + state + "</p>");
		out.println("<p>" + account_number + "</p>");
		out.println("<p>" + balance + "</p>");
		out.println("<p>" + ifsc_code + "</p>");
	}
}