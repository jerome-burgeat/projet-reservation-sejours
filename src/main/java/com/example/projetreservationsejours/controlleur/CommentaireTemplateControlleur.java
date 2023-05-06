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
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class CommentaireTemplateControlleur {

    @FXML
    private Text commentaire;

    @FXML
    private ImageView image;

    @FXML
    private Text note;


    @FXML
    private Label username;

    public void setCommentaire(String pseudo, Commentaire response) {
        username.setText(pseudo);
        commentaire.setText(response.getReponse().replaceAll("\\|", "\n"));
        if(response.getNote()==0){
            note.setText("");
        }
        else{
            DecimalFormat df = new DecimalFormat("#.#"); // Round to 1 decimal place
            note.setText(df.format(response.getNote()).toString()+"/5");
        }
    }
}
