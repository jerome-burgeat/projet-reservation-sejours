package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    Application application;
    @FXML
    private Button boutonConnexion;

    @FXML
    private Button bountonInscription;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML
    void showPageInscription(ActionEvent event) throws IOException {
        application.fenetreControlleur.popupFenetre("PageInscription.fxml","S'inscrire");
    }

    @FXML
    void showPageConnexion(ActionEvent event) throws IOException {
        application.fenetreControlleur.popupFenetre("PageConnexion.fxml","Se connecter");
    }
}
