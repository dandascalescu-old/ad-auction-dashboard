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
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

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

    new Thread() {
      public void run() {
        selectedCampaign.getValue().cacheData();
        //TODO Change to load data over time: check campaign.loadData(limit, offset);
        Platform.runLater(new Runnable() {
          public void run() {
            updateTotalMetrics();
            updateTotalCosts();
            updateBounceRateDefault();
            setupDemographicSelector();
            setupAverageSelector();
            setUpTotalsSelector();
          }
        });
      }
    }.start();
  }

  public ObservableList<Campaign> campaignsList() {
    return campaigns;
  }

  public ObservableList<String> totalList() {return totals;}

  public ObservableList<String> averagesList() {
    return averages;
  }

  public ObservableList<Demographic> demographicsList() {
    return demographics;
  }

  public ObservableList<Series<String, Number>> averageChartData() {
    return averageChartData;
  }

  public ObservableList<Series<String, Number>> totalMetricChartData() {return totalMetricChartData;}

  public ObservableList<PieChart.Data> demographicsChartData() {
    return demographicsChartData;
  }

  public StringProperty selectedAverageProperty() {
    return selectedAverage;
  }

  public StringProperty selectedTotalMetricProperty() { return selectedTotals;}

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

  public StringProperty bounceRateTextProperty() {
    return bounceRateText;
  }

  public StringProperty getBounceRateText(){return bounceRateText;}

  public StringProperty getConversionUniquesText(){return totalConversionsText;}

  public StringProperty getTotalImpressionsText() { return totalImpressionsText; }

  public StringProperty getTotalClicksText() { return totalClicksText;}

  public StringProperty getTotalUniquesText() { return totalUniquesText;}

  public StringProperty getTotalBouncesText() { return totalBouncesText;}

  public StringProperty getTotalConversionsText() { return totalConversionsText;}


  private void updateTotalCosts() {
    totalClickCost.setValue("£" + selectedCampaign.getValue().getTotalClickCost().setScale(2, RoundingMode.CEILING).toPlainString());
    totalImpresCost.setValue("£" + selectedCampaign.getValue().getTotalImpressionCost().setScale(2, RoundingMode.CEILING).toPlainString());
    totalCost.setValue("£" + selectedCampaign.getValue().getTotalCost().setScale(2, RoundingMode.CEILING).toPlainString());
    clickThroughRateText.setValue(selectedCampaign.getValue().getClickThroughRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
  }

  private void updateTotalMetrics(){

    totalImpressionsText.setValue("32121");
    totalClicksText.setValue("99833");
    totalUniquesText.setValue("12002");
    totalBouncesText.setValue("11143");
    totalConversionsText.setValue("9737");
  }

  private void updateBounceRateDefault() {
    updateBounceRateByPages((byte) 1);
  }

  private void updateBounceRateByTime(long maxSeconds, boolean allowInf) {
    bounceRateText.setValue(selectedCampaign.getValue().getBounceRateByTime(maxSeconds, allowInf).setScale(2, RoundingMode.CEILING).toPlainString() + "%");
  }

  private void updateBounceRateByPages(byte maxPages) {
    bounceRateText.setValue(selectedCampaign.getValue().getBounceRateByPages(maxPages).setScale(2, RoundingMode.CEILING).toPlainString() + "%");
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
      if (selectedAverage.getValue().equals(totalImpressions)) {
        //Update Total Metrics Graph with total impressions over time
        //use method updateTotalMetricLineChartData();

      } else if (selectedAverage.getValue().equals(totalClicks)) {
        //Update Total Metrics Graph with total clicks over time
        //use method updateTotalMetricLineChartData();

      } else if (selectedAverage.getValue().equals(totalUniques)) {
        //Update Total Metrics Graph with total uniques over time
        //use method updateTotalMetricLineChartData();

      } else if (selectedAverage.getValue().equals(totalBounces)) {
        //Update Total Metrics Graph with total bounces over time
        //use method updateTotalMetricLineChartData();

      } else if (selectedAverage.getValue().equals(totalConversions)) {
        //Update Total Metrics Graph with total conversions over time
        //use method updateTotalMetricLineChartData();

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
      data.setNode(new ChartPointLabel(data.getYValue().toString()));
      s.getData().add(data);
    }
    averageChartData.add(s);
  }

  private void updateTotalMetricLineChartData (HashMap<String, BigDecimal> dataMap) {

  }


}

final class ChartPointLabel extends StackPane {
  public ChartPointLabel(String value) {
    setPrefSize(12, 12);

    final Label label = new Label(value);
    label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
    label.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

    label.setTextFill(Color.DARKGRAY);
    label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);

    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent e) {
        getChildren().setAll(label);
        label.setTranslateY(e.getY()+24);
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
