module org.example {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    exports org.example;
    opens org.example to javafx.graphics;
}