package com.devanlocker.transitapplication;

import android.os.PatternMatcher;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by devan on 8/29/2017.
 */

public class USCParser {
    public static ArrayList<Route> getRoutes() throws ExecutionException, InterruptedException {
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
}
