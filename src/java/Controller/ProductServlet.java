/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ProductDAO;
import Model.product;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


 @WebServlet("/ProductServlet")
@MultipartConfig
public class ProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String ingredients = request.getParameter("ingredients");

        Part filePart = request.getPart("image");
        String fileName = filePart.getSubmittedFileName();
        String uploadPath = getServletContext().getRealPath("/img");

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        filePart.write(uploadPath + File.separator + fileName);

        product product = new product(name, price, ingredients, "img/" + fileName);

        ProductDAO productDAO = new ProductDAO();
        productDAO.addProduct(product);

        response.sendRedirect("MenuServlet"); // Redirect to servlet that loads the menu
    }
}
