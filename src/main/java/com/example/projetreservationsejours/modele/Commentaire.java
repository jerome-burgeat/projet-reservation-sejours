package com.example.projetreservationsejours.modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Commentaire {

    private int id;

    private int location_id;

    private int user_id;

    private String reponse;

    public static Commentaire fromCsv(String csvLine) {
        String[] tokens = csvLine.split(";");
        Commentaire commentaire = new Commentaire();
        commentaire.setId(Integer.parseInt(tokens[0]));
        commentaire.setLocation_id(Integer.parseInt(tokens[1]));
        commentaire.setUser_id(Integer.parseInt(tokens[2]));
        commentaire.setReponse(tokens[3]);
        return commentaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", location_id=" + location_id +
                ", user_id=" + user_id +
                ", reponse='" + reponse + '\'' +
                '}';
    }
}
