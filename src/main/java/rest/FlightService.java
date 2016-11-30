package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import entity.*;
import facade.AirlineCoreFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

/**
 *
 * @author Acer
 */
@Path("flight")
public class FlightService {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final AirlineCoreFacade acf = new AirlineCoreFacade();

    @GET
    @Path("{flightNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlightByFlightNumber(@PathParam("flightNumber") String flightNumber) {
        String json;
        Flight f = acf.getFlightByFlightNumber(flightNumber);
        json = gson.toJson(f);
        return json;

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{flightNumber}/{seats}/{flightTime}")
    public String addFlight(@PathParam("flightNumber") String flightNumber,
                            @PathParam("seats") String seats,
                            @PathParam("flightTime") int flightTime) {
        Flight f = new Flight(flightNumber, seats, flightTime);
        acf.addFlight(f);
        String json = gson.toJson(f);
        return "{ \"status\": \"Done\" } "+json;
    }
}
