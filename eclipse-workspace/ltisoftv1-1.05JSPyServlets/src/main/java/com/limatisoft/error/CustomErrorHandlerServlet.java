package com.limatisoft.error;

import java.io.IOException;

import com.limatisoft.exceptions.AuthException;
import com.limatisoft.exceptions.CodeRequiredException;
import com.limatisoft.exceptions.CodeUniqueException;
import com.limatisoft.exceptions.DataException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/error-handler")
public class CustomErrorHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CustomErrorHandlerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Throwable throwable = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
		 String errorMessage = throwable.getMessage(); 
		 if(throwable instanceof AuthException ) {
			request.setAttribute("errorMessage", errorMessage); 
			request.getRequestDispatcher("/login.jsp").forward(request, response); 
		 }else if(throwable instanceof CodeUniqueException 
				 || throwable instanceof CodeRequiredException
				 	|| throwable instanceof DataException){
			request.setAttribute("errorMessage", errorMessage); 
			request.getRequestDispatcher("/WEB-INF/jsp/products/productForm.jsp").forward(request, response); 
		 }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
