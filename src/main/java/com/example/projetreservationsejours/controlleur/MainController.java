package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;

import com.example.projetreservationsejours.modele.AllLocation;
import com.example.projetreservationsejours.modele.AllUser;
import com.example.projetreservationsejours.modele.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    Application application;

    @FXML
    private VBox cardContainer;

    @FXML
    private DatePicker dateDebut;

    @FXML
    private DatePicker dateFin;

    @FXML
    private Button boutonConnexion;

    @FXML
    private Button bountonInscription;

    @FXML
    private ImageView home;

    @FXML
    private ImageView logout;

    @FXML
    private ImageView shopping_cart;

    @FXML
    private TextField searchTextField;

    @FXML
    private ImageView user;

    @FXML
    private Text nbLocation;

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
            userName.setText(application.userConnected.getUsername());
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


    /**
     * For all the locations available:
     *  - check if searchTextField, dateDebut and dateFin is in the resource file
     *      -> Not null: display elements
     *      -> Null: display error
     *  - possibilities to search on field or more
     * */
    @FXML
    void searchBar(ActionEvent event) {
        AllLocation allLocation = new AllLocation();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            allLocation.loadData("locations.csv", searchTextField.getText(), dateDebut.getValue().format(formatter), dateFin.getValue().format(formatter));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        allLocation.displayLocationList();

        if(this.isUserConnected()) {
            userName.setText(application.userConnected.getUsername());
        }

        cardContainer.getChildren().clear();
        int cpt = 0;
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);

        if(searchTextField.getText().equals("")) {
            try {
                allLocation.loadData("locations.csv");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            allLocation.displayLocationList();

            if(this.isUserConnected()) {
                userName.setText(application.userConnected.getUsername());
            }
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
        else if (allLocation.getLocationList().isEmpty()) {
            hBox.getChildren().add(new Text("La recherche n'a pas aboutie"));
            cardContainer.getChildren().add(hBox);
        }
        else {
            for (Location card : allLocation.getLocationList()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("cardTemplate.fxml"));
                    AnchorPane cardNode = loader.load();
                    CardTemplateControlleur cardController = loader.getController();
                    cardController.setCard(card);
                    if (allLocation.getLocationList().size() < 3) {
                        hBox.getChildren().add(cardNode);
                        cardContainer.getChildren().add(hBox);
                    } else if (cpt < 3) {
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
    }


    /**
     * Go back to home windows
     * */
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
    public boolean isUserConnected() {
        if(application.userConnected != null) {
            System.out.println(application.userConnected.toString());
        }
        return application.userConnected != null;
    }
}
