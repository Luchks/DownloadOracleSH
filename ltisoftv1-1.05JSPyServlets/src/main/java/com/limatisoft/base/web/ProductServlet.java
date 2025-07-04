package com.limatisoft.base.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.limatisoft.ApplicationContext;
import com.limatisoft.base.model.Product;
import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.repository.ProductTypeRepository;
import com.limatisoft.base.repository.jdbc.ProductTypeJdbcRepository;
import com.limatisoft.base.service.ProductCommandService;
import com.limatisoft.base.service.ProductQueryService;
import com.limatisoft.base.service.ProductTypeQueryService;

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
	private ProductTypeQueryService productTypeService;

	public ProductServlet() {
		super();
		productQueryService = ApplicationContext.getInstance().getProductQueryService();
		productCommandService = ApplicationContext.getInstance().getProductCommandService();

		productTypeService = ApplicationContext.getInstance().getProductTypeQueryService();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action) {
			case "list":
				listProducts(request, response);
				break;
			case "new":
				showNewForm(request, response);
				break;
			case "edit":
				showEditForm(request, response);
				break;
			case "delete":
				deleteProduct(request, response);
				break;
			default:
				response.sendRedirect("products?action=list");
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

		List<ProductType> tipos = productTypeService.findAll();
		request.setAttribute("tiposProducto", tipos);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/WEB-INF/jsp/products/productForm.jsp").forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		Product product = productQueryService.findByCode(code);

		List<ProductType> tipos = productTypeService.findAll();
		request.setAttribute("tiposProducto", tipos);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/WEB-INF/jsp/products/productForm.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");

	    if ("save".equals(action)) {
	        Product product = fromRequestToProduct(request);

	        try {
	            if (product.getId() == null) {
	                productCommandService.saveUniqueProduct(product);
	            } else {
	                productCommandService.updateUniqueProduct(product);
	            }
	            response.sendRedirect("products?action=list&success=true");

	        } catch (com.limatisoft.exceptions.CodeUniqueException e) {
	
	            List<ProductType> tipos = productTypeService.findAll();
	            request.setAttribute("tiposProducto", tipos);
	            request.setAttribute("product", product);
	            request.setAttribute("errorMessage", e.getMessage());

	            request.getRequestDispatcher("/WEB-INF/jsp/products/productForm.jsp").forward(request, response);
	        }
	    }
	}


	private void saveForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Product product = fromRequestToProduct(request);
		if (product.getId() == null) {
			productCommandService.saveUniqueProduct(product);
		} else {
			productCommandService.updateUniqueProduct(product);
		}
		response.sendRedirect("products?action=list");
	}

	private Product fromRequestToProduct(HttpServletRequest request) {
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String salesPrice = request.getParameter("salesPrice");
		String productTypeCode = request.getParameter("productTypeCode");

		Product product = new Product();
		product.setCode(code);
		product.setName(name);
		product.setSalesPrice(new BigDecimal(salesPrice));

		if (id != null && !id.equals("")) {
			product.setId(Long.valueOf(id));
		}

		// Recuperar el tipo de producto según el código seleccionado
		if (productTypeCode != null && !productTypeCode.isEmpty()) {
			ProductTypeRepository repo = ApplicationContext.getInstance().getProductTypeRepository();
			ProductType type = productTypeService.findByCode(productTypeCode);
			product.setProductType(type);
		}

		return product;
	}


	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		if (code != null && !code.isEmpty()) {
			try {
				productCommandService.deleteUniqueProduct(code);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		response.sendRedirect("products?action=list");
	}
}
