package com.comp2211.dashboard.viewmodel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.comp2211.dashboard.Campaign;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.junit.Test;
// import org.testfx.api.FxAssert;
// import org.testfx.matcher.control.LabeledMatchers;
// import org.testfx.matcher.control.TextMatchers;
// import org.testfx.service.query.NodeQuery;

public class PrimaryController implements Initializable {

  @FXML BorderPane mainPane;

  @FXML Pane databasePane, dashboardPane;

  @FXML JFXButton profileButton;

  @FXML JFXComboBox<String> averageCombobox;

  @FXML LineChart<String, Double> average_linechart;

  @FXML BarChart<String, Double> demographics_barchart;

  @FXML JFXComboBox<String> demogCombobox, campaignCombobox;

  @FXML Text averageCostTitle, totalCostTitle, demographicsTitle;
  @FXML Text totalClickCost, totalImpresCost, totalCost, clickThroughRateText;


  Campaign campaign;

  String avgCostAcq = "Average Cost of Acquisition",
      avgCostImpr = "Average Cost of Impression",
      avgCostClick = "Average Cost of Click",

      percAge = "Age",
      percGender = "Gender",
      percIncome = "Income",
      percContext = "Context";

  //Linked with button to open Database Panel
  public void openDatabasePane(ActionEvent event) {
    mainPane.setCenter(databasePane);
  }

  //Linked with button to open Dashboard Panel
  public void openDashboardPane(ActionEvent event) {
    mainPane.setCenter(dashboardPane);
  }


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    averageCombobox.getItems().addAll(avgCostAcq, avgCostImpr, avgCostClick);
    demogCombobox.getItems().addAll(percAge, percGender, percIncome, percContext);
    campaignCombobox.getItems().addAll("Campaign 1", "Campaign 2", "Campaign 3", "Campaign 4");
    averageCombobox.setPromptText(avgCostAcq);
    demogCombobox.setPromptText(percAge);
    campaignCombobox.setPromptText("Campaign 1");



  }

  //Not called from fxml
  public void setCampaign(Campaign campaign) {
    this.campaign = campaign;
    populateChart(campaign.getDatedAcquisitionCostAverages(), "Acquisitions", average_linechart);
    populateChart(campaign.getAgePercentage(), "Age", demographics_barchart);
    updateAll();
  }

  //Not called from fxml

  public void updateAll() {
    setTotalCosts();
    // populateDemogChart();

  }

  //Not called from fxml
  private void setTotalCosts() {

    totalClickCost.setText("£" + campaign.getTotalClickCost().setScale(2, RoundingMode.CEILING).toString());
    totalImpresCost.setText("£" + campaign.getTotalImpressionCost().setScale(2, RoundingMode.CEILING).toString());
    totalCost.setText("£" + campaign.getTotalCost().setScale(2, RoundingMode.CEILING).toString());
    clickThroughRateText.setText(campaign.getClickThroughRate().setScale(2, RoundingMode.CEILING).toString() + "%");

  }

  public void averageComboboxController(ActionEvent event) {

    String averageComboboxValue = averageCombobox.getValue();

    if (averageComboboxValue.equals(avgCostAcq)) {
      populateChart(campaign.getDatedAcquisitionCostAverages(), "Acquisitions", average_linechart);
    } else if (averageComboboxValue.equals(avgCostImpr)) {
      populateChart(campaign.getDatedImpressionCostAverages(), "Impressions", average_linechart);

    } else if (averageComboboxValue.equals(avgCostClick)) {
      populateChart(campaign.getDatedClickCostAverages(), "Clicks", average_linechart);
    }
  }

  public void campaignComboboxController(){

  }

  public void demogComboboxController() {

    String averageComboboxValue = demogCombobox.getValue();

    if (averageComboboxValue.equals(percAge)) {
      populateChart(campaign.getAgePercentage(), "Age", demographics_barchart);
    } else if (averageComboboxValue.equals(percGender)) {
      populateChart(campaign.getGenderPercentage(), "Gender", demographics_barchart);
    } else if (averageComboboxValue.equals(percIncome)) {
      populateChart(campaign.getIncomePercentage(), "Income", demographics_barchart);
    } else if (averageComboboxValue.equals(percContext)) {
      populateChart(campaign.getContextPercentage(), "Context", demographics_barchart);
    }
  }


  /**
   * Given a hashmap of dates with averages for the dates, and a series name, generates a graph.
   *
   * @param hm HashMap containing Date (as a string) and average for the date.
   * @param name Series name for graph.
   */
  private void populateChart(
      HashMap<String, BigDecimal> hm, String name, XYChart<String, Double> chart) {

      chart.getData().clear();
      XYChart.Series<String, Double> seriesAverage = new XYChart.Series<String, Double>();

      Double previousValue = new Double(0.0);
      for (String dateString : hm.keySet()) {

        XYChart.Data<String, Double> data = new XYChart.Data<String, Double>(dateString, hm.get(dateString).doubleValue());
        data.setNode(new HoveredThresholdNode(previousValue,hm.get(dateString).doubleValue()));

        seriesAverage.getData().add(data);

        previousValue = hm.get(dateString).doubleValue();
      }

      seriesAverage.setName(name);
      chart.getData().add(seriesAverage);
  }

  @Test
  public void should_contain_text_with_text() {
    // System.out.println(averageCostTitle.getText());
    // FxAssert.verifyThat(averageCostTitle, TextMatchers.hasText(averageCostTitle.getText()));

    // FxAssert.verifyThat( "#averageCostTitle", LabeledMatchers.hasText(""));

  }



  class HoveredThresholdNode extends StackPane {
    HoveredThresholdNode(Double priorValue, Double value) {
      setPrefSize(12, 12);

      final Label label = createDataThresholdLabel(String.valueOf(priorValue), String.valueOf(value));

      setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          getChildren().setAll(label);
          setCursor(Cursor.NONE);
          toFront();
        }
      });
      setOnMouseExited(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          getChildren().clear();
          setCursor(Cursor.CROSSHAIR);
        }
      });
    }

    private Label createDataThresholdLabel(String priorValue, String value) {
      final Label label = new Label(value + "");
      label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
      label.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");


      label.setTextFill(Color.DARKGRAY);



      label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
      return label;
    }
  }
}
