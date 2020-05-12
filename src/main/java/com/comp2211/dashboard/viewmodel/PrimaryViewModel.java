package com.comp2211.dashboard.viewmodel;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.model.data.Filter;
import com.comp2211.dashboard.util.Logger;
import com.comp2211.dashboard.view.PrimaryView;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class PrimaryViewModel implements ViewModel {

  // TODO: Refactor to service and inject...
  private ObservableList<String> averages;
  private ObservableList<Demographic> demographics;
  private ObservableList<Campaign> campaigns;
  private ObservableList<String> totals;
  private ObservableList<String> rates;

  private ObservableList<Series<String, Number>> averageChartData;
  private ObservableList<PieChart.Data> demographicsChartData;
  private ObservableList<Series<String, Number>> totalMetricChartData;
  private ObservableList<Series<String, Number>> rateChartData;
  private ObservableList<Series<String, Number>> totalCostChartData;

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
  private StringProperty selectedRate = new SimpleStringProperty("");
  private ObjectProperty<Demographic> selectedDemographic = new SimpleObjectProperty<>();
  private ObjectProperty<Campaign> selectedCampaign = new SimpleObjectProperty<>();

  //TODO rename so they are the same as the totals below?
  private final String avgCostClick = "Average Cost of Click";
  private final String avgCostImpr = "Average Cost of Impression";
  private final String avgCostAcq = "Average Cost of Acquisition";

  private final String totalImpressions = "Impressions";
  private final String totalClicks = "Clicks";
  private final String totalUniques = "Uniques";
  private final String totalBounces = "Bounces";
  private final String totalConversions = "Conversions";

  private final String rateBounce = "Bounce Rate";
  private final String rateCTR = "Click Through Rate";

  public void initialize() {
    campaigns = FXCollections.observableArrayList();
    averages = FXCollections.observableArrayList();
    demographics = FXCollections.observableArrayList();
    averageChartData = FXCollections.observableArrayList();
    demographicsChartData = FXCollections.observableArrayList();
    totalMetricChartData = FXCollections.observableArrayList();
    rateChartData = FXCollections.observableArrayList();
    totalCostChartData = FXCollections.observableArrayList();
    totals = FXCollections.observableArrayList();
    rates = FXCollections.observableArrayList();

    campaigns.addAll(Campaign.getCampaigns());
    //TODO reorder so same as totals?
    averages.addAll(avgCostClick, avgCostImpr, avgCostAcq);
    demographics.addAll(Demographics.Demographic.values());
    totals.addAll(totalImpressions, totalClicks, totalUniques, totalBounces, totalConversions);
    rates.addAll(rateBounce, rateCTR);

    setupCampaignSelector();

    Filter filter = (selectedCampaign.getValue().hasAppliedFilter() ? selectedCampaign.getValue().getAppliedFilter() : new Filter());

    // This was in an runnable block, which has been removed to make testing more manageable
    selectedCampaign.getValue().cacheData(filter);

    //TODO put updates into fewer methods, where possible
    updateTotalMetrics();
    updateTotalCosts();
    updateBouncesCountDefault(filter);

    setupDemographicSelector();
    setupAverageSelector();
    setUpTotalsSelector();
    setUpRatesSelector();

    setupFilterReceiving();
    setupGranReceiving();
    setupGranAvgsReceiving();
    setupGranCostTotalsReceiving();
    setupGranRatesReceiving();
    setupBounceReceiving();
    setupCampaignReceiving();
  }

  public ObservableList<Campaign> campaignsList() {
    return campaigns;
  }

  public ObservableList<String> ratesList() {return rates; }

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

  public ObservableList<Series<String, Number>> rateChartData() {
    return rateChartData;
  }

  public ObservableList<Series<String, Number>> totalCostChartData() {
    return totalCostChartData;
  }

  public StringProperty selectedAverageProperty() {
    return selectedAverage;
  }

  public StringProperty selectedRatePropery() { return selectedRate; }

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
    totalClickCost.setValue("£" + selectedCampaign.getValue().getTotalClickCost().divide(BigDecimal.valueOf(100L), RoundingMode.CEILING).setScale(2, RoundingMode.CEILING).toPlainString());
    totalImpresCost.setValue("£" + selectedCampaign.getValue().getTotalImpressionCost().divide(BigDecimal.valueOf(100L), RoundingMode.CEILING).setScale(2, RoundingMode.CEILING).toPlainString());
    totalCost.setValue("£" + selectedCampaign.getValue().getTotalCost().divide(BigDecimal.valueOf(100L), RoundingMode.CEILING).setScale(2, RoundingMode.CEILING).toPlainString());

    updateTotalCostLineChartData(selectedCampaign.getValue().getDatedCostTotals());

    clickThroughRateText.setValue(selectedCampaign.getValue().getClickThroughRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
    conversionsPerUniqueText.setValue(selectedCampaign.getValue().getConversionsPerUnique().setScale(2, RoundingMode.CEILING).toPlainString());
  }

  private void updateTotalMetrics() {
    totalImpressionsText.setValue(String.valueOf(selectedCampaign.getValue().getImpressionDataCount()));
    totalClicksText.setValue(String.valueOf(selectedCampaign.getValue().getClickDataCount()));
    totalUniquesText.setValue(String.valueOf(selectedCampaign.getValue().getUniquesCount()));
    totalConversionsText.setValue(String.valueOf(selectedCampaign.getValue().getConversionsCount()));
  }

  private void updateBouncesCountDefault(Filter filter) {
    updateBouncesCountByPages((byte) 1, filter);
  }

  private void updateBouncesCountByTime(long maxSeconds, boolean allowInf, Filter filter) {
    selectedCampaign.getValue().updateBouncesByTime(maxSeconds, allowInf, filter);
    updateBounceMetrics();
  }

  private void updateBouncesCountByPages(byte maxPages, Filter filter) {
    selectedCampaign.getValue().updateBouncesByPages(maxPages, filter);
    updateBounceMetrics();
  }

  private void updateBounceMetrics() {
    totalBouncesText.setValue(String.valueOf(selectedCampaign.getValue().getBouncesCount()));
    bounceRateText.setValue(selectedCampaign.getValue().getBounceRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
    bouncesPerConversionText.setValue(selectedCampaign.getValue().getBouncesPerConversion().setScale(2, RoundingMode.CEILING).toPlainString());
    if (selectedTotals.getValue().equals(totalBounces))
      updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedBounceTotals());
    if (selectedRate.getValue().equals(rateBounce))
      updateRates();
  }

  private void setupCampaignSelector() {
    selectedCampaign.addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        Optional<Campaign> matchingCampaign = Campaign.getCampaigns().stream().filter(newVal::equals).findFirst();
        matchingCampaign.ifPresent(cam -> selectedCampaign.setValue(cam));
        updateCampaign();
      } else {
        selectedCampaign.setValue(Campaign.getCampaigns().get(0));
      }

    });

    selectedCampaign.setValue(Campaign.getCampaigns().get(0));
  }

  private void updateCampaign(){
    //TODO move all selectors to correct positions when switching campaigns, maybe save all selectors' positions (such as time-gran/graphs) along with applied filter
    Filter filter = (selectedCampaign.getValue().hasAppliedFilter() ? selectedCampaign.getValue().getAppliedFilter() : new Filter());
    updateTotalMetrics();
    updateTotalCosts();

    updateBounceMetrics();

    updatePieChartData(selectedCampaign.getValue().getPercentageMap(Demographic.Gender));
    updateAverages();
    updateTotals();
  }

  private void setupAverageSelector() {
    selectedAverage.addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        Optional<String> matchingAverage = averages.stream().filter(newVal::equals).findFirst();
        matchingAverage.ifPresent(avg -> selectedAverage.setValue(avg));
      } else {
        selectedAverage.setValue(avgCostClick);
      }
      updateAverages();
    });

    selectedAverage.setValue(avgCostClick);
  }


  private void updateAverages() {
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
  }

  private void setUpRatesSelector() {
    selectedRate.addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        Optional<String> matchingAverage = rates.stream().filter(newVal::equals).findFirst();
        matchingAverage.ifPresent(avg -> selectedRate.setValue(avg));
      } else {
        selectedRate.setValue(rateBounce);
      }
      updateRates();
    });

    selectedRate.setValue(rateBounce);
  }

  private void updateRates(){
    switch (selectedRate.getValue()) {
      case rateBounce :
        updateRatesLineChartData(selectedCampaign.getValue().getDatedBounceRates());
        break;
      case rateCTR :
        updateRatesLineChartData(selectedCampaign.getValue().getDatedCTRs());
        break;
    }
  }

  private void setUpTotalsSelector(){
    selectedTotals.addListener((obs, oldVal, newVal) -> {
      if (newVal != null) {
        Optional<String> matchingAverage = totals.stream().filter(newVal::equals).findFirst();
        matchingAverage.ifPresent(avg -> selectedTotals.setValue(avg));
      } else {
        selectedTotals.setValue(totalImpressions);
      }
      updateTotals();
    });

    selectedTotals.setValue(totalImpressions);
  }
  private void updateTotals() {
    //TODO refactor into one method like demographics
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

  //TODO factor out repeated code, add better checking of formats, add formatting to other chart updates
  private void updateTotalMetricLineChartData(HashMap<String, Long> dataMap) {
    totalMetricChartData.clear();
    Series<String, Number> s = new Series<>();
    s.setName(selectedCampaign.getValue().toString());
    for (Entry<String, Long> entry : dataMap.entrySet()) {

      //TODO change format for each chart
      SimpleDateFormat previousFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat previousFormat2 = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat myFormat = new SimpleDateFormat("yyyy/MM/dd - HHmm");
      String reformattedStr = null;
      try {
        reformattedStr = myFormat.format(previousFormat.parse(entry.getKey()));
      } catch (ParseException e) {
        try {
          reformattedStr = myFormat.format(previousFormat2.parse(entry.getKey()));
        } catch (ParseException e2) {
          System.err.println(e2);
        }
      }

      Data<String, Number> data = new XYChart.Data<>(reformattedStr, entry.getValue().doubleValue());
      s.getData().add(data);
    }
    totalMetricChartData.add(s);
  }

  private void updateRatesLineChartData(HashMap<String, BigDecimal> dataMap) {
    rateChartData.clear();
    Series<String, Number> s = new Series<>();
    s.setName(selectedCampaign.getValue().toString());
    for (Entry<String, BigDecimal> entry : dataMap.entrySet()) {

      SimpleDateFormat previousFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat previousFormat2 = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat myFormat = new SimpleDateFormat("yyyy/MM/dd - HHmm");
      String reformattedStr = null;
      try {
        reformattedStr = myFormat.format(previousFormat.parse(entry.getKey()));
      } catch (ParseException e) {
        try {
          reformattedStr = myFormat.format(previousFormat2.parse(entry.getKey()));
        } catch (ParseException e2) {
          System.err.println(e2);
        }
      }

      Data<String, Number> data = new XYChart.Data<>(reformattedStr, entry.getValue());
      s.getData().add(data);
    }
    rateChartData.add(s);
  }

  private void updateTotalCostLineChartData(HashMap<String, BigDecimal> dataMap) {
    totalCostChartData.clear();
    Series<String, Number> s = new Series<>();
    s.setName(selectedCampaign.getValue().toString());
    for (Entry<String, BigDecimal> entry : dataMap.entrySet()) {

      SimpleDateFormat previousFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat previousFormat2 = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat myFormat = new SimpleDateFormat("yyyy/MM/dd - HHmm");
      String reformattedStr = null;
      try {
        reformattedStr = myFormat.format(previousFormat.parse(entry.getKey()));
      } catch (ParseException e) {
        try {
          reformattedStr = myFormat.format(previousFormat2.parse(entry.getKey()));
        } catch (ParseException e2) {
          System.err.println(e2);
        }
      }

      Data<String, Number> data = new XYChart.Data<>(reformattedStr, entry.getValue().divide(BigDecimal.valueOf(100L), RoundingMode.CEILING));
      s.getData().add(data);
    }
    totalCostChartData.add(s);
  }

  private void setupFilterReceiving() {
    MvvmFX.getNotificationCenter().subscribe(PrimaryFilterDialogModel.FILTER_NOTIFICATION, (key, payload) -> {
      try {
        Filter filter = (Filter) payload[0];
        filter.setCampaignID(selectedCampaign.get().getCampaignID());
        selectedCampaign.getValue().cacheData(filter);

        updateTotalMetrics();
        updateTotalCosts();
        //TODO change to apply correct bounce method
        updateBouncesCountDefault(filter);

        updatePieChartData(selectedCampaign.getValue().getPercentageMap(selectedDemographic.getValue()));
        updateTotals();
        updateAverages();

        Logger.log("[INFO] Filter applied successfully.");
      } catch (ClassCastException e) {
        e.printStackTrace();
        Logger.log("[ERROR] Invalid filter received");
      }
    });
  }

  private void setupGranReceiving() {
    MvvmFX.getNotificationCenter().subscribe(PrimaryView.GRAN_NOTIFICATION, (key, payload) -> {
      try {
        byte granularity = (byte) payload[0];
        selectedCampaign.getValue().updateTotalsGranularity(granularity);
        updateTotals();

        Logger.log("[INFO] Granularity changed successfully.");
      } catch (ClassCastException e) {
        e.printStackTrace();
        Logger.log("[ERROR] Invalid granularity received");
      }
    });
  }

  private void setupGranAvgsReceiving() {
    MvvmFX.getNotificationCenter().subscribe(PrimaryView.GRAN_NOTIFICATION_AVG, (key, payload) -> {
      try {
        byte granularity = (byte) payload[0];
        selectedCampaign.getValue().updateAvgsGranularity(granularity);
        updateAverages();

        Logger.log("[INFO] Granularity changed successfully.");
      } catch (ClassCastException e) {
        e.printStackTrace();
        Logger.log("[ERROR] Invalid granularity received");
      }
    });
  }

  private void setupGranCostTotalsReceiving() {
    MvvmFX.getNotificationCenter().subscribe(PrimaryView.GRAN_NOTIFICATION_COST_TOTAL, (key, payload) -> {
      try {
        byte granularity = (byte) payload[0];
        selectedCampaign.getValue().updateCostTotalsGranularity(granularity);
        updateTotalCostLineChartData(selectedCampaign.getValue().getDatedCostTotals());

        Logger.log("[INFO] Granularity changed successfully.");
      } catch (ClassCastException e) {
        e.printStackTrace();
        Logger.log("[ERROR] Invalid granularity received");
      }
    });
  }

  private void setupGranRatesReceiving() {
    MvvmFX.getNotificationCenter().subscribe(PrimaryView.GRAN_NOTIFICATION_RATES, (key, payload) -> {
      try {
        byte granularity = (byte) payload[0];
        selectedCampaign.getValue().updateRatesGranularity(granularity);
        updateRates();

        Logger.log("[INFO] Granularity changed successfully.");
      } catch (ClassCastException e) {
        e.printStackTrace();
        Logger.log("[ERROR] Invalid granularity received");
      }
    });
  }

  private void setupCampaignReceiving(){
    MvvmFX.getNotificationCenter().subscribe("Imported", (key, payload) -> {
      campaigns.setAll(Campaign.getCampaigns());
      DatabaseViewModel.changeProgressToCompleted((Campaign) payload[0]);
      System.out.println("campaigns set.");
      });
  }

  private void setupBounceReceiving() {
    MvvmFX.getNotificationCenter().subscribe("Bounce", (key, payload) -> {
      updateBounceMetrics();
    });
  }

}
