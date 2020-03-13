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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

  private ObservableList<Series<String, Number>> averageChartData;
  private ObservableList<PieChart.Data> demographicsChartData;

  private StringProperty totalClickCost = new SimpleStringProperty("");
  private StringProperty totalImpresCost = new SimpleStringProperty("");
  private StringProperty totalCost = new SimpleStringProperty("");
  private StringProperty clickThroughRateText = new SimpleStringProperty("");
  private StringProperty bounceRateText = new SimpleStringProperty("");

  private StringProperty conversionUniquesText = new SimpleStringProperty("");

  private StringProperty selectedAverage = new SimpleStringProperty("");
  private ObjectProperty<Demographic> selectedDemographic = new SimpleObjectProperty<>();
  private ObjectProperty<Campaign> selectedCampaign = new SimpleObjectProperty<>();

  private final String avgCostClick = "Average Cost of Click";
  private final String avgCostImpr = "Average Cost of Impression";
  private final String avgCostAcq = "Average Cost of Acquisition";  

  public void initialize() {
    campaigns = FXCollections.observableArrayList();
    averages = FXCollections.observableArrayList();
    demographics = FXCollections.observableArrayList();
    averageChartData = FXCollections.observableArrayList();
    demographicsChartData = FXCollections.observableArrayList();

    campaigns.addAll(Campaign.getCampaigns());
    averages.addAll(avgCostClick, avgCostImpr, avgCostAcq);
    demographics.addAll(Demographics.Demographic.values());

    setupCampaignSelector();

    new Thread() {
      public void run() {
        selectedCampaign.getValue().cacheData();
        //TODO Change to load data over time: check campaign.loadData(limit, offset);
        Platform.runLater(new Runnable() {
          public void run() {
            updateTotalCosts();
            updateBounceRateDefault();
            setupDemographicSelector();
            setupAverageSelector();
          }
        });
      }
    }.start();
  }

  public ObservableList<Campaign> campaignsList() {
    return campaigns;
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

  public ObservableList<PieChart.Data> demographicsChartData() {
    return demographicsChartData;
  }

  public StringProperty selectedAverageProperty() {
    return selectedAverage;
  }

  public ObjectProperty<Demographic> selectedDemographicProperty() {
    return selectedDemographic;
  }

  public ObjectProperty<Campaign> selectedCampaignProperty() {
    return selectedCampaign;
  }

  public StringProperty getBounceRateText(){return bounceRateText;}

  public StringProperty getConversionUniquesText(){return conversionUniquesText ;}

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

  private void updateTotalCosts() {
    totalClickCost.setValue("£" + selectedCampaign.getValue().getTotalClickCost().setScale(2, RoundingMode.CEILING).toPlainString());
    totalImpresCost.setValue("£" + selectedCampaign.getValue().getTotalImpressionCost().setScale(2, RoundingMode.CEILING).toPlainString());
    totalCost.setValue("£" + selectedCampaign.getValue().getTotalCost().setScale(2, RoundingMode.CEILING).toPlainString());
    clickThroughRateText.setValue(selectedCampaign.getValue().getClickThroughRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
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
    selectedAverage.addListener(
        (obs, oldVal, newVal) -> {
          System.out.println("selectedAverage listener running");
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
          System.out.println("selectedAverage: " + selectedAverage);
        });
    selectedAverage.setValue(avgCostClick);
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
