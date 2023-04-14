package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;
import com.example.projetreservationsejours.modele.Location;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CardTemplateControlleur {

    Application application;

    @FXML
    private ImageView image;

    @FXML
    private Label titre;
    @FXML
    private Label prix;

    public void setCard(Location location) {
        //image.setImage(new Image(location.getUrlPhoto()));
        image.setOnMouseClicked((MouseEvent event) -> {
            // Load the card details FXML file
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardDetails.fxml"));
                Parent root = fxmlLoader.load();

                CardDetailsControlleur cardDetailsController = fxmlLoader.getController();
                cardDetailsController.setCard(location);
                Scene scene = new Scene(root);
                application.fenetreControlleur.getStagePrincipale().centerOnScreen();
                application.fenetreControlleur.getStagePrincipale().setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        titre.setText(location.getTitle());
        titre.setAlignment(Pos.BASELINE_CENTER);
        prix.setText(location.getPrice() + " €");
    }
}
