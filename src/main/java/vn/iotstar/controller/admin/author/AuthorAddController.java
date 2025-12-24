package vn.iotstar.controller.admin.author;

import java.io.IOException;

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

@WebServlet("/admin/author/add")
public class AuthorAddController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final AuthorService authorService = new AuthorServiceImpl(new AuthorRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/admin/author/add-author.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("account");
        if(user == null || !Boolean.TRUE.equals(user.getIsAdmin())) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("authorName");
        String dobStr = req.getParameter("dateOfBirth");

        Author author = new Author();
        author.setAuthorName(name);
        if(dobStr != null && !dobStr.isEmpty()) {
            author.setDateOfBirth(java.time.LocalDate.parse(dobStr));
        }

        authorService.save(author);
        resp.sendRedirect(req.getContextPath() + "/admin/author/list");
    }
}
