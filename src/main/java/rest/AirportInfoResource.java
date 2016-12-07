/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * REST Web Service
 *
 * @author edipetres
 */
@Path("airportinfo")
public class AirportInfoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AirportInfoResource
     */
    public AirportInfoResource() {
    }

    /**
     * Retrieves representation of an instance of rest.AirportInfoResource
     *
     * @param airportCode
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{airportcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("airportcode") String airportCode) throws ParseException {

        final String API_CODE = "8668d011bb5f4919d41b7a7930099d09";
        final String URL = "https://airport.api.aero/airport/" + airportCode + "?user_key=" + API_CODE;
        String jsonResponse = null;

        try {
            URL obj = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + URL);
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
            System.out.println("Exception in AirportInfo. " + e.toString());
        }

        jsonResponse = jsonResponse.replace("callback(", "");
        jsonResponse = jsonResponse.substring(0, jsonResponse.lastIndexOf(")") - 1);
        jsonResponse += "}";
        
        JSONObject jsonObject = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            jsonObject = (JSONObject) parser.parse(jsonResponse);
        } catch (ParseException ex) {
            jsonObject = (JSONObject) parser.parse("{error:Cannot get airport info}");
        }

        if (jsonResponse != null) {
            return jsonResponse;
        }

        return "An error has occured getting the airport info.";
    }

    /**
     * PUT method for updating or creating an instance of AirportInfoResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
