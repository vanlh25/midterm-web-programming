package vn.iotstar.controller.admin.rating;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.entity.User;
import vn.iotstar.service.RatingService;
import vn.iotstar.service.impl.RatingServiceImpl;
import vn.iotstar.repository.impl.RatingRepositoryImpl;

@WebServlet("/admin/rating/delete")
public class RatingDeleteController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final RatingService ratingService = new RatingServiceImpl(new RatingRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("account");
        if(user == null || !Boolean.TRUE.equals(user.getIsAdmin())) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        int userId = Integer.parseInt(req.getParameter("userId"));
        int bookId = Integer.parseInt(req.getParameter("bookId"));

        ratingService.deleteById(userId, bookId);
        resp.sendRedirect(req.getContextPath() + "/admin/rating/list");
    }
}
