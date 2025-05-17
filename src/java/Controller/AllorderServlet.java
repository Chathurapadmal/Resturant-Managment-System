package Controller;

import DAO.OrderDAO;
import DAO.dbdao;  // import DBConnection helper
import Model.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.annotation.WebServlet;


@WebServlet ("/AllorderServlet")
public class AllorderServlet extends HttpServlet {

    
    
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
try (Connection conn = dbdao.getConnection()) {
    OrderDAO orderDAO = new OrderDAO(conn);
    List<Order> orderList = orderDAO.getAllOrders();  // can throw SQLException
    request.setAttribute("orderList", orderList);
    request.getRequestDispatcher("all_orders.jsp").forward(request, response);
} catch (Exception e) {
    e.printStackTrace();
    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
}
    }}