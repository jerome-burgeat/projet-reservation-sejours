package com.example.projetreservationsejours;

import com.example.projetreservationsejours.controlleur.FenetreControlleur;
import com.example.projetreservationsejours.modele.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static FenetreControlleur fenetreControlleur = new FenetreControlleur();
    public static User userConnected;

    @Override
    public void start(Stage stage) throws IOException {
        //Récupère le fxml Accueil
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Accueil.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rezerweethus");
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("images/logo.png")));
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