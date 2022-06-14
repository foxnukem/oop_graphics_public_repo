module com.example.oop_graphics {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.oop_graphics to javafx.fxml;
    exports com.example.oop_graphics;
}