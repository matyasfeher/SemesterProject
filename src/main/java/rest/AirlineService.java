package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import entity.*;
import facade.AirlineDBFacade;
import facade.AirportFacade;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Acer
 */
@Path("flights")
public class AirlineService {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final AirlineDBFacade acf = new AirlineDBFacade();
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
    @Path("{from}/{date}/{tickets}")
    public String getFlight(@PathParam("from") String from,
            @PathParam("date") String date,
            @PathParam("tickets") String tickets) {

        String json = null;
        JSONArray flights = new JSONArray();

        AirlineDBFacade facade = new AirlineDBFacade();
        List<FlightInstance> flightList = facade.getFlightInstancesBetweenAirports(from, null, date);
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


        for (FlightInstance fi : flightList) {
            JSONObject singleFlight = new JSONObject();
            singleFlight.put("flightID", fi.getFlightId());
            singleFlight.put("flightNumber", fi.getFlight().getFlightNumber());
            singleFlight.put("date", df.format(fi.getDate()));
            singleFlight.put("numberOfSeats", fi.getAvailableSeats());
            singleFlight.put("totalPrice", fi.getPrice());
            singleFlight.put("travelTime", fi.getFlight().getFlightTime());
            singleFlight.put("origin", fi.getFlight().getFrom().getCode());
            //singleFlight.put("originName", fi.getFlightFromTo().getFrom().getCity());
            singleFlight.put("destination", fi.getFlight().getTo().getCode());
            singleFlight.put("destinationName", fi.getFlight().getTo().getCity());
            flights.add(singleFlight);
        }

        JSONObject object = new JSONObject();
        object.put("airline", "SemestAir");
        object.put("flights", flights);

        json = gson.toJson(object);

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

        AirlineDBFacade facade = new AirlineDBFacade();
        List<FlightInstance> flightList = facade.getFlightInstancesBetweenAirports(from, to, date);
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


        for (FlightInstance fi : flightList) {
            JSONObject singleFlight = new JSONObject();
            singleFlight.put("flightID", fi.getFlightId());
            singleFlight.put("flightNumber", fi.getFlight().getFlightNumber());
            singleFlight.put("date", df.format(fi.getDate()));
            singleFlight.put("numberOfSeats", fi.getAvailableSeats());
            singleFlight.put("totalPrice", fi.getPrice());
            singleFlight.put("travelTime", fi.getFlight().getFlightTime());
            singleFlight.put("origin", fi.getFlight().getFrom().getCode());
            //singleFlight.put("originName", fi.getFlightFromTo().getFrom().getCity());
            singleFlight.put("destination", fi.getFlight().getTo().getCode());
            singleFlight.put("destinationName", fi.getFlight().getTo().getCity());
            flights.add(singleFlight);
        }

        JSONObject object = new JSONObject();
        object.put("airline", "SemestAir");
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
