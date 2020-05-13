package com.comp2211.dashboard.view;

import javafx.event.EventHandler;
import javafx.scene.chart.Axis;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public final class ChartPointView extends StackPane {
  public ChartPointView(String value) {
    setPrefSize(10, 10);

    final Label label = new Label(value);
    label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
    label.setStyle("-fx-font-size: 11; -fx-font-weight: bold;");

    label.setTextFill(Color.DARKGRAY);
    label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);

    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent e) {
        getChildren().setAll(label);
        System.out.println(e.getSceneY() + " : " + e.getScreenY());
        label.setTranslateY(e.getY()+12);//24
        toFront();
      }
    });
    setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent e) {
        getChildren().clear();
      }
    });
  }

  public <Y> ChartPointView(String value, Axis<Y> yAxis) {
    setPrefSize(12, 12);

    final Label label = new Label(value);
    label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
    label.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

    label.setTextFill(Color.DARKGRAY);
    label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);

    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent e) {
        getChildren().setAll(label);
        double xlocation = yAxis.sceneToLocal(e.getSceneX(), e.getSceneY()).getX();
        double ylocation = yAxis.sceneToLocal(e.getSceneX(), e.getSceneY()).getY();
        label.setTranslateY(e.getY());
        label.setTranslateX(e.getX());
        if(ylocation > 50)
          label.setTranslateY(-12);//24
        if(ylocation <= 50)
          label.setTranslateY(+12);
        if(xlocation < 50)
          label.setTranslateX(+24);
        if(xlocation > 500)
          label.setTranslateX(-24);
//        System.out.println("x:" + xlocation + "y:" + ylocation);
        toFront();
      }
    });
    setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent e) {
        getChildren().clear();
      }
    });
  }
}