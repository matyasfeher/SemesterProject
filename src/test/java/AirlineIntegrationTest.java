/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nicolai
 */

import org.junit.BeforeClass;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.net.MalformedURLException;
import java.util.Random;
import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import static org.hamcrest.Matchers.*;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
//import test.utils.EmbeddedTomcat;




public class AirlineIntegrationTest {
    
    @Test
    public void testFlightExistBetweenTwoAirports(){
        given()
                .contentType("application/json")
                .when()
                .get("api/allflights/cph/stn/2017-01-01T08:00:00.000Z/1")
                .then()
                .statusCode(200)
                .body("flightID", contains(""),
                        "flightNumber", contains(""),
                        "date", equalTo("2017-01-01T08:00:00.000Z/1"),
                        "numberOfSeats", greaterThan(0),
                        "totalPrice", greaterThan(0),
                        "traveltime", greaterThan(0),
                        "origin", equalToIgnoringCase("cph"),
                        "destination", equalToIgnoringCase("stn"));
    }
    
    @Test
    public void testFlightsFromAnAirport(){
        given()
                .contentType("application/json")
                .when()
                .get("api/allflights/cph/2017-01-01T08:00:00.000Z/1")
                .then()
                .statusCode(200)
                .body("flightID", contains(""),
                        "flightNumber", contains(""),
                        "date", equalTo("2017-01-01T08:00:00.000Z/1"),
                        "numberOfSeats", greaterThan(0),
                        "totalPrice", greaterThan(0),
                        "traveltime", greaterThan(0),
                        "origin", equalToIgnoringCase("cph"));
    }
    
    @Test
    public void testBooking(){
        given()
                .contentType("application/json")
                .when()
                .post("api/reservation/flightId")
                .then()
                .statusCode(200)
                .body("flightID", contains(""),
                        "numberOfSeats", greaterThan(0),
                        "reserveeName", contains(""),
                        "reservePhone", contains(""),
                        "reserveeEmail", contains(""),
                        "passengers[0].firstName", contains(""),
                        "passengers[0].lastName", contains(""));
    }
}