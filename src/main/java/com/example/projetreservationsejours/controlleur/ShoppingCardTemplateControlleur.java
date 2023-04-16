package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;
import com.example.projetreservationsejours.modele.AllLocation;
import com.example.projetreservationsejours.modele.AllUser;
import com.example.projetreservationsejours.modele.Location;
import com.example.projetreservationsejours.modele.LocationLoue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ShoppingCardTemplateControlleur {

    Application application;

    @FXML
    private Text dateDebut;

    @FXML
    private Text dateFin;

    @FXML
    private Text hote;

    @FXML
    private ImageView image;

    @FXML
    private Text lieu;

    @FXML
    private Text nbMaxPersonne;

    @FXML
    private Text prix;

    @FXML
    private Text titre;

    public void setUserChoice(LocationLoue locationLoue) throws IOException {
        AllLocation allLocation = new AllLocation();
        allLocation.loadData("locations.csv");

        AllUser users = new AllUser();
        users.loadData("utilisateurs.csv");

        image.setImage(new Image(allLocation.getLocationList().get(locationLoue.getLocation_id()).getUrlPhoto()));
        titre.setText((allLocation.getLocationList().get(locationLoue.getLocation_id()).getTitle()));
        dateDebut.setText((allLocation.getLocationList().get(locationLoue.getLocation_id()).getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        dateFin.setText((allLocation.getLocationList().get(locationLoue.getLocation_id()).getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        prix.setText((allLocation.getLocationList().get(locationLoue.getLocation_id()).getPrice() + " €"));
        lieu.setText((allLocation.getLocationList().get(locationLoue.getLocation_id()).getLocation()));
        nbMaxPersonne.setText(String.valueOf((allLocation.getLocationList().get(locationLoue.getLocation_id()).getNumberOfPeople())));
        hote.setText(users.getUsers().get(Integer.parseInt(allLocation.getLocationList().get(locationLoue.getLocation_id()).getHost_user_id())-1).getNom());
    }

    @FXML
    void eraseChoice(MouseEvent event) {
        application.fenetreControlleur.showNotification("Supprimer","Suppression en cours..",2000,"images/Right.png");
    }

}
