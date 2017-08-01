package com.devanlocker.transitapplication;

import android.util.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LAMetroParser {

    private static final String GET_ROUTES = "http://api.metro.net/agencies/lametro/routes/";

    public static ArrayList<Route> getRoutes()
            throws ExecutionException, InterruptedException {
        HttpGetRequest getRoutesRequest = new HttpGetRequest();
        String routesMessage = getRoutesRequest.execute(GET_ROUTES).get();
        JsonReader jsonReader = new JsonReader(new StringReader(routesMessage));
        ArrayList<Route> routes = LAMetroParser.parseRoutesMessage(jsonReader);
        return routes;
    }

    private static ArrayList<Route> parseRoutesMessage(JsonReader reader) {
        ArrayList<Route> routes = new ArrayList<Route>();

        try {
            reader.beginObject();
            while(reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("items")) {
                    reader.beginArray();
                    while(reader.hasNext()) {
                        routes.add(parseRoute(reader));
                    }
                    reader.endArray();
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return routes;
    }

    private static Route parseRoute(JsonReader reader) {
        int number = -1;
        String description = "";
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("id")) {
                    number = reader.nextInt();
                } else if (name.equals("display_name")) {
                    description = reader.nextString();
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Route(number, description);
    }

    public static ArrayList<Stop> getStops(int routeNumber)
            throws ExecutionException, InterruptedException {
        HttpGetRequest getStopsRequest = new HttpGetRequest();
        String requestString = GET_ROUTES + Integer.toString(routeNumber) + "/stops/";
        String stopsMessage = getStopsRequest.execute(requestString).get();
        JsonReader jsonReader = new JsonReader(new StringReader(stopsMessage));
        ArrayList<Stop> stops = parseStopsMessage(jsonReader);
        return stops;
    }

    private static ArrayList<Stop> parseStopsMessage(JsonReader reader) {
        ArrayList<Stop> stops = new ArrayList<Stop>();
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("items")) {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        stops.add(parseStop(reader));
                    }
                    reader.endArray();

                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stops;
    }

    private static Stop parseStop(JsonReader reader) {
        double latitude = 0.0;
        double longitude = 0.0;
        int id = -1;
        String displayName = "";

        try {
            reader.beginObject();
            while(reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("latitude")) {
                    latitude = reader.nextDouble();
                } else if (name.equals("longitude")) {
                    longitude = reader.nextDouble();
                } else if (name.equals("display_name")) {
                    displayName = reader.nextString();
                } else if (name.equals("id")) {
                    id = reader.nextInt();
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Stop(latitude, longitude, id, displayName);
    }
}
