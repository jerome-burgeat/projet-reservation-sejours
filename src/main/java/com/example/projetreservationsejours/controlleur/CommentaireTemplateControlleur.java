package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;
import com.example.projetreservationsejours.modele.AllCommentaire;
import com.example.projetreservationsejours.modele.AllUser;
import com.example.projetreservationsejours.modele.Commentaire;
import com.example.projetreservationsejours.modele.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommentaireTemplateControlleur {

    private int locationId;

    @FXML
    private Label commentaire;

    @FXML
    private ImageView image;

    @FXML
    private Label username;

    public void setLocationId(int id) {
        this.locationId = id;
    }

    public void setCommentaire(String pseudo, Commentaire response) {
        username.setText(pseudo);
        commentaire.setText(response.getReponse());
    }

}
