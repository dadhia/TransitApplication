package com.devanlocker.transitapplication;

import android.os.PatternMatcher;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by devan on 8/29/2017.
 */

public class USCParser {
    public static ArrayList<Route> getRoutes()
            throws ExecutionException, InterruptedException {
        HttpGetRequest request = new HttpGetRequest();
        String requestString = "https://www.uscbuses.com/simple/routes/";
        String routesMessage = request.execute(requestString).get();

        ArrayList<Route> routes = new ArrayList<Route>();
        Pattern routeRegEx = Pattern.compile("/routes[^<]*");
        Pattern numberRegEx = Pattern.compile("[^d]*");
        Pattern descriptionRegEx = Pattern.compile(">[^<]*");
        Matcher matcher = routeRegEx.matcher(routesMessage);
        while (matcher.find()) {
            int routeNumber = 0;
            String description = "";
            String routeInfoSubstring = matcher.group();
            Matcher findNumber = numberRegEx.matcher(routeInfoSubstring);
            if (findNumber.find()) {
                String routeNumberSubstring = findNumber.group();
                String routeNumberAsString = routeInfoSubstring.substring(8, routeNumberSubstring.length()-1);
                routeNumber = Integer.valueOf(routeNumberAsString);
            }
            Matcher findDescription = descriptionRegEx.matcher(routeInfoSubstring);
            if (findDescription.find()) {
                String descriptionSubstring = findDescription.group();
                description = descriptionSubstring.substring(1);
            }
            routes.add(new Route(routeNumber, description));
        }
        return routes;
    }

    public static ArrayList<Stop> getStops(int routeNumber)
            throws ExecutionException, InterruptedException {
        ArrayList<Stop> stops = new ArrayList<Stop>();

        //Create an HTTP request and retrieve data from the text only web version
        HttpGetRequest request = new HttpGetRequest();
        String requestString = "https://www.uscbuses.com/simple/routes/"
                                + Integer.toString(routeNumber) + "/stops";
        String stopsMessage = request.execute(requestString).get();

        //build regular expression
        Pattern stopRegEx = Pattern.compile("stops/[^<]*");
        Pattern idRegEx = Pattern.compile("[^\"]*");
        Pattern nameRegEx = Pattern.compile(">[^<]*");

        Matcher findAllStops = stopRegEx.matcher(stopsMessage);
        //find stop data using regular expression
        while(findAllStops.find()) {
            int stopId = 0;
            String stopName = "";

            //Extract data using regular expression
            String stopSubstring = findAllStops.group();
            Matcher findStopId = idRegEx.matcher(stopSubstring);
            if (findStopId.find()) {
                stopId = Integer.valueOf(findStopId.group().substring(6));
            }
            Matcher findStopName = nameRegEx.matcher(stopSubstring);
            if (findStopName.find()) {
                stopName = findStopName.group().substring(1);
            }
            stops.add(new Stop(0.0, 0.0, stopId, stopName));
        }
        return stops;
    }

    public static String getArrivals(int routeNumber, int stopNumber)
            throws ExecutionException, InterruptedException {
        HttpGetRequest request = new HttpGetRequest();
        String requestString = "https://uscbuses.com/simple/routes/"
                                + Integer.toString(routeNumber)
                                + "/stops/"
                                + Integer.toString(stopNumber);
        String arrivalsMessage = request.execute(requestString).get();
        Pattern arrivalsRegEx = Pattern.compile("Arrival Times.*");
        Pattern individualMessageRegEx = Pattern.compile("<li>[^<]*");

        Matcher findArrivals = arrivalsRegEx.matcher(arrivalsMessage);
        if (findArrivals.find()){
            StringBuilder stringBuilder = new StringBuilder();

            //Trim our message to the section we want
            arrivalsMessage = findArrivals.group();
            Matcher messageRetriever = individualMessageRegEx.matcher(arrivalsMessage);
            while(messageRetriever.find()) {
                stringBuilder.append(messageRetriever.group().substring(4));
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        } else {
            return "An error occurred. Please try again.";
        }
    }
}
