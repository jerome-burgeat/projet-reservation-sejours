module com.example.projetreservationsejours {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    opens com.example.projetreservationsejours to javafx.fxml;
    exports com.example.projetreservationsejours;

    opens com.example.projetreservationsejours.controlleur to javafx.fxml;
    exports com.example.projetreservationsejours.controlleur;
}