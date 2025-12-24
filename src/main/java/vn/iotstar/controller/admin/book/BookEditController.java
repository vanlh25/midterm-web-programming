package vn.iotstar.controller.admin.book;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import vn.iotstar.entity.Book;
import vn.iotstar.entity.User;
import vn.iotstar.service.BookService;
import vn.iotstar.service.impl.BookServiceImpl;
import vn.iotstar.repository.impl.BookRepositoryImpl;
import vn.iotstar.util.Constant;

@WebServlet("/admin/book/edit")
@MultipartConfig(
    fileSizeThreshold = 1024*1024,
    maxFileSize = 10*1024*1024,
    maxRequestSize = 50*1024*1024
)
public class BookEditController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final BookService bookService = new BookServiceImpl(new BookRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        Book book = bookService.findById(id);
        req.setAttribute("book", book);
        req.getRequestDispatcher("/views/admin/book/edit-book.jsp").forward(req, resp);
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
        int id = Integer.parseInt(req.getParameter("bookId"));
        Book book = bookService.findById(id);

        if(book != null) {
            book.setTitle(req.getParameter("title"));
            book.setPublisher(req.getParameter("publisher"));
            try { book.setPrice(Double.parseDouble(req.getParameter("price"))); } catch(Exception ignored) {}
            book.setDescription(req.getParameter("description"));
            try { book.setQuantity(Integer.parseInt(req.getParameter("quantity"))); } catch(Exception ignored) {}
            String publishDateStr = req.getParameter("publishDate");
            if(publishDateStr != null && !publishDateStr.isEmpty()) {
                book.setPublishDate(java.time.LocalDate.parse(publishDateStr));
            }

            Part part = req.getPart("coverImage");
            if(part != null && part.getSize() > 0) {
                String submittedFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                int dot = submittedFileName.lastIndexOf(".");
                String ext = (dot != -1) ? submittedFileName.substring(dot) : "";
                String fileNameSaved = System.currentTimeMillis() + ext;
                File uploadDir = new File(Constant.DIR);
                if(!uploadDir.exists()) uploadDir.mkdirs();
                part.write(Constant.DIR + "/" + fileNameSaved);
                book.setCoverImage(fileNameSaved);
            }

            bookService.save(book);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/book/list");
    }
}
