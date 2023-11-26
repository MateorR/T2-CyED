package com.t2.cyed.controller;

import com.t2.cyed.ElectionsApp;
import com.t2.cyed.model.Votation;
import com.t2.cyed.model.Implementation;
import com.t2.cyed.model.Map;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import com.t2.cyed.util.*;


import java.net.URL;
import java.util.*;

public class Game implements Initializable {

  private Map map;

  private int convenced = 0;

  boolean helpMethod1 = false;
  boolean helpMethod2 = false;

  @FXML
  private AnchorPane mainScreen;

  private int convencedPoints = 200;

  @FXML
  private Label armyLabel;

  @FXML
  private Label difficultyLabel;

  private HashMap<Integer, RadioButton> radioButtons;

  private HashMap<Integer, RadioButton> radioButtonsConquered;

  private LinkedList<Edge<Integer, Votation>> routes;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    armyLabel.setText("CONVENCIMIENTO: " + convencedPoints);
    difficultyLabel.setText("RESISTENCIA DEL PUEBLO: ");
  }

  public void setImplementation(Implementation implementationEnum) {
    map = new Map(implementationEnum);
    mainScreen.getChildren().addAll(map.getRadioButtons().values());
    mainScreen.getChildren().addAll(map.getLines().values());
    radioButtons = map.getRadioButtons();
    routes = map.getGraph().getEdge();
    radioButtonsConquered = new HashMap<>();
  }

  public void attackAction() {
    setRadioButtonsConvenced();
    if (checkAmountRadioButtonsSelected()) {
      int[] selected = selectedAction();
      int difficulty = validateDirectionOfEdge(selected[0], searchCity(selected[1]));
      convencedPoints -= difficulty;
      armyLabel.setText("CONVENCIMIENTO: " + convencedPoints);
      validateUsedPower();
      if (convencedPoints < 0.1) {
        armyLabel.setText("CONVENCIMIENTO: " + 0);
        surrenderAction();
      }
    }
  }

  public void consultAction() {
    if (checkAmountRadioButtonsSelected()) {
      setRadioButtonsConvenced();
      for (int i = 0; i < radioButtons.size(); i++) {
        if (radioButtons.get(i).isSelected() && !radioButtons.get(i).isDisable()) {
          for (int j = 0; j < radioButtonsConquered.size(); j++) {
            if (validateDirectionOfEdge(i, searchCity(j)) == -1 && j == radioButtonsConquered.size() - 1) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Error en consulta");
              alert.setHeaderText(null);
              alert.setContentText("No tienes ningún pueblo convencido cercano a este");
              alert.showAndWait();
              break;
            } else if (validateDirectionOfEdge(i, searchCity(j)) != -1) {
              difficultyLabel.setText("ABSTINENCIA DEL PUEBLO: " + validateDirectionOfEdge(i, searchCity(j)));
              break;
            }
          }
        }
      }

    }
  }

  public void surrenderAction() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Derrotado");
    alert.setHeaderText(null);
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image("file:" + (Objects.requireNonNull(ElectionsApp.class.getResource("flag.jpg"))).getPath()));
    alert.setContentText("Malas decisiones Massa ha ganado las elecciones!!");
    alert.showAndWait();
    System.exit(0);
  }

  public void dijkstraAction() {
    helpMethod2 = true;
    ArrayList<Integer> distances = map.getGraph().dijkstra(0);
    for (int i = 0; i < distances.size(); i++) {
      radioButtons.get(i).setText(String.valueOf(distances.get(i)));
    }
    ArrayList<Integer> path = map.getGraph().shortestPath(0, 49);
    for (Integer integer : path) {
      radioButtons.get(integer).setStyle("-fx-background-color: #0b41f1;");
    }
    convencedPoints -= 30;

  }

  public void kruskalAction() {
    helpMethod1 = true;
    ArrayList<Edge<Integer, Votation>> minimumSpanningTree = map.getGraph().kruskal();
    HashMap<Integer, Line> lines = map.getLines();

    int count = 0;
    for (Edge<Integer, Votation> edge : minimumSpanningTree) {
      if (count >= 10) break;

      int startNode = edge.getStart().getKey();
      int endNode = edge.getDestination().getKey();

      Line line = lines.get(startNode + endNode);
      line.setStroke(Color.BLUE);
      line.setStrokeWidth(3);

      count++;
    }
    convencedPoints -= 10;
  }

  public int validateDirectionOfEdge(int city1, int city2) {
    int[] checkDirections = new int[2];
    checkDirections[0] = searchRoute(map.getGraph().getVertex(city1).getValue(), map.getGraph().getVertex(city2).getValue());
    checkDirections[1] = searchRoute(map.getGraph().getVertex(city2).getValue(), map.getGraph().getVertex(city1).getValue());
    if (checkDirections[0] != -1) {
      return checkDirections[0];
    } else if (checkDirections[1] != -1) {
      return checkDirections[1];
    } else {
      return -1;
    }
  }

  public int[] selectedAction() {
    int[] selected = new int[2];
    for (int i = 0; i < radioButtons.size(); i++) {
      if (radioButtons.get(i).isSelected() && !radioButtons.get(i).isDisable()) {
        for (int j = 0; j < radioButtonsConquered.size(); j++) {
          if (validateDirectionOfEdge(i, searchCity(j)) == -1 && j == radioButtonsConquered.size() - 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en ataque");
            alert.setHeaderText(null);
            alert.setContentText("No hay ruta disponible porque no tienes ningún pueblo conquistado cercano a este");
            alert.showAndWait();
          } else if (validateDirectionOfEdge(i, searchCity(j)) != -1) {
            radioButtons.get(i).setDisable(true);
            radioButtons.get(i).setStyle("-fx-background-color: BLUE;");
            selected[0] = i;
            selected[1] = j;
            return selected;
          }
        }
      }
    }
    return selected;
  }

  public int searchCity(int conquered) {
    for (int i = 0; i < radioButtons.size(); i++) {
      if (radioButtons.get(i).equals(radioButtonsConquered.get(conquered))) {
        return i;
      }
    }
    return -1;
  }

  public boolean checkAmountRadioButtonsSelected() {
    int count = 0;
    for (int i = 0; i < radioButtons.size(); i++) {
      if (radioButtons.get(i).isSelected() && !radioButtons.get(i).isDisable()) {
        count++;
      }
    }
    if (count == 0) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Ups selecciona un pueblo");
      alert.setHeaderText(null);
      alert.setContentText("Por favor selecciona un pueblo para convencer");
      alert.showAndWait();
      return false;
    } else if (count > 1) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Ups solo puedes seleccionar un pueblo");
      alert.setHeaderText(null);
      alert.setContentText("Por favor selecciona solo un pueblo para convencer");
      alert.showAndWait();
      return false;
    }
    return true;
  }

  public void setRadioButtonsConvenced() {
    for (int i = 0; i < radioButtons.size(); i++) {
      if (radioButtons.get(i).isSelected() && radioButtons.get(i).isDisable()) {
        radioButtonsConquered.put(convenced, radioButtons.get(i));
        convenced++;
      }
    }
  }

  public int searchRoute(Votation votation, Votation votation2) {

    for (Edge<Integer, Votation> route : routes) {
      if (route.getStart().getValue().equals(votation) && route.getDestination().getValue().equals(votation2)) {
        return route.getWeight();
      }
    }
    return -1;

  }

  public void validateUsedPower() {
    if (radioButtons.get(49).isDisable()) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Victoria");
      alert.setHeaderText(null);
      int villages = 0;
      for (int i = 0; i < radioButtons.size(); i++) {
        if (radioButtons.get(i).isSelected() && radioButtons.get(i).isDisable()) {
          villages++;
        }
      }
      int score = (convencedPoints * 5 + villages * 50);
      if (helpMethod2 && helpMethod1) {
        score -= 250;
      } else if (helpMethod1) {
        score -= 100;
      } else if (helpMethod2) {
        score -= 200;
      }
      alert.setContentText("FELICIDADES MILEI HAS GANADO LAS ELECCIONES!! \n Tu puntaje final ha sido de: " + score + " Puntos \n por tus " + convencedPoints + " puntos de convencimiento y " + villages + " pueblos convencidos");
      Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
      stage.getIcons().add(new Image("file:" + (Objects.requireNonNull(ElectionsApp.class.getResource("flag.jpg"))).getPath()));

      Label label = new Label();
      Image imagen = new Image("file:" + (Objects.requireNonNull(ElectionsApp.class.getResource("milei.jpg"))).getPath());
      ImageView imageView = new ImageView(imagen);
      imageView.setFitWidth(100);
      imageView.setFitHeight(100);
      label.setGraphic(imageView);
      alert.getDialogPane().setGraphic(label);

      alert.showAndWait();
      System.exit(0);
    }
  }
}
