package Controller;

import DAO.UserDAO;
import Model.Userm;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024)
public class UpdateProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Userm user = (Userm) session.getAttribute("loggedInUser");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String fullName = request.getParameter("fullName");
        String bio = request.getParameter("bio");

        Part filePart = request.getPart("profilePicture");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadPath = getServletContext().getRealPath("") + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String newFileName = null;
        if (fileName != null && !fileName.isEmpty()) {
            newFileName = UUID.randomUUID().toString() + "_" + fileName;
            filePart.write(uploadPath + File.separator + newFileName);
            user.setProfilePicture(newFileName); // assuming this is a field in your User object
        }

        // Update user object
        user.setFullName(fullName);
        user.setBio(bio);

        // TODO: Update user in the database
       UserDAO userDAO = new UserDAO();
       userDAO.updateUser(user);


        session.setAttribute("loggedInUser", user); // Refresh session object
        response.sendRedirect("profile.jsp");
    }
}
