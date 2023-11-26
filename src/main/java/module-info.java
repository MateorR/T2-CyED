module ui {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;

  opens com.t2.cyed to javafx.fxml;
  opens com.t2.cyed.controller to javafx.fxml;
  opens com.t2.cyed.model to javafx.fxml;
  exports com.t2.cyed.controller;
  exports com.t2.cyed.model;
  exports com.t2.cyed;

  opens com.t2.cyed.util to javafx.fxml;
  exports com.t2.cyed.util;
}