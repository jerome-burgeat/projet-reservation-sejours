package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;
import com.example.projetreservationsejours.modele.AllUser;
import com.example.projetreservationsejours.modele.Location;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CardDetailsControlleur implements Initializable {

    Application application;

    AllUser users;

    @FXML
    private VBox comments;

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
    private Text note;

    @FXML
    private Text prix;

    @FXML
    private Text titre;

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

        if(this.isUserConnected()) {
            userName.setText(application.userConnected.getNom());
        }

        users = new AllUser();
        try {
            users.loadData("utilisateurs.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCard(Location location) {
        image.setImage(new Image(location.getUrlPhoto()));
        titre.setText(location.getTitle());
        dateDebut.setText(location.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dateFin.setText(location.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        prix.setText(location.getPrice() + " €");
        lieu.setText(location.getLocation());
        nbMaxPersonne.setText(String.valueOf(location.getNumberOfPeople()));
        hote.setText(users.getUsers().get(Integer.parseInt(location.getHost())-1).getNom());
    }

    @FXML
    void addToCart(MouseEvent event) {

    }

    @FXML
    void backHome(MouseEvent event) throws IOException {
        application.fenetreControlleur.changerDeFenetre("Accueil.fxml");
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
    void logout(MouseEvent event) throws IOException {
        application.userConnected = null;
        userName.setText("");
        changeHeaderVisibility();
        application.fenetreControlleur.changerDeFenetre("Accueil.fxml");
        application.fenetreControlleur.showNotification("Deconnexion","Vous êtes désormais déconnecté",2000,"images/Right.png");
    }

    public boolean isUserConnected() {
        return application.userConnected != null;
    }
}
