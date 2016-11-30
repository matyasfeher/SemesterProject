package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Airport;
import facade.AirportFacade;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Acer
 */
@Path("airport")
public class AirportService {
    private static final AirportFacade airportFacade = new AirportFacade();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAirportByCode(@PathParam("code") String code){
    Airport a = airportFacade.getAirportByCode(code);
    String json = gson.toJson(a);
    return json;
    }
}
