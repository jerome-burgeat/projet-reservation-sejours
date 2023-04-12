package com.example.projetreservationsejours.controlleur;
import com.example.projetreservationsejours.Application;
import com.example.projetreservationsejours.modele.AllUser;
import com.example.projetreservationsejours.modele.User;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PageInscriptionControlleur extends Preloader implements Initializable {
    Application application;

    @FXML
    private Pane pageInscriptionStage;

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

    private User user = new User();

    private AllUser allUsers =  new AllUser();

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

    Boolean verifyTextFieldEmpty(TextField textfield,Text text, String error,Boolean isValid){
        if(textfield.getText().isEmpty()){
            text.setText(error +" est obligatoire");
            isValid = false;
        }
        if(textfield==email){
            String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            if(!patternMatches(textfield.getText(), regexPattern)){
                text.setText(error + " est invalide");
                isValid = false;
            }
        }
        if(textfield.getText().contains(";")){
            text.setText(error + " ne peut contenir ';'");
            isValid = false;
        }
        return  isValid;
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
    void verifyInscriptionDetails(ActionEvent event) throws IOException {
        Boolean isValid = true;
        resetAllText();
        isValid = verifyTextFieldEmpty(prenom,erreurPrenom,"Le prénom",isValid);
        isValid = verifyTextFieldEmpty(nomUtilisateur,erreurNomUtilisateur,"Le nom d'utilisateur",isValid);
        isValid = verifyTextFieldEmpty(nom,erreurNom,"Le nom",isValid);
        isValid = verifyTextFieldEmpty(email,erreurEmail,"L'email",isValid);
        isValid = verifyTextFieldEmpty(motDePasse,erreurPassword,"Le mot de passe",isValid);

        if(!checkboxVoyageur.isSelected() && !checkboxHote.isSelected()){
            erreurCheckbox.setText("Veuillez choisir un mode");
            isValid = false;
        }
        if(!chekboxTermesEtConditions.isSelected()){
            erreurCheckboxTermes.setText("Veuillez accepter les termes et conditions");
            isValid = false;
        }
        if(isValid){
            for(int i=0; i<allUsers.getUsers().size() && isValid; i++){
               if(allUsers.getUsers().get(i).getUsername().equals(nomUtilisateur.getText()) || allUsers.getUsers().get(i).getEmail().equals(email.getText())){
                   erreurCheckboxTermes.setText("Cet utilisateur existe déjà ! ");
                   isValid= false;
               }
            }
        }
        if(isValid){
            user.setId(allUsers.getUsers().size()+1);
            user.setPrenom(prenom.getText());
            user.setNom(nom.getText());
            user.setUsername(nomUtilisateur.getText());
            user.setEmail(email.getText());
            user.setPassword(motDePasse.getText());
            user.setVoyageur(checkboxVoyageur.isSelected());
            user.setHote(checkboxHote.isSelected());
            allUsers.addNewUserToCsv("utilisateurs.csv",user);
            application.fenetreControlleur.showNotification("Connexion","Votre inscription a été validé",2000,"images/Right.png");
            application.userConnected = user;
            Stage stage = (Stage) pageInscriptionStage.getScene().getWindow();
            stage.close();
        }

    }


    @Override
    public void start(Stage stage) throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allUsers.loadData("utilisateurs.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
