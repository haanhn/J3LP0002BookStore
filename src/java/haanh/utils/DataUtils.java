/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.utils;

import haanh.user.UserDAO;
import haanh.viewmodel.UserVM;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author HaAnh
 */
public class DataUtils {

    public static final String FILE_PNG = ".png";
    public static final String FILE_JPG = ".jpg";
    public static final String FILE_JPEG = ".jpeg";
    
    
    //Code for validation
    public static final int ERR_USER_ID_EXISTED = -2;
    public static final int DATA_VALID = 1;
    public static final int DATA_INVALID = -1;

    public static int validateUserId(String userId) {
        int code = DATA_VALID;
        if (userId.length() < 5 || userId.length() > 15) {
            code = DATA_INVALID;
        } else {
            UserDAO dao = new UserDAO();
            boolean existed;
            try {
                existed = dao.checkUserIdExist(userId);
                if (existed) {
                    code = ERR_USER_ID_EXISTED;
                }
            } catch (NamingException | SQLException ex) {
                code = DATA_INVALID;
            }
        }
        return code;
    }
    
    public static boolean validateEmailFormat(String email) {
        boolean valid = false;
        String regex = "[a-zA-Z0-9]{5,30}@[a-zA-Z0-9]{1,20}(\\.[a-zA-Z0-9]{1,20}){1,2}";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            valid = true;
        }
        return valid;
    }

    public static boolean validatePhoneFormat(String phone) {
        String regex = "[0-9]{8,15}";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    
    public static boolean validateDate(String dateStr) {
        boolean valid = true;
        if (dateStr == null || dateStr.length() == 0) {
            return false;
        }

        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            valid = false;
        }
        return valid;
    }

    public static Date getDateFromString(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            java.util.Date dateUtil = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(dateUtil.getTime());
            Date date = new Date(cal.getTimeInMillis());
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static boolean validatePhotoFormat(String photo) {
        boolean valid = false;
        if (photo != null && photo.length() >= 0) {
            photo = photo.toLowerCase();
            if (photo.endsWith(FILE_PNG) || photo.endsWith(FILE_JPG) || photo.endsWith(FILE_JPEG)) {
                valid = true;
            }
        }
        return valid;
    }

    public static String getRandomFilename(FileItem item) {
        String itemName = item.getName();
        String extension = itemName.substring(itemName.lastIndexOf("."));
        String random = UUID.randomUUID().toString();
        String filename = random + extension;
        return filename;
    }
    
    public static String getRandomFilename(Part part) {
        String submittedFilename = part.getSubmittedFileName();
        String extension = submittedFilename.substring(submittedFilename.lastIndexOf("."));
        String random = UUID.randomUUID().toString();
        String filename = random + extension;
        return filename;
    }
    
    public static UserVM getCurrentUser(HttpServletRequest request) {
        UserVM user = null;
        HttpSession session = request.getSession(false);
        if (session != null) {
            user = (UserVM) session.getAttribute(UrlConstants.ATTR_CURRENT_USER);
        }
        return user;
    }
}
