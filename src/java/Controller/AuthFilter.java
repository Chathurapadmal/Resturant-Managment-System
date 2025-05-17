package Controller;

import Model.Userm;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*") // Applies to all requests
public class AuthFilter implements Filter {

    public void init(FilterConfig filterConfig) {}

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String uri = request.getRequestURI();
        boolean isLoginPage = uri.endsWith("Login.jsp") || uri.endsWith("LoginServlet");
        boolean isResource = uri.contains("css") || uri.contains("js") || uri.contains("images");

        if (isLoginPage || isResource) {
            chain.doFilter(req, res);
            return;
        }

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");

        // Restrict access based on roles
        if (uri.contains("AdminDashboard.jsp") || uri.contains("add_user.jsp")) {
            if (!"admin".equalsIgnoreCase(role)) {
                response.sendRedirect("Login.jsp");
                return;
            }
        } else if (uri.contains("KitchenDashboardServlet")) {
            if (!"waiter".equalsIgnoreCase(role) && !"admin".equalsIgnoreCase(role)) {
                response.sendRedirect("Login.jsp");
                return;
            }
        } else if (uri.contains("POSServlet")) {
            if (!"cashier".equalsIgnoreCase(role) && !"admin".equalsIgnoreCase(role)) {
                response.sendRedirect("Login.jsp");
                return;
            }
        }

        chain.doFilter(req, res); // Allow the request
    }

    public void destroy() {}
}
