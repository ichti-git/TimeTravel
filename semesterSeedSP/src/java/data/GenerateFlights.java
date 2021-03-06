/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import timeTravel.facade.Facade;

/**
 *
 * @author ichti (Simon T)
 */
/*
{
"date": ISO-8601 String,
"numberOfSeats": Integer,
"totalPrice": Number (Euro),
"flightID": String,
"traveltime": Integer (minutes),
"destination": IATA-Code (String),
"origin":"IATA-Code (String)
}
* */
public class GenerateFlights {

    public static void setAirports(String[] aAirports) {
        airports = aAirports;
    }
    private int minDay = 1;
    private int maxDay = 28;
    private int minMonth = 1;
    private int maxMonth = 2;
    private int minSeats = 1;
    private int maxSeats = 100;
    private int minPrice = 50;
    private int maxPrice = 5000;
    private int minTravelTime = 60;
    private int maxTravelTime = 360;
    /*
    private static String[] airports = {"BCN", "CDG", "CPH", "STN", "SXF",
                                        "LAX", "SFO", "JFK", "HND", "PEK",
                                        "AMS", "IST", "GRU", "YYZ", "DXB",
                                        "ICN", "FCO", "MEL", "JNB", "CAI",
                                        "MEX", "BBU", "HEM", "SIN", "DME"};
                                        * */
    private static String[] airports = {"BCN", "CDG", "CPH", "STN", "SXF",
                                        "LAX", "SFO", "AMS", "FCO", "HEM"};
    
    public void generateFlights(int amount) {
        for (int i = 0; i < amount; i++) {
            int day = randomInt(minDay, maxDay);
            String dayString = ""+day;
            if (day < 10) {
                dayString = "0"+dayString;
            }
            int month = randomInt(minMonth, maxMonth);
            String dateString = "2016-0"+month+"-"+dayString+"T00:00:00.000Z";
            //DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            //Date date = sdfISO.parse(dateString);
            int seats = randomInt(minSeats, maxSeats);
            int price = randomInt(minPrice, maxPrice);
            int travelTime = randomInt(minTravelTime, maxTravelTime);
            int originRandom = randomInt(0, airports.length-1);
            int destinationRandom = randomInt(0, airports.length-1);
            String origin = airports[originRandom];
            String destination = airports[destinationRandom];
            String flightId = "TT"+randomString(6)+":"+dateString;
            Facade facade = new Facade();
            facade.addFlightInstance(dateString, seats, price, flightId, travelTime, destination, origin);
        }
    }
    private int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max-min+1)+min;
    }
    
    private String randomString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) 
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public void setMinDay(int minDay) {
        this.minDay = minDay;
    }

    public void setMaxDay(int maxDay) {
        this.maxDay = maxDay;
    }

    public void setMinMonth(int minMonth) {
        this.minMonth = minMonth;
    }

    public void setMaxMonth(int maxMonth) {
        this.maxMonth = maxMonth;
    }

    public void setMinSeats(int minSeats) {
        this.minSeats = minSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinTravelTime(int minTravelTime) {
        this.minTravelTime = minTravelTime;
    }

    public void setMaxTravelTime(int maxTravelTime) {
        this.maxTravelTime = maxTravelTime;
    }
}