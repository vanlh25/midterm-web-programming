package vn.iotstar.filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User;

@WebFilter(urlPatterns = "/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        // Lấy user từ session (entity User)
        User user = (User) session.getAttribute("account");

        // Kiểm tra user tồn tại và là admin
        if (user != null && user.getIsAdmin()) {
            chain.doFilter(request, response); // Cho phép đi tiếp
        } else {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        }
    }
}
