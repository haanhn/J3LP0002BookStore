/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.book.BookDAO;
import haanh.utils.DataUtils;
import haanh.utils.UrlConstants;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author HaAnh
 */
@MultipartConfig
public class ServletAdminUpdateBookImage extends HttpServlet {
    
    private static final String PAGE_ERROR = "/J3LP0002BookStore/error.html";
//    private static final String SERVLET_VIEW_BOOK_DETAIL = "/J3LP0002BookStore/admin/viewBookDetail";
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
        log("is multipart" + ServletFileUpload.isMultipartContent(request));
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                Part photo = request.getPart("bookImage");
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                url = url + "?bookId=" + bookId;
                
                log("Photo " + photo);
                log("Photo file name " + photo.getSubmittedFileName());
                log("bookId " + bookId);
                
                boolean validPhoto = DataUtils.validatePhotoFormat(photo.getSubmittedFileName());
                if (validPhoto) {
                    String filename = DataUtils.getRandomFilename(photo);
                    File file = new File(getServletContext().getRealPath("/") + filename);
                    log("To write img into" + file.getPath());
                    photo.write(file.getPath());
                    BookDAO dao = new BookDAO();
                    dao.updateBookImage(bookId, filename);
                } 
                //khong dung duoc vi res.sendRedirect
//                else {
//                    request.setAttribute(UrlConstants.ATTR_MESSAGE_PHOTO, "Allow file image .png or .jpg only");
//                }
                
            }
            catch (Exception ex) {
                log(ex.getMessage(), ex);
                url = PAGE_ERROR;
            }
        }

        response.sendRedirect(url);
        //when use senRedirect: /error.html:
//        --> http://localhost:8084/error.html
//      when use sendRedirect: error.html
//      --> http://localhost:8084/J3LP0002BookStore/admin/error.html
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
                photo = item;
                log("Photo name: " + photo.getName());
            }
        }
        return photo;
    }

}
