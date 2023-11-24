module ui {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;

  opens com.t2.cyed to javafx.fxml;
  exports com.t2.cyed;
}