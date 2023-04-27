package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;
import com.example.projetreservationsejours.modele.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class ShoppingCardTemplateControlleur {

    Application application;

    @FXML
    private ImageView image;

    @FXML
    private ImageView delete;

    @FXML
    private Text dateDebut;

    @FXML
    private Text dateFin;

    @FXML
    private Text hote;

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

        Location currentLocation = allLocation.getLocationList().get(locationLoue.getLocation_id());

        String url = "D:\\CoursAmphi\\MASTER\\S2\\Prototypage\\projet-reservation-sejours\\src\\main\\resources\\com\\example\\projetreservationsejours\\images\\Wrong.png";

        delete.setImage(new Image(url));
        delete.setOnMouseClicked((MouseEvent event) -> {
            application.fenetreControlleur.showNotification("Supprimer","Suppression en cours..",2000,"images/Right.png");
            AllLocationLoue allLocationLoue = new AllLocationLoue();
            try {
                allLocationLoue.loadData("location_loue.csv");
                allLocationLoue.deleteLocationLoueFromCsv("location_loue.csv", locationLoue.getId());
                application.fenetreControlleur.changerDeFenetre("Accueil.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        image.setImage(new Image(currentLocation.getUrlPhoto()));
        image.setOnMouseClicked((MouseEvent event) -> {
            // Load the card details FXML file
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardDetails.fxml"));
                Parent root = fxmlLoader.load();

                CardDetailsControlleur cardDetailsController = fxmlLoader.getController();
                cardDetailsController.setCard(currentLocation);
                Scene scene = new Scene(root);
                application.fenetreControlleur.getStagePrincipale().centerOnScreen();
                application.fenetreControlleur.getStagePrincipale().setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        titre.setText(currentLocation.getTitle());
        dateDebut.setText(currentLocation.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dateFin.setText(currentLocation.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        prix.setText(currentLocation.getPrice() + " €");
        lieu.setText(currentLocation.getLocation());
        nbMaxPersonne.setText(String.valueOf(currentLocation.getNumberOfPeople()));
        hote.setText(users.getUsers().get(Integer.parseInt(currentLocation.getHost_user_id())-1).getNom());
    }

    @FXML
    void eraseChoice(MouseEvent event) {
        application.fenetreControlleur.showNotification("Supprimer","Suppression en cours..",2000,"images/Right.png");
    }

}
