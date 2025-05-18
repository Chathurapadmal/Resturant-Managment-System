package Controller;

import DAO.DashboardDAO;
import Model.Order;
import Controller.TopItem;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import com.google.gson.Gson;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        DashboardDAO dao = new DashboardDAO();

        int todaysOrders = dao.getTodayOrdersCount();
        double todaysSales = dao.getTodaySalesTotal();
        List<Order> orders = dao.getTodayOrders();
        List<TopItem> topItems = dao.getTopSellingItems();
        List<TopItem> salesSummary = dao.getSalesSummary();

        // Prepare data for Chart.js Sales Summary
        List<String> dates = new ArrayList<>();
        List<Integer> totals = new ArrayList<>();
        for (TopItem item : salesSummary) {
            dates.add(item.getName());  // Assuming name holds the date or label
            totals.add(item.getTotalSold());  // Using totalSold as sales value
        }

        // Prepare feedback data for Pie Chart
        Map<String, Integer> feedbackCounts = dao.getFeedbackCounts();
        List<String> feedbackLabels = new ArrayList<>();
        List<Integer> feedbackData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : feedbackCounts.entrySet()) {
            feedbackLabels.add(entry.getKey());
            feedbackData.add(entry.getValue());
        }

        Gson gson = new Gson();
        String datesJson = gson.toJson(dates);
        String totalsJson = gson.toJson(totals);
        String feedbackLabelsJson = gson.toJson(feedbackLabels);
        String feedbackDataJson = gson.toJson(feedbackData);

        request.setAttribute("todaysOrders", todaysOrders);
        request.setAttribute("todaysSales", todaysSales);
        request.setAttribute("orders", orders);
        request.setAttribute("topItems", topItems);
        request.setAttribute("datesJson", datesJson);
        request.setAttribute("totalsJson", totalsJson);
        request.setAttribute("feedbackLabelsJson", feedbackLabelsJson);
        request.setAttribute("feedbackDataJson", feedbackDataJson);

        RequestDispatcher rd = request.getRequestDispatcher("Admin_Dashboard.jsp");
        rd.forward(request, response);
    }
}
