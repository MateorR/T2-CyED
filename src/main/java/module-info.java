module com.example.t2cyed {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.t2cyed to javafx.fxml;
    exports com.example.t2cyed;
}