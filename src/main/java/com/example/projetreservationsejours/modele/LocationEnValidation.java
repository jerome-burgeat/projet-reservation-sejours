package com.example.projetreservationsejours.modele;

public class LocationEnValidation {
    private int id;
    private int location_id;
    private int user_id;
    private boolean isValide;

    public LocationEnValidation(){}

    public LocationEnValidation(int id, int location_id, int user_id) {
        this.id = id;
        this.location_id = location_id;
        this.user_id = user_id;
    }

    public static LocationEnValidation fromCsv(String csvLine) {
        String[] tokens = csvLine.split(";");
        LocationEnValidation locationEnValidation = new LocationEnValidation();
        locationEnValidation.setId(Integer.parseInt(tokens[0]));
        locationEnValidation.setLocation_id(Integer.parseInt(tokens[1]));
        locationEnValidation.setUser_id(Integer.parseInt(tokens[2]));
        locationEnValidation.setValide(Integer.parseInt(tokens[3]) == 1);
        return locationEnValidation;
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
        return "LocationEnValidation{" +
                "id=" + id +
                ", location_id=" + location_id +
                ", user_id=" + user_id +
                '}';
    }
}
