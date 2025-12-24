package vn.iotstar.controller.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.repository.impl.UserRepositoryImpl;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = "/auth/register")
public class RegisterController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final String REGISTER_PAGE = "/views/auth/register.jsp";
    public static final String LOGIN_URL = "/auth/login";
    public static final String WAITING_URL = "/auth/waiting";

    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            resp.sendRedirect(req.getContextPath() + WAITING_URL);
            return;
        }

        req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String fullName = req.getParameter("fullName");
        String phoneStr = req.getParameter("phone");
        String password = req.getParameter("password");

        String alertMsg = "";

        // Check rỗng
        if (email == null || email.isEmpty() ||
            fullName == null || fullName.isEmpty() ||
            phoneStr == null || phoneStr.isEmpty() ||
            password == null || password.isEmpty()) {

            alertMsg = "Vui lòng điền đầy đủ thông tin!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
            return;
        }

        // Parse phone
        int phone = 0;
        try {
            phone = Integer.parseInt(phoneStr);
        } catch (NumberFormatException e) {
            alertMsg = "Số điện thoại không hợp lệ!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
            return;
        }

        // Check email tồn tại
        if (userService.existsByEmail(email)) {
            alertMsg = "Email đã tồn tại!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
            return;
        }

        // Thực hiện đăng ký
        boolean isSuccess = userService.register(email, fullName, phone, password);

        if (isSuccess) {
            resp.sendRedirect(req.getContextPath() + LOGIN_URL);
        } else {
            alertMsg = "Đăng ký thất bại! Vui lòng thử lại.";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
        }
    }
}
