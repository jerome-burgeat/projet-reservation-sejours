package com.example.projetreservationsejours.modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AllLocationLoue {
    private List<LocationLoue> locationLoueList;

    public AllLocationLoue() {
        locationLoueList = new ArrayList<>();
    }

    public void loadData(String filename) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LocationLoue locationLoue = LocationLoue.fromCsv(line);
                locationLoueList.add(locationLoue);
            }
        }
    }

    public void loadData(String filename, int user_id) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LocationLoue locationLoue = LocationLoue.fromCsv(line);
                if(locationLoue.getUser_id() == user_id) {
                    locationLoueList.add(locationLoue);
                }
            }
        }
    }

    public void addNewLocationLoueToCsv(String filename,LocationLoue locationLoue) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        FileWriter writer = new FileWriter(path.toRealPath().toFile(), true);
        String line = Integer.toString(locationLoue.getId()) + ";"
                + locationLoue.getLocation_id() + ";"
                + locationLoue.getUser_id() + ";"
                + 0 ;
        writer.append(line+"\n");
        writer.close();
    }

    public List<LocationLoue> getLocationList() {
        return locationLoueList;
    }

    public void setLocationList(List<LocationLoue> locationLoueList) { this.locationLoueList = locationLoueList;}

    public int howManyLocationLoue() { return locationLoueList.size(); }

    public void displayLocationLoueList() {
        System.out.println("Vos locations louées {");
        for (int i = 0; i < this.locationLoueList.size() ; i++) {
            System.out.println("    " + this.locationLoueList.get(i).toString());
        }
        System.out.println("}");
    }
}
