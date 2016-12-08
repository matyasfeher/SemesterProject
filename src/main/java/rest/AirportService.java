package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Airport;
import facade.AirportFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Acer
 */
@Path("airport")
public class AirportService {

    private static final AirportFacade airportFacade = new AirportFacade();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAirportByCode() {
        
        
        
        String jsonString = "{\"name\": \"Afghanistanw\", \"code\": \"AF\"}, {\"name\": \"Albania\", \"code\": \"AL\"},"
                + "  {\"name\": \"Algeria\", \"code\": \"DZ\"}, \n"
                + "  {\"name\": \"American Samoa\", \"code\": \"AS\"}, \n"
                + "  {\"name\": \"AndorrA\", \"code\": \"AD\"}, \n"
                + "  {\"name\": \"Angola\", \"code\": \"AO\"}, \n"
                + "  {\"name\": \"Anguilla\", \"code\": \"AI\"}, \n"
                + "  {\"name\": \"Antigua and Barbuda\", \"code\": \"AG\"}, \n"
                + "  {\"name\": \"Argentina\", \"code\": \"AR\"}, \n"
                + "  {\"name\": \"Armenia\", \"code\": \"AM\"}, \n"
                + "  {\"name\": \"Aruba\", \"code\": \"AW\"}, \n"
                + "  {\"name\": \"Australia\", \"code\": \"AU\"}, \n"
                + "  {\"name\": \"Austria\", \"code\": \"AT\"}, \n"
                + "  {\"name\": \"Azerbaijan\", \"code\": \"AZ\"}";
        
        String toJson = gson.toJson(jsonString);
        return toJson;

    }
}
