package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class FenetreControlleur {

    Stage stagePrincipale;
    Scene scenePrincipale;

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
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle(titreFenetre);
        stage.show();
    }

    public void showNotification(String title, String text,int milliSecs,String imagePath) {
        ImageView image = new ImageView(Application.class.getResource(imagePath).toExternalForm());
        image.setPreserveRatio(true);
        image.setFitWidth(50);
        image.setFitHeight(50);
        Notifications notification = Notifications.create();
        notification.hideAfter(new Duration(milliSecs));
        notification.title(title);
        notification.text(text);
        notification.graphic(image);
        notification.owner(getStagePrincipale());
        if(title.equals("Alerte")) {
            Platform.runLater(() -> notification.showWarning());
        }
        else if(title.equals("Erreur")) {
            Platform.runLater(() -> notification.showError());
        }
        else {
            Platform.runLater(() -> notification.show());
        }
    }
}
