package vn.iotstar.controller.admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;

@WebServlet("/admin/home")
public class AdminHomeController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static final String ADMIN_HOME_PAGE = "/views/admin/home.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

     // Lấy thông tin user từ session
        User user = (User) session.getAttribute("account");

        // Đặt tên đầy đủ và quyền admin lên request để sử dụng trong JSP
        req.setAttribute("fullname", user.getFullname());
        req.setAttribute("isAdmin", user.getIsAdmin());
        // Forward tới trang home admin
        req.getRequestDispatcher(ADMIN_HOME_PAGE).forward(req, resp);
    }
}
