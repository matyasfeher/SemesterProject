package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.FlightInstance;
import facade.AirlineDBFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Acer
 */
@Path("reservation")
public class BookingService {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final AirlineDBFacade facade = new AirlineDBFacade();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{flightId}")
    public String book(@PathParam("flightId") String flightId, String postJson) {
        JSONParser jparser = new JSONParser();
        JSONObject requestObject = new JSONObject();
        String json;
        FlightInstance fi = facade.getFlightInstanceByFlightId(flightId);
        if (fi == null) {
            JSONObject errorObject = new JSONObject();
            errorObject.put("Error", "404");
            errorObject.put("Message", "There is no such flight");
            json = gson.toJson(errorObject);
            return json;
        } else {
            try {
                requestObject = (JSONObject) jparser.parse(postJson);
            } catch (ParseException ex) {
                requestObject.put("Error", ex.getMessage());
                json = gson.toJson(requestObject);
                return json;
            }
            if (requestObject.get("numberOfSeats") == null) {
                JSONObject errorObject = new JSONObject();
                errorObject.put("Error", "404");
                errorObject.put("Message", "Number of seats is null");
                json = gson.toJson(errorObject);
                return json;
            } else {
                JSONObject responseObject = new JSONObject();
                responseObject.put("flightNumber", fi.getFlightId());
                responseObject.put("origin", fi.getFlight().getFrom());
                responseObject.put("destination", fi.getFlight().getTo());
                responseObject.put("destination", fi.getDate());
                responseObject.put("flightTime", fi.getTime());
                responseObject.put("numberOfSeats", requestObject.get("numberOfSeats"));
                responseObject.put("passengers", requestObject.get("passengers"));
                json = gson.toJson(responseObject);
                return json;
            }
        }
    }
}
