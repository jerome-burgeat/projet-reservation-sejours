package com.example.projetreservationsejours;

import com.example.projetreservationsejours.controlleur.FenetreControlleur;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static FenetreControlleur fenetreControlleur = new FenetreControlleur();
    @Override
    public void start(Stage stage) throws IOException {
        //Récupère le fxml Accueil
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Accueil.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
        //Injecte la vue dans le controlleur de fenetre
        fenetreControlleur.setStagePrincipale(stage);
        fenetreControlleur.setScenePrincipale(scene);


    }

    public static void main(String[] args) {
        launch();
    }
}