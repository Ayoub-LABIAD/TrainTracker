package com.traintracker.traintracker.data.train;

/**
 * Created by ayoub on 16-Aug-17.
 */

public class Train {

    private String id;
    private String name;
    private String departure;
    private String destination;
    private String type;

    public Train(String id, String name, String departure, String destination, String type) {
        this.id = id;
        this.name = name;
        this.departure = departure;
        this.destination = destination;
        this.type = type;
    }

    public Train() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
