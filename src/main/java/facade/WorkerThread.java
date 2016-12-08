/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author edipetres
 */
public class WorkerThread implements Callable<JSONObject> {

    String URL;

    WorkerThread(String url) {
        URL = url;
    }

    @Override
    public JSONObject call() throws Exception {
        JSONParser parser = new JSONParser();
        String jsonResponse = null;

        try {
            URL obj = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("USER_AGENT", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + URL);
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
            try (BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = input.readLine()) != null) {
                    response.append(inputLine);
                }
                jsonResponse = response.toString();
                System.out.println("JSON response\n" + jsonResponse);
            } catch (Exception ex) {

            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        
        JSONObject jsonOBJResponse = (JSONObject) parser.parse(jsonResponse);
        if (jsonOBJResponse != null) {
            return jsonOBJResponse;
        }
        return null;
    }

}