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
        *{
            margin:0;
            padding:0;
            box-sizing:border-box;
            font-family:Segoe UI, Tahoma, Geneva, Verdana, sans-serif;
        }

        body{
            background:#eef2f7;
            padding:40px;
        }

        .container{
            width:95%;
            max-width:1200px;
            margin:auto;
        }

        h2{
            text-align:center;
            color:#2c3e50;
            margin-bottom:30px;
            font-size:34px;
        }

        .card{
            background:white;
            padding:25px;
            border-radius:12px;
            box-shadow:0 5px 15px rgba(0,0,0,.12);
            margin-bottom:30px;
        }

        .form-group{
            margin-bottom:18px;
        }

        .form-group label{
            display:block;
            margin-bottom:6px;
            font-weight:bold;
            color:#555;
        }

        .form-group input{
            width:100%;
            padding:11px;
            border:1px solid #ccc;
            border-radius:8px;
            font-size:15px;
            transition:.3s;
        }

        .form-group input:focus{
            outline:none;
            border-color:#3498db;
            box-shadow:0 0 5px rgba(52,152,219,.3);
        }

        .btn{
            border:none;
            padding:11px 22px;
            border-radius:8px;
            cursor:pointer;
            color:white;
            font-size:15px;
            text-decoration:none;
            display:inline-block;
        }

        .btn-save{
            background:#3498db;
        }

        .btn-save:hover{
            background:#2980b9;
        }

        .btn-cancel{
            background:#7f8c8d;
        }

        .btn-cancel:hover{
            background:#636e72;
        }

        table{
            width:100%;
            border-collapse:collapse;
            background:white;
            border-radius:12px;
            overflow:hidden;
            box-shadow:0 5px 15px rgba(0,0,0,.12);
        }

        thead{
            background:#3498db;
            color:white;
        }

        th{
            padding:15px;
        }

        td{
            padding:12px;
            text-align:center;
            border-bottom:1px solid #eee;
        }

        tbody tr:nth-child(even){
            background:#f8f9fa;
        }

        tbody tr:hover{
            background:#eaf4ff;
        }

        .actions a{
            text-decoration:none;
            color:white;
            padding:7px 12px;
            border-radius:6px;
            margin:0 3px;
            font-size:14px;
        }

        .actions a:first-child{
            background:#27ae60;
        }

        .actions a:first-child:hover{
            background:#1e8449;
        }

        .actions a:last-child{
            background:#e74c3c;
        }

        .actions a:last-child:hover{
            background:#c0392b;
        }
    </style>

</head>

<body>

<div class="container">

<h2>Quản Lý Sản Phẩm</h2>

<%
    Product editProduct = (Product) request.getAttribute("editProduct");
    List<Product> list = (List<Product>) request.getAttribute("products");
    if (list == null) {
        list = new ProductDAO().getAllProducts();
    }
%>

<div class="card">

<form action="<%= request.getContextPath() %>/products" method="post">

    <input type="hidden" name="action"
        value="<%= editProduct != null ? "update" : "create" %>">

    <% if(editProduct != null){ %>
        <input type="hidden" name="productId"
            value="<%= editProduct.getProductID() %>">
    <% } %>

    <div class="form-group">
        <label>Tên sản phẩm</label>
        <input type="text" name="productName"
            value="<%= editProduct!=null ? editProduct.getProductName() : "" %>" required>
    </div>

    <div class="form-group">
        <label>Supplier ID</label>
        <input type="number" name="supplierId"
            value="<%= editProduct!=null ? editProduct.getSupplierID() : "" %>" required>
    </div>

    <div class="form-group">
        <label>Category ID</label>
        <input type="number" name="categoryId"
            value="<%= editProduct!=null ? editProduct.getCategoryID() : "" %>" required>
    </div>

    <div class="form-group">
        <label>Quantity Per Unit</label>
        <input type="text" name="quantityPerUnit"
            value="<%= editProduct!=null ? editProduct.getQuantityPerUnit() : "" %>">
    </div>

    <div class="form-group">
        <label>Unit Price</label>
        <input type="number" step="0.01" name="unitPrice"
               value="<%= editProduct!=null ? editProduct.getUnitPrice() : "" %>" required>
    </div>

    <div class="form-group">
        <label>Units In Stock</label>
        <input type="number" name="unitsInStock"
               value="<%= editProduct!=null ? editProduct.getUnitsInStock() : "" %>" required>
    </div>

    <button class="btn btn-save" type="submit">
        <%= editProduct != null ? "Cập nhật" : "Thêm mới" %>
    </button>

    <% if(editProduct != null){ %>
        <a class="btn btn-cancel"
           href="<%= request.getContextPath() %>/products">
            Hủy
        </a>
    <% } %>

</form>

</div>

<table>

    <thead>
    <tr>
        <th>ID</th>
        <th>Tên sản phẩm</th>
        <th>Supplier</th>
        <th>Category</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Stock</th>
        <th>Hành động</th>
    </tr>
    </thead>

    <tbody>

    <% if(list!=null){
        for(Product p:list){ %>

    <tr>

        <td><%= p.getProductID() %></td>
        <td><%= p.getProductName() %></td>
        <td><%= p.getSupplierID() %></td>
        <td><%= p.getCategoryID() %></td>
        <td><%= p.getQuantityPerUnit() %></td>
        <td>$ <%= p.getUnitPrice() %></td>
        <td><%= p.getUnitsInStock() %></td>

        <td class="actions">

            <a href="<%=request.getContextPath()%>/products?action=edit&id=<%=p.getProductID()%>">
                Sửa
            </a>

            <a href="<%=request.getContextPath()%>/products?action=delete&id=<%=p.getProductID()%>"
            onclick="return confirm('Xóa sản phẩm này?');">
                Xóa
            </a>

        </td>

    </tr>

    <% }} %>

    </tbody>

</table>

</div>

</body>
</html>