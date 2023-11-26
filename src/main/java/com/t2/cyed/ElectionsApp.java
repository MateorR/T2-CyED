package com.t2.cyed;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class ElectionsApp extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(ElectionsApp.class.getResource("Main-View.fxml"));
    Scene scene = new Scene((Parent) fxmlLoader.load(), 370, 420);
    stage.setTitle("A POR LA LIBERTAD!!");
    stage.getIcons().add(new Image("file:" + (ElectionsApp.class.getResource("flag.jpg")).getPath()));
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}