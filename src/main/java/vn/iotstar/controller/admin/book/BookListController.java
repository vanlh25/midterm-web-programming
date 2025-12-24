package vn.iotstar.controller.admin.book;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.entity.Book;
import vn.iotstar.entity.User;
import vn.iotstar.service.BookService;
import vn.iotstar.service.impl.BookServiceImpl;
import vn.iotstar.repository.impl.BookRepositoryImpl;

@WebServlet("/admin/book/list")
public class BookListController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final BookService bookService = new BookServiceImpl(new BookRepositoryImpl());
    private static final int PAGE_SIZE = 2;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1️Kiểm tra session & quyền admin
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        User user = (User) session.getAttribute("account");
        if (!Boolean.TRUE.equals(user.getIsAdmin())) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        // 2️ Lấy trang hiện tại
        int page = 1;
        String pageParam = req.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1; // tránh page âm hoặc 0
            } catch (NumberFormatException ignored) {}
        }

        // 3️Lấy sách theo phân trang
        long totalBooks = bookService.count();
        int totalPages = (int) Math.ceil((double) totalBooks / PAGE_SIZE);

        // Giới hạn page không vượt quá totalPages
        if (page > totalPages && totalPages > 0) {
            page = totalPages;
        }

        List<Book> books = bookService.findAll(page, PAGE_SIZE);

        // 4 Gửi dữ liệu tới JSP
        req.setAttribute("books", books);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        RequestDispatcher rd = req.getRequestDispatcher("/views/admin/book/list-book.jsp");
        rd.forward(req, resp);
    }
}
