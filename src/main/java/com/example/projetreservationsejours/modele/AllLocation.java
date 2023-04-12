package com.example.projetreservationsejours.modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public List<Location> getLocationList() {
        return locationList;
    }

    public void displayLocationList() {
        System.out.println("Informations {");
        for (int i = 0; i < this.locationList.size() ; i++) {
            System.out.println("    " + this.locationList.get(i).toString());
        }
        System.out.println("}");
    }
}
