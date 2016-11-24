package request;

import entity.Airport;
import facade.AirportFacade;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Acer
 */
public class AirportRequest {

    public static void main(String[] args) throws Exception {
        getAirports();

    }

    private static void getAirports() throws Exception {
        String jsonResponse = null;
        JSONArray airportList;
        AirportFacade airportFacade = new AirportFacade();
        try {
            String url = "https://iatacodes.org/api/v6/airports?api_key=7b9e8945-7b4d-41ab-b0cb-ee213f47b5b2";
 
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("User-agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            in.close();
            jsonResponse = response.toString();

        } catch (Exception e) {
            e.getMessage();
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
        airportList = (JSONArray) jsonObject.get("response");
        for (int i = 0; i < airportList.size(); i++) {
            JSONObject airport = (JSONObject) parser.parse(airportList.get(i).toString());
            String cc = (String) airport.get("country_code");
            String name = (String) airport.get("name");
            String code = (String) airport.get("code");
            Airport a = new Airport(code, name, cc);
            airportFacade.addAirports(a);
        }
       
       
       
    }

}
