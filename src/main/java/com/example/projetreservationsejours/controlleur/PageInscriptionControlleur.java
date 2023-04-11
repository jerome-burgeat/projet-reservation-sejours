package com.example.projetreservationsejours.controlleur;
import com.example.projetreservationsejours.Application;
import javafx.application.Preloader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PageInscriptionControlleur extends Preloader implements Initializable {
    Application application;

    @FXML
    private Button boutonInscrire;
    @FXML
    private CheckBox checkboxHote;

    @FXML
    private CheckBox checkboxVoyageur;

    @FXML
    private CheckBox chekboxTermesEtConditions;

    @FXML
    private TextField email;

    @FXML
    private PasswordField motDePasse;

    @FXML
    private TextField nom;

    @FXML
    private TextField nomUtilisateur;

    @FXML
    private TextField prenom;
    @FXML
    private Text erreurEmail;

    @FXML
    private Text erreurNom;

    @FXML
    private Text erreurNomUtilisateur;

    @FXML
    private Text erreurPassword;

    @FXML
    private Text erreurPrenom;

    @FXML
    private Text erreurCheckbox;

    @FXML
    private Text erreurCheckboxTermes;

    public void setButton(Button button) {
            button.setBackground(new Background(new BackgroundFill(Color.WHEAT, null, null)));
            button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    @FXML
    void checkHote(ActionEvent event) {
        if(checkboxHote.isSelected()){
            checkboxVoyageur.setDisable(true);
        }
        else{
            checkboxVoyageur.setDisable(false);
        }
    }

    @FXML
    void checkVoyageur(ActionEvent event) {
        if(checkboxVoyageur.isSelected()){
            checkboxHote.setDisable(true);
        }
        else{
            checkboxHote.setDisable(false);
        }

    }

    void verifyTextFieldEmpty(TextField textfield,Text text, String error){
        if(textfield.getText().isEmpty()){
            text.setText(error +" est obligatoire");
        }
        if(textfield==email){
            String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            if(!patternMatches(textfield.getText(), regexPattern)){
                text.setText(error + " est invalide");
            }
        }
        if(textfield.getText().contains(";")){
            text.setText(error + " ne peut contenir ';'");
        }
    }
    void resetAllText(){
        erreurPrenom.setText("");
        erreurNomUtilisateur.setText("");
        erreurNom.setText("");
        erreurEmail.setText("");
        erreurPassword.setText("");
        erreurCheckbox.setText("");
        erreurCheckboxTermes.setText("");
    }

    public boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    @FXML
    void verifyInscriptionDetails(ActionEvent event){
        resetAllText();
        verifyTextFieldEmpty(prenom,erreurPrenom,"Le prénom");
        verifyTextFieldEmpty(nomUtilisateur,erreurNomUtilisateur,"Le nom d'utilisateur");
        verifyTextFieldEmpty(nom,erreurNom,"Le nom");
        verifyTextFieldEmpty(email,erreurEmail,"L'email");
        verifyTextFieldEmpty(motDePasse,erreurPassword,"Le mot de passe");

        if(!checkboxVoyageur.isSelected() && !checkboxHote.isSelected()){
            erreurCheckbox.setText("Veuillez choisir un mode");
        }
        if(!chekboxTermesEtConditions.isSelected()){
            erreurCheckboxTermes.setText("Veuillez accepter les termes et conditions");
        }

    }
    @Override
    public void start(Stage stage) throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
