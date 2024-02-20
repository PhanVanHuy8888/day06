package phanvanhuy.vn.servlet;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


import phanvanhuy.vn.beans.Product;
import phanvanhuy.vn.conn.ConnectionUtils;
import phanvanhuy.vn.utils.ProductUtils;

import java.sql.*;

/**
 * Servlet implementation class productEditServlet
 */
@WebServlet("/productEdit")
public class ProductEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String errorString = null;
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/productEdit.jsp");
		String code = (String) request.getParameter("code");
		if(code == null || code == "") {
			errorString = "Bạn chưa chọn sản phẩm cần sửa";
			request.setAttribute("errorString", errorString);
			dispatcher.forward(request, response);
			return;
		}
		Connection conn = null;
		Product product = null;
		errorString = null;
		try {
			conn = ConnectionUtils.getMSSQLConnection();
			product = ProductUtils.findProduct(conn, code);
			if(product == null) {
				errorString = "Không tìm thấy sản phẩm có mã" + code;
			}
		} catch(Exception e ) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		if(errorString != null || product == null) {
			request.setAttribute("errorString", errorString);
			dispatcher.forward(request, response);
			return;
		}
		request.setAttribute("errorString", errorString);
		request.setAttribute("product", product);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String errorString = null;
		String code = (String) request.getParameter("code");
		String name = (String) request.getParameter("name");
		String priceStr = (String) request.getParameter("price");
		float price = 0;
		try {
			price = Float.parseFloat(priceStr);
		} catch(Exception e) {
			errorString = e.getMessage();
		}
		Product product = new Product(code, name, price);
		
		if(errorString != null) {
			request.setAttribute("errorString", errorString);
			request.setAttribute("product", product);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/productEdit.jsp");
			dispatcher.forward(request, response);
		}
		
		Connection conn = null;
		try {
			conn = ConnectionUtils.getMSSQLConnection();
			ProductUtils.updateProduct(conn, product);
			response.sendRedirect(request.getContextPath() + "/productList");
			
		}catch(Exception e) {
			e.printStackTrace();
			errorString = e.getMessage();
			request.setAttribute("errorString", errorString);
			request.setAttribute("product", product);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/productEdit.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}
