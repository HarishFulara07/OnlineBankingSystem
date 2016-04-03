package com.project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Registration
 */

@WebServlet("/registration2")

public class RegistrationStep2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Long account_number;
	String first_name;
	String last_name;
	String gender;
	String dob;
	String email;
	Long mobile_number;
	String address;
	String district;
	String city;
	Integer pincode;
	String state;
	Long balance;
	String ifsc_code;
	String username;
	String password;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		account_number = RegistrationStep1.account_number;
		first_name = RegistrationStep1.first_name;
		last_name = RegistrationStep1.last_name;
		gender = RegistrationStep1.gender;
		dob = RegistrationStep1.dob;
		email = RegistrationStep1.email;
		mobile_number = RegistrationStep1.mobile_number;
		address = RegistrationStep1.address;
		district = RegistrationStep1.district;
		city = RegistrationStep1.city;
		pincode = RegistrationStep1.pincode;
		state = RegistrationStep1.state;
		balance = 100000L;
		ifsc_code = RegistrationStep1.ifsc_code;
		username = RegistrationStep1.username;
		password = RegistrationStep1.password;
		Connection connection = null;
		Statement statement = null; 
		
		String query1 = "INSERT INTO customer_personal_info VALUES(" + account_number + ", '" + first_name + "', '"
						+ last_name  + "', '" + gender + "', '" + dob + "')";
		
		String query2 = "INSERT INTO customer_contact_info VALUES(" + account_number + ", '" + email + "', " 
						+ mobile_number + ")";
		
		String query3 = "INSERT INTO customer_residence_info VALUES(" + account_number + ", '" + address + "', '" + district + "', '" + city + "', " + pincode + ", '" + state + "')";
		
		String query4 = "INSERT INTO customer_account_info VALUES(" + account_number + ", " + balance + ", '" 
				+ ifsc_code + "')";
		
		String query5 = "INSERT INTO customer_login_info VALUES(" + account_number + ", '" + username + "', " 
				+ "SHA1('" + password + "'))";
		
		//System.out.println(query1);
		
		try {			
			connection = JDBCMySQLConnection.getConnection();
			statement = connection.createStatement();
			
			statement.executeUpdate(query1);
			statement.executeUpdate(query2);
			statement.executeUpdate(query3);
			statement.executeUpdate(query4);
			statement.executeUpdate(query5);
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
		
		response.sendRedirect("sendregemail");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}