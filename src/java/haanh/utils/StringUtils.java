/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author HaAnh
 */
public class StringUtils {
    
    public static String getSHA256HashedString(String str) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = digest.digest(str.getBytes());
        String hashed = Base64.getEncoder().encodeToString(bytes);
        return hashed;
    }
}
