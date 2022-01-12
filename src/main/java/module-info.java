module App {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    opens App to javafx.graphics;
    exports App;
}