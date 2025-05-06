package Controller;

import Model.*;
import dao.UserDAO;
import Controller.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = (User) userDAO.validateUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("User", user);
            response.sendRedirect("DashboardServlet");
        } else {
            request.setAttribute("error", "Invalid username or password!");
            RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
            rd.forward(request, response);
        }
    }
}
