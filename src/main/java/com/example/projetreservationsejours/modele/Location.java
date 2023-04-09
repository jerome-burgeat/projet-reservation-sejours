package com.example.projetreservationsejours.modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Location {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private String location;
    private String title;
    private int numberOfPeople;
    private String host;
    private String urlPhoto;

    // getters and setters omitted for brevity

    public static Location fromCsv(String csvLine) {
        String[] tokens = csvLine.split(";");
        Location location = new Location();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        location.setId(Integer.parseInt(tokens[0]));
        location.setStartDate(LocalDate.parse(tokens[1], formatter));
        location.setEndDate(LocalDate.parse(tokens[2], formatter));
        location.setPrice(Double.parseDouble(tokens[3]));
        location.setLocation(tokens[4]);
        location.setTitle(tokens[5]);
        location.setNumberOfPeople(Integer.parseInt(tokens[6]));
        location.setHost(tokens[7]);
        location.setUrlPhoto(tokens[8]);
        return location;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUrlPhoto() { return urlPhoto; }

    public void setUrlPhoto(String urlPhoto) { this.urlPhoto = urlPhoto;}

    @Override
    public String toString() {
        return "Location {" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", location='" + location +
                ", title='" + title +
                ", numberOfPeople=" + numberOfPeople +
                ", host='" + host +
                ", urlPhoto='" + urlPhoto +
                '}';
    }
}
