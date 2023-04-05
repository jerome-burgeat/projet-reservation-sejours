package com.example.projetreservationsejours.modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Datum> dataList;

    public Data() {
        dataList = new ArrayList<>();
    }

    public void loadData(String filename) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Datum datum = Datum.fromCsv(line);
                dataList.add(datum);
            }
        }
    }

    public List<Datum> getDataList() {
        return dataList;
    }

    public void displayDataList() {
        System.out.println("Informations {");
        for (int i = 0; i < this.dataList.size() ; i++) {
            System.out.println("    " + this.dataList.get(i).toString());
        }
        System.out.println("}");
    }
}
