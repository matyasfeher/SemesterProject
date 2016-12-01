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
import facade.AirportFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Acer
 */
@Path("flight")
public class FlightService {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final AirlineCoreFacade acf = new AirlineCoreFacade();
    private static final AirportFacade af = new AirportFacade();

    @GET
    @Path("{flightNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlightByFlightNumber(@PathParam("flightNumber") String flightNumber) {
        String json;
        Flight f = acf.getFlightByFlightNumber(flightNumber);
        json = gson.toJson(f);
        return json;

    }

    @GET
    @Path("{from}/{to}/{date}/{tickets}")
    public String getFlight(@PathParam("from") String from,
            @PathParam("to") String to,
            @PathParam("date") String date,
            @PathParam("tickets") String tickets) {
        
        String json = null;
        JSONArray flights = new JSONArray();
        JSONObject flight = new JSONObject();
        flights.add(flight);

        flight.put("flightID", "");
        flight.put("flightNumber", "");
        flight.put("date", "");
        flight.put("numberOfSeats", "");
        flight.put("totalPrice", "");
        flight.put("travelTime", "");
        flight.put("origin", "");
        flight.put("destination", "");

        JSONObject object = new JSONObject();
        object.put("airline", "");
        object.put("flights", flights);

        json = gson.toJson(object);

        return json;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{flightNumber}/{seats}/{flightTime}/{from}/{to}")
    public String addFlight(@PathParam("flightNumber") String flightNumber,
            @PathParam("seats") String seats,
            @PathParam("flightTime") int flightTime,
            @PathParam("from") String from,
            @PathParam("to") String to) {
        Airport tempFrom = af.getAirportByCode(from);
        Airport tempTo = af.getAirportByCode(to);
        Flight f = new Flight(flightNumber, seats, flightTime, tempFrom, tempTo);
        acf.addFlight(f);
        String json = gson.toJson(f);
        return "{ \"status\": \"Done\" } " + json;
    }
}
