/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.*;
import facade.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Persistence;
import org.json.simple.JSONObject;
import request.Flights;

/**
 *
 * @author edipetres
 */
public class Tester {

    public static void main(String[] args) {
//<<<<<<< HEAD
//        //Persistence.generateSchema("pu", null);
////        List<Flight> list;
//////        AirlineCoreFacade acf = new AirlineCoreFacade();
//=======
////        //Persistence.generateSchema("pu", null);
////        List<Flight> list;
////     //   AirlineCoreFacade acf = new AirlineCoreFacade();
//>>>>>>> origin/master
////        list = acf.getAllFlight();
////        Airline a = new Airline("SemestAir", list);
////        acf.addAirline(a);

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
