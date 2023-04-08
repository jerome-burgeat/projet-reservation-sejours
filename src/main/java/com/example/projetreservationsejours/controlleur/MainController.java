package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;

import com.example.projetreservationsejours.modele.Data;
import com.example.projetreservationsejours.modele.Datum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    Application application;

    @FXML
    private VBox cardContainer;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Initialisation de la
        Data data = new Data();
        try {
            data.loadData("database.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        data.displayDataList();
        int cpt = 0;
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        for (Datum card : data.getDataList()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("cardTemplate.fxml"));
                AnchorPane cardNode = loader.load();
                CardTemplateControlleur cardController = loader.getController();
                cardController.setCard(card);
                if(cpt < 3) {
                    hBox.getChildren().add(cardNode);
                    cpt++;
                }
                else {
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
    void changerDeFenetre(ActionEvent event) throws IOException {
        application.fenetreControlleur.changerDeFenetre("PageInscription.fxml");
    }
}
