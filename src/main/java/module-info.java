module App {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires jol.core;
    opens App to javafx.graphics;
    exports App;
    exports App.SortClasses;
    opens App.SortClasses to javafx.graphics;
}