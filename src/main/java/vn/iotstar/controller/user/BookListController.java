package vn.iotstar.controller.user;

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

@WebServlet("/user/book/list")
public class BookListController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final BookService bookService = new BookServiceImpl(new BookRepositoryImpl());
    private static final int PAGE_SIZE = 2; // số sách mỗi trang

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = null;
        if(session != null) {
            user = (User) session.getAttribute("account");
        }

        int page = 1;
        String pageParam = req.getParameter("page");
        if(pageParam != null) {
            try { page = Integer.parseInt(pageParam); } 
            catch (NumberFormatException ignored) {}
        }

        // Lấy sách phân trang
        List<Book> books = bookService.findAll(page, PAGE_SIZE);
        long totalBooks = bookService.count();
        int totalPages = (int)Math.ceil((double) totalBooks / PAGE_SIZE);

        req.setAttribute("books", books);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        // Forward sang JSP
        RequestDispatcher rd = req.getRequestDispatcher("/views/user/book/list-book.jsp");
        rd.forward(req, resp);
    }
}
