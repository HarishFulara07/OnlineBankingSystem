package com.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Servlet implementation class RegistrationStep1
 */

@WebServlet("/registration1")
public class RegistrationStep1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Long account_number;
	static String first_name;
	static String last_name;
	static String gender;
	static String dob;
	static String email;
	static Long mobile_number;
	static String address;
	static String district;
	static String city;
	static Integer pincode;
	static String state;
	static Long balance;
	static String ifsc_code;
	static String username;
	static String password;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		account_number = Long.valueOf(request.getParameter("acno"));
		first_name = request.getParameter("firstname");
		last_name = request.getParameter("lastname");
		gender = request.getParameter("gender");
		dob = request.getParameter("dob");
		email = request.getParameter("email");
		mobile_number = Long.valueOf(request.getParameter("mobile"));
		address = request.getParameter("address");
		district = request.getParameter("district");
		city = request.getParameter("city");
		pincode = Integer.valueOf(request.getParameter("pincode"));
		state = request.getParameter("state");
		balance = 100000L;
		ifsc_code = request.getParameter("branch");
		username = request.getParameter("username");
		password = request.getParameter("password");
		
		/*
		 * create img folder and place images in it if it is not already present in home folder
		 */
		String savePath = System.getProperty("user.home") + File.separator + "img";
		String getPath = getServletContext().getRealPath("/img");
	    
	    File ImgSaveDir = new File(savePath);
	    File ImgGetDir = new File(getPath);
	    
	    if(!ImgSaveDir.exists())
	    {
	    	ImgSaveDir.mkdir();
	    	
	    	String files[] = ImgGetDir.list();
		    //System.out.println(getServletContext().getRealPath("/img"));
		    //System.out.println("hi");
		    
		    for(String file : files)
		    {
		    	InputStream is = null;
			    OutputStream os = null;
			    File saveFile = new File(savePath + File.separator + file);
			    File getFile = new File(getPath + File.separator + file);
			    
			    try {
			        is = new FileInputStream(getFile);
			        os = new FileOutputStream(saveFile);
			        byte[] buffer = new byte[1024];
			        int length;
			        while ((length = is.read(buffer)) > 0) {
			            os.write(buffer, 0, length);
			        }
			    } finally {
			        is.close();
			        os.close();
			    }
		    }
	    }
	    
		response.sendRedirect("registerpart2.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
