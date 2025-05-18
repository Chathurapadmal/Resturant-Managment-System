package Controller;

import java.io.File;
import javax.servlet.http.Part;

public class PathUtil {
    public static String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        if (contentDisp == null)
            return null;
        for (String token : contentDisp.split(";")) {
            if (token.trim().startsWith("filename")) {
                String fileName = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
                // MSIE fix for full path file name
                return fileName.substring(fileName.lastIndexOf(File.separator) + 1);
            }
        }
        return null;
    }
}
