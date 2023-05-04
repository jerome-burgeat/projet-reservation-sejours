package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;
import com.example.projetreservationsejours.modele.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CardDetailsControlleur implements Initializable {

    Application application;

    AllUser users;

    @FXML
    private VBox comments;

    @FXML
    private Text dateDebut;

    @FXML
    private Text dateFin;

    @FXML
    private Text hote;

    @FXML
    private ImageView image;

    @FXML
    private Text lieu;

    @FXML
    private Text nbMaxPersonne;

    @FXML
    private Text note;

    @FXML
    private Text prix;

    @FXML
    private Text titre;

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
    private ImageView user;

    @FXML
    private Text nbLocation;

    @FXML
    private Text userName;

    @FXML
    private TextArea addCommentaire;

    private int locationId;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        changeHeaderVisibility();

        if(this.isUserConnected()) {
            userName.setText(application.userConnected.getUsername());
            AllLocationLoue allLocationLoue = new AllLocationLoue();
            try {
                allLocationLoue.loadData("location_loue.csv", application.userConnected.getId());
                nbLocation.setText(String.valueOf(allLocationLoue.howManyLocationLoue()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        users = new AllUser();
        try {
            users.loadData("utilisateurs.csv");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        shopping_cart.setOnMouseClicked(event -> {
            try {
                application.fenetreControlleur.changerDeFenetre("ShoppingCardDetails.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setCard(Location location) {
        image.setImage(new Image(location.getUrlPhoto()));
        titre.setText(location.getTitle());
        dateDebut.setText(location.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dateFin.setText(location.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        prix.setText(location.getPrice() + " €");
        lieu.setText(location.getLocation());
        nbMaxPersonne.setText(String.valueOf(location.getNumberOfPeople()));
        hote.setText(users.getUsers().get(Integer.parseInt(location.getHost_user_id())-1).getNom());
        addCommentaire.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    //Si l'utilisateur n'est pas connecté
                    if(!isUserConnected()) {
                        application.fenetreControlleur.showNotification("Alerte","Veuillez vous connecter !",2000,"images/Right.png");
                    }
                    //Si l'utilisateur est connecté
                    else {
                        String text = addCommentaire.getText();
                        comments.getChildren().clear();

                        AllCommentaire commentaireByLocationId = new AllCommentaire();

                        try {
                            commentaireByLocationId.loadData("commentaires.csv");
                            Commentaire commentaire = new Commentaire(commentaireByLocationId.getCommentaireList().size(),
                                    location.getId(), application.userConnected.getId(), text);
                            commentaireByLocationId.getCommentaireList().clear();
                            commentaireByLocationId.addNewCommentaireToCsv("commentaires.csv", commentaire);
                            commentaireByLocationId.loadData("commentaires.csv", location.getId());
                            commentaireByLocationId.displayCommentaireList();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        for (Commentaire response : commentaireByLocationId.getCommentaireList()) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentaireTemplate.fxml"));
                                AnchorPane cardNode = loader.load();
                                CommentaireTemplateControlleur commentaireControlleur = loader.getController();
                                commentaireControlleur.setCommentaire(users.getUsers().get(Integer.parseInt(String.valueOf(response.getUser_id() - 1))).getUsername(), response);
                                comments.getChildren().add(cardNode);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        application.fenetreControlleur.showNotification("Commentaire","Votre commentaire est publié !",2000,"images/Right.png");
                        addCommentaire.setText("");
                    }
                }
            }
        });

        AllCommentaire commentaireByLocationId = new AllCommentaire();
        try {
            commentaireByLocationId.loadData("commentaires.csv", location.getId());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Commentaire response : commentaireByLocationId.getCommentaireList()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentaireTemplate.fxml"));
                AnchorPane cardNode = loader.load();
                CommentaireTemplateControlleur commentaireControlleur = loader.getController();
                commentaireControlleur.setCommentaire(users.getUsers().get(Integer.parseInt(String.valueOf(response.getUser_id()-1))).getUsername(), response);
                comments.getChildren().add(cardNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        locationId = location.getId();
    }

    @FXML
    void addToCart(MouseEvent event) {
        if(!isUserConnected()) {
            application.fenetreControlleur.showNotification("Alerte","Veuillez vous connecter !",2000,"images/Right.png");
        }
        else {
            try {
                AllLocationLoue allLocationLoue = new AllLocationLoue();
                allLocationLoue.loadData("location_loue.csv");
                boolean isHasAlready = false;
                for(int i=0; i < allLocationLoue.howManyLocationLoue() && !isHasAlready; i++) {
                    if(allLocationLoue.getLocationList().get(i).getLocation_id() == locationId-1
                        && allLocationLoue.getLocationList().get(i).getUser_id() == application.userConnected.getId()) {
                        isHasAlready = true;
                    }
                }
                if(isHasAlready) {
                    application.fenetreControlleur.showNotification("Alerte","Votre demande de location est en cours !",2000,"images/Right.png");
                }
                else {
                    LocationLoue locationLoue = new LocationLoue(allLocationLoue.howManyLocationLoue() + 1, locationId-1, application.userConnected.getId());
                    allLocationLoue.addNewLocationLoueToCsv("location_loue.csv", locationLoue);
                    application.fenetreControlleur.showNotification("Alerte", "Location ajoutée au panier !", 2000, "images/Right.png");
                    application.fenetreControlleur.changerDeFenetre("Accueil.fxml");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

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
        application.fenetreControlleur.showNotification("Deconnexion","Vous êtes désormais déconnecté",2000,"images/Right.png");
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
}
