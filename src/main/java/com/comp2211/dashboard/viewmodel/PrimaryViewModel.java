package com.comp2211.dashboard.viewmodel;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.io.DatabaseManager;
import de.saxsys.mvvmfx.ViewModel;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class PrimaryViewModel implements ViewModel {

  // TODO: Refactor to service and inject...
  private Campaign campaign;

  private ObservableList<String> averages;
  private ObservableList<Series<String, Double>> averageLinechartData;

  private StringProperty totalClickCost = new SimpleStringProperty("500.0");
  private StringProperty totalImpresCost = new SimpleStringProperty("500.0");
  private StringProperty totalCost = new SimpleStringProperty("500.0");
  private StringProperty clickThroughRateText = new SimpleStringProperty("500.0");

  private StringProperty selectedAverage = new SimpleStringProperty();

  private final String avgCostAcq = "Average Cost of Acquisition";
  private final String avgCostImpr = "Average Cost of Impression";
  private final String avgCostClick = "Average Cost of Click";

  public void initialize() {
    averages = FXCollections.observableArrayList();
    averageLinechartData = FXCollections.observableArrayList(); // http://www.java2s.com/Code/Java/JavaFX/LineChartfromObservableListXYChartSeriesStringDouble.htm

    averages.addAll(avgCostAcq, avgCostImpr, avgCostClick);

    setupAverageSelector();

    DatabaseManager.init();
    Campaign campaign = new Campaign("1");
    campaign.cacheData(0);
    System.out.println(campaign.getTotalCost());

    HashMap<String, BigDecimal> hm = campaign.getDatedAcquisitionCostAverages();

      XYChart.Series<String, Double> seriesAverage = new XYChart.Series<>();

      for (String dateString : hm.keySet()) {

        XYChart.Data<String, Double> data = new XYChart.Data<>(dateString, hm.get(dateString).doubleValue());

        seriesAverage.getData().add(data);
      }

      seriesAverage.setName("Acquisitions");

      averageLinechartData.add(seriesAverage);
  }

  public ObservableList<String> averagesList() {
    return averages;
  }

  public ObservableList averageLinechartData() {
    return averageLinechartData;
  }

  public StringProperty selectedAverageProperty() {
    return selectedAverage;
  }

  public StringProperty totalClickCostProperty() {
    return totalClickCost;
  }

  public StringProperty totalImpresCostProperty() {
    return totalImpresCost;
  }

  public StringProperty totalCostProperty() {
    return totalCost;
  }

  public StringProperty clickThroughRateTextProperty() {
    return clickThroughRateText;
  }

  private void setupAverageSelector() {
    String defaultAverage = avgCostAcq;
    selectedAverage.setValue(defaultAverage);

    selectedAverage.addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        Optional<String> matchingAverage = averages.stream()
            .filter(avg -> newVal.equals(avg))
            .findFirst();

        matchingAverage.ifPresent(a -> selectedAverage.set(a));

      } else {
        selectedAverage.set(defaultAverage);
      }
    });
  }

}
