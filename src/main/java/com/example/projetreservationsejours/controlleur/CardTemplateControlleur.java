package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.modele.Datum;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardTemplateControlleur {

    @FXML
    private ImageView image;

    @FXML
    private Label titre;
    @FXML
    private Label prix;



    public void initialize() {
    }

    public void setCard(Datum datum) {
        image.setImage(new Image(datum.getUrlPhoto()));
        titre.setText(datum.getTitle());
        titre.setAlignment(Pos.BASELINE_CENTER);
        prix.setText(datum.getPrice() + " €");
    }
}
