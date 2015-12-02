/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import com.jayway.restassured.parsing.Parser;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.ApplicationConfig;
import static test.BackendTest.server;

/**
 *
 * @author ichti (Simon T)
 */ 
public class RestTest {
    File fiSchemaFile = new File("resources/flightinfo-schema.json");
    File fiErrorSchemaFile = new File("resources/flightinfo-error-schema.json");
    File flightsSchemaFile = new File("resources/flights-schema.json");
    File flightsErrorSchemaFile = new File("resources/flightinfo-error-schema.json");
    //File reservationSchemaFile = new File("resources/reservation-schema.json"); TODO
    static Server server;

    public RestTest() {
        baseURI = "http://localhost:8083";
        defaultParser = Parser.JSON;
        basePath = "/api";
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new Server(8083);
        ServletHolder servletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
        servletHolder.setInitParameter("javax.ws.rs.Application", ApplicationConfig.class.getName());
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(servletHolder, "/api/*");
        server.setHandler(contextHandler);
        server.start();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        server.stop();
        //waiting for all the server threads to terminate so we can exit gracefully
        server.join();
    }
    //api/flightinfo/CPH/2016-01-07T00:00:00.000Z/2
    //Test flight search
    //api/flightinfo/:from/:date/:numTickets
    //api/flightinfo/:from/:to/date/:numTickets
    
    //api/flightinfo tests
    @Test
    public void flightinfoFromHappyPath() throws ParseException {
        String from = "CPH";
        String date = "2016-01-07T00:00:00.000Z"; 
        //Cannot test for date, since we search for a date, but is given back unknown time of that date
        int tickets = 2;
        given().
                contentType("application/json").
                when().
                get("/flightinfo/"+from+"/"+date +"/"+tickets).
                then().
                statusCode(200).
                body("flights[0].numberOfSeats", equalTo(2)).
                body("flights[0].origin", equalTo("CPH")).
                body(matchesJsonSchemaInClasspath("flightinfo-schema.json"));
    }
    
