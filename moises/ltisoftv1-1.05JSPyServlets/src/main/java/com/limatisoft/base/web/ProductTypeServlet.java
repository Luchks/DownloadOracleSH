package com.limatisoft.base.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.limatisoft.ApplicationContext;
import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.service.ProductTypeCommandService;
import com.limatisoft.base.service.ProductTypeQueryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/productTypes")
public class ProductTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProductTypeQueryService productTypeQueryService;
    private ProductTypeCommandService productTypeCommandService;
    public ProductTypeServlet() {
        super();
        productTypeQueryService = ApplicationContext.getInstance().getProductTypeQueryService();
        productTypeCommandService = ApplicationContext.getInstance().getProductTypeCommandService();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		 switch(action) {
		 	case "list" :
		 		listProductTypes(request, response);
		 		break;
		 	case "new" :
		 		showNewForm(request, response);
		 		break;
		 	case "edit" :
		 		showEditForm(request, response);
		 		break;
		 	case "delete" :
		 		showDeleteForm(request, response);
		 		break;
		 }
	}

	private void listProductTypes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ProductType> productTypeList = productTypeQueryService.findAll();
		request.setAttribute("productTypeList", productTypeList);
		request.getRequestDispatcher("/WEB-INF/jsp/productTypes/productTypeList.jsp").forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductType productType = new ProductType();
		productType.setCode("");
		productType.setName("");
		request.setAttribute("productType", productType);
		request.getRequestDispatcher("/WEB-INF/jsp/productTypes/productTypeForm.jsp").forward(request, response);
	}
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		ProductType productType = productTypeQueryService.findByCode(code);
		request.setAttribute("productType", productType);
		request.getRequestDispatcher("/WEB-INF/jsp/productTypes/productTypeForm.jsp").forward(request, response);
	}
	
	private void showDeleteForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		ProductType productType = productTypeQueryService.findByCode(code);
		productTypeCommandService.delete(productType);
		response.sendRedirect("productTypes?action=list");
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		 switch(action) {
		 	case "save" :
		 		saveForm(request, response);
		 		break;
		 }
	}
	
	private void saveForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductType productType = fromRequestToProductType(request);
		if(productType.getId() == null) {
			productTypeCommandService.saveUniqueProductType(productType);
		}else {
			productTypeCommandService.updateUniqueProductType(productType);
		}
		//listProductTypes(request, response);
		response.sendRedirect("productTypes?action=list"); 
	}

	private ProductType fromRequestToProductType(HttpServletRequest request) {
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		ProductType productType = new ProductType();
		productType.setCode(code);
		productType.setName(name);
		if(id != null && id.equals("") == false) {
			productType.setId(Long.valueOf(id));
		}
		return productType;
	}
}
