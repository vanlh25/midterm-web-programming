package vn.iotstar.controller.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = {"/admin/users", "/admin/user-edit", "/admin/user-delete"})
public class UserController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();
    private static final int PAGE_SIZE = 10; // số record trên mỗi trang

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        User admin = (User) session.getAttribute("account");
        if(admin.getIsAdmin() == null || !admin.getIsAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/user/home"); // Không phải admin
            return;
        }

        String uri = req.getRequestURI();
        if(uri.endsWith("/admin/users")) {
            handleList(req, resp);
        } else if(uri.endsWith("/admin/user-edit")) {
            handleEditGet(req, resp);
        } else if(uri.endsWith("/admin/user-delete")) {
            handleDelete(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        User admin = (User) session.getAttribute("account");
        if(admin.getIsAdmin() == null || !admin.getIsAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/user/home");
            return;
        }

        String uri = req.getRequestURI();
        if(uri.endsWith("/admin/user-edit")) {
            handleEditPost(req, resp);
        }
    }

    // ======= Xử lý danh sách người dùng =======
    private void handleList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int page = 1;
        String pageParam = req.getParameter("page");
        if(pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch(NumberFormatException e) {
                page = 1;
            }
        }

        long totalUsers = userService.count();
        int totalPages = (int) Math.ceil((double) totalUsers / PAGE_SIZE);

        List<User> users = userService.findAll(page, PAGE_SIZE);

        req.setAttribute("users", users);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/views/admin/user-list.jsp").forward(req, resp);
    }

    // ======= Xử lý xem/ sửa user =======
    private void handleEditGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if(idParam == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            User user = userService.findById(id);
            if(user == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/users");
                return;
            }

            req.setAttribute("user", user);
            req.getRequestDispatcher("/views/admin/user-edit.jsp").forward(req, resp);

        } catch(NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
    }

    private void handleEditPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if(idParam == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            User user = userService.findById(id);
            if(user == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/users");
                return;
            }

            // Cập nhật thông tin
            user.setFullname(req.getParameter("fullName"));
            String phoneStr = req.getParameter("phone");
            if(phoneStr != null && !phoneStr.isEmpty()) {
                try {
                    user.setPhone(Integer.parseInt(phoneStr));
                } catch(NumberFormatException e) {
                    // Không hợp lệ thì bỏ qua
                }
            }

            String isAdminStr = req.getParameter("isAdmin");
            user.setIsAdmin("on".equals(isAdminStr));

            userService.save(user);

            resp.sendRedirect(req.getContextPath() + "/admin/users");

        } catch(NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
    }

    // ======= Xử lý xóa user =======
    private void handleDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String idParam = req.getParameter("id");
        if(idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                userService.deleteById(id);
            } catch(NumberFormatException e) {
                // Bỏ qua
            }
        }

        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}
