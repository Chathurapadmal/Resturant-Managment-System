package Model;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "product", urlPatterns = {"/product"})
public class product {

    public product(String string, double aDouble, String string1, String string2) {
    }

    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public double getPrice() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getIngredients() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getImagePath() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
public class Product {
    private String name;
    private double price;
    private String ingredients;
    private String imagePath;

    public Product(String name, double price, String ingredients, String imagePath) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        this.imagePath = imagePath;
    }

    // getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getIngredients() { return ingredients; }
    public String getImagePath() { return imagePath; }
}
}