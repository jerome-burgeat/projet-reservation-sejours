package com.example.projetreservationsejours.controlleur;
import com.example.projetreservationsejours.Application;
import javafx.application.Preloader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PageInscriptionControlleur extends Preloader implements Initializable {
    Application application;
    @FXML
    private Button popUpPageInscription;

    @FXML
    void popUpPageInscription(ActionEvent event) throws IOException {
        application.fenetreControlleur.popupFenetre("Accueil.fxml");
    }

    public void setButton(Button button) {
            button.setBackground(new Background(new BackgroundFill(Color.WHEAT, null, null)));
            button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       setButton(popUpPageInscription);
    }
}