    @Test
    public void flightinfoFromToHappyPath() throws ParseException {
        String from = "CPH";
        String to = "SXF";
        String date = "2016-01-07T00:00:00.000Z"; 
        //Cannot test for date, since we search for a date, but is given back unknown time of that date
        int tickets = 2;
        given().
                contentType("application/json").
                when().
                get("/flightinfo/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(200).
                body("flights[0].numberOfSeats", equalTo(2)).
                body("flights[0].origin", equalTo("CPH")).
                body("flights[0].destination", equalTo("SXF")).
                body(matchesJsonSchema(fiSchemaFile));
    }        
    
    @Test
    public void flightinfoUnknownFrom() {
        String from = "CP";
        String date = "2016-01-07T00:00:00.000Z";
        int tickets = 2;
        given().
                contentType("application/json").
                when().
                get("/flightinfo/"+from+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(1)).
                body(matchesJsonSchema(fiErrorSchemaFile));
    }
    @Test
    public void flightinfoUnknownTo() {
        String from = "CPH";
        String to = "LKÅÅ";
        String date = "2016-01-07T00:00:00.000Z";
        int tickets = 2;
        given().
                contentType("application/json").
                when().
                get("/flightinfo/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(1)).
                body(matchesJsonSchema(fiErrorSchemaFile));
    }
    @Test
    public void flightinfoErrorDate() {
        String from = "CPH";
        String to = "SXF";
        String date = "2016-01-0700:00:00.000Z"; //T is missing from this date
        int tickets = 2;
        given().
                contentType("application/json").
                when().
                get("/flightinfo/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(3)).
                body(matchesJsonSchema(fiErrorSchemaFile));
    }
    @Test
    public void flightinfoNegativeTickets() {
        String from = "CPH";
        String to = "SXF";
        String date = "2016-01-07T00:00:00.000Z";
        int tickets = -2;
        given().
                contentType("application/json").
                when().
                get("/flightinfo/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(3)).
                body(matchesJsonSchema(fiErrorSchemaFile));
    }
    @Test
    public void flightinfoStringTickets() {
        String from = "CPH";
        String to = "SXF";
        String date = "2016-01-07T00:00:00.000Z";
        String tickets = "Err";
        given().
                contentType("application/json").
                when().
                get("/flightinfo/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(3)).
                body(matchesJsonSchema(fiErrorSchemaFile));
    }
    @Test
    public void flightinfoFarPastDate() {
        String from = "CPH";
        String to = "SXF";
        String date = "1980-01-07T00:00:00.000Z"; //Using date far in the past, hopefully no flight shows up
        int tickets = 1;
        given().
                contentType("application/json").
                when().
                get("/flightinfo/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(1)).
                body(matchesJsonSchema(fiErrorSchemaFile));
    }
    @Test
    public void flightinfoNoTickets() {
        String from = "CPH";
        String to = "SXF";
        String date = "2016-01-07T00:00:00.000Z";
        int tickets = 56833; //No planes can hold that many passengers!
        given().
                contentType("application/json").
                when().
                get("/flightinfo/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(2)).
                body(matchesJsonSchema(fiErrorSchemaFile));
    }
    //api/flights (internal api)
    @Test
    public void flightsFromHappyPath() throws ParseException {
        String from = "CPH";
        String date = "2016-01-07T00:00:00.000Z"; 
        //Cannot test for date, since we search for a date, but is given back unknown time of that date
        int tickets = 2;
        given().
                contentType("application/json").
                when().
                get("/flights/"+from+"/"+date +"/"+tickets).
                then().
                statusCode(200).
                body("[0].numberOfSeats", equalTo(2)).
                body("[0].origin", equalTo("CPH")).
                body(matchesJsonSchema(flightsSchemaFile));
    }
    
    @Test
    public void flightsFromToHappyPath() throws ParseException {
        String from = "CPH";
        String to = "SXF";
        String date = "2016-01-07T00:00:00.000Z"; 
        //Cannot test for date, since we search for a date, but is given back unknown time of that date
        int tickets = 2;
        given().
                contentType("application/json").
                when().
                get("/flights/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(200).
                body("[0].numberOfSeats", equalTo(2)).
                body("[0].origin", equalTo("CPH")).
                body("[0].destination", equalTo("SXF")).
                body(matchesJsonSchema(flightsSchemaFile));
    }        
    
    @Test
    public void flightsUnknownFrom() {
        String from = "CP";
        String date = "2016-01-07T00:00:00.000Z";
        int tickets = 2;
        given().
                contentType("application/json").
                when().
                get("/flights/"+from+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(1)).
                body(matchesJsonSchema(flightsErrorSchemaFile));
    }
    @Test
    public void flightsUnknownTo() {
        String from = "CPH";
        String to = "LKÅÅ";
        String date = "2016-01-07T00:00:00.000Z";
        int tickets = 2;
        given().
                contentType("application/json").
                when().
                get("/flights/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(1)).
                body(matchesJsonSchema(flightsErrorSchemaFile));
    }
    @Test
    public void flightsErrorDate() {
        String from = "CPH";
        String to = "SXF";
        String date = "2016-01-0700:00:00.000Z"; //T is missing from this date
        int tickets = 2;
        given().
                contentType("application/json").
                when().
                get("/flights/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(3)).
                body(matchesJsonSchema(flightsErrorSchemaFile));
    }
    @Test
    public void flightsNegativeTickets() {
        String from = "CPH";
        String to = "SXF";
        String date = "2016-01-07T00:00:00.000Z";
        int tickets = -2;
        given().
                contentType("application/json").
                when().
                get("/flights/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(3)).
                body(matchesJsonSchema(flightsErrorSchemaFile));
    }
    @Test
    public void flightsStringTickets() {
        String from = "CPH";
        String to = "SXF";
        String date = "2016-01-07T00:00:00.000Z";
        String tickets = "Err";
        given().
                contentType("application/json").
                when().
                get("/flights/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(3)).
                body(matchesJsonSchema(flightsErrorSchemaFile));
    }
    @Test
    public void flightsFarPastDate() {
        String from = "CPH";
        String to = "SXF";
        String date = "1980-01-07T00:00:00.000Z"; //Using date far in the past, hopefully no flight shows up
        int tickets = 1;
        given().
                contentType("application/json").
                when().
                get("/flights/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(1)).
                body(matchesJsonSchema(flightsErrorSchemaFile));
    }
    @Test
    public void flightsNoTickets() {
        String from = "CPH";
        String to = "SXF";
        String date = "2016-01-07T00:00:00.000Z";
        int tickets = 56833; //No planes can hold that many passengers!
        given().
                contentType("application/json").
                when().
                get("/flights/"+from+"/"+to+"/"+date+"/"+tickets).
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(2)).
                body(matchesJsonSchema(flightsErrorSchemaFile));
    }
    
    //Login test TODO later sprints
    /*
    @Test
    public void LoginWrongUsernameAndPassword() {
        //wrong username and password
        given().
                contentType("application/json").
                body("{'username':'john','password':'doe'}").
                when().
                post("/login").
                then().
                statusCode(401).
                body("error.message", equalTo("Ilegal username or password"));
    }
    * */
}
