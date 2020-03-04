package com.comp2211.dashboard.viewmodel;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.io.DatabaseManager;
import com.comp2211.dashboard.view.ChartPointLabel;
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

  // TODO: Allow multiple campaigns
  private final String campaign1 = "Campaign 1";

  private ObservableList<String> averages;
  private ObservableList<String> demographics;
  private ObservableList<String> campaigns;

  private ObservableList<Series<String, Double>> averageLinechartData;
  private ObservableList<Series<String, Double>> demographicsBarchartData;

  private StringProperty totalClickCost = new SimpleStringProperty("500.0");
  private StringProperty totalImpresCost = new SimpleStringProperty("500.0");
  private StringProperty totalCost = new SimpleStringProperty("500.0");
  private StringProperty clickThroughRateText = new SimpleStringProperty("500.0");

  private StringProperty selectedAverage = new SimpleStringProperty();
  private StringProperty selectedDemographic = new SimpleStringProperty();
  private StringProperty selectedCampaign = new SimpleStringProperty();

  private final String avgCostAcq = "Average Cost of Acquisition";
  private final String avgCostImpr = "Average Cost of Impression";
  private final String avgCostClick = "Average Cost of Click";

  private final String demAge = "Age";
  private final String demGender = "Gender";
  private final String demIncome = "Income";
  private final String demContext = "Context";

  public void initialize() {
    averages = FXCollections.observableArrayList();
    demographics = FXCollections.observableArrayList();
    campaigns = FXCollections.observableArrayList();

    averages.addAll(avgCostAcq, avgCostImpr, avgCostClick);
    demographics.addAll(demAge, demGender, demIncome, demContext);
    campaigns.add(campaign1);

    averageLinechartData = FXCollections.observableArrayList(); // http://www.java2s.com/Code/Java/JavaFX/LineChartfromObservableListXYChartSeriesStringDouble.htm
    demographicsBarchartData = FXCollections.observableArrayList();

    setupAverageSelector();
    setupDemographicSelector();
    setupCampaignSelector();

    DatabaseManager.init();
    campaign = new Campaign("1");
    campaign.cacheData(0);

    populateChart(campaign.getDatedAcquisitionCostAverages(), "Acquisition", averageLinechartData);
    populateChart(campaign.getAgePercentage(), "Age", demographicsBarchartData);
  }

  public ObservableList<String> averagesList() {
    return averages;
  }

  public ObservableList<String> demographicsList() {
    return demographics;
  }

  public ObservableList<String> campaignsList() {
    return campaigns;
  }

  public ObservableList averageLinechartData() {
    return averageLinechartData;
  }

  public ObservableList demographicsBarchartData() {
    return demographicsBarchartData;
  }

  public StringProperty selectedAverageProperty() {
    return selectedAverage;
  }

  public StringProperty selectedDemographicProperty() {
    return selectedDemographic;
  }

  public StringProperty selectedCampaignProperty() {
    return selectedCampaign;
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
//    String defaultAverage = averages.get(0);
    String defaultAverage = null;
    selectedAverage.setValue(defaultAverage);

    selectedAverage.addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        Optional<String> matchingAverage = averages.stream()
            .filter(newVal::equals)
            .findFirst();

        matchingAverage.ifPresent(avg -> selectedAverage.setValue(avg));

      } else {
        selectedAverage.setValue(defaultAverage);
      }

      if (selectedAverage.getValue().equals(avgCostAcq)) {
        populateChart(campaign.getDatedAcquisitionCostAverages(), "Acquisitions", averageLinechartData);
      } else if (selectedAverage.getValue().equals(avgCostImpr)) {
        populateChart(campaign.getDatedImpressionCostAverages(), "Impressions", averageLinechartData);
      } else if (selectedAverage.getValue().equals(avgCostClick)) {
        populateChart(campaign.getDatedClickCostAverages(), "Clicks", averageLinechartData);
      }
    });
  }

  private void setupDemographicSelector() {
//    String defaultDemographic = demographics.get(0);
    String defaultDemographic = null;
    selectedDemographic.setValue(defaultDemographic);

    selectedDemographic.addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        Optional<String> matchingDemographic = demographics.stream()
            .filter(newVal::equals)
            .findFirst();

        matchingDemographic.ifPresent(dem -> selectedDemographic.setValue(dem));

      } else {
        selectedDemographic.setValue(defaultDemographic);
      }

      if (selectedDemographic.getValue().equals(demAge)) {
        populateChart(campaign.getAgePercentage(), "Age", demographicsBarchartData);
      } else if (selectedDemographic.getValue().equals(demGender)) {
        populateChart(campaign.getGenderPercentage(), "Gender", demographicsBarchartData);
      } else if (selectedDemographic.getValue().equals(demIncome)) {
        populateChart(campaign.getIncomePercentage(), "Income", demographicsBarchartData);
      } else if (selectedDemographic.getValue().equals(demContext)) {
        populateChart(campaign.getContextPercentage(), "Context", demographicsBarchartData);
      }
    });
  }

  private void setupCampaignSelector() {
//    String defaultCampaign = campaigns.get(0);
    String defaultCampaign = null;
    selectedCampaign.setValue(defaultCampaign);
  }

  /**
   * Given a HashMap of dates with averages for the dates, and a series name, generates data for a graph.
   *
   * @param hm HashMap containing Date (as a string) and average for the date.
   * @param name Series name for graph.
   */
  private void populateChart(HashMap<String, BigDecimal> hm, String name, ObservableList<Series<String, Double>> chartData) {
    XYChart.Series<String, Double> seriesAverage = new XYChart.Series<>();

    Double previousValue = new Double(0.0);
    for (String dateString : hm.keySet()) {
      XYChart.Data<String, Double> data = new XYChart.Data<>(dateString, hm.get(dateString).doubleValue());

      // TODO: Refactor, this is not MVVM (view code is in viewmodel)
      data.setNode(new ChartPointLabel(previousValue, hm.get(dateString).doubleValue()));

      seriesAverage.getData().add(data);
    }

    seriesAverage.setName(name);

    chartData.clear();
    chartData.add(seriesAverage);
  }

}
