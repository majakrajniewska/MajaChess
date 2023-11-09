module com.example.majachessalpha {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.majachessalpha to javafx.fxml;
    exports com.example.majachessalpha;
}