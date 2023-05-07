package com.example.projetreservationsejours.modele;

import java.io.*;
import java.nio.file.Files;
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

    public void loadData(String filename, int user_id) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LocationEnValidation locationEnValidation = LocationEnValidation.fromCsv(line);
                AllLocation allLocation = new AllLocation();
                allLocation.loadData("locations.csv");
                boolean isLocationFromUserConnected = false;
                for(int i=0; i < allLocation.getLocationList().size() && isLocationFromUserConnected == false; i++) {
                    if(allLocation.getLocationList().get(i).getId() == locationEnValidation.getLocation_id() &&
                        Integer.parseInt(allLocation.getLocationList().get(i).getHost_user_id()) == user_id) {
                        isLocationFromUserConnected = true;
                        break;
                    }
                }
                if(isLocationFromUserConnected) {
                    locationEnValidationList.add(locationEnValidation);
                }
            }
        }
    }

    public void loadData(String filename, int user_id, boolean terminated) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LocationEnValidation locationEnValidation = LocationEnValidation.fromCsv(line);
                AllLocation allLocation = new AllLocation();
                allLocation.loadData("locations.csv");                boolean isLocationFromUserConnected = false;
                for(int i=0; i < allLocation.getLocationList().size() && isLocationFromUserConnected == false; i++) {
                    if(allLocation.getLocationList().get(i).getId() == locationEnValidation.getLocation_id() &&
                            Integer.parseInt(allLocation.getLocationList().get(i).getHost_user_id()) == user_id) {
                        isLocationFromUserConnected = true;
                        break;
                    }
                }
                if(isLocationFromUserConnected && locationEnValidation.isValide() == terminated) {
                    locationEnValidationList.add(locationEnValidation);
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

    public void approuveLocationToCsv(String filename,LocationEnValidation locationEnValidation) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        //Modification dans location_en_cours.csv
        List<String> lines = Files.readAllLines(path);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] values = line.split(";");
            int id = Integer.parseInt(values[0].trim());
            if (id == locationEnValidation.getId()) {
                values[3] = "1";
                line = String.join(";", values);
                lines.set(i, line);
                break;
            }
        }
        Files.write(path, lines);
        //Modification dans location_loue.csv
        AllLocationLoue allLocationLoue = new AllLocationLoue();
        allLocationLoue.approuveLocationToCsv("location_loue.csv", locationEnValidation);
    }

    public void deleteLocationLoueFromCsv(String filename, int locationLoueId) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir") + pathRessources + filename);
        File inputFile = path.toRealPath().toFile();
        File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            String[] values = currentLine.split(";");
            int id = Integer.parseInt(values[0].trim());
            if (id != locationLoueId) {
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }

        writer.close();
        reader.close();

        inputFile.delete();
        tempFile.renameTo(inputFile);
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
