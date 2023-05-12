package com.example.projetreservationsejours.modele;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AllCommentaire {

    private List<Commentaire> allCommentaire;

    public AllCommentaire() {
        allCommentaire = new ArrayList<>();
    }

    public void loadData(String filename) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Commentaire commentaire = Commentaire.fromCsv(line);
                allCommentaire.add(commentaire);
            }
        }
    }
    public void loadData(String filename, int id) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Commentaire commentaire = Commentaire.fromCsv(line);
                if(commentaire.getLocation_id() == id) {
                    allCommentaire.add(commentaire);
                }
            }
        }
    }

    public List<Commentaire> getCommentaireList() {
        return allCommentaire;
    }

    public void setCommentaireList(List<Commentaire> allCommentaire) { this.allCommentaire = allCommentaire;}

    public void displayCommentaireList() {
        System.out.println("Informations {");
        for (int i = 0; i < this.allCommentaire.size() ; i++) {
            System.out.println("    " + this.allCommentaire.get(i).toString());
        }
        System.out.println("}");
    }

    public void addNewCommentaireToCsv(String filename,Commentaire commentaire) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toRealPath().toFile(), true))) {
            String line = Integer.toString(commentaire.getId()) + ";"
                    + commentaire.getLocation_id() + ";"
                    + commentaire.getUser_id() + ";"
                    + commentaire.getReponse()+";"
                    + commentaire.getNote();
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
