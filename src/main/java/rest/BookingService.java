package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.FlightInstance;
import facade.AirlineDBFacade;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
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
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        
        if (fi == null) {
            JSONObject errorObject = new JSONObject();
            errorObject.put("httpError", 400);
            errorObject.put("errorCode", 1);
            errorObject.put("message", "Booking unsuccessful.");
            json = gson.toJson(errorObject);
            return json;
        } else {
            try {
                requestObject = (JSONObject) jparser.parse(postJson);
            } catch (ParseException ex) {
                requestObject.put("httpError", 400);
                requestObject.put("errorCode", 3);
                requestObject.put("message", "POST request is not a JSON format. Parse exception.");
                json = gson.toJson(requestObject);
                return json;
            }
            if (Integer.parseInt(requestObject.get("numberOfSeats").toString()) > Integer.parseInt(fi.getAvailableSeats())) {
                System.out.println("NUMBER OF SEATS REQUESTED: "+ requestObject.get("numberOfSeats"));
                System.out.println("As integer: "+ Integer.parseInt(requestObject.get("numberOfSeats").toString()) );
                JSONObject errorObject = new JSONObject();
                errorObject.put("httpError", 400);
                errorObject.put("errorCode", 2);
                errorObject.put("message", "Not ennough seats available.");
                json = gson.toJson(errorObject);
                return json;
            } else {
                JSONObject responseObject = new JSONObject();
                responseObject.put("flightNumber", fi.getFlightId());
                responseObject.put("origin", fi.getFlight().getFrom().getCity() + " (" + fi.getFlight().getFrom().getCode() +")");
                responseObject.put("destination", fi.getFlight().getTo().getCity() + " (" + fi.getFlight().getTo().getCode() +")");
                responseObject.put("date", df.format(fi.getDate()));
                responseObject.put("flightTime", fi.getTime());
                responseObject.put("numberOfSeats", requestObject.get("numberOfSeats"));
                responseObject.put("reserveeName", requestObject.get("passengers[0].firstName"));
                responseObject.put("passengers", requestObject.get("passengers"));
                
                json = gson.toJson(responseObject);
                return json;
            }
        }
    }
}
