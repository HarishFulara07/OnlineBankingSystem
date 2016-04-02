package com.project;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/*
 * Servlet implementation class Registration
 */

@WebServlet("/regupload2")

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)   // 50MB

public class RegUpload2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "OBSFiles";
	Long account_number;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		account_number = RegistrationStep1.account_number;
		
		//For image upload selected by user from his/her computer
		
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
        
        for (Part part : request.getParts())
        {
            String fileName = extractFileName(part);
            //System.out.println("filename: " + fileName);
            part.write(savePath + File.separator + fileName);
        }
		
		response.sendRedirect("registerpart2.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String extractFileName(Part part) 
    {
        String contentDisp = part.getHeader("content-disposition");
        //System.out.println("content: " + contentDisp);
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