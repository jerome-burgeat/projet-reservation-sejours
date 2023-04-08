package com.example.projetreservationsejours.modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Datum {
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private String location;
    private String title;
    private int numberOfPeople;
    private String host;
    private String urlPhoto;

    // getters and setters omitted for brevity

    public static Datum fromCsv(String csvLine) {
        String[] tokens = csvLine.split(";");
        Datum datum = new Datum();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        datum.setStartDate(LocalDate.parse(tokens[0], formatter));
        datum.setEndDate(LocalDate.parse(tokens[1], formatter));
        datum.setPrice(Double.parseDouble(tokens[2]));
        datum.setLocation(tokens[3]);
        datum.setTitle(tokens[4]);
        datum.setNumberOfPeople(Integer.parseInt(tokens[5]));
        datum.setHost(tokens[6]);
        datum.setUrlPhoto(tokens[7]);
        return datum;
    }

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
