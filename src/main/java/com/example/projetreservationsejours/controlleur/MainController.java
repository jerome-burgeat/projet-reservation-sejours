package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    Application application;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML
    void changerDeFenetre(ActionEvent event) throws IOException {
        application.fenetreControlleur.changerDeFenetre("Accueil.fxml");
    }
     /*@FXML
    void afficherRegles(ActionEvent event) throws IOException {
        application.fenetreControlleur.afficherFenetre("Regles.fxml");
    }*/
}
