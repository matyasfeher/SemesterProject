/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import org.json.simple.JSONArray;

/**
 *
 * @author edipetres
 */
public class HttpRequestTester {

    public static void main(String[] args) {

        List<String> airlineList = new ArrayList();
        //airlineList.add("api/flights");
        airlineList.add("http://airline-plaul.rhcloud.com/api/flightinfo/");
        String fromAirport = "CPH";
        String toAirport = "bud";
        int passengers = 1;
        String date = "2017-01-01";

        JSONArray responseArray = new JSONArray();

        for (String airlineUrl : airlineList) {
            String response = requestFlightsFromAirline(airlineUrl, fromAirport, toAirport, date, passengers);
            responseArray.add(response);
        }
        
        System.out.println("RESPONSE ARRAY: \n" + responseArray.toJSONString());

    }

    public static String requestFlightsFromAirline(String url, String from, String to, String date, int passengers) {

        if (!to.equals("")) {
            url = url + from + "/" + to + "/" + date + "T00:00:00.000Z" + "/" + passengers;
        } else {
            url = url + from + "/" + date + "T00:00:00.000Z" + "/" + passengers;
        }

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("USER_AGENT", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response code : " + responseCode);

            //Create error response text when response code is different from 200
            String JSONerrorResponse = "";
            try {
                BufferedReader errorinput = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String errorInputLine;
                StringBuilder errorResponse = new StringBuilder();
                while ((errorInputLine = errorinput.readLine()) != null) {
                    errorResponse.append(errorInputLine).append("\n");
                }
                JSONerrorResponse = errorResponse.toString();
            } catch (NullPointerException ex) {
                
            }

            //Create response text with status 200 OK
            StringBuilder response;
            String jsonResponse;
            try (BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = input.readLine()) != null) {
                    response.append(inputLine).append("\n");
                }
                jsonResponse = response.toString();
            } catch (Exception ex) {
                return JSONerrorResponse;
            }

            return jsonResponse;
        } catch (IOException ex) {
            Logger.getLogger(HttpRequestTester.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
    }
}
