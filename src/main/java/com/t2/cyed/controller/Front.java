package com.t2.cyed.controller;

import com.t2.cyed.ElectionsApp;
import com.t2.cyed.model.Implementation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class Front {
  @FXML
  private ComboBox<String> graphComboBox;

  @FXML
  private Button buttonPlay;

  @FXML
  protected void onStartButtonClick() throws IOException {
    String implementation = graphComboBox.getValue();
    if (implementation == null || implementation.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error por implementacion");
      alert.setHeaderText(null);
      alert.setContentText("Debes escoger una implementacion para el grafo!");
      alert.showAndWait();
      return;
    }
    Implementation implementationEnum = implementation.equals("Matriz de Adyacencia") ? Implementation.ADJACENCY_MATRIX : Implementation.ADJACENCY_LIST;
    Game gameController = obtainControllerWindow("Gameplay", "LIBERTAD!!!").getController();
    gameController.setImplementation(implementationEnum);
    Stage stage = (Stage) buttonPlay.getScene().getWindow();
    stage.close();
  }

  public FXMLLoader obtainControllerWindow(String fxmlName, String stageTitle) {
    Parent rootNode;
    FXMLLoader fxmlLoader = new FXMLLoader(ElectionsApp.class.getResource(fxmlName + ".fxml"));

    Stage newStage = new Stage();
    try {
      newStage.setTitle(stageTitle);
      newStage.getIcons().add(new Image("file:" + (Objects.requireNonNull(ElectionsApp.class.getResource("flag.jpg"))).getPath()));
      rootNode = fxmlLoader.load();
      newStage.setScene(new Scene(rootNode));
      newStage.show();
      return fxmlLoader;
    } catch (IOException e) {
      return null;
    }
  }

}