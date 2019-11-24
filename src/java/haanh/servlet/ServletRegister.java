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
import haanh.utils.DataUtils;
import haanh.utils.UrlConstants;
import haanh.viewmodel.UserVMError;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HaAnh
 */
public class ServletRegister extends HttpServlet {

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
        
        String url = UrlConstants.PAGE_REGISTER;
        
        try {
            //Get parameters
            String userId = request.getParameter("userId").toLowerCase().trim();
            String password = request.getParameter("password").trim();
            String confirm = request.getParameter("confirm").trim();
            String fullname = request.getParameter("fullname").trim();
            String email = request.getParameter("email").trim().toLowerCase();
            String phone = request.getParameter("phone").trim();
            
            UserVMError error = validateInsertUserData(userId, password, confirm, fullname, email, phone);
            
            if (error == null) {
                UserDAO userDAO = new UserDAO();
                UserProfileDAO profileDAO = new UserProfileDAO();
                
                UserDTO user = new UserDTO(userId, password, true, DBUtils.ROLE_USER);
                UserProfileDTO profile = new UserProfileDTO(userId, fullname, email, phone);
                
                userDAO.insertUser(user);
                profileDAO.insertUser(profile);
                
                url = UrlConstants.PAGE_LOGIN;
                request.setAttribute(UrlConstants.ATTR_MESSAGE, "Register successfully!");
            } else {
                request.setAttribute(UrlConstants.ATTR_ERROR, error);
            }
        } catch (NamingException | SQLException | NoSuchAlgorithmException ex) {
            url = UrlConstants.PAGE_ERROR;
            log(ex.getMessage(), ex);
        }
        
        request.getRequestDispatcher(url).forward(request, response);
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

    private UserVMError validateInsertUserData(String userId, String password, String confirm,
            String fullname, String email, String phone)
            throws NamingException, SQLException {
        UserVMError error = new UserVMError();
        boolean err = false;
        int code;
        //validate User Id
        code = DataUtils.validateUserId(userId);
        if (code == DataUtils.DATA_INVALID) {
            error.setUserIdErr("User Id length 5-15");
            err = true;
        } else if (code == DataUtils.ERR_USER_ID_EXISTED) {
            error.setUserIdErr("User Id existed, please choose another");
            err = true;
        }
        //validate password
        if (password.length() < 5 || password.length() > 30) {
            error.setPasswordErr("Password length 5-30");
            err = true;
        }
        //validate confirm
        if (error.getPasswordErr() == null) {
            if (!confirm.equals(password)) {
                error.setConfirmErr("Confirm must match password");
                err = true;
            }
        }
        //validate fullname
        if (fullname.length() == 0) {
            error.setFullnameErr("Fullname required");
            err = true;
        }
        //validate email
        if (!DataUtils.validateEmailFormat(email)) {
            error.setEmailErr("Email format abc@xy.xy[.xy]");
            err = true;
        } else {
            UserProfileDAO dao = new UserProfileDAO();
            if (dao.checkEmailExist(email)) {
                error.setEmailErr("Email existed, please choose another");
                err = true;
            }
        }
        //validate phone
        if (!DataUtils.validatePhoneFormat(phone)) {
            error.setPhoneErr("Phone allows digits, length: 8-15");
            err = true;
        } else {
            UserProfileDAO dao = new UserProfileDAO();
            if (dao.checkPhoneExist(phone)) {
                error.setPhoneErr("Phone existed, please choose another");
                err = true;
            }
        }
        if (!err) {
            error = null;
        }
        return error;
    }
}
