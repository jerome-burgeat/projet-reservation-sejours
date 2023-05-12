package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;
import com.example.projetreservationsejours.modele.AllLocationEnValidation;
import com.example.projetreservationsejours.modele.AllLocationLoue;
import com.example.projetreservationsejours.modele.LocationEnValidation;
import com.example.projetreservationsejours.modele.LocationLoue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HostCardDetailsControlleur implements Initializable {

    Application application;

    @FXML
    private Button bountonInscription;

    @FXML
    private Button boutonConnexion;

    @FXML
    private Button changeViewHostMode;

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
    private Text userName;

    @FXML
    private FlowPane flowPane;

    @FXML
    private HBox hboxPane;

    @FXML
    private HBox hboxPane2;

    @FXML
    private Pane headerPane;

    @FXML
    private VBox userChoice;

    @FXML
    private Pane mainPane;

    @FXML
    private Pane pane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox userChoice1;

    @FXML
    private VBox vboxPane;

    @FXML
    private ChoiceBox viewHostMode;

    ObservableList<String> options = FXCollections.observableArrayList("Toutes les demandes","Toutes les demandes validées");


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setBackground();
        changeHeaderVisibility();

        if (this.isUserConnected()) {
            userName.setText(application.userConnected.getUsername());
            AllLocationLoue allLocationLoue = new AllLocationLoue();
            try {
                allLocationLoue.loadData("location_loue.csv", application.userConnected.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            nbLocation.setText(String.valueOf(allLocationLoue.howManyLocationLoue()));
        }

        AllLocationEnValidation allLocationEnValidation = new AllLocationEnValidation();
        try {
            viewHostMode.setValue(options.get(0));
            viewHostMode.setItems(options);
            if(viewHostMode.getValue().equals(options.get(0))) {
                allLocationEnValidation.loadData("location_en_validation.csv", application.userConnected.getId());
            } else if (viewHostMode.getValue().equals(options.get(1))) {
                allLocationEnValidation.loadData("location_en_validation.csv", application.userConnected.getId(), true);
            }
            //nbLocation.setText(String.valueOf(allLocationEnValidation.howManyLocationLoue()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (LocationEnValidation hostCard : allLocationEnValidation.getLocationEnValidationList()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HostCardTemplate.fxml"));
                AnchorPane cardNode = loader.load();
                HostCardTemplateControlleur hostCardTemplateControlleur = loader.getController();
                hostCardTemplateControlleur.setUserChoice(hostCard);
                userChoice.getChildren().add(cardNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        shopping_cart.setOnMouseClicked(event -> {
            try {
                application.fenetreControlleur.changerDeFenetre("ShoppingCardDetails.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        user.setOnMouseClicked(event -> {
            application.fenetreControlleur.showNotification("Alerte", "Vous êtes déjà dans la bonne fenêtre", 2000, "warning");
        });
    }

    /*public void setUserChoiceCard(Location location) {

    }*/

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
        application.fenetreControlleur.showNotification("Deconnexion","Vous êtes désormais déconnecté",2000,"success");
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

    @FXML
    void search(ActionEvent event) throws IOException {
        userChoice.getChildren().clear();
        if (this.isUserConnected()) {
            userName.setText(application.userConnected.getUsername());
            AllLocationLoue allLocationLoue = new AllLocationLoue();
            try {
                allLocationLoue.loadData("location_loue.csv", application.userConnected.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            nbLocation.setText(String.valueOf(allLocationLoue.howManyLocationLoue()));
        }
        AllLocationEnValidation allLocationEnValidation = new AllLocationEnValidation();
        try {
            if(viewHostMode.getValue().equals(options.get(0))) {
                allLocationEnValidation.loadData("location_en_validation.csv", application.userConnected.getId());
            } else if (viewHostMode.getValue().equals(options.get(1))) {
                allLocationEnValidation.loadData("location_en_validation.csv", application.userConnected.getId(), true);
            }
            //nbLocation.setText(String.valueOf(allLocationEnValidation.howManyLocationLoue()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (LocationEnValidation hostCard : allLocationEnValidation.getLocationEnValidationList()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HostCardTemplate.fxml"));
                AnchorPane cardNode = loader.load();
                HostCardTemplateControlleur hostCardTemplateControlleur = loader.getController();
                hostCardTemplateControlleur.setUserChoice(hostCard);
                userChoice.getChildren().add(cardNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setBackground(){
        flowPane.setStyle("-fx-background-color: #FFFFFF;");
        hboxPane.setStyle("-fx-background-color: #FFFFFF;");
        hboxPane2.setStyle("-fx-background-color: #FFFFFF;");
        userChoice.setStyle("-fx-background-color: #FFFFFF;");
        mainPane.setStyle("-fx-background-color: #FFFFFF;");
        pane.setStyle("-fx-background-color: #FFFFFF;");
        scrollPane.setStyle("-fx-background-color: #FFFFFF;");
        userChoice1.setStyle("-fx-background-color: #FFFFFF;");
        vboxPane.setStyle("-fx-background-color: #FFFFFF;");
        headerPane.setStyle("-fx-background-color:#800020");
        boutonConnexion.setStyle("-fx-background-color:#FECEA8; -fx-text-fill: #800020; -fx-border-radius: 30;-fx-background-radius: 30;-fx-border-color: #800020; -fx-arc-width: 30");
        bountonInscription.setStyle("-fx-background-color:#FECEA8; -fx-text-fill: #800020; -fx-border-radius: 30;-fx-background-radius: 30;-fx-border-color: #800020; -fx-arc-width: 30");
        viewHostMode.setStyle("-fx-background-color: #FFFFFF;-fx-font-size: 18px; -fx-font-family: 'Perpetua';");
        changeViewHostMode.setStyle("-fx-background-color:#FECEA8; -fx-text-fill: #800020; -fx-border-radius: 30;-fx-background-radius: 30;-fx-border-color: #800020; -fx-arc-width: 30");
    }
}
