package com.t2.cyed.model;

import com.t2.cyed.ElectionsApp;
import com.t2.cyed.controller.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import util.GraphAdjacentMatrix;
import javafx.scene.shape.Line;
import util.GraphAdjacentList;
import javafx.scene.Cursor;
import util.GraphType;
import util.Graphable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Map {

  private final Graphable<Integer, Votation> map;
  private final HashMap<Integer, RadioButton> radioButtons;
  private final HashMap<Integer, Line> lines;

  public Map(Implementation implementationEnum) {
    if (implementationEnum == Implementation.ADJACENCY_LIST) {
      map = new GraphAdjacentList<>(GraphType.DIRECTED);
    } else {
      map = new GraphAdjacentMatrix<>(50, GraphType.DIRECTED);
    }
    radioButtons = new HashMap<>();
    lines = new HashMap<>();
    addCities();
    addRoutes();
  }

  public void addCities() {

    int[] positionsLevel0 = new int[]{290, 264, 315, 290, 323, 343, 323, 282, 554, 517, 508, 478, 478, 464, 447, 447};
    int[] positionsLevel1 = new int[]{290, 331, 307, 290, 323, 365, 357, 335, 409, 413, 392, 366, 366, 366, 392, 384};
    int[] positionsLevel2 = new int[]{282, 315, 315, 384, 357, 339, 343, 298, 342, 313, 342, 313, 342, 296, 322, 288};
    int[] positionsLevel3 = new int[]{315, 351, 392, 357, 417, 384, 431, 463, 210, 263, 280, 201, 225, 233, 276, 233};
    int[] positionsLevel4 = new int[]{306, 323, 347, 376, 392, 400, 431, 455, 147, 112, 129, 156, 193, 139, 165, 174};
    int[] positionsLevel5 = new int[]{335, 365, 359, 399, 392, 439, 479, 392, 88, 29, 80, 79, 104, 121, 113, 38};

    Votation milei = new Votation((335), (630), 0);
    map.addVertex(milei.getId(), milei);
    RadioButton radioButton1 = new RadioButton();
    radioButton1.setStyle("-fx-background-color: BLUE;");
    radioButton1.setLayoutX(milei.getX());
    radioButton1.setLayoutY(milei.getY());
    radioButton1.setCursor(Cursor.HAND);
    radioButton1.setSelected(true);
    radioButton1.setDisable(true);
    radioButtons.put(milei.getId(), radioButton1);

    Votation massa = new Votation(470, 100, 49);
    map.addVertex(massa.getId(), massa);
    RadioButton radioButton2 = new RadioButton();
    radioButton2.setStyle("-fx-background-color: RED;");
    radioButton2.setLayoutX(massa.getX());
    radioButton2.setLayoutY(massa.getY());
    radioButton2.setCursor(Cursor.HAND);
    radioButtons.put(massa.getId(), radioButton2);

    int nodeCount = 1;
    nodeCount = generateCities(positionsLevel0, nodeCount);
    nodeCount = generateCities(positionsLevel1, nodeCount);
    nodeCount = generateCities(positionsLevel2, nodeCount);
    nodeCount = generateCities(positionsLevel3, nodeCount);
    nodeCount = generateCities(positionsLevel4, nodeCount);
    generateCities(positionsLevel5, nodeCount);

  }

  private int generateCities(int[] positionLeve, int nodeCount) {
    for (int i = 1; i < 9; i++) {
      Votation votation = new Votation(positionLeve[i - 1], positionLeve[i + 7], nodeCount);
      map.addVertex(votation.getId(), votation);
      RadioButton radioButton3 = new RadioButton();
      radioButton3.setLayoutX(votation.getX());
      radioButton3.setLayoutY(votation.getY());
      radioButton3.setCursor(Cursor.HAND);
      radioButtons.put(votation.getId(), radioButton3);
      nodeCount++;
    }
    return nodeCount;
  }

  public void addRoutes() {
    Random random = new Random();

    Route[] routes = new Route[57]; //57

    routes[0] = new Route(6, 0, random.nextInt(20) + 1);
    routes[1] = new Route(0, 7, random.nextInt(20) + 1);
    routes[2] = new Route(0, 2, random.nextInt(20) + 1);
    routes[3] = new Route(49, 45, random.nextInt(20) + 1);
    routes[4] = new Route(49, 46, random.nextInt(20) + 1);
    routes[5] = new Route(49, 48, random.nextInt(20) + 1);
    routes[6] = new Route(2, 1, random.nextInt(20) + 1);
    routes[7] = new Route(2, 3, random.nextInt(20) + 1);
    routes[8] = new Route(5, 4, random.nextInt(20) + 1);
    routes[9] = new Route(5, 6, random.nextInt(20) + 1);
    routes[10] = new Route(46, 47, random.nextInt(20) + 1);

    //segunda parte 9 - 15
    routes[11] = new Route(8, 16, random.nextInt(20) + 1);
    routes[12] = new Route(1, 9, random.nextInt(20) + 1);
    routes[13] = new Route(3, 10, random.nextInt(20) + 1);
    routes[14] = new Route(4, 11, random.nextInt(20) + 1);
    routes[15] = new Route(6, 13, random.nextInt(20) + 1);
    routes[16] = new Route(7, 14, random.nextInt(20) + 1);
    routes[17] = new Route(7, 15, random.nextInt(20) + 1);
    routes[18] = new Route(7, 16, random.nextInt(20) + 1);
    routes[19] = new Route(13, 12, random.nextInt(20) + 1);

    //tercera

    routes[20] = new Route(10, 17, random.nextInt(20) + 1);
    routes[21] = new Route(11, 18, random.nextInt(20) + 1);
    routes[22] = new Route(12, 19, random.nextInt(20) + 1);
    routes[23] = new Route(12, 20, random.nextInt(20) + 1);
    routes[24] = new Route(14, 21, random.nextInt(20) + 1);
    routes[25] = new Route(15, 22, random.nextInt(20) + 1);
    routes[26] = new Route(16, 23, random.nextInt(20) + 1);
    routes[27] = new Route(16, 24, random.nextInt(20) + 1);


    //cuarta

    routes[28] = new Route(17, 25, random.nextInt(20) + 1);
    routes[29] = new Route(18, 26, random.nextInt(20) + 1);
    routes[30] = new Route(19, 27, random.nextInt(20) + 1);
    routes[31] = new Route(20, 28, random.nextInt(20) + 1);
    routes[32] = new Route(21, 29, random.nextInt(20) + 1);
    routes[33] = new Route(22, 30, random.nextInt(20) + 1);
    routes[34] = new Route(23, 31, random.nextInt(20) + 1);
    routes[35] = new Route(24, 32, random.nextInt(20) + 1);
    routes[36] = new Route(24, 33, random.nextInt(20) + 1);

    //quinta

    routes[37] = new Route(25, 34, random.nextInt(20) + 1);
    routes[38] = new Route(26, 35, random.nextInt(20) + 1);
    routes[39] = new Route(27, 36, random.nextInt(20) + 1);
    routes[40] = new Route(28, 37, random.nextInt(20) + 1);
    routes[41] = new Route(29, 38, random.nextInt(20) + 1);
    routes[42] = new Route(30, 39, random.nextInt(20) + 1);
    routes[43] = new Route(31, 40, random.nextInt(20) + 1);
    routes[44] = new Route(32, 41, random.nextInt(20) + 1);
    routes[45] = new Route(33, 41, random.nextInt(20) + 1);


    //sexta

    routes[46] = new Route(34, 43, random.nextInt(20) + 1);
    routes[47] = new Route(35, 43, random.nextInt(20) + 1);
    routes[48] = new Route(36, 43, random.nextInt(20) + 1);
    routes[49] = new Route(37, 45, random.nextInt(20) + 1);
    routes[50] = new Route(38, 45, random.nextInt(20) + 1);
    routes[51] = new Route(47, 39, random.nextInt(20) + 1);

    //union sexta con primera

    routes[52] = new Route(40, 48, random.nextInt(20) + 1);
    routes[53] = new Route(41, 40, random.nextInt(20) + 1);
    routes[54] = new Route(44, 45, random.nextInt(20) + 1);
    routes[55] = new Route(43, 42, random.nextInt(20) + 1);
    routes[56] = new Route(43, 44, random.nextInt(20) + 1);


    for (Route route : routes) {
      map.addEdge(route.getCityA(), route.getCityB(), route.getDifficulty());

      RadioButton city1 = radioButtons.get(route.getCityA());
      RadioButton city2 = radioButtons.get(route.getCityB());

      Line line = new Line(city1.getLayoutX() + 8, city1.getLayoutY() + 8, city2.getLayoutX() + 8, city2.getLayoutY() + 8);

      line.setCursor(Cursor.HAND);
      String tooltipText = String.valueOf(route.getDifficulty());
      Tooltip tooltip = new Tooltip(tooltipText);
      Tooltip.install(line, tooltip);
      line.setStroke(Color.BLACK);

      line.setStrokeWidth(1.5);
      lines.put(route.getCityA() + route.getCityB(), line);
    }

  }

  public Graphable<Integer, Votation> getGraph() {
    return map;
  }

  public HashMap<Integer, RadioButton> getRadioButtons() {
    return radioButtons;
  }

  public HashMap<Integer, Line> getLines() {
    return lines;
  }


}
