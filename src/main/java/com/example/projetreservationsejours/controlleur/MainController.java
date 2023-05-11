package com.example.projetreservationsejours.controlleur;

import com.example.projetreservationsejours.Application;

import com.example.projetreservationsejours.modele.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    Application application;

    @FXML
    private Pane researchPane;

    @FXML
    private Pane headerPane;

    @FXML
    private ScrollPane scrollPane;

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

    @FXML
    private HBox hboxPane;

    @FXML
    private VBox container;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Button boutonRecherche;

    @FXML
    private Pane mainPane;

    @FXML
    private VBox vboxPane;

    @FXML
    private ChoiceBox viewMode;

    ObservableList<String> options = FXCollections.observableArrayList("Toutes les demandes","Mes locations");

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        changeHeaderVisibility();
        scrollPane.setStyle("-fx-background-color: #FFFFFF;");
        cardContainer.setStyle("-fx-background-color: #FFFFFF;");
        researchPane.setStyle("-fx-background-color: #FFFFFF;");
        hboxPane.setStyle("-fx-background-color: #FFFFFF;");
        container.setStyle("-fx-background-color: #FFFFFF;");
        flowPane.setStyle("-fx-background-color: #FFFFFF;");
        vboxPane.setStyle("-fx-background-color: #FFFFFF;");

        headerPane.setStyle("-fx-background-color:#800020");
        boutonConnexion.setStyle("-fx-background-color:#FECEA8; -fx-text-fill: #800020; -fx-border-radius: 30;-fx-background-radius: 30;-fx-border-color: #800020; -fx-arc-width: 30");
        bountonInscription.setStyle("-fx-background-color:#FECEA8; -fx-text-fill: #800020; -fx-border-radius: 30;-fx-background-radius: 30;-fx-border-color: #800020; -fx-arc-width: 30");
        boutonRecherche.setStyle("-fx-background-color: #800020; -fx-text-fill:#FFFFFF; -fx-border-radius: 30;-fx-background-radius: 30;-fx-border-color: #FFFFFF; -fx-arc-width: 30");
        dateDebut.setStyle("-fx-background-color: #FFFFFF;");
        dateDebut.getEditor().setStyle("-fx-font-size: 18px; -fx-font-family: 'Perpetua';");
        dateFin.setStyle("-fx-background-color: #FFFFFF;");
        dateFin.getEditor().setStyle("-fx-font-size: 18px; -fx-font-family: 'Perpetua';");
        viewMode.setStyle("-fx-background-color: #FFFFFF;-fx-font-size: 18px; -fx-font-family: 'Perpetua';");
        searchTextField.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #D3D3D3");
        // Initialisation des locations
        AllLocation allLocation = new AllLocation();
        try {
            allLocation.loadDataAvailable("locations.csv", true);
            viewMode.setValue(options.get(0));
            viewMode.setItems(options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() >= 2) {
                allLocation.getLocationList().clear();
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    if(dateDebut.getValue() == null) {
                        allLocation.loadData("locations.csv", newValue);
                    }
                    else {
                        allLocation.loadData("locations.csv", newValue, dateDebut.getValue().format(formatter), dateFin.getValue().format(formatter));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //allLocation.displayLocationList();

                if(this.isUserConnected()) {
                    userName.setText(application.userConnected.getUsername());
                }

        /*if (viewMode.getValue().equals(options.get(1))) {
            for (int i=0; i < allLocation.getLocationList().size(); i++) {
                if(Integer.parseInt(allLocation.getLocationList().get(i).getHost_user_id()) != this.application.userConnected.getId()) {
                    allLocation.getLocationList().remove(i);
                }
            }
        }*/
                //nbLocation.setText(String.valueOf(allLocationEnValidation.howManyLocationLoue()));

                cardContainer.getChildren().clear();
                if (allLocation.getLocationList().isEmpty()) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.BASELINE_CENTER);
                    hBox.getChildren().add(new Text("La recherche n'a pas aboutie"));
                    cardContainer.getChildren().add(hBox);
                }
                else {
                    if(searchTextField.getText().equals("")) {
                        try {
                            allLocation.getLocationList().clear();
                            allLocation.loadData("locations.csv");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        if (this.isUserConnected()) {
                            userName.setText(application.userConnected.getUsername());
                            AllLocationLoue allLocationLoue = new AllLocationLoue();
                            try {
                                allLocationLoue.loadData("location_loue.csv", application.userConnected.getId());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            nbLocation.setText(String.valueOf(allLocationLoue.howManyLocationLoue()));
                        }
                    }
                    if (viewMode.getValue().equals(options.get(1))) {
                        for(int i=0; i < allLocation.getLocationList().size(); i++) {
                            if(!allLocation.getLocationList().get(i).getHost_user_id().equals(String.valueOf(this.application.userConnected.getId()))) {
                                allLocation.getLocationList().remove(i);
                                i--;
                            }
                        }
                    }
                    allLocation.displayLocationList();
                    displayAllLocation(allLocation);
                }
            }
        });

        if(this.isUserConnected()) {
            userName.setText(application.userConnected.getUsername());
            viewMode.setDisable(false);
            AllLocationLoue allLocationLoue = new AllLocationLoue();
            try {
                allLocationLoue.loadData("location_loue.csv", application.userConnected.getId());
                nbLocation.setText(String.valueOf(allLocationLoue.howManyLocationLoue()));
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

            user.setOnMouseClicked(event -> {
                try {
                    application.fenetreControlleur.changerDeFenetre("HostCardDetails.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else {
            viewMode.setDisable(true);
        }

        displayAllLocation(allLocation);
    }


    /**
     * For all the locations available:
     *  - check if searchTextField, dateDebut and dateFin is in the resource file
     *      -> Not null: display elements
     *      -> Null: display error
     *  - possibilities to search on field or more
     * */
    @FXML
    void searchBar(ActionEvent event) throws IOException {
        AllLocation allLocation = new AllLocation();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if(dateDebut.getValue() == null) {
                allLocation.loadData("locations.csv", searchTextField.getText());
            }
            else {
                allLocation.loadData("locations.csv", searchTextField.getText(), dateDebut.getValue().format(formatter), dateFin.getValue().format(formatter));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //allLocation.displayLocationList();

        if(this.isUserConnected()) {
            userName.setText(application.userConnected.getUsername());
        }

        /*if (viewMode.getValue().equals(options.get(1))) {
            for (int i=0; i < allLocation.getLocationList().size(); i++) {
                if(Integer.parseInt(allLocation.getLocationList().get(i).getHost_user_id()) != this.application.userConnected.getId()) {
                    allLocation.getLocationList().remove(i);
                }
            }
        }*/
        //nbLocation.setText(String.valueOf(allLocationEnValidation.howManyLocationLoue()));

        cardContainer.getChildren().clear();
        if (allLocation.getLocationList().isEmpty()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BASELINE_CENTER);
            hBox.getChildren().add(new Text("La recherche n'a pas aboutie"));
            cardContainer.getChildren().add(hBox);
        }
        else {
            if(searchTextField.getText().equals("")) {
                try {
                    allLocation.getLocationList().clear();
                    allLocation.loadData("locations.csv");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (this.isUserConnected()) {
                    userName.setText(application.userConnected.getUsername());
                    AllLocationLoue allLocationLoue = new AllLocationLoue();
                    allLocationLoue.loadData("location_loue.csv", application.userConnected.getId());
                    nbLocation.setText(String.valueOf(allLocationLoue.howManyLocationLoue()));
                }
            }
           if (viewMode.getValue().equals(options.get(1))) {
                for(int i=0; i < allLocation.getLocationList().size(); i++) {
                    if(!allLocation.getLocationList().get(i).getHost_user_id().equals(String.valueOf(this.application.userConnected.getId()))) {
                        allLocation.getLocationList().remove(i);
                        i--;
                    }
                }
            }
            allLocation.displayLocationList();
            displayAllLocation(allLocation);
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
    void logout(MouseEvent event) throws IOException {
        application.userConnected = null;
        userName.setText("");
        changeHeaderVisibility();
        application.fenetreControlleur.showNotification("Deconnexion","Vous êtes désormais déconnecté",2000,"success");
        application.fenetreControlleur.changerDeFenetre("Accueil.fxml");
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

    private void displayAllLocation(AllLocation allLocation) {
        ImageCache imageCache = new ImageCache();
        final int numberOfCardsPerRow = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfCardsPerRow);

        List<Location> locationList = allLocation.getLocationList();
        int totalLocations = locationList.size();
        int batchSize = numberOfCardsPerRow;

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            int currentIndex = 0;
            while (currentIndex < totalLocations) {
                List<Location> batch = locationList.subList(currentIndex, Math.min(currentIndex + batchSize, totalLocations));
                currentIndex += batchSize;

                List<CompletableFuture<AnchorPane>> cardNodeFutures = batch.stream()
                        .map(location -> CompletableFuture.supplyAsync(() -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("cardTemplate.fxml"));
                                AnchorPane cardNode = loader.load();
                                CardTemplateControlleur cardController = loader.getController();
                                cardController.setCard(location,imageCache);
                                return cardNode;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }, executor))
                        .collect(Collectors.toList());

                CompletableFuture<Void> batchFuture = CompletableFuture.allOf(cardNodeFutures.toArray(new CompletableFuture[0]))
                        .thenApplyAsync(__ -> {
                            List<AnchorPane> cardNodes = cardNodeFutures.stream()
                                    .map(CompletableFuture::join)
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());

                            Platform.runLater(() -> {
                                HBox hBox = new HBox();
                                hBox.setAlignment(Pos.BASELINE_CENTER);
                                hBox.getChildren().addAll(cardNodes);
                                cardContainer.getChildren().add(hBox);
                            });

                            return null;
                        }, Platform::runLater);

                batchFuture.join();
            }
        }, executor);

        future.whenComplete((__, throwable) -> executor.shutdown());
        try {
            future.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
