module ui {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.media;
  requires java.desktop;
  opens ui to javafx.fxml;
  exports ui;
  exports ui.Sprites;
  opens ui.Sprites to javafx.fxml;
}