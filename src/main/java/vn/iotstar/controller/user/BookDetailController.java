package vn.iotstar.controller.user;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.entity.Book;
import vn.iotstar.entity.Rating;
import vn.iotstar.entity.User;
import vn.iotstar.service.BookService;
import vn.iotstar.service.RatingService;
import vn.iotstar.service.impl.BookServiceImpl;
import vn.iotstar.service.impl.RatingServiceImpl;
import vn.iotstar.repository.impl.BookRepositoryImpl;
import vn.iotstar.repository.impl.RatingRepositoryImpl;

@WebServlet("/user/book/detail")
public class BookDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final BookService bookService = new BookServiceImpl(new BookRepositoryImpl());
    private final RatingService ratingService = new RatingServiceImpl(new RatingRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        int bookId = Integer.parseInt(req.getParameter("id"));
        Book book = bookService.findById(bookId);

        if (book != null) {
            // Lấy top 10 review gần nhất
            List<Rating> topReviews = book.getRatings().stream()
                    .sorted((r1, r2) -> r2.getRating().compareTo(r1.getRating()))
                    .limit(10)
                    .collect(Collectors.toList());

            // Chuẩn bị authors
            String authors = book.getBookAuthors().stream()
                    .map(ba -> ba.getAuthor().getAuthorName())
                    .collect(Collectors.joining(", "));

            req.setAttribute("book", book);
            req.setAttribute("authors", authors);
            req.setAttribute("topReviews", topReviews);

            // Lấy user hiện tại
            HttpSession session = req.getSession(false);
            if (session != null) {
                User currentUser = (User) session.getAttribute("account");
                req.setAttribute("currentUser", currentUser);
            }
        }

        RequestDispatcher rd = req.getRequestDispatcher("/views/user/book/detail-book.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        int bookId = Integer.parseInt(req.getParameter("id"));
        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("account");

        if (currentUser != null) {
            int ratingValue = Integer.parseInt(req.getParameter("rating"));
            String reviewText = req.getParameter("reviewText");

            Book book = bookService.findById(bookId);
            if (book != null) {
                Rating rating = new Rating();
                rating.setBook(book);
                rating.setUser(currentUser);
                rating.setRating(ratingValue);
                rating.setReviewText(reviewText);

                ratingService.save(rating);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/user/book/detail?id=" + bookId);
    }
}
