/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.book.BookDAO;
import haanh.utils.DataUtils;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author HaAnh
 */
public class ServletAdminUpdateBookImage extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        log(this.getServletName() + " processRequest");

        String url = UrlConstants.SERVLET_ADMIN_VIEW_BOOK_DETAIL;
//        log("is multipart" + ServletFileUpload.isMultipartContent(request));
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                Map<String, String> params = new HashMap<>();
                FileItem photo = ServletAdminInsertBook.getParameters(request, params);

                boolean validPhoto = ServletAdminInsertBook.validatePhoto(photo);
                log("valid photo = " + validPhoto);
                int bookId = Integer.parseInt(params.get("bookId"));
                log("bookId = " + bookId);
                url = url + "?bookId=" + bookId;

                if (validPhoto) {
                    String filename = DataUtils.getRandomFilename(photo);
                    boolean resultPhoto = ServletAdminInsertBook.insertPhoto(photo, filename, getServletContext().getRealPath("/"));
                    if (resultPhoto) {
                        BookDAO bookDAO = new BookDAO();
                        bookDAO.updateBookImage(bookId, filename);
                    }
                }
            } catch (FileUploadException | NamingException | SQLException ex) {
                url = UrlConstants.PAGE_ERROR;
                log(ex.getMessage(), ex);
            }
        }

        response.sendRedirect(url);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public FileItem getParameters(HttpServletRequest request, Map<String, String> params) throws FileUploadException {
        FileItem photo = null;
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = upload.parseRequest(request);

        Iterator ite = items.iterator();
        while (ite.hasNext()) {
            FileItem item = (FileItem) ite.next();
            if (item.isFormField()) {
                params.put(item.getFieldName(), item.getString());
                log(item.getFieldName() + " " + item.getString());
            } else {
                log("Photo name: " + photo.getName());
                photo = item;
            }
        }
        return photo;
    }

}
