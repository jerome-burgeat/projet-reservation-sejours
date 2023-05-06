package com.example.projetreservationsejours.controlleur;
import com.example.projetreservationsejours.Application;
import com.example.projetreservationsejours.modele.User;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PageConnexionControlleur extends Preloader implements Initializable {
    Application application;
    @FXML
    private Button boutonConnexion;

    @FXML
    private CheckBox checkboxHote;

    @FXML
    private CheckBox checkboxVoyageur;

    @FXML
    private Text erreurCheckbox;

    @FXML
    private Text erreurConnexion;

    @FXML
    private Text erreurNomUtilisateur;

    @FXML
    private Text erreurPassword;

    @FXML
    private PasswordField motDePasse;

    @FXML
    private TextField nomUtilisateur;

    @FXML
    private Pane pageConnexionStage;

    private User user = new User();

    @FXML
    void checkHote(ActionEvent event) {
        checkboxVoyageur.setDisable(checkboxHote.isSelected());
    }

    @FXML
    void checkVoyageur(ActionEvent event) {
        checkboxHote.setDisable(checkboxVoyageur.isSelected());
    }

    Boolean verifyTextFieldEmpty(TextField textfield,Text text, String error,Boolean isValid){
        if(textfield.getText().isEmpty()){
            text.setText(error +" est obligatoire");
            isValid = false;
        }
        if(textfield.getText().contains(";")){
            text.setText(error + " ne peut contenir ';'");
            isValid = false;
        }
        return  isValid;
    }

    void resetAllText(){
        erreurNomUtilisateur.setText("");
        erreurPassword.setText("");
        erreurCheckbox.setText("");
        erreurConnexion.setText("");
    }

    @FXML
    void verifyInscriptionDetails(ActionEvent event) throws IOException {
        Boolean isValid = true;
        Boolean found = false;
        resetAllText();
        isValid = verifyTextFieldEmpty(nomUtilisateur,erreurNomUtilisateur,"Le nom d'utilisateur",isValid);
        isValid = verifyTextFieldEmpty(motDePasse,erreurPassword,"Le mot de passe",isValid);

        if(!checkboxVoyageur.isSelected() && !checkboxHote.isSelected()){
            erreurCheckbox.setText("Veuillez choisir un mode");
            isValid = false;
        }
        if(isValid){
            for(int i=0; i<application.allUsers.getUsers().size() && !found; i++){
                if(application.allUsers.getUsers().get(i).getUsername().equals(nomUtilisateur.getText()) && application.allUsers.getUsers().get(i).getPassword().equals(motDePasse.getText())){
                    found=true;
                    application.fenetreControlleur.showNotification("Connexion","Vous êtes désormais connecté",2000,"success");
                    application.userConnected = application.allUsers.findUserByUsernameAndPassword(nomUtilisateur.getText(), motDePasse.getText());
                    application.fenetreControlleur.changerDeFenetre("Accueil.fxml");
                    Stage stage = (Stage) pageConnexionStage.getScene().getWindow();
                    stage.close();
                }
            }
            if(!found) {
                erreurConnexion.setText("Les identifiants sont incorrectes");
            }
        }
    }
    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomUtilisateur.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #D3D3D3");
        motDePasse.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #D3D3D3");
        pageConnexionStage.setStyle("-fx-background-color: #FFFFFF;");
        boutonConnexion.setStyle("-fx-background-color: #800020; -fx-text-fill:#FFFFFF; -fx-border-radius: 30;-fx-background-radius: 30;-fx-border-color: #FFFFFF; -fx-arc-width: 30");
    }
}
