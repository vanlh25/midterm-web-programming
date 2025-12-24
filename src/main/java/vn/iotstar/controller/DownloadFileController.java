package vn.iotstar.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.util.Constant;

@WebServlet(urlPatterns = "/image")
public class DownloadFileController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String fileName = req.getParameter("fname");
        File file = new File(Constant.DIR + "/" + fileName);

        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = getServletContext().getMimeType(file.getAbsolutePath());
        resp.setContentType(mimeType != null ? mimeType : "application/octet-stream");

        try (FileInputStream fis = new FileInputStream(file)) {
            IOUtils.copy(fis, resp.getOutputStream());
        }
    }
}
