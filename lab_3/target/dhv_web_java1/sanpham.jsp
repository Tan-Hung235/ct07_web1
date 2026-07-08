```jsp
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="dao.ProductDAO" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        form { margin-bottom: 20px; padding: 15px; border: 1px solid #ccc; width: 70%; }
        input, button, a.button { padding: 6px; margin: 4px 0; }
        table { border-collapse: collapse; width: 90%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
        .actions a { margin: 0 5px; }
    </style>
</head>
<body>
    <h2>QUẢN LÝ SẢN PHẨM</h2>

    <%
        Product editProduct = (Product) request.getAttribute("editProduct");
        List<Product> list = (List<Product>) request.getAttribute("products");
        if (list == null) {
            list = new ProductDAO().getAllProducts();
        }
    %>

    <form action="<%= request.getContextPath() %>/products" method="post">
        <input type="hidden" name="action" value="<%= editProduct != null ? "update" : "create" %>">
        <% if (editProduct != null) { %>
            <input type="hidden" name="productId" value="<%= editProduct.getProductID() %>">
        <% } %>

        <div>Tên sản phẩm: <input type="text" name="productName" value="<%= editProduct != null ? editProduct.getProductName() : "" %>" required></div>
        <div>Supplier ID: <input type="number" name="supplierId" value="<%= editProduct != null ? editProduct.getSupplierID() : "" %>" required></div>
        <div>Category ID: <input type="number" name="categoryId" value="<%= editProduct != null ? editProduct.getCategoryID() : "" %>" required></div>
        <div>Quantity Per Unit: <input type="text" name="quantityPerUnit" value="<%= editProduct != null ? editProduct.getQuantityPerUnit() : "" %>"></div>
        <div>Unit Price: <input type="number" step="0.01" name="unitPrice" value="<%= editProduct != null ? editProduct.getUnitPrice() : "" %>" required></div>
        <div>Units In Stock: <input type="number" name="unitsInStock" value="<%= editProduct != null ? editProduct.getUnitsInStock() : "" %>" required></div>
        <div style="margin-top:10px;">
            <button type="submit"><%= editProduct != null ? "Cập nhật" : "Thêm mới" %></button>
            <% if (editProduct != null) { %>
                <a href="<%= request.getContextPath() %>/products">Hủy</a>
            <% } %>
        </div>
    </form>

    <table>
        <tr>
            <th>ID</th>
            <th>Tên sản phẩm</th>
            <th>Supplier ID</th>
            <th>Category ID</th>
            <th>Quantity Per Unit</th>
            <th>Unit Price</th>
            <th>Units In Stock</th>
            <th>Hành động</th>
        </tr>

        <% if (list != null) {
            for (Product p : list) { %>
        <tr>
            <td><%= p.getProductID() %></td>
            <td><%= p.getProductName() %></td>
            <td><%= p.getSupplierID() %></td>
            <td><%= p.getCategoryID() %></td>
            <td><%= p.getQuantityPerUnit() %></td>
            <td><%= p.getUnitPrice() %></td>
            <td><%= p.getUnitsInStock() %></td>
            <td class="actions">
                <a href="<%= request.getContextPath() %>/products?action=edit&id=<%= p.getProductID() %>">Sửa</a>
                <a href="<%= request.getContextPath() %>/products?action=delete&id=<%= p.getProductID() %>" onclick="return confirm('Xóa sản phẩm này?');">Xóa</a>
            </td>
        </tr>
        <% }
        } %>
    </table>
</body>
</html>
```
