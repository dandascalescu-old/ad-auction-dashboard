package com.comp2211.dashboard.view;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ChartPointLabel extends StackPane {
  public ChartPointLabel(Double priorValue, Double value) {
    setPrefSize(12, 12);

    final Label label = createDataThresholdLabel(String.valueOf(priorValue), String.valueOf(value));

    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        getChildren().setAll(label);
        setCursor(Cursor.NONE);
        toFront();
      }
    });
    setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        getChildren().clear();
        setCursor(Cursor.CROSSHAIR);
      }
    });
  }

  private Label createDataThresholdLabel(String priorValue, String value) {
    final Label label = new Label(value + "");
    label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
    label.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

    label.setTextFill(Color.DARKGRAY);



    label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
    return label;
  }
}