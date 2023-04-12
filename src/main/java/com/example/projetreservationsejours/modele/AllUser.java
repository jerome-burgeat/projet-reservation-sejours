package com.example.projetreservationsejours.modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AllUser {

    private List<User> users;

    public AllUser() { this.users = new ArrayList<>(); }

    public void loadData(String filename) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toRealPath().toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromCsv(line);
                user.toString();
                users.add(user);
            }
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void displayUsers() {
        System.out.println("User infos {");
        for (int i = 0; i < this.users.size() ; i++) {
            System.out.println("    " + this.users.get(i).toString());
        }
        System.out.println("}");
    }

    public String verifyIfVoyageurOrHote(Boolean bool)
    {
        if(bool){
            return "1";
        }
        else{
            return "0";
        }
    }

    public void addNewUserToCsv(String filename,User user) throws IOException {
        String pathRessources = "\\src\\main\\resources\\com\\example\\projetreservationsejours\\ressources\\";
        Path path = Paths.get(System.getProperty("user.dir")+ pathRessources + filename);
        FileWriter writer = new FileWriter(path.toRealPath().toFile(), true);
        String voyageur = verifyIfVoyageurOrHote(user.getVoyageur());
        String hote = verifyIfVoyageurOrHote(user.getHote());
        String line = Integer.toString(user.getId()) + ";" + user.getPrenom() + ";" + user.getNom() + ";" + user.getUsername() + ";"
                + user.getEmail() + ";" + user.getPassword() + ";" + voyageur + ";" + hote;
        writer.append(line+"\n");
        writer.close();
    }

}
