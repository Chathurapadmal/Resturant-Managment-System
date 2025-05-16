package Controller;

import DAO.OrderDAO;
import Model.Order;
import com.sun.jdi.connect.spi.Connection;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO((java.sql.Connection) (Connection) getServletContext().getAttribute("DBConnection"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("id"));

        Order order = orderDAO.getOrderById(orderId); // <-- You need to implement this
        req.setAttribute("order", order);

        RequestDispatcher dispatcher = req.getRequestDispatcher("bill.jsp");
        dispatcher.forward(req, resp);
    }
}
