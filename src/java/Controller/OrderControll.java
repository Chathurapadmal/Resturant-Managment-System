package controller;

import dao.OrderDAO;
import model.PosPageServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/OrderControll")
public class OrderControll extends HttpServlet {
    private OrderDAO orderDAO;

    public void init() {
        orderDAO = new OrderDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            int tableId = Integer.parseInt(request.getParameter("table_id"));
            int waiterId = Integer.parseInt(request.getParameter("waiter_id"));
            double totalAmount = Double.parseDouble(request.getParameter("total_amount"));
            String orderStatus = request.getParameter("order_status");

            PosPageServlet order = new PosPageServlet();
            order.setTableId(tableId);
            order.setWaiterId(waiterId);
            order.setTotalAmount(totalAmount);
            order.setOrderStatus(orderStatus);

            orderDAO.createOrder(order);
            response.sendRedirect("pos.jsp");
        }
    }
}
