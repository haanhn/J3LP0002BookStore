/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.author.AuthorDAO;
import haanh.book.BookDAO;
import haanh.book.BookDTO;
import haanh.category.CategoryDAO;
import haanh.utils.DBUtils;
import haanh.utils.UrlConstants;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HaAnh
 */
public class ServletSearchBook extends HttpServlet {

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
        String url = UrlConstants.PAGE_HOME;
        
        try {
            //Get Parameters
            String title = request.getParameter("searchedTitle").trim();
            String minMOneyStr = request.getParameter("minMoney").trim();
            String maxMOneyStr = request.getParameter("maxMoney").trim();
            int categoryId = Integer.parseInt(request.getParameter("category"));
            
            //Process min & max
            double min = getMoney(minMOneyStr, DBUtils.BOOK_PRICE_MIN);
            double max = getMoney(maxMOneyStr, DBUtils.BOOK_PRICE_MAX);
            if (min > max) {
                min = DBUtils.BOOK_PRICE_MIN;
                max = DBUtils.BOOK_PRICE_MAX;
            }
            
            BookDAO dao = new BookDAO();
            CategoryDAO categoryDAO = new CategoryDAO();
            AuthorDAO authorDAO = new AuthorDAO();
            
            List<BookDTO> list = dao.searchBooks(title, min, max, categoryId);
            Map<Integer, String> categories = categoryDAO.getAllCategories();
            Map<Integer, String> authors = authorDAO.getAllAuthors();
            
            request.setAttribute(UrlConstants.ATTR_BOOKS, list);
            request.setAttribute(UrlConstants.ATTR_CATEGORIES, categories);
            request.setAttribute(UrlConstants.ATTR_AUTHORS, authors);
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

    private double getMoney(String moneyStr, double defaultMoney) {
        double money = defaultMoney;
        try {
            money = Double.parseDouble(moneyStr);
        } catch (NumberFormatException ex) {
        }
        return money;
    }
}
