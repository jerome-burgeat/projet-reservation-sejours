package com.example.projetreservationsejours.modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AllLocation {
    private List<Location> locationList;

    public AllLocation() {
        locationList = new ArrayList<>();
    }

    public void loadData(String filename) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Location location = Location.fromCsv(line);
                locationList.add(location);
            }
        }
    }

    public void loadDataAvailable(String filename, boolean available) throws IOException {
        available = true;
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Location location = Location.fromCsv(line);
                if(!location.isLocation_loue()) {
                    locationList.add(location);
                }
            }
        }
    }

    public void loadData(String filename, String country) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        String regex = "^.*" + country + ".*$";
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Location location = Location.fromCsv(line);
                if(location.getLocation().matches(regex)) {
                    locationList.add(location);
                }
            }
        }
    }

    public void loadData(String filename, String country, String dateDebut, String dateFin) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        String regex = "^.*" + country + ".*$";
        String regexDebut = "^.*" + dateDebut + ".*$";
        String regexFin = "^.*" + dateFin + ".*$";
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Location location = Location.fromCsv(line);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                if(location.getLocation().matches(regex) && location.getStartDate().format(formatter).matches(regexDebut) && location.getEndDate().format(formatter).matches(regexFin)) {
                    locationList.add(location);
                }
            }
        }
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) { this.locationList = locationList;}

    public void displayLocationList() {
        System.out.println("Informations {");
        for (int i = 0; i < this.locationList.size() ; i++) {
            System.out.println("    " + this.locationList.get(i).toString());
        }
        System.out.println("}");
    }
}
