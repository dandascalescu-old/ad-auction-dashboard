package com.comp2211.dashboard.view;

import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;

public class HoverBarChart<X,Y> extends BarChart<X,Y> {

  public HoverBarChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis) {
    this(xAxis, yAxis, FXCollections.<Series<X, Y>> observableArrayList());
  }

  public HoverBarChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis, @NamedArg("data") ObservableList<Series<X, Y>> data) {
    super(xAxis, yAxis, data);
  }

  @Override protected  void seriesAdded(Series<X,Y> series, int seriesIndex) {
    for (int j=0; j < series.getData().size(); j++) {
      Data<X,Y> item = series.getData().get(j);
      item.setNode(new ChartPointView(String.format("%.2f",Float.parseFloat(item.getYValue().toString())),getYAxis()));
    }

    super.seriesAdded(series, seriesIndex);
  }

}
