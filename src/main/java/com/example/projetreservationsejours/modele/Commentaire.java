package com.example.projetreservationsejours.modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Commentaire {

    private int id;

    private int location_id;

    private int user_id;

    private String reponse;

    private double note;

    public Commentaire() {}

    public Commentaire(int id, int location_id, int user_id, String reponse, double note) {
        this.id = id;
        this.location_id = location_id;
        this.user_id = user_id;
        this.reponse = reponse;
        this.note = note;
    }

    public static Commentaire fromCsv(String csvLine) {
        String[] tokens = csvLine.split(";");
        Commentaire commentaire = new Commentaire();
        commentaire.setId(Integer.parseInt(tokens[0]));
        commentaire.setLocation_id(Integer.parseInt(tokens[1]));
        commentaire.setUser_id(Integer.parseInt(tokens[2]));
        commentaire.setReponse(tokens[3]);
        commentaire.setNote(Double.parseDouble(tokens[4]));
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

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
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
