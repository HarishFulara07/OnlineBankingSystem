package com.project;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/registration")

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)   // 50MB

public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "OBSFiles";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long account_number = Long.valueOf(request.getParameter("acno"));
		String first_name = request.getParameter("firstname");
		String last_name = request.getParameter("lastname");
		String gender = request.getParameter("gender");
		String dob = request.getParameter("dob");
		String email = request.getParameter("email");
		Long mobile_number = Long.valueOf(request.getParameter("mobile"));
		String house_number = request.getParameter("hno");
		String street = request.getParameter("street");
		String district = request.getParameter("district");
		String city = request.getParameter("city");
		Integer pincode = Integer.valueOf(request.getParameter("pincode"));
		String state = request.getParameter("state");
		Long balance = 100000L;
		String ifsc_code = request.getParameter("branch");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Connection connection = null;
		Statement statement = null; 
		
		String query1 = "INSERT INTO customer_personal_info VALUES(" + account_number + ", '" + first_name + "', '"
						+ last_name  + "', '" + gender + "', '" + dob + "')";
		
		String query2 = "INSERT INTO customer_contact_info VALUES(" + account_number + ", '" + email + "', " 
						+ mobile_number + ")";
		
		String query3 = "INSERT INTO customer_residence_info VALUES(" + account_number + ", '" + house_number + "', '"
				+ street  + "', '" + district + "', '" + city + "', " + pincode + ", '" + state + "')";
		
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
		
		/*For image upload*/
		
		String savePath = System.getProperty("user.home") + File.separator + SAVE_DIR;
	       
        File fileSaveDir = new File(savePath);
        
        if (!fileSaveDir.exists()) 
        {
            fileSaveDir.mkdir();
        }
        
        savePath = savePath + File.separator + account_number;
        
        File ImgSaveDir = new File(savePath);
        
        if (!ImgSaveDir.exists()) 
        {
            ImgSaveDir.mkdir();
        }
        
        System.out.print(ImgSaveDir.canWrite());
        for (Part part : request.getParts()) 
        {
            String fileName = extractFileName(part);
            part.write(savePath + File.separator + fileName);
        }
    
		response.sendRedirect("index.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String extractFileName(Part part) 
    {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) 
        {
            if (s.trim().startsWith("filename")) 
            {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
