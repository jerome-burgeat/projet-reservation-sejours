package com.example.projetreservationsejours.modele;


public class User {

    private int id;

    private String prenom;

    private String nom;

    private String username;

    private String email;

    private String password;

    private Boolean voyageur;

    private Boolean hote;

    public static User fromCsv(String csvLine) {
        String[] tokens = csvLine.split(";");
        User user = new User();
        user.setId(Integer.parseInt(tokens[0]));
        user.setPrenom(tokens[1]);
        user.setNom(tokens[2]);
        user.setUsername(tokens[3]);
        user.setEmail(tokens[4]);
        user.setPassword(tokens[5]);
        user.setVoyageur(tokens[6]);
        user.setHote(tokens[7]);
        return user;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getHote() {
        return hote;
    }

    public Boolean getVoyageur() {
        return voyageur;
    }

    public void setVoyageur(String voyageur) {
        if(voyageur.equals("1")){
            this.voyageur = true;
        }
        else if(voyageur.equals("0")) {
            this.voyageur = false;
        }
    }

    public void setHote(Boolean hote) {
        this.hote = hote;
    }


    public void setVoyageur(Boolean voyageur) {
        this.voyageur = voyageur;
    }

    public void setHote(String hote) {
        if(hote.equals("1")){
            this.hote = true;
        }
        else if(hote.equals("0")) {
            this.hote = false;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
