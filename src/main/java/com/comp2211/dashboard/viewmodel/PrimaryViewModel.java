package com.comp2211.dashboard.viewmodel;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import de.saxsys.mvvmfx.ViewModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class PrimaryViewModel implements ViewModel {

  // TODO: Refactor to service and inject...
  private ObservableList<String> averages;
  private ObservableList<Demographic> demographics;
  private ObservableList<Campaign> campaigns;
  private ObservableList<String> totals;

  private ObservableList<Series<String, Number>> averageChartData;
  private ObservableList<PieChart.Data> demographicsChartData;
  private ObservableList<Series<String, Number>> totalMetricChartData;

  private StringProperty totalClickCost = new SimpleStringProperty("");
  private StringProperty totalImpresCost = new SimpleStringProperty("");
  private StringProperty totalCost = new SimpleStringProperty("");
  private StringProperty clickThroughRateText = new SimpleStringProperty("");
  private StringProperty bounceRateText = new SimpleStringProperty("");
  private StringProperty conversionUniquesText = new SimpleStringProperty("");
  private StringProperty bounceConversionText = new SimpleStringProperty("");

  private StringProperty totalImpressionsText = new SimpleStringProperty("");
  private StringProperty totalClicksText = new SimpleStringProperty("");
  private StringProperty totalUniquesText = new SimpleStringProperty("");
  private StringProperty totalBouncesText = new SimpleStringProperty("");
  private StringProperty totalConversionsText = new SimpleStringProperty("");


  private StringProperty selectedAverage = new SimpleStringProperty("");
  private StringProperty selectedTotals = new SimpleStringProperty("");
  private ObjectProperty<Demographic> selectedDemographic = new SimpleObjectProperty<Demographic>();
  private ObjectProperty<Campaign> selectedCampaign = new SimpleObjectProperty<Campaign>();

  private final String avgCostClick = "Average Cost of Click";
  private final String avgCostImpr = "Average Cost of Impression";
  private final String avgCostAcq = "Average Cost of Acquisition";

  private final String totalImpressions = "Impressions";
  private final String totalClicks = "Clicks";
  private final String totalUniques = "Uniques";
  private final String totalBounces = "Bounces";
  private final String totalConversions = "Conversions";

  public void initialize() {
    campaigns = FXCollections.observableArrayList();
    averages = FXCollections.observableArrayList();
    demographics = FXCollections.observableArrayList();
    averageChartData = FXCollections.observableArrayList();
    demographicsChartData = FXCollections.observableArrayList();
    totals = FXCollections.observableArrayList();
    totalMetricChartData = FXCollections.observableArrayList();

    campaigns.addAll(Campaign.getCampaigns());
    averages.addAll(avgCostClick, avgCostImpr, avgCostAcq);
    demographics.addAll(Demographics.Demographic.values());
    totals.addAll(totalImpressions, totalClicks, totalUniques, totalBounces, totalConversions);

    setupCampaignSelector();

    // This was in an runnable block, which has been removed to make testing more manageable
    selectedCampaign.getValue().cacheData();

    updateTotalMetrics();
    updateTotalCosts();
    updateBouncesCountDefault();
    setupDemographicSelector();
    setupAverageSelector();
    setUpTotalsSelector();
  }

  public ObservableList<Campaign> campaignsList() {
    return campaigns;
  }

  public ObservableList<String> totalList() {
    return totals;
  }

  public ObservableList<String> averagesList() {
    return averages;
  }

  public ObservableList<Demographic> demographicsList() {
    return demographics;
  }

  public ObservableList<Series<String, Number>> averageChartData() {
    return averageChartData;
  }

  public ObservableList<Series<String, Number>> totalMetricChartData() {
    return totalMetricChartData;
  }

  public ObservableList<PieChart.Data> demographicsChartData() {
    return demographicsChartData;
  }

  public StringProperty selectedAverageProperty() {
    return selectedAverage;
  }

  public StringProperty selectedTotalMetricProperty() {
    return selectedTotals;
  }

  public ObjectProperty<Demographic> selectedDemographicProperty() {
    return selectedDemographic;
  }

  public ObjectProperty<Campaign> selectedCampaignProperty() {
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

  public StringProperty bounceConversionTextProperty() { return bounceConversionText; }

  public StringProperty bounceRateTextProperty() {
    return bounceRateText;
  }

  public StringProperty getConversionUniquesProperty() {
    return conversionUniquesText;
  }

  public StringProperty getTotalImpressionsProperty() {
    return totalImpressionsText;
  }

  public StringProperty getTotalClicksProperty() {
    return totalClicksText;
  }

  public StringProperty getTotalUniquesProperty() {
    return totalUniquesText;
  }

  public StringProperty getTotalBouncesProperty() {
    return totalBouncesText;
  }

  public StringProperty getTotalConversionsProperty() {
    return totalConversionsText;
  }

  private void updateTotalCosts() {
    totalClickCost.setValue("£" + selectedCampaign.getValue().getTotalClickCost().setScale(2, RoundingMode.CEILING).toPlainString());
    totalImpresCost.setValue("£" + selectedCampaign.getValue().getTotalImpressionCost().setScale(2, RoundingMode.CEILING).toPlainString());
    totalCost.setValue("£" + selectedCampaign.getValue().getTotalCost().setScale(2, RoundingMode.CEILING).toPlainString());

    clickThroughRateText.setValue(selectedCampaign.getValue().getClickThroughRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
    
    //TODO:: Need to set value for bounceConversionText;
    bounceConversionText.setValue("0.404");
    
    conversionUniquesText.setValue(selectedCampaign.getValue().getConversionsPerUniques().setScale(2, RoundingMode.CEILING).toPlainString());
  }

  private void updateTotalMetrics() {
    totalImpressionsText.setValue(String.valueOf(selectedCampaign.getValue().getImpressionDataCount()));
    totalClicksText.setValue(String.valueOf(selectedCampaign.getValue().getClickDataCount()));
    totalUniquesText.setValue(String.valueOf(selectedCampaign.getValue().getUniquesCount()));
    totalConversionsText.setValue(String.valueOf(selectedCampaign.getValue().getConversionsCount()));
  }

  private void updateBouncesCountDefault() {
    updateBouncesCountByPages((byte) 1);
  }

  private void updateBouncesCountByTime(long maxSeconds, boolean allowInf) {
    selectedCampaign.getValue().updateBouncesByTime(maxSeconds, allowInf);
    totalBouncesText.setValue(String.valueOf(selectedCampaign.getValue().getBouncesCount()));
    bounceRateText.setValue(selectedCampaign.getValue().getBounceRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
    if (selectedTotals.getValue().equals(totalBounces))
      updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedBounceTotals());
  }

  private void updateBouncesCountByPages(byte maxPages) {
    selectedCampaign.getValue().updateBouncesByPages(maxPages);
    totalBouncesText.setValue(String.valueOf(selectedCampaign.getValue().getBouncesCount()));
    bounceRateText.setValue(selectedCampaign.getValue().getBounceRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
    if (selectedTotals.getValue().equals(totalBounces))
      updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedBounceTotals());
  }

  private void setupCampaignSelector() {
    selectedCampaign.addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        Optional<Campaign> matchingCampaign = Campaign.getCampaigns().stream().filter(newVal::equals).findFirst();
        matchingCampaign.ifPresent(cam -> selectedCampaign.setValue(cam));
      } else {
        selectedCampaign.setValue(Campaign.getCampaigns().get(0));
      }
    });
    selectedCampaign.setValue(Campaign.getCampaigns().get(0));
  }

  private void setupAverageSelector() {
    selectedAverage.addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        Optional<String> matchingAverage = averages.stream().filter(newVal::equals).findFirst();
        matchingAverage.ifPresent(avg -> selectedAverage.setValue(avg));
      } else {
        selectedAverage.setValue(avgCostClick);
      }
      if (selectedAverage.getValue().equals(avgCostClick)) {
        updateLineChartData(selectedCampaign.getValue().getDatedClickCostAverages());
      } else if (selectedAverage.getValue().equals(avgCostImpr)) {
        updateLineChartData(selectedCampaign.getValue().getDatedImpressionCostAverages());
      } else if (selectedAverage.getValue().equals(avgCostAcq)) {
        updateLineChartData(selectedCampaign.getValue().getDatedAcquisitionCostAverages());
      }
    });
    selectedAverage.setValue(avgCostClick);
  }

  private void setUpTotalsSelector(){

    selectedTotals.addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        Optional<String> matchingAverage = totals.stream().filter(newVal::equals).findFirst();
        matchingAverage.ifPresent(avg -> selectedTotals.setValue(avg));
      } else {
        selectedTotals.setValue(totalImpressions);
      }
      if (selectedTotals.getValue().equals(totalImpressions)) {
        updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedImpressionTotals());
      } else if (selectedTotals.getValue().equals(totalClicks)) {
        updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedClickTotals());
      } else if (selectedTotals.getValue().equals(totalUniques)) {
        updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedUniqueTotals());
      } else if (selectedTotals.getValue().equals(totalBounces)) {
        updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedBounceTotals());
      } else if (selectedTotals.getValue().equals(totalConversions)) {
        updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedAcquisitionTotals());
      }
    });
    selectedTotals.setValue(totalImpressions);
  }

  private void setupDemographicSelector() {
    selectedDemographic.addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        Optional<Demographic> matchingDemographic = Stream.of(Demographics.Demographic.values()).filter(newVal::equals).findFirst();
        matchingDemographic.ifPresent(dem -> selectedDemographic.setValue(dem));
      } else {
        selectedDemographic.setValue(Demographic.Gender);
      }
      updatePieChartData(selectedCampaign.getValue().getPercentageMap(selectedDemographic.getValue()));
    });
    selectedDemographic.setValue(Demographic.Gender);
  }

  private void updatePieChartData(HashMap<String, BigDecimal> dataMap) {
    demographicsChartData.clear();
    for (Entry<String, BigDecimal> entry : dataMap.entrySet()) {
      PieChart.Data pd = new PieChart.Data(entry.getKey(), entry.getValue().doubleValue());
      pd.setName(pd.getName() + " "+ entry.getValue().setScale(1, RoundingMode.HALF_UP).doubleValue() + "%");
      demographicsChartData.add(pd);
    }
  }

  private void updateLineChartData(HashMap<String, BigDecimal> dataMap) {
    averageChartData.clear();
    Series<String, Number> s = new Series<>();
    s.setName(selectedCampaign.getValue().toString());
    for (Entry<String, BigDecimal> entry : dataMap.entrySet()) {
      Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue().doubleValue());
      s.getData().add(data);
    }
    averageChartData.add(s);
  }

  private void updateTotalMetricLineChartData(HashMap<String, Long> dataMap) {
    totalMetricChartData.clear();
    Series<String, Number> s = new Series<>();
    s.setName(selectedCampaign.getValue().toString());
    for (Entry<String, Long> entry : dataMap.entrySet()) {
      Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue());
      s.getData().add(data);
    }
    totalMetricChartData.add(s);
  }

}
