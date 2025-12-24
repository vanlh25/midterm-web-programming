package vn.iotstar.controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;

@WebServlet(urlPatterns = "/auth/waiting")
public class WaitingController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final String LOGIN_PAGE = "/views/auth/login.jsp";
    public static final String USER_HOME = "/user/home";
    public static final String ADMIN_HOME = "/admin/home";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            // Chưa login → về trang login
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            return;
        }

        // Lấy User từ session
        User user = (User) session.getAttribute("account");

        if (user.getIsAdmin() != null && user.getIsAdmin()) {
            // Admin
            resp.sendRedirect(req.getContextPath() + ADMIN_HOME);
        } else {
            // User bình thường
            resp.sendRedirect(req.getContextPath() + USER_HOME);
        }
    }
}
