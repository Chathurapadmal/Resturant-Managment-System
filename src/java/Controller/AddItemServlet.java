package servlet;

import DAO.ItemDAO;
import Model.Item;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/AddItemServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1,  // 1MB
        maxFileSize = 1024 * 1024 * 10,               // 10MB
        maxRequestSize = 1024 * 1024 * 15)            // 15MB
public class AddItemServlet extends HttpServlet {

    private Connection connection;
    private static final String UPLOAD_DIR = "uploads";

    @Override
    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/resturentsystem", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        Part filePart = request.getPart("image");
        String fileName = new File(filePart.getSubmittedFileName()).getName();

        try {
            double price = Double.parseDouble(priceStr);

            // Save the image file
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            filePart.write(uploadPath + File.separator + fileName);

            // Create and insert item
            Item newItem = new Item(0, name, price, fileName);
            ItemDAO itemDAO = new ItemDAO(connection);

            boolean success = itemDAO.addItem(newItem);

            if (success) {
                response.sendRedirect("add_item.jsp?message=Item+added+successfully");
            } else {
                response.sendRedirect("add_item.jsp?message=Failed+to+add+item");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("add_item.jsp?message=Error+adding+item");
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
