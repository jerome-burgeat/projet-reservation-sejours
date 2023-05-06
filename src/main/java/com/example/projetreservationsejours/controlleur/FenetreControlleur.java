package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class FenetreControlleur {

    Stage stagePrincipale;
    Scene scenePrincipale;
    ImageView image;

    public FenetreControlleur() {
    }

    public Stage getStagePrincipale() {
        return stagePrincipale;
    }

    public void setStagePrincipale(Stage stagePrincipale) {

        this.stagePrincipale = stagePrincipale;
        this.stagePrincipale.centerOnScreen();
    }

    public void setScenePrincipale(Scene scenePrincipale) {
        this.scenePrincipale = scenePrincipale;
    }

    public void changerDeFenetre(String nomFenetre) throws IOException {
        Parent root = new FXMLLoader(getClass().getResource(nomFenetre)).load();
        Scene scene = new Scene(root);
        this.getStagePrincipale().centerOnScreen();
        this.getStagePrincipale().setScene(scene);
    }

    public void popupFenetre(String nomFenetre,String titreFenetre) throws IOException {
        Parent root = new FXMLLoader(getClass().getResource(nomFenetre)).load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("images/logo.png")));
        stage.setResizable(false);
        stage.setIconified(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle(titreFenetre);
        stage.show();
    }

    public void showNotification(String title, String text,int milliSecs,String mode) {
        Notifications notification = Notifications.create();
        notification.hideAfter(new Duration(milliSecs));
        notification.title(title);
        notification.text(text);
        if(mode.equals("warning")){
            image = new ImageView(Application.class.getResource("images/Warning.png").toExternalForm());
            image.setPreserveRatio(true);
            image.setFitWidth(40);
            image.setFitHeight(40);
        } else if (mode.equals("success")) {
            image = new ImageView(Application.class.getResource("images/Right.png").toExternalForm());
            image.setPreserveRatio(true);
            image.setFitWidth(50);
            image.setFitHeight(50);
        } else if (mode.equals("error")) {
            image = new ImageView(Application.class.getResource("images/WrongNotification.png").toExternalForm());
            image.setPreserveRatio(true);
            image.setFitWidth(50);
            image.setFitHeight(50);
        }
        notification.graphic(image);
        notification.owner(getStagePrincipale());
        //getStagePrincipale().getScene().getStylesheets().add(Application.class.getResource("CSS/Test.css").toExternalForm());
        Platform.runLater(() -> notification.show());
    }
}
