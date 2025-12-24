package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//annotation
@WebServlet("/home")  // URL /home sẽ được xử lý bởi servlet này
public class HomeController extends HttpServlet{
	
	public static final String WEB_HOME_PAGE = "/views/web/home.jsp";
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException{
		// Chuyển tiếp tới trang home.jsp
        req.getRequestDispatcher(WEB_HOME_PAGE).forward(req, resp);
	}
}
