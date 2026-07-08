package dhv.edu.demoSpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
// import kma.at19.backend_at19.persistence.*;
// import kma.at19.backend_at19.models.*;
import java.util.*;
import dhv.edu.demoSpring.models.Product;

@RestController /*Chú thích đây là backend */
public class StudentController 
{

   @GetMapping("/liststudents")
public List<String> listStudents() {
    return List.of(
        "Nguyễn Văn A",
        "Nguyễn Văn B",
        "Trần Thị C"
    );
}

// Danh sách sản phẩm tĩnh
    private static final List<Product> products = List.of(
            new Product(1, "Laptop Dell Inspiron", 18000000),
            new Product(2, "MacBook Air M2", 28990000),
            new Product(3, "Chuột Logitech M650", 650000),
            new Product(4, "Bàn phím cơ AKKO", 1250000),
            new Product(5, "Màn hình LG 24 inch", 3500000),
            new Product(6, "Tai nghe Sony WH-CH520", 1490000),
            new Product(7, "SSD Samsung 1TB", 2200000),
            new Product(8, "RAM Kingston 16GB", 1200000),
            new Product(9, "USB Sandisk 64GB", 250000),
            new Product(10, "Webcam Logitech C920", 1900000)
    );

    @GetMapping("/listproducts")
    public List<Product> listProducts() {
        return products;
    }


}