/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.book.BookDAO;
import haanh.book.BookDTO;
import haanh.book.BookError;
import haanh.utils.DataUtils;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author HaAnh
 */
public class ServletAdminUpdateBook extends HttpServlet {

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
        
        
        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                Map<String, String> params = new HashMap<>();
//                FileItem photo = ServletAdminInsertBook.getParameters(request, params);
                FileItem photo = null;
                //Get parameters        
                String title = params.get("title").toLowerCase().trim();
                String description = params.get("description").trim();
                String priceStr = params.get("price").trim();
                String quantityStr = params.get("quantity").trim();
                String dateStr = params.get("dateImported").trim();
                
                BookError error = validateInput(title, description, quantityStr, priceStr, dateStr);
                boolean validPhoto = true;
                String filename = params.get("currentPhoto");
                if (photo != null) {
                    validPhoto = ServletAdminInsertBook.validatePhoto(photo);
                    filename = UUID.randomUUID().toString();
                } 
                
                
                
                if ((error == null) && validPhoto) {
                    int id = Integer.parseInt(request.getParameter("bookId"));
                    double price = Double.parseDouble(params.get("price").trim());
                    int quantity = Integer.parseInt(params.get("quantity").trim());
                    boolean active = false;
                    if (params.get("active") != null) {
                        active = true;
                    }
                    int category = Integer.parseInt(params.get("category").trim());
                    int author = Integer.parseInt(params.get("author").trim());
                    Date date = DataUtils.getDateFromString(dateStr);
                    
                    BookDAO bookDAO = new BookDAO();
                    BookDTO bookDTO = new BookDTO(id, title, description, quantity, price, null, date, active, category, author);
                    
                } else {
                    request.setAttribute(UrlConstants.ATTR_ERROR, error);
                    if (!validPhoto) {
                        request.setAttribute(UrlConstants.ATTR_MESSAGE_PHOTO, "Please choose photo file .png or .jpg only");
                    }
                }
            }
        } catch (Exception ex) {
            log(ex.getMessage(), ex);
        }

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

    public BookError validateInput(String title, String description, String quantityStr, String priceStr, String dateStr) {
        BookError error = new BookError();
        boolean err = false;

        //Title
        if (title.length() == 0) {
            err = true;
            error.setTitleErr("Title required");
        }
        //Description
        if (description.length() == 0) {
            err = true;
            error.setDescriptionErr("Description required");
        }
        //Quantity
        if (quantityStr.length() == 0) {
            err = true;
            error.setQuantityErr("Quantity required");
        } else {
            try {
                int quan = Integer.parseInt(quantityStr);
                if (quan < 0) {
                    err = true;
                    error.setQuantityErr("Quantity >= 0");
                }
            } catch (NumberFormatException e) {
                err = true;
                error.setQuantityErr("Integer format only");
            }
        }
        //Price
        if (priceStr.length() == 0) {
            err = true;
            error.setPriceErr("Price required");
        } else {
            try {
                double price = Double.parseDouble(priceStr);
                if (price <= 0) {
                    err = true;
                    error.setPriceErr("Price > 0");
                }
            } catch (NumberFormatException e) {
                err = true;
                error.setPriceErr("Double format only");
            }
        }
        //Date
        if (dateStr.length() == 0) {
            error.setDateImportedErr("Required");
            err = true;
        } else if (!DataUtils.validateDate(dateStr)) {
            error.setDateImportedErr("Date format: dd/MM/yyyy and must be a valid date");
            err = true;
        }
        
        if (!err) {
            error = null;
        }
        return error;
    }

}
