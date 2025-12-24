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

@WebServlet("/admin/book/add")
@MultipartConfig(
    fileSizeThreshold = 1024*1024,
    maxFileSize = 10*1024*1024,
    maxRequestSize = 50*1024*1024
)
public class BookAddController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final BookService bookService = new BookServiceImpl(new BookRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/admin/book/add-book.jsp").forward(req, resp);
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

        String title = req.getParameter("title");
        String publisher = req.getParameter("publisher");
        String priceStr = req.getParameter("price");
        String description = req.getParameter("description");
        String publishDateStr = req.getParameter("publishDate");
        String quantityStr = req.getParameter("quantity");

        Book book = new Book();
        book.setTitle(title);
        book.setPublisher(publisher);
        try { book.setPrice(Double.parseDouble(priceStr)); } catch(Exception ignored) {}
        book.setDescription(description);
        try { book.setQuantity(Integer.parseInt(quantityStr)); } catch(Exception ignored) {}
        if(publishDateStr != null && !publishDateStr.isEmpty()) {
            book.setPublishDate(java.time.LocalDate.parse(publishDateStr));
        }

        // Upload cover image
        Part part = req.getPart("coverImage");
        String fileNameSaved = null;
        if(part != null && part.getSize() > 0) {
            String submittedFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            int dot = submittedFileName.lastIndexOf(".");
            String ext = (dot != -1) ? submittedFileName.substring(dot) : "";
            fileNameSaved = System.currentTimeMillis() + ext;
            File uploadDir = new File(Constant.DIR);
            if(!uploadDir.exists()) uploadDir.mkdirs();
            part.write(Constant.DIR + "/" + fileNameSaved);
        }
        book.setCoverImage(fileNameSaved);

        bookService.save(book);
        resp.sendRedirect(req.getContextPath() + "/admin/book/list");
    }
}
