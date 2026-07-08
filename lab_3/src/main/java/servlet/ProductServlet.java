package servlet;

import dao.ProductDAO;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        String action = request.getParameter("action");
        try {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Product product = productDAO.getProductById(id);
                request.setAttribute("editProduct", product);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                productDAO.deleteProduct(id);
            }
        } catch (NumberFormatException e) {
            // Bỏ qua và tiếp tục tải trang
        }
        request.setAttribute("products", productDAO.getAllProducts());
        request.getRequestDispatcher("/sanpham.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hỗ trợ tiếng Việt khi cập nhật/thêm sản phẩm
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try {
            String productName = request.getParameter("productName");
            int supplierId = Integer.parseInt(request.getParameter("supplierId"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            String quantityPerUnit = request.getParameter("quantityPerUnit");
            double unitPrice = Double.parseDouble(request.getParameter("unitPrice"));
            int unitsInStock = Integer.parseInt(request.getParameter("unitsInStock"));
            if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("productId"));
                Product p = new Product(id, productName, supplierId, categoryId, quantityPerUnit, unitPrice, unitsInStock);
                productDAO.updateProduct(p);
            } else {
                Product p = new Product(0, productName, supplierId, categoryId, quantityPerUnit, unitPrice, unitsInStock);
                productDAO.insertProduct(p);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        response.sendRedirect(buildRedirectUrl(request, "/products"));
    }
}