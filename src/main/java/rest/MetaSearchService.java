/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import facade.AirlineDBFacade;
import facade.MetaSearchFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import request.Flights;
import utils.Tester;

/**
 * REST Web Service
 *
 * @author edipetres
 */
//Called the path 'allflights' to differentiate from individual airlines' APIs
@Path("searchflights")
public class MetaSearchService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Status() {
        return "Hello from API";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{from}/{date}/{tickets}")
    public String getJson(@PathParam("from") String fromAirport, @PathParam("date") String date, @PathParam("tickets") int tickets) {
        Flights fsearch = new Flights();
        MetaSearchFacade msf = new MetaSearchFacade();
        //JSONObject jsonFlights = fsearch.getFlightWebsite(fromAirport, date, tickets);
        JSONObject jsonFlights = msf.searchAllAirlines(fromAirport, null, date, tickets);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonFlights.toJSONString());
        String prettyJsonString = gson.toJson(je);

        return prettyJsonString;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{from}/{to}/{date}/{tickets}")
    public String getJsonBetweenTwoAirports(@PathParam("from") String fromAirport, @PathParam("to") String toAirport, @PathParam("date") String date, @PathParam("tickets") int tickets){
        Flights flightsearch = new Flights();
        JSONObject jsonToAndFromFlights = flightsearch.getFlightBetweenTwoAirports(fromAirport, toAirport, date, tickets);
    
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonToAndFromFlights.toJSONString());
        String prettyJsonString = gson.toJson(je);

        return prettyJsonString;      
    }        

   
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
