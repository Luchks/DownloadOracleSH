package com.limatisoft.base.web;

import java.io.IOException;
import java.util.List;

import com.limatisoft.ApplicationContext;
import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.service.ProductTypeCommandService;
import com.limatisoft.base.service.ProductTypeQueryService;
import com.limatisoft.exceptions.CodeUniqueException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product-types")
public class ProductTypeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductTypeQueryService productTypeQueryService;
    private final ProductTypeCommandService productTypeCommandService;

    public ProductTypeServlet() {
        this.productTypeQueryService = ApplicationContext.getInstance().getProductTypeQueryService();
        this.productTypeCommandService = ApplicationContext.getInstance().getProductTypeCommandService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty() || "list".equalsIgnoreCase(action)) {
            listProductTypes(request, response);
        } else if ("new".equalsIgnoreCase(action)) {
            ProductType emptyType = new ProductType();
            request.setAttribute("productType", emptyType);
            request.getRequestDispatcher("/WEB-INF/jsp/products/productTypeForm.jsp").forward(request, response);
        } else if ("edit".equalsIgnoreCase(action)) {
            showEditForm(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            deleteProductType(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Acción no válida");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("save".equalsIgnoreCase(action)) {
            saveProductType(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Acción no válida");
        }
    }

    private void listProductTypes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ProductType> typeList = productTypeQueryService.findAll();
        request.setAttribute("typeList", typeList);
        request.getRequestDispatcher("/WEB-INF/jsp/products/productTypeList.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code != null && !code.trim().isEmpty()) {
            ProductType productType = productTypeQueryService.findByCode(code);
            if (productType != null) {
                request.setAttribute("productType", productType);
                request.getRequestDispatcher("/WEB-INF/jsp/products/productTypeForm.jsp").forward(request, response);
                return;
            }
        }

        response.sendRedirect("product-types?action=list");
    }

    private void saveProductType(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String code = request.getParameter("code");
        String name = request.getParameter("name");

        ProductType productType = new ProductType();
        if (idParam != null && !idParam.isEmpty()) {
            productType.setId(Long.parseLong(idParam));
        }
        productType.setCode(code);
        productType.setName(name);

        try {
            if (productType.getId() != null) {
                productTypeCommandService.updateUniqueProductType(productType);
            } else {
                productTypeCommandService.saveUniqueProductType(productType);
            }
            response.sendRedirect("product-types?action=list");
        } catch (CodeUniqueException e) {
            request.setAttribute("productType", productType);
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/products/productTypeForm.jsp").forward(request, response);
        }
    }

    private void deleteProductType(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code != null && !code.trim().isEmpty()) {
            productTypeCommandService.deleteByCode(code);
        }

        response.sendRedirect("product-types?action=list");
    }
}
