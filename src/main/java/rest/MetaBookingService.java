package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;


/**
 *
 * @author Acer
 */
@Path("metabooking")
public class MetaBookingService {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{airline}/{flightId}")
    public String book(@PathParam("airline") String airline, @PathParam("flightId") String flightId, String incoming) throws IOException {
        String jsonResponse = null;
        String url = null;
        if (airline.equals("SemestAir")) {
            url = "http://localhost:8081/SemesterProject/api/reservation/" + flightId;
        }
        if (airline.equals("AngularJS Airline")) {
            url = "http://airline-plaul.rhcloud.com/flightreservation/" + flightId;
        }
        if (airline == null) {
            JSONObject error = new JSONObject();
            error.put("httpEror", "404");
            error.put("errorCode", "10");
            error.put("message", "There is no airline");
            jsonResponse = gson.toJson(error);
            return jsonResponse;
        }

        URL serverUrl = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        try {
            BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            httpRequestBodyWriter.write(incoming);
            httpRequestBodyWriter.close();
        } catch (Exception e) {
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine + "\n");
        }
        in.close();
        jsonResponse = response.toString();
        String json = gson.toJson(jsonResponse);
        return json;
    }
}
