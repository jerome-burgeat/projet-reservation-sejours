package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;
import com.example.projetreservationsejours.modele.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ShoppingCardDetailsControlleur implements Initializable {

    Application application;

    @FXML
    private Button bountonInscription;

    @FXML
    private Button boutonConnexion;

    @FXML
    private ImageView home;

    @FXML
    private ImageView logout;

    @FXML
    private Text nbLocation;

    @FXML
    private ImageView shopping_cart;

    @FXML
    private ImageView user;

    @FXML
    private VBox userChoice;

    @FXML
    private Text userName;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        changeHeaderVisibility();

        userName.setText(application.userConnected.getUsername());
        AllLocationLoue allLocationLoue = new AllLocationLoue();
        try {
            allLocationLoue.loadData("location_loue.csv", application.userConnected.getId());
            nbLocation.setText(String.valueOf(allLocationLoue.howManyLocationLoue()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (LocationLoue shoppingCard : allLocationLoue.getLocationList()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ShoppingCardTemplate.fxml"));
                AnchorPane cardNode = loader.load();
                ShoppingCardTemplateControlleur shoppingCardTemplateControlleur = loader.getController();
                shoppingCardTemplateControlleur.setUserChoice(shoppingCard);
                userChoice.getChildren().add(cardNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*public void setUserChoiceCard(Location location) {

    }*/

    @FXML
    void validerShoppingCard(MouseEvent event) {
        System.out.println("je valide");
    }

    @FXML
    void backHome(MouseEvent event) throws IOException {
        application.fenetreControlleur.changerDeFenetre("Accueil.fxml");
    }

    @FXML
    void showPageInscription(ActionEvent event) throws IOException {
        application.fenetreControlleur.popupFenetre("PageInscription.fxml","S'inscrire");
    }

    @FXML
    void showPageConnexion(ActionEvent event) throws IOException {
        application.fenetreControlleur.popupFenetre("PageConnexion.fxml","Se connecter");
    }

    /**
     * Reset the status of the user connected
     * */
    @FXML
    void logout(MouseEvent event) {
        application.userConnected = null;
        userName.setText("");
        changeHeaderVisibility();
        application.fenetreControlleur.showNotification("Deconnexion","Vous êtes désormais déconnecté",2000,"images/Right.png");
        try {
            application.fenetreControlleur.changerDeFenetre("Accueil.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * If user is connected, display his personal header
     * Else display sign-in and sign-up buttons
     * */
    public void changeHeaderVisibility() {
        /*
         * Only visible when the user is connected
         * */
        home.setVisible(isUserConnected());
        shopping_cart.setVisible(isUserConnected());
        user.setVisible(isUserConnected());
        userName.setVisible(isUserConnected());
        logout.setVisible(isUserConnected());
        nbLocation.setVisible(isUserConnected());

        /*
         * Only visible when the user is disconnected
         */
        boutonConnexion.setVisible(!isUserConnected());
        bountonInscription.setVisible(!isUserConnected());
    }

    /**
     * Check if the user is connected
     * @return boolean
     * */
    public boolean isUserConnected() { return application.userConnected != null; }
}
