package Controller;

import DAO.UserDAO;
import Model.Userm;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        Userm user = userDAO.validateUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            // Redirect based on user role
            String role = user.getRole();
            switch (role.toLowerCase()) {
                case "admin":
                    response.sendRedirect("DashboardServlet");
                    break;
                case "waiter":
                    response.sendRedirect("KitchenDashboardServlet");
                    break;
                case "cashier":
                    response.sendRedirect("POSServlet");
                    break;
                default:
                    request.setAttribute("error", "Unknown role. Contact admin.");
                    RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
                    rd.forward(request, response);
                    break;
            }
        } else {
            request.setAttribute("error", "Invalid username or password");
            RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
            rd.forward(request, response);
        }
    }
}
