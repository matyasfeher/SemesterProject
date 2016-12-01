/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import utils.Tester;

/**
 *
 * @author Nicolai
 */
public class Flights {


    public JSONObject getFlightWebsite(String fromAirport, String date, int passangers) {
        String jsonResponse = null;

        //validate date
        if (!validateDate(date)) {
            JSONObject json = new JSONObject();
            json.put("httpError", "400");
            json.put("errorCode", "3");
            json.put("message", "Invalid date format.");
            return json;
        }
        try {
            String url = "http://airline-plaul.rhcloud.com/api/flightinfo/" + fromAirport + "/" + date + "/" + passangers;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            jsonResponse = response.toString();
            in.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Work with received JSON object
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(jsonResponse);
            System.out.println("Airline: " + jsonObject.get("airline"));
            return jsonObject;
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(Flights.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONObject json = new JSONObject();
        json.put("httpError", "500");
        json.put("errorCode", "4");
        json.put("message", "Unknown error in flights().");
        return json;
    }

    public JSONObject getFlightBetweenTwoAirports(String fromAirport, String toAirport, String date, int passengers) {
        String jsonResponse = null;

        //validate date
        if (!validateDate(date)) {
            JSONObject json = new JSONObject();
            json.put("httpError", "400");
            json.put("errorCode", "3");
            json.put("message", "Invalid date format.");
            return json;
        }
        try {
            String url = "http://airline-plaul.rhcloud.com/api/flightinfo/" + fromAirport + "/" + toAirport + "/" + date + "/" + passengers;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("USER_AGENT", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response code : " + responseCode);

            BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            jsonResponse = response.toString();
            input.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        
        try {
            jsonObject = (JSONObject) parser.parse(jsonResponse);
            System.out.println("Airline: " + jsonObject.get("airline"));
            return jsonObject;
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(Flights.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONObject json = new JSONObject();
        json.put("httpError", "500");
        json.put("errorCode", "4");
        json.put("message", "Unknown error in flights().");
        return json;
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
