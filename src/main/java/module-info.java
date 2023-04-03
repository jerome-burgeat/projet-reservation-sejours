module com.example.projetreservationsejours {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.example.projetreservationsejours to javafx.fxml;
    exports com.example.projetreservationsejours;
}