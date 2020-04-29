package com.comp2211.dashboard.viewmodel;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.util.Logger;
import com.comp2211.dashboard.viewmodel.PrimaryFilterDialogModel.Filter;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
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
  private StringProperty conversionsPerUniqueText = new SimpleStringProperty("");
  private StringProperty bouncesPerConversionText = new SimpleStringProperty("");

  private StringProperty totalImpressionsText = new SimpleStringProperty("");
  private StringProperty totalClicksText = new SimpleStringProperty("");
  private StringProperty totalUniquesText = new SimpleStringProperty("");
  private StringProperty totalBouncesText = new SimpleStringProperty("");
  private StringProperty totalConversionsText = new SimpleStringProperty("");

  private StringProperty selectedAverage = new SimpleStringProperty("");
  private StringProperty selectedTotals = new SimpleStringProperty("");
  private ObjectProperty<Demographic> selectedDemographic = new SimpleObjectProperty<>();
  private ObjectProperty<Campaign> selectedCampaign = new SimpleObjectProperty<>();

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
    setupFilterReceiving();
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

  public StringProperty bounceConversionTextProperty() { return bouncesPerConversionText; }

  public StringProperty bounceRateTextProperty() {
    return bounceRateText;
  }

  public StringProperty getConversionUniquesProperty() {
    return conversionsPerUniqueText;
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
    updateTotalCosts(new Filter());
  }
  private void updateTotalCosts(Filter filter) {
    totalClickCost.setValue("£" + selectedCampaign.getValue().getTotalClickCost(filter).setScale(2, RoundingMode.CEILING).toPlainString());
    totalImpresCost.setValue("£" + selectedCampaign.getValue().getTotalImpressionCost().setScale(2, RoundingMode.CEILING).toPlainString());
    totalCost.setValue("£" + selectedCampaign.getValue().getTotalCost().setScale(2, RoundingMode.CEILING).toPlainString());

    clickThroughRateText.setValue(selectedCampaign.getValue().getClickThroughRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
    conversionsPerUniqueText.setValue(selectedCampaign.getValue().getConversionsPerUnique().setScale(2, RoundingMode.CEILING).toPlainString());
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
    updateBounceMetrics();
  }

  private void updateBouncesCountByPages(byte maxPages) {
    selectedCampaign.getValue().updateBouncesByPages(maxPages);
    updateBounceMetrics();
  }

  private void updateBounceMetrics() {
    totalBouncesText.setValue(String.valueOf(selectedCampaign.getValue().getBouncesCount()));
    bounceRateText.setValue(selectedCampaign.getValue().getBounceRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
    bouncesPerConversionText.setValue(selectedCampaign.getValue().getBouncesPerConversion().setScale(2, RoundingMode.CEILING).toPlainString());
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
      switch (selectedAverage.getValue()) {
        case avgCostClick:
          updateLineChartData(selectedCampaign.getValue().getDatedClickCostAverages());
          break;
        case avgCostImpr:
          updateLineChartData(selectedCampaign.getValue().getDatedImpressionCostAverages());
          break;
        case avgCostAcq:
          updateLineChartData(selectedCampaign.getValue().getDatedAcquisitionCostAverages());
          break;
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
      switch (selectedTotals.getValue()) {
        case totalImpressions:
          updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedImpressionTotals());
          break;
        case totalClicks:
          updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedClickTotals());
          break;
        case totalUniques:
          updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedUniqueTotals());
          break;
        case totalBounces:
          updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedBounceTotals());
          break;
        case totalConversions:
          updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedAcquisitionTotals());
          break;
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

  private void setupFilterReceiving() {
    NotificationCenter notificationCenter = MvvmFX.getNotificationCenter();
    notificationCenter.subscribe(PrimaryFilterDialogModel.FILTER_NOTIFICATION, (key, payload) -> {
      try {
        /*LocalDate startDate = (LocalDate) payload[0];
        LocalDate endDate = (LocalDate) payload[1];
        String genderString = (String) payload[2];
        String ageString = (String) payload[3];
        String incomeString = (String) payload[4];
        String contextString = (String) payload[5];*/
        Filter filter = (Filter) payload[0];

        //

        updateTotalMetrics();
        updateTotalCosts(filter);
        //TODO change to apply correct bounce method
        updateBouncesCountDefault();
        //TODO add graph updates
      } catch (ClassCastException e) {
        e.printStackTrace();
        Logger.log("Invalid filter received");
      }
    });
  }

}
