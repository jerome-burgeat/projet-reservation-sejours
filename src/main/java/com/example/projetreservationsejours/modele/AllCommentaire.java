package com.example.projetreservationsejours.modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AllCommentaire {

    private List<Commentaire> allCommentaire;

    public AllCommentaire() {
        allCommentaire = new ArrayList<>();
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
}
