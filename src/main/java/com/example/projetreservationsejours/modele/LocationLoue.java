package com.example.projetreservationsejours.modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocationLoue {
    private int id;
    private int location_id;
    private int user_id;
    private boolean isValide;

    public LocationLoue(){}

    public LocationLoue(int id, int location_id, int user_id) {
        this.id = id;
        this.location_id = location_id;
        this.user_id = user_id;
    }

    public static LocationLoue fromCsv(String csvLine) {
        String[] tokens = csvLine.split(";");
        LocationLoue locationLoue = new LocationLoue();
        locationLoue.setId(Integer.parseInt(tokens[0]));
        locationLoue.setLocation_id(Integer.parseInt(tokens[1]));
        locationLoue.setUser_id(Integer.parseInt(tokens[2]));
        locationLoue.setValide(Integer.parseInt(tokens[3]) == 1);
        return locationLoue;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getLocation_id() { return location_id; }

    public void setLocation_id(int location_id) { this.location_id = location_id; }

    public int getUser_id() { return user_id; }

    public void setUser_id(int user_id) { this.user_id = user_id; }

    public boolean isValide() { return isValide; }

    public void setValide(boolean valide) { isValide = valide; }

    @Override
    public String toString() {
        return "LocationLoue{" +
                "id=" + id +
                ", location_id=" + location_id +
                ", user_id=" + user_id +
                '}';
    }
}
