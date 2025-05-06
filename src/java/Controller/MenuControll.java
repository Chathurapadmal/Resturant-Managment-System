package controller;

import dao.MenuDAO;
import model.MenuItemServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/MenuControll")
public class MenuControll extends HttpServlet {
    private MenuDAO menuDAO;

    public void init() {
        menuDAO = new MenuDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MenuItemServlet> items = menuDAO.getAllMenuItems();
        request.setAttribute("menuItems", items);
        RequestDispatcher dispatcher = request.getRequestDispatcher("menu.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            String description = request.getParameter("description");
            MenuItemServlet newItem = new MenuItemServlet(name, price, description);
            menuDAO.insertMenuItem(newItem);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            menuDAO.deleteMenuItem(id);
        }
        response.sendRedirect("MenuControll");
    }
}
