package dao;

import context.DBContext;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public ProductDAO() {
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();

        String sql = """
                SELECT productId,
                    productName,
                    supplierId,
                    categoryId,
                    quantityPerUnit,
                    unitPrice,
                    unitsInStock
                FROM Product
                """;

        try (
                Connection conn = new DBContext().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getInt("supplierId"),
                        rs.getInt("categoryId"),
                        rs.getString("quantityPerUnit"),
                        rs.getDouble("unitPrice"),
                        rs.getInt("unitsInStock")
                );
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Product getProductById(int id) {
        String sql = """
                SELECT productId,
                    productName,
                    supplierId,
                    categoryId,
                    quantityPerUnit,
                    unitPrice,
                    unitsInStock
                FROM Product
                WHERE productId = ?
                """;

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("productId"),
                            rs.getString("productName"),
                            rs.getInt("supplierId"),
                            rs.getInt("categoryId"),
                            rs.getString("quantityPerUnit"),
                            rs.getDouble("unitPrice"),
                            rs.getInt("unitsInStock")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertProduct(Product p) {
        String sql = """
                INSERT INTO Product (productName, supplierId, categoryId, quantityPerUnit, unitPrice, unitsInStock, discontinued)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getSupplierID());
            ps.setInt(3, p.getCategoryID());
            ps.setString(4, p.getQuantityPerUnit());
            ps.setDouble(5, p.getUnitPrice());
            ps.setInt(6, p.getUnitsInStock());
            ps.setInt(7, 0);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProduct(Product p) {
        String sql = """
                UPDATE Product
                SET productName = ?, supplierId = ?, categoryId = ?, quantityPerUnit = ?, unitPrice = ?, unitsInStock = ?
                WHERE productId = ?
                """;

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getSupplierID());
            ps.setInt(3, p.getCategoryID());
            ps.setString(4, p.getQuantityPerUnit());
            ps.setDouble(5, p.getUnitPrice());
            ps.setInt(6, p.getUnitsInStock());
            ps.setInt(7, p.getProductID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int id) {
        String sqlDeleteOrderDetails = "DELETE FROM OrderDetail WHERE productId = ?";
        String sqlDeleteProduct = "DELETE FROM Product WHERE productId = ?";

        try (Connection conn = new DBContext().getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psOrderDetails = conn.prepareStatement(sqlDeleteOrderDetails);
                PreparedStatement psProduct = conn.prepareStatement(sqlDeleteProduct)) {
                psOrderDetails.setInt(1, id);
                psOrderDetails.executeUpdate();

                psProduct.setInt(1, id);
                int affectedRows = psProduct.executeUpdate();

                conn.commit();
                return affectedRows > 0;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}