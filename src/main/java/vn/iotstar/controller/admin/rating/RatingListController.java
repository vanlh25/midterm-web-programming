package vn.iotstar.controller.admin.rating;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.entity.Rating;
import vn.iotstar.entity.User;
import vn.iotstar.service.RatingService;
import vn.iotstar.service.impl.RatingServiceImpl;
import vn.iotstar.repository.impl.RatingRepositoryImpl;

@WebServlet("/admin/rating/list")
public class RatingListController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final RatingService ratingService = new RatingServiceImpl(new RatingRepositoryImpl());
    private static final int PAGE_SIZE = 10;

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

        List<Rating> ratings = ratingService.findAll(page, PAGE_SIZE);
        long totalRatings = ratingService.count();
        int totalPages = (int)Math.ceil((double) totalRatings / PAGE_SIZE);

        req.setAttribute("ratings", ratings);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        RequestDispatcher rd = req.getRequestDispatcher("/views/admin/rating/list-rating.jsp");
        rd.forward(req, resp);
    }
}
