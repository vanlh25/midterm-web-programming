package vn.iotstar.controller.admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

@WebServlet("/admin/profile")
public class ProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();
    private static final String LOGIN_URL = "/auth/login";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + LOGIN_URL);
            return;
        }

        User user = (User) session.getAttribute("account");
        req.setAttribute("user", user);

        // Forward tới JSP profile theo role
        String profileJsp = (user.getIsAdmin() != null && user.getIsAdmin()) ?
                "/views/admin/profile.jsp" : "/views/user/profile.jsp";

        req.getRequestDispatcher(profileJsp).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + LOGIN_URL);
            return;
        }

        User user = (User) session.getAttribute("account");
        user.setFullname(req.getParameter("fullName"));

        String phoneStr = req.getParameter("phone");
        if(phoneStr != null && !phoneStr.isEmpty()) {
            try {
                user.setPhone(Integer.parseInt(phoneStr));
            } catch(NumberFormatException e) {
                // Nếu không hợp lệ thì không cập nhật phone
            }
        }

        // Lưu thay đổi
        userService.save(user);
        session.setAttribute("account", user);

        // Chuyển hướng về home theo isAdmin
        String redirectUrl = (user.getIsAdmin() != null && user.getIsAdmin()) ?
                req.getContextPath() + "/admin/home" :
                req.getContextPath() + "/user/home";

        resp.sendRedirect(redirectUrl);
    }
}
