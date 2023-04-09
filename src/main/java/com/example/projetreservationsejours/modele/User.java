package com.example.projetreservationsejours.modele;


public class User {

    private int id;

    private String nom;

    public static User fromCsv(String csvLine) {
        String[] tokens = csvLine.split(";");
        User user = new User();
        user.setId(Integer.parseInt(tokens[0]));
        user.setNom(tokens[1]);
        return user;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
