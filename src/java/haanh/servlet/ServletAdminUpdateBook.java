/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.author.AuthorDAO;
import haanh.book.BookDAO;
import haanh.book.BookDTO;
import haanh.book.BookError;
import haanh.category.CategoryDAO;
import haanh.utils.DataUtils;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

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

        String url = UrlConstants.PAGE_ADMIN_BACKGROUND;
        request.setAttribute(UrlConstants.ATTR_INCLUDED_PAGE, UrlConstants.PAGE_ADMIN_BOOK_DETAIL);
        
        //Get parameters        
        int id = Integer.parseInt(request.getParameter("bookId"));
        String title = request.getParameter("title").toLowerCase().trim();
        String priceStr = request.getParameter("price").trim();
        String quantityStr = request.getParameter("quantity").trim();
        String description = request.getParameter("description").trim();
        String dateStr = request.getParameter("dateImported").trim();
        String activeStr = request.getParameter("active");
        int author = Integer.parseInt(request.getParameter("author"));
        int category = Integer.parseInt(request.getParameter("category"));

        BookError error = validateInput(title, description, quantityStr, priceStr, dateStr);

        try {
            if (error == null) {
                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);
                Date date = DataUtils.getDateFromString(dateStr);
                boolean active = false;
                if (activeStr != null) {
                    active = true;
                }

                BookDTO bookDTO = new BookDTO(id, title, description, quantity, price, null, date, active, category, author);

                BookDAO bookDAO = new BookDAO();
                boolean result = bookDAO.updateBook(bookDTO);
                
                if (result) {
                    request.setAttribute(UrlConstants.ATTR_MESSAGE, "Update book successfully!");
                } else {
                    request.setAttribute(UrlConstants.ATTR_MESSAGE, "Update book failed!");
                }
            } else {
                BookDAO bookDAO = new BookDAO();
                request.setAttribute(UrlConstants.ATTR_ERROR, error);
                request.setAttribute(UrlConstants.ATTR_BOOK, bookDAO.getBookById(id));
            }
            
            BookDAO bookDAO = new BookDAO();
            CategoryDAO categoryDAO = new CategoryDAO();
            AuthorDAO authorDAO = new AuthorDAO();
            
            BookDTO bookDTO = bookDAO.getBookById(id);
            Map<Integer, String> authors = authorDAO.getAllAuthors();
            Map<Integer, String> categories = categoryDAO.getAllCategories();
            
            request.setAttribute(UrlConstants.ATTR_BOOK, bookDTO);
            request.setAttribute(UrlConstants.ATTR_AUTHORS, authors);
            request.setAttribute(UrlConstants.ATTR_CATEGORIES, categories);
        } catch (NamingException | SQLException ex) {
            url = UrlConstants.PAGE_ERROR;
            log(ex.getMessage(), ex);
        } catch (Exception ex) {
            url = UrlConstants.PAGE_ERROR;
            log(ex.getMessage(), ex);
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
