package com.example.projetreservationsejours.controlleur;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FenetreControlleur {

    Stage stagePrincipale;
    Scene scenePrincipale;

    public FenetreControlleur() {
    }

    public Stage getStagePrincipale() {
        return stagePrincipale;
    }

    public void setStagePrincipale(Stage stagePrincipale) {

        this.stagePrincipale = stagePrincipale;
        this.stagePrincipale.centerOnScreen();
    }

    public void setScenePrincipale(Scene scenePrincipale) {
        this.scenePrincipale = scenePrincipale;
    }

    public void changerDeFenetre(String nomFenetre) throws IOException {
        Parent root = new FXMLLoader(getClass().getResource(nomFenetre)).load();
        Scene scene = new Scene(root);
        this.getStagePrincipale().centerOnScreen();
        this.getStagePrincipale().setScene(scene);
    }

    public void popupFenetre(String nomFenetre,String titreFenetre) throws IOException {
        Parent root = new FXMLLoader(getClass().getResource(nomFenetre)).load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle(titreFenetre);
        stage.show();
    }

}
