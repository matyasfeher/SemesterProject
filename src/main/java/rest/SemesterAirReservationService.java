/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entity.FlightInstance;
import facade.AirlineDBFacade;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author edipetres
 */
@Path("reservation")
public class SemesterAirReservationService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ReservationResource
     */
    public SemesterAirReservationService() {
    }

    /**
     * Retrieves representation of an instance of
     * rest.SemesterAirReservationService
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        return "Hey from reservation.";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{fligntID}")
    public String reserveFlight(@PathParam("flightID") String flightID) {
        AirlineDBFacade adbf = new AirlineDBFacade();
        FlightInstance flightInstance = adbf.getFlightInstanceByFlightID(flightID);
        
        
        
        return "";
    }

    /**
     * PUT method for updating or creating an instance of
     * SemesterAirReservationService
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
