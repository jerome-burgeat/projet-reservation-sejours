package com.example.projetreservationsejours.modele;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AllLocationEnValidation {
    private List<LocationEnValidation> locationEnValidationList;

    public AllLocationEnValidation() {
        locationEnValidationList = new ArrayList<>();
    }

    public void loadData(String filename) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LocationEnValidation locationEnValidation = LocationEnValidation.fromCsv(line);
                locationEnValidationList.add(locationEnValidation);
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

    public List<LocationEnValidation> getLocationEnValidationList() { return locationEnValidationList;}

    public void setLocationEnValidationList(List<LocationEnValidation> locationEnValidationList) { this.locationEnValidationList = locationEnValidationList;}

    public void displayLocationEnValidationList() {
        System.out.println("Vos locations louées {");
        for (int i = 0; i < this.locationEnValidationList.size() ; i++) {
            System.out.println("    " + this.locationEnValidationList.get(i).toString());
        }
        System.out.println("}");
    }
}
