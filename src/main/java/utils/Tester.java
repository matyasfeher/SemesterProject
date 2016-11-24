/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edipetres
 */
public class Tester {
    
    public static void main(String[] args) {
        String validDate = "2016-01-06T08:00:00.000Z";
        String invalid = "24-12-2016";
        System.out.println("Valid date result " + validateDate(validDate));
        System.out.println("Invalid date result " + validateDate(invalid));
        
    }
    
    //Validates that the date confirms to
    public static boolean validateDate(String uncertainDate) {
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = sdfISO.parse(uncertainDate);
        } catch (ParseException ex) {
            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
