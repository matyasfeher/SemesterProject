/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

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
@Path("allflights")
public class SearchService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SearchService
     */
    public SearchService() {
    }

    /**
     * Retrieves representation of an instance of rest.SearchService
     * @param jsonString
     * @return an instance of java.lang.String
     */
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Status() {
        return "Hello from API";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{from}/{date}/{tickets}")
    public String getJson(@PathParam("from") String fromAirport, @PathParam("date") String date, @PathParam("tickets") int tickets) {
        //TODO return proper representation object
        System.out.println("tickets = " + tickets);
        System.out.println("date = " + date);
        System.out.println("fromAirport = " + fromAirport);
        Flights fsearch = new Flights();
        JSONObject jsonFlights = fsearch.getFlightSite(fromAirport, date, tickets);
        return jsonFlights.toJSONString();
    }

    /**
     * PUT method for updating or creating an instance of SearchService
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
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
