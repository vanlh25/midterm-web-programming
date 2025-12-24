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

@WebServlet("/auth/reset-password")
public class ResetPasswordController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final String RESET_PASSWORD_PAGE = "/views/auth/reset-password.jsp";
    public static final String LOGIN_PAGE = "/views/auth/login.jsp";
    public static final String FORGOT_PASSWORD_URL = "/auth/forgot-password";

    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("resetEmail") == null) {
            resp.sendRedirect(req.getContextPath() + FORGOT_PASSWORD_URL);
            return;
        }

        req.getRequestDispatcher(RESET_PASSWORD_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("resetEmail") == null) {
            resp.sendRedirect(req.getContextPath() + LOGIN_PAGE);
            return;
        }

        String email = (String) session.getAttribute("resetEmail");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String alertMsg = "";

        if (password == null || confirmPassword == null || password.isEmpty() || confirmPassword.isEmpty()) {
            alertMsg = "Vui lòng nhập đầy đủ mật khẩu.";
        } else if (!password.equals(confirmPassword)) {
            alertMsg = "Passwords không trùng khớp!";
        } else if (password.length() < 6) {
            alertMsg = "Password phải có ít nhất 6 kí tự!";
        } else {
            boolean success = userService.updatePasswordByEmail(email, password);
            if (success) {
                alertMsg = "Reset password thành công!";
                session.removeAttribute("resetEmail");
                req.setAttribute("alert", alertMsg);
                req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
                return;
            } else {
                alertMsg = "System error!";
            }
        }

        req.setAttribute("alert", alertMsg);
        req.getRequestDispatcher(RESET_PASSWORD_PAGE).forward(req, resp);
    }
}
