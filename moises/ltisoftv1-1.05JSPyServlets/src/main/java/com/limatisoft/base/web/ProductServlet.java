package com.limatisoft.base.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.limatisoft.ApplicationContext;
import com.limatisoft.base.model.Product;
import com.limatisoft.base.service.ProductCommandService;
import com.limatisoft.base.service.ProductQueryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProductQueryService productQueryService;
    private ProductCommandService productCommandService;
    public ProductServlet() {
        super();
        productQueryService = ApplicationContext.getInstance().getProductQueryService();
        productCommandService = ApplicationContext.getInstance().getProductCommandService();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		 switch(action) {
		 	case "list" :
		 		listProducts(request, response);
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

	private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Product> productList = productQueryService.findAll();
		request.setAttribute("productList", productList);
		request.getRequestDispatcher("/WEB-INF/jsp/products/productList.jsp").forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Product product = new Product();
		product.setCode("");
		product.setName("");
		product.setSalesPrice(BigDecimal.ZERO);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/WEB-INF/jsp/products/productForm.jsp").forward(request, response);
	}
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		Product product = productQueryService.findByCode(code);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/WEB-INF/jsp/products/productForm.jsp").forward(request, response);
	}
	
	private void showDeleteForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		Product product = productQueryService.findByCode(code);
		productCommandService.delete(product);
		response.sendRedirect("products?action=list");
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
		Product product = fromRequestToProduct(request);
		if(product.getId() == null) {
			productCommandService.saveUniqueProduct(product);
		}else {
			productCommandService.updateUniqueProduct(product);
		}
		//listProducts(request, response);
		response.sendRedirect("products?action=list"); 
	}

	private Product fromRequestToProduct(HttpServletRequest request) {
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String salesPrice = request.getParameter("salesPrice");
		Product product = new Product();
		product.setCode(code);
		product.setName(name);
		product.setSalesPrice(new BigDecimal(salesPrice));
		if(id != null && id.equals("") == false) {
			product.setId(Long.valueOf(id));
		}
		return product;
	}
}
