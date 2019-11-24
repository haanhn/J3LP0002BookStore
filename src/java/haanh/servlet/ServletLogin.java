/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.servlet;

import haanh.user.UserDAO;
import haanh.user.UserDTO;
import haanh.userprofile.UserProfileDAO;
import haanh.userprofile.UserProfileDTO;
import haanh.utils.DBUtils;
import haanh.utils.UrlConstants;
import haanh.viewmodel.UserVM;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HaAnh
 */
public class ServletLogin extends HttpServlet {

    private static final String SERVLET_ADMIN_HOME = "/J3LP0002BookStore/admin/getAllBooks";
    private static final String SERVLET_HOME = "/J3LP0002BookStore/getBooks";
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
        log("processRequest");
        UserVM userVm = null;
        
        String url = UrlConstants.PAGE_LOGIN_ERROR;
        
        String userId = request.getParameter("userId").trim();
        String password = request.getParameter("password").trim();
        
        try {
            UserDAO dao = new UserDAO();
            UserDTO userDto = dao.login(userId, password);
            
            if (userDto != null) {
                
                UserProfileDAO profileDao = new UserProfileDAO();
                UserProfileDTO profileDto = profileDao.findByUserId(userId);
                
                userVm = new UserVM(userId, 
                        profileDto.getFullname(), 
                        profileDto.getEmail(), 
                        profileDto.getPhone(), 
                        userDto.getActive(), 
                        userDto.getRoleId());
                
                if (userDto.getRoleId().equals(DBUtils.ROLE_ADMIN)) {
                    url = SERVLET_ADMIN_HOME;
                } else {
                    url = SERVLET_HOME;
                }
                
                HttpSession session = request.getSession();
                session.setAttribute(UrlConstants.ATTR_CURRENT_USER, userVm);
            }
        } catch (NoSuchAlgorithmException | SQLException | NamingException ex) {
            url = UrlConstants.PAGE_ERROR;
            log(ex.getMessage(), ex);
        } catch (Exception ex) {
            url = UrlConstants.PAGE_ERROR;
            log(ex.getMessage(), ex);            
        }
        
        if (userVm != null) {
            response.sendRedirect(url);
        } else {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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

}
