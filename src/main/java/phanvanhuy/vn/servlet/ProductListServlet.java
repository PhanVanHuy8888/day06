package phanvanhuy.vn.servlet;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import phanvanhuy.vn.beans.Product;
import phanvanhuy.vn.conn.ConnectionUtils;
import phanvanhuy.vn.utils.ProductUtils;

import java.sql.*;
import java.util.*;

@WebServlet("/productList")
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		String errorString = null;
		List<Product> list = null;
		try {
			conn = ConnectionUtils.getMSSQLConnection();
			
			try {
				list = ProductUtils.queryProduct(conn);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
			request.setAttribute("errorString", errorString);
			request.setAttribute("productList", list);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/productList.jsp");
			dispatcher.forward(request, response);
		} catch	(ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
			errorString = e1.getMessage();
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/productList.jsp");
			request.setAttribute("errorString", errorString);
			dispatcher.forward(request, response);
		}
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
