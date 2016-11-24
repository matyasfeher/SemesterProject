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
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;

/**
 *
 * @author Nicolai
 */
public class Flights {
    
//    public String getFlights(String depAirport, String depDate, int tickets){
//        
//    }
    
    public static void main(String[] args) throws Exception {
        getFlightSite();
        
    }
    private static void getFlightSite() throws Exception{
        
        try {
        String url= "http://airline-plaul.rhcloud.com/api/flightinfo/cph/2017-01-06T08:00:00.000Z/2";
        
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
        
        while((inputLine = in.readLine()) != null){
            response.append(inputLine + "\n");
        }
        in.close();
        System.out.println(response.toString());
        
        } catch (Exception e) {
            e.printStackTrace();}
        
    }
}
