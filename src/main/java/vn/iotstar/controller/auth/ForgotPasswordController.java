package vn.iotstar.controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.repository.impl.UserRepositoryImpl;

@WebServlet("/auth/forgot-password")
public class ForgotPasswordController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final String FORGOT_PASSWORD_PAGE = "/views/auth/forgot-password.jsp";
    public static final String RESET_PASSWORD_URL = "/auth/reset-password";

    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.getRequestDispatcher(FORGOT_PASSWORD_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String alertMsg = "";

        if (email == null || email.trim().isEmpty()) {
            alertMsg = "Email không được để trống.";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(FORGOT_PASSWORD_PAGE).forward(req, resp);
            return;
        }

        if (!userService.existsByEmail(email)) {
            alertMsg = "Email không tồn tại trong hệ thống.";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(FORGOT_PASSWORD_PAGE).forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("resetEmail", email);

        resp.sendRedirect(req.getContextPath() + RESET_PASSWORD_URL);
    }
}
