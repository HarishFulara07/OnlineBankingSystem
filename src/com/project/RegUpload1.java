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

/**
 * Servlet implementation class Registration
 */

@WebServlet("/regupload1")

public class RegUpload1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "OBSFiles";
	Long account_number;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		account_number = RegistrationStep1.account_number;
		
		/*
		 * Images which are selected by the user from our set of images
		 */
		String[] selected = null;
		selected = request.getParameterValues("selector");
		//System.out.println(selected == null);
		
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
        
		if(selected != null)
		{	
			for(int i = 0; i < selected.length; ++i) 
			{
				InputStream is = null;
			    OutputStream os = null;
			    String getPath = System.getProperty("user.home") + File.separator + "img";
			    File ImgSaveDir2 = new File(savePath + File.separator + selected[i]);
			    File ImgGetDir = new File(getPath + File.separator + selected[i]);
			    try {
			        is = new FileInputStream(ImgGetDir);
			        os = new FileOutputStream(ImgSaveDir2);
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
