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
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
public class ServletAdminInsertBook extends HttpServlet {

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
        log(this.getServletName() + " processRequest" );
        
        String url = UrlConstants.SERVLET_ADMIN_PAGE_INSERT_BOOK;
        request.setAttribute(UrlConstants.ATTR_INCLUDED_PAGE, UrlConstants.PAGE_ADMIN_INSERT_BOOK);
        
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                Map<String, String> params = new HashMap<>();
                FileItem photo = getParameters(request, params);

                //Get parameters        
                String title = params.get("title").toLowerCase().trim();
                String description = params.get("description").trim();
                String priceStr = params.get("price").trim();
                String quantityStr = params.get("quantity").trim();
                //validate data
                boolean validPhoto = validatePhoto(photo);
                BookError error = validateInput(title, description, quantityStr, priceStr);
                
                log("validPhoto " + validPhoto);
                log("input error null " + (error == null));
                if (validPhoto && (error == null)) {
                    double price = Double.parseDouble(params.get("price").trim());
                    int quantity = Integer.parseInt(params.get("quantity").trim());
                    boolean active = true;
                    int category = Integer.parseInt(params.get("category").trim());
                    int author = Integer.parseInt(params.get("author").trim());
                    Date current = new Date(System.currentTimeMillis());

                    String filename = DataUtils.getRandomFilename(photo);
                    log("random filename: " + filename);
                    BookDTO bookDTO = new BookDTO(null, title, description, quantity, price, filename, current, active, category, author);
                    BookDAO bookDAO = new BookDAO();
                    boolean resultBook = bookDAO.insertBook(bookDTO);
                    boolean resultPhoto;
                    if (resultBook) {
                        request.setAttribute(UrlConstants.ATTR_MESSAGE_BOOK, "Insert book successfully!");
                        resultPhoto = insertPhoto(photo, filename, getServletContext().getRealPath("/"));
                        if (resultPhoto) {
                            request.setAttribute(UrlConstants.ATTR_MESSAGE_PHOTO, "Insert photo successfully!");
                        } else {
                            request.setAttribute(UrlConstants.ATTR_MESSAGE_PHOTO, "Insert photo failed!");
                        }
                    } else {
                        request.setAttribute(UrlConstants.ATTR_MESSAGE_BOOK, "Insert book failed!");
                    }
                } else {
                    request.setAttribute(UrlConstants.ATTR_ERROR, error);
                    if (!validPhoto) {
                        request.setAttribute(UrlConstants.ATTR_MESSAGE_PHOTO, "Please choose photo file .png or .jpg only");
                        
                    }
                }
            } catch (FileUploadException ex) {
                url = UrlConstants.PAGE_ERROR;
                log(ex.getMessage(), ex);
            } catch (NamingException | SQLException ex) {
                url = UrlConstants.PAGE_ERROR;
                log(ex.getMessage(), ex);
            }
        }
        
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
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

    public static FileItem getParameters(HttpServletRequest request, Map<String, String> params) throws FileUploadException {
        FileItem photo = null;
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = upload.parseRequest(request);

        Iterator ite = items.iterator();
        while (ite.hasNext()) {
            FileItem item = (FileItem) ite.next();
            if (item.isFormField()) {
                params.put(item.getFieldName(), item.getString());
//                log(item.getName() + ": item.getName()");
//                log(item.getFieldName() + ": " + item.getString());
            } else {
                photo = item;
//                log("Photo name: " + photo.getFieldName());
//                log("Photo name: " + photo.getString());
//                log("Photo name: " + photo.getName());
            }
        }
        
        //set parameters to request
        request.setAttribute(UrlConstants.ATTR_PARAMS, params);
        return photo;
    }

    public static boolean validatePhoto(FileItem photo) {
        boolean valid;
        if (photo == null) {
            valid = false;
        } else {
            String fileName = photo.getName();
            valid = DataUtils.validatePhotoFormat(fileName);
        }
        return valid;
    }

    public BookError validateInput(String title, String description, String quantityStr, String priceStr) {
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
//        if (dateStr.length() == 0) {
//            error.setDateImportedErr("Required");
//            err = true;
//        } else if (!DataUtils.validateDate(dateStr)) {
//            error.setDateImportedErr("Date format: dd/MM/yyyy and must be a valid date");
//            err = true;
//        }

        if (!err) {
            error = null;
        }
        return error;
    }

    //Insert an existing photo
    public static boolean insertPhoto(FileItem item, String filename, String folderPath) {
        boolean result = false;

//        String itemName = item.getName();
//        String extension = itemName.substring(filename.lastIndexOf("."));
//        filename = filename + extension;
        try {
            File file = new File(folderPath + filename);
            item.write(file);
            result = true;
        } catch (Exception ex) {
            
//            log(ex.getMessage(), ex);
        }
        return result;
    }

}
