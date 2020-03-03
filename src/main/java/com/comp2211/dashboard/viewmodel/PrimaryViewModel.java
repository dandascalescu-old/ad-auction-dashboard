package com.comp2211.dashboard.viewmodel;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.itemlist.ItemList;
import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PrimaryViewModel implements ViewModel {

  private ObservableList<String> averages;

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
    averages.addAll(avgCostAcq, avgCostImpr, avgCostClick);

    setupAverageSelector();
  }

  public ObservableList<String> averagesList() {
    return averages;
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
        selectedAverage.set(defaultAverage); // Default
      }
    });
  }

}
