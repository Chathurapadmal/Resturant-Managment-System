package servlet;

import DAO.ItemDAO;
import Model.Item;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/AddItemServlet")
public class AddItemServlet extends HttpServlet {

    private Connection connection;

    public void init() {
        try {
            // Replace with your DB credentials
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/resturentsystem");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");

        try {
            double price = Double.parseDouble(priceStr);

            Item newItem = new Item(0, name, price);
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

    public void destroy() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
