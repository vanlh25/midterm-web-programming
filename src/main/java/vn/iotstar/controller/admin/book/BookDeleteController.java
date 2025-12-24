package vn.iotstar.controller.admin.book;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.entity.User;
import vn.iotstar.service.BookService;
import vn.iotstar.service.impl.BookServiceImpl;
import vn.iotstar.repository.impl.BookRepositoryImpl;

@WebServlet("/admin/book/delete")
public class BookDeleteController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final BookService bookService = new BookServiceImpl(new BookRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("account");
        if(user == null || !Boolean.TRUE.equals(user.getIsAdmin())) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));
        bookService.deleteById(id);
        resp.sendRedirect(req.getContextPath() + "/admin/book/list");
    }
}
