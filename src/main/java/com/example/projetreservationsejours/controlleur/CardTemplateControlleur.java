package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;
import com.example.projetreservationsejours.modele.ImageCache;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class CardTemplateControlleur {

    Application application;

    @FXML
    private ImageView image;

    @FXML
    private Label titre;
    @FXML
    private Label prix;

    @FXML
    private AnchorPane anchorPane;


    public void setCard(Location location, ImageCache imageCache) {
        image.setFitWidth(230);
        image.setFitHeight(150);
        image.setPreserveRatio(false);
        image.setImage(imageCache.getImage(Application.class.getResource("imagesLocations/"+location.getUrlPhoto()).toExternalForm()));
        BorderPane borderPane = new BorderPane(image);
        borderPane.setStyle("-fx-border-color: #FECEA8; -fx-border-radius: 10; -fx-border-width: 2;");
        Rectangle clipRect = new Rectangle(230, 150);
        clipRect.setArcWidth(12);
        clipRect.setArcHeight(12);
        image.setClip(clipRect);
        borderPane.setCenter(image);
        borderPane.setPrefSize(230, 150);
        anchorPane.getChildren().add(borderPane);
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
        titre.setStyle("-fx-text-fill: #800020;");
        titre.setAlignment(Pos.BASELINE_CENTER);
        prix.setText(location.getPrice() + " €");
        prix.setStyle("-fx-text-fill: #800020;");
    }
}
