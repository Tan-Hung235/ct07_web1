package servlet;

import context.DBContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private String sha256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String buildRedirectUrl(HttpServletRequest request, String path) {
        String forwardedProto = request.getHeader("X-Forwarded-Proto");
        String forwardedHost = request.getHeader("X-Forwarded-Host");
        String host = forwardedHost != null && !forwardedHost.isBlank()
                ? forwardedHost
                : request.getHeader("Host");
        String scheme = forwardedProto != null && !forwardedProto.isBlank()
                ? forwardedProto
                : request.getScheme();

        if (host == null || host.isBlank()) {
            return request.getContextPath() + path;
        }

        return scheme + "://" + host + request.getContextPath() + path;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hỗ trợ tiếng Việt khi gửi dữ liệu lên
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String sql = "SELECT password FROM tbl_user WHERE username=?";
        try (Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            if (conn == null) {
                throw new SQLException("Không thể kết nối tới cơ sở dữ liệu");
            }
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String dbPassword = rs.getString("password");
                String inputPassword = sha256(password);
                if (dbPassword.equalsIgnoreCase(inputPassword)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", username);
                    response.sendRedirect(buildRedirectUrl(request, "/products"));
                    return;
                }
            }
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            request.getRequestDispatcher("/login.html").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi đăng nhập: " + e.getMessage());
            request.getRequestDispatcher("/login.html").forward(request, response);
        }
    }
}