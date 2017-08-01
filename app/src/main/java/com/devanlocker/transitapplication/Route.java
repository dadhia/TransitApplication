package com.devanlocker.transitapplication;

/**
 * Created by devan on 7/23/2017.
 */

public class Route {
    private int number;
    private String description;

    public Route(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public String getDecription() {
        return description;
    }
}
