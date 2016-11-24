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
import org.json.simple.JSONObject;
import request.Flights;

/**
 *
 * @author edipetres
 */
public class Tester {
    
    public static void main(String[] args) {
        String validDate = "2017-01-06T08:00:00.000Z";
        Flights flights = new Flights();
        JSONObject flightSite = flights.getFlightSite("cph", validDate, 2);
        System.out.println("Airline: "+flightSite.get("airline"));
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
