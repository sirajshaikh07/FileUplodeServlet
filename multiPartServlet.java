package com.blog.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class multiPartServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "D:\\Eclipse\\BlogX\\src\\main\\webapp\\img";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File uploadDir = new File(UPLOAD_DIRECTORY);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        for (Part part : request.getParts()) {
            String fileName = getFileName(part);
            if (fileName != null && !fileName.isEmpty()) {
                part.write(uploadDir + File.separator + fileName);
            }
        }
    }

    private String getFileName(Part part) {
        if (part != null) {
            for (String content : part.getHeader("content-disposition").split(";")) {
                if (content.trim().startsWith("filename")) {
                    return content.substring(content.indexOf("=") + 2, content.length() - 1);
                }
            }
        }
        return null; // Return null if no filename found or part is null
    }
}
