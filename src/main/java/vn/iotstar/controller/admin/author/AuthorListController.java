package vn.iotstar.controller.admin.author;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.entity.Author;
import vn.iotstar.entity.User;
import vn.iotstar.service.AuthorService;
import vn.iotstar.service.impl.AuthorServiceImpl;
import vn.iotstar.repository.impl.AuthorRepositoryImpl;

@WebServlet("/admin/author/list")
public class AuthorListController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final AuthorService authorService = new AuthorServiceImpl(new AuthorRepositoryImpl());
    private static final int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        User user = (User) session.getAttribute("account");
        if(!Boolean.TRUE.equals(user.getIsAdmin())) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        int page = 1;
        String pageParam = req.getParameter("page");
        if(pageParam != null) {
            try { page = Integer.parseInt(pageParam); } catch (NumberFormatException ignored) {}
        }

        List<Author> authors = authorService.findAll(page, PAGE_SIZE);
        long totalAuthors = authorService.count();
        int totalPages = (int)Math.ceil((double) totalAuthors / PAGE_SIZE);

        req.setAttribute("authors", authors);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        RequestDispatcher rd = req.getRequestDispatcher("/views/admin/author/list-author.jsp");
        rd.forward(req, resp);
    }
}
