package vn.iotstar.controller.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.entity.User;
import vn.iotstar.repository.impl.UserRepositoryImpl;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = "/auth/login")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final String LOGIN_PAGE = "/views/auth/login.jsp";
    public static final String WAITING_URL = "/auth/waiting";
    public static final String COOKIE_REMEMBER = "rememberEmail";

    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        // Nếu session đã tồn tại và có user --> chuyển tới waiting
        if (session != null && session.getAttribute("account") != null) {
            resp.sendRedirect(req.getContextPath() + WAITING_URL);
            return;
        }

        // Kiểm tra cookie Remember Me
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (COOKIE_REMEMBER.equals(c.getName())) {
                    String email = c.getValue();
                    User user = userService.findByEmail(email);

                    if (user != null) {
                        session = req.getSession(true);
                        session.setAttribute("account", user);

                        resp.sendRedirect(req.getContextPath() + WAITING_URL);
                        return;
                    }
                }
            }
        }

        // Không có session hoặc cookie → vào trang login
        req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        boolean isRemember = "on".equals(req.getParameter("remember"));

        String alertMsg = "";

        // Check rỗng
        if (email == null || email.isEmpty() ||
            password == null || password.isEmpty()) {

            alertMsg = "Email hoặc mật khẩu không được để trống.";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            return;
        }

        // Login
        User user = userService.login(email, password);

        if (user != null) {

            HttpSession session = req.getSession(true);
            session.setAttribute("account", user);

            // Ghi cookie remember me
            if (isRemember) {
                saveRemember(resp, email);
            }

            resp.sendRedirect(req.getContextPath() + WAITING_URL);
            return;

        } else {
            alertMsg = "Email hoặc mật khẩu không đúng.";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
        }
    }

    private void saveRemember(HttpServletResponse resp, String email) {
        Cookie cookie = new Cookie(COOKIE_REMEMBER, email);
        cookie.setMaxAge(60 * 30); // 30 phút
        resp.addCookie(cookie);
    }
}

//
/*
NOTE LOGIN CONTROLLER:

1️.Request parameters từ login.jsp:
   - "email"     -> String email
   - "password"  -> String password
   - "remember"  -> checkbox "on" nếu tick

2️.Response / Forward:
   - Khi login thất bại hoặc rỗng:
       req.setAttribute("alert", "message") -> hiển thị trên JSP
   - Khi login thành công:
       session.setAttribute("account", user)
       redirect sang WAITING_URL

3️.Cookie Remember Me:
   - Cookie tên "rememberEmail", thời gian sống 30 phút
   - Đọc cookie trong doGet để tự động login

4️.Session:
   - Key "account" lưu user, các trang khác có thể lấy từ session

5️.Charset:
   - UTF-8 cho request và response
*/


















