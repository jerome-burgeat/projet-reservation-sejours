package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;
import com.example.projetreservationsejours.modele.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CardDetailsControlleur implements Initializable {

    Application application;

    AllUser users;

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

    @FXML
    private FlowPane flowPane;

    @FXML
    private HBox hboxPane;

    @FXML
    private HBox hboxPane2;

    @FXML
    private Pane headerPane;

    @FXML
    private Pane mainPane;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane3;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vBoxPane;

    @FXML
    private VBox vboxPane2;

    @FXML
    private VBox comments;

    @FXML
    private Button boutonAjoutPanier;

    @FXML
    private Button ajouterCommentaire;

    private Rating rating = new Rating(5);;

    private Rating addRatingComment;

    private Location location;

    private double globalNote;

    private double totalNote;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        defineBackground();
        setRating();
        changeHeaderVisibility();
        setTextAreaFormatting();
        if (this.isUserConnected()) {
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
        this.location = location;
        image.setFitWidth(513);
        image.setFitHeight(436);
        image.setPreserveRatio(false);
        image.setImage(new Image(location.getUrlPhoto()));
        BorderPane borderPane = new BorderPane(image);
        borderPane.setStyle("-fx-border-color: #FECEA8; -fx-border-radius: 10; -fx-border-width: 2;");
        Rectangle clipRect = new Rectangle(513,436);
        clipRect.setArcWidth(12);
        clipRect.setArcHeight(12);
        image.setClip(clipRect);
        borderPane.setCenter(image);
        borderPane.setPrefSize(513, 436);
        pane2.getChildren().add(borderPane);
        titre.setText(location.getTitle());
        dateDebut.setText(location.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dateFin.setText(location.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        prix.setText(location.getPrice() + " €");
        lieu.setText(location.getLocation());
        nbMaxPersonne.setText(String.valueOf(location.getNumberOfPeople()));
        hote.setText(users.getUsers().get(Integer.parseInt(location.getHost_user_id())-1).getNom());

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
                totalNote += response.getNote();
                comments.getChildren().add(cardNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        globalNote = totalNote/commentaireByLocationId.getCommentaireList().size();
        totalNote =0;
        rating.setRating(globalNote);
        locationId = location.getId();
    }

    @FXML
    void addToCart(MouseEvent event) {
        if(!isUserConnected()) {
            application.fenetreControlleur.showNotification("Alerte","Veuillez vous connecter !",2000,"warning");
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
                    application.fenetreControlleur.showNotification("Alerte","Votre demande de location est en cours !",2000,"warning");
                }
                else {
                    LocationLoue locationLoue = new LocationLoue(allLocationLoue.howManyLocationLoue() + 1, locationId-1, application.userConnected.getId());
                    allLocationLoue.addNewLocationLoueToCsv("location_loue.csv", locationLoue);
                    application.fenetreControlleur.showNotification("Alerte", "Location ajoutée au panier !", 2000, "warning");
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
        application.fenetreControlleur.showNotification("Deconnexion","Vous êtes désormais déconnecté",2000,"success");
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

    public void defineBackground(){
        flowPane.setStyle("-fx-background-color: #FFFFFF;");
        hboxPane.setStyle("-fx-background-color: #FFFFFF;");
        hboxPane2.setStyle("-fx-background-color: #FFFFFF;");
        mainPane.setStyle("-fx-background-color: #FFFFFF;");
        pane2.setStyle("-fx-background-color: #FFFFFF;");
        pane3.setStyle("-fx-background-color: #FFFFFF;");
        scrollPane.setStyle("-fx-background-color: #FFFFFF;");
        vBoxPane.setStyle("-fx-background-color: #FFFFFF;");
        vboxPane2.setStyle("-fx-background-color: #FFFFFF;");
        comments.setStyle("-fx-background-color: #FFFFFF;");
        headerPane.setStyle("-fx-background-color:#800020");
        boutonConnexion.setStyle("-fx-background-color:#FECEA8; -fx-text-fill: #800020; -fx-border-radius: 30;-fx-background-radius: 30;-fx-border-color: #800020; -fx-arc-width: 30");
        bountonInscription.setStyle("-fx-background-color:#FECEA8; -fx-text-fill: #800020; -fx-border-radius: 30;-fx-background-radius: 30;-fx-border-color: #800020; -fx-arc-width: 30");
        boutonAjoutPanier.setStyle("-fx-background-color: #800020; -fx-text-fill:#FFFFFF; -fx-border-radius: 30;-fx-background-radius: 30;-fx-border-color: #FFFFFF; -fx-arc-width: 30");
        addCommentaire.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #D3D3D3");
        ajouterCommentaire.setStyle("-fx-background-color: #800020; -fx-text-fill:#FFFFFF; -fx-border-radius: 30;-fx-background-radius: 30;-fx-border-color: #FFFFFF; -fx-arc-width: 30");
    }

    public void setRating(){
        //global rating of the location
        rating.setPartialRating(true);
        rating.setUpdateOnHover(false);
        rating.setDisable(true);
        rating.setLayoutX(1100);
        rating.setLayoutY(70);
        pane2.getChildren().add(rating);

        //new rating for a new comment
        addRatingComment = new Rating(5);
        addRatingComment.setRating(0);
        addRatingComment.setPartialRating(true);
        addRatingComment.setUpdateOnHover(false);
        addRatingComment.setLayoutX(550);
        addRatingComment.setLayoutY(95);
        addRatingComment.setPrefWidth(150);
        pane3.getChildren().add(addRatingComment);

    }

    @FXML
    void addComment(ActionEvent event) {
        if(!isUserConnected()) {
            application.fenetreControlleur.showNotification("Alerte","Veuillez vous connecter !",2000,"warning");
        }
        //Si l'utilisateur est connecté
        else {
            String text = addCommentaire.getText().replaceAll("\\r\\n|\\r|\\n", "|");
            if(text.chars().filter(ch -> ch == '|').count()>3){
                application.fenetreControlleur.showNotification("Erreur","Vos caractères font plus de quatre lignes",2000,"error");
            }
            else {
                comments.getChildren().clear();

                AllCommentaire commentaireByLocationId = new AllCommentaire();

                try {
                    commentaireByLocationId.loadData("commentaires.csv");
                    Commentaire commentaire = new Commentaire(commentaireByLocationId.getCommentaireList().size(), location.getId(), application.userConnected.getId(), text, addRatingComment.getRating());
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
                        totalNote += response.getNote();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                globalNote = totalNote / commentaireByLocationId.getCommentaireList().size();
                rating.setRating(globalNote);
                totalNote = 0;
                application.fenetreControlleur.showNotification("Commentaire", "Votre commentaire est publié !", 2000, "success");
                addCommentaire.setText("");
                addRatingComment.setRating(0);
            }
        }

    }
    public void setTextAreaFormatting(){
        addCommentaire.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isAdded()) {
                String newText = addCommentaire.getText().substring(0, change.getRangeStart()) + change.getText()
                        + addCommentaire.getText().substring(change.getRangeEnd());
                // Check if the width exceeds the maximum width and wrap to a new line
                javafx.scene.text.Text text = new javafx.scene.text.Text(newText);
                text.setFont(addCommentaire.getFont());
                if (text.getLayoutBounds().getWidth() > 371) {
                    int insertIndex = change.getRangeStart() + change.getText().length() - 1;
                    String modifiedText = newText.substring(0, insertIndex) + "\n" + newText.substring(insertIndex);
                    addCommentaire.setText(modifiedText);
                    addCommentaire.positionCaret(modifiedText.length());
                    return null;
                }
            }
            return change;
        }));
    }
}
