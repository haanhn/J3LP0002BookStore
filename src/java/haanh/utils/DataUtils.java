/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author HaAnh
 */
public class DataUtils {

    public static final String FILE_PNG = ".png";
    public static final String FILE_JPG = ".jpg";
    public static final String FILE_JPEG = ".jpeg";

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
}
