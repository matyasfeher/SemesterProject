/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.HttpRequestTester;

/**
 *
 * @author edipetres
 */
public class MetaSearchFacade {

    List<String> airlineList = new ArrayList();
    
//    public static void main(String[] args) {
//        MetaSearchFacade mtf = new MetaSearchFacade();
//        JSONObject searchAllAirlines = mtf.searchAllAirlines("CPH", "BUD", "2017-01-01T08:00:00.000Z", 2);
//        System.out.println("searchAllAirlines = " + searchAllAirlines);
//        
//        
//    }

    public MetaSearchFacade() {
        airlineList.add("http://localhost:8080/SemesterProject/api/flights/");
        airlineList.add("http://airline-plaul.rhcloud.com/api/flightinfo/");
    }

    public JSONObject searchAllAirlines(String from, String to, String date, int passengers) {
        
        JSONObject responseObj = new JSONObject();
        JSONArray responseArray = new JSONArray();
        
        String urlExtension = null;
        if (to != null) {
            urlExtension = from + "/" + to + "/" + date + "/" + passengers;
        } else {
            urlExtension = from + "/" + date + "/" + passengers;
        }
        

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(airlineList.size());

        List<Future<JSONObject>> resultList = new ArrayList<>();

        //Start worker threads with each airline
        for (String airlineUrl : airlineList) {
            String URL = airlineUrl + urlExtension;
            WorkerThread worker = new WorkerThread(URL);
            Future<JSONObject> result = executor.submit(worker);
            resultList.add(result);
        }

        //Loop through results and build up the response object from them
        for (Future<JSONObject> future : resultList) {
            try {
                JSONObject singleResponse = future.get();
                if (singleResponse != null) {
                    responseArray.add(singleResponse);
                }
                System.out.println("Future result is - " + " - " + future.get() + "; And Task done is " + future.isDone());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        executor.shutdown();
        
        
        responseObj.put("results", responseArray);
        return responseObj;
    }

    private JSONObject requestFlightsFromAirline(String url, String from, String to, String date, int passengers) {

        JSONParser parser = new JSONParser();

        if (to != null) {
            url = url + from + "/" + to + "/" + date + "/" + passengers;
        } else {
            url = url + from + "/" + date + "/" + passengers;
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
                    errorResponse.append(errorInputLine);
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
                    response.append(inputLine);
                }
                jsonResponse = response.toString();
                System.out.println("JSON response\n" + jsonResponse);
            } catch (Exception ex) {
                try {
                    return (JSONObject) parser.parse(JSONerrorResponse);
                } catch (ParseException ex1) {
                    Logger.getLogger(MetaSearchFacade.class.getName()).log(Level.SEVERE, null, ex1);
                    return null;
                }
            }

            JSONObject jsonObjResponse = null;
            try {
                jsonObjResponse = (JSONObject) parser.parse(jsonResponse);
            } catch (ParseException ex) {
                Logger.getLogger(MetaSearchFacade.class.getName()).log(Level.SEVERE, null, ex);
            }

            return jsonObjResponse;
        } catch (IOException ex) {
            Logger.getLogger(HttpRequestTester.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
