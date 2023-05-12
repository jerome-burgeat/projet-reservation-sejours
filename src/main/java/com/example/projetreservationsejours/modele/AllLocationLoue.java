package com.example.projetreservationsejours.modele;

import java.io.*;
import java.nio.file.Files;
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
                if(locationLoue.getUser_id() == user_id && locationLoue.isValide() == false) {
                    locationLoueList.add(locationLoue);
                }
            }
        }
    }

    public void loadData(String filename, int user_id, boolean isLoue) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LocationLoue locationLoue = LocationLoue.fromCsv(line);
                if(locationLoue.getUser_id() == user_id && locationLoue.isValide() == isLoue) {
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

    public void approuveLocationToCsv(String filename,LocationEnValidation locationEnValidation) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        //Modification dans location_en_cours.csv
        List<String> lines = Files.readAllLines(path);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] values = line.split(";");
            int locationId = Integer.parseInt(values[1].trim());
            int UserWhoWantToDoLocationId = Integer.parseInt(values[2].trim());
            if (locationId == locationEnValidation.getLocation_id() && UserWhoWantToDoLocationId == locationEnValidation.getUser_id()) {
                values[3] = "1";
                line = String.join(";", values);
                lines.set(i, line);
                break;
            }
        }
        Files.write(path, lines);
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
