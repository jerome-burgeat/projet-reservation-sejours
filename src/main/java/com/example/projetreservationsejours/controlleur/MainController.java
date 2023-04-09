package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;

import com.example.projetreservationsejours.modele.AllLocation;
import com.example.projetreservationsejours.modele.AllUser;
import com.example.projetreservationsejours.modele.Location;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    Application application;

    @FXML
    private VBox cardContainer;

    @FXML
    private Button connexion;

    @FXML
    private ImageView home;

    @FXML
    private Button inscription;

    @FXML
    private ImageView logout;

    @FXML
    private ImageView shopping_cart;

    @FXML
    private ImageView user;

    @FXML
    private Text userName;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        changeHeaderVisibility();
        // Initialisation de la
        AllLocation allLocation = new AllLocation();
        try {
            allLocation.loadData("locations.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        allLocation.displayLocationList();

        if(this.isUserConnected()) {
            userName.setText(application.userConnected.getNom());
        }

        int cpt = 0;
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        for (Location card : allLocation.getLocationList()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("cardTemplate.fxml"));
                AnchorPane cardNode = loader.load();
                CardTemplateControlleur cardController = loader.getController();
                cardController.setCard(card);
                if (cpt < 3) {
                    hBox.getChildren().add(cardNode);
                    cpt++;
                } else {
                    cardContainer.getChildren().add(hBox);
                    cpt = 0;
                    hBox = new HBox();
                    hBox.setAlignment(Pos.BASELINE_CENTER);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void backHome(MouseEvent event) throws IOException {
        application.fenetreControlleur.changerDeFenetre("Accueil.fxml");
    }

    @FXML
    void connexion(MouseEvent event) {
        AllUser users = new AllUser();
        try {
            users.loadData("utilisateurs.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        application.userConnected = users.getUsers().get(8);
        userName.setText(application.userConnected.getNom());
        changeHeaderVisibility();
    }

    @FXML
    void logout(MouseEvent event) {
        application.userConnected = null;
        userName.setText("");
        changeHeaderVisibility();
    }

    public void changeHeaderVisibility() {
        home.setVisible(isUserConnected());
        shopping_cart.setVisible(isUserConnected());
        user.setVisible(isUserConnected());
        userName.setVisible(isUserConnected());
        logout.setVisible(isUserConnected());

        //Show
        connexion.setVisible(!isUserConnected());
        inscription.setVisible(!isUserConnected());
    }

    public boolean isUserConnected() {
        return application.userConnected != null;
    }
}
