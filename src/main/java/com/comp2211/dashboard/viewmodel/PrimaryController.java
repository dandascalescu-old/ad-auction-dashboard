package com.comp2211.dashboard.viewmodel;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.comp2211.dashboard.Campaign;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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

  @FXML JFXComboBox<String> demogCombobox;

  @FXML Text averageCostTitle, totalCostTitle, demographicsTitle;
  @FXML Text totalClickCost, totalImpresCost, totalCost;

  Campaign campaign;

  String avgCostAcq = "Average Cost of Acquisition",
      avgCostImpr = "Average Cost of Impression",
      avgCostClick = "Average Cost of Click",
      percAge = "Age",
      percGender = "Gender",
      percIncome = "Income",
      percContext = "Context";

  public void openDatabasePane(ActionEvent event) {
    mainPane.setCenter(databasePane);
  }

  public void openDashboardPane(ActionEvent event) {
    mainPane.setCenter(dashboardPane);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    averageCombobox.getItems().addAll(avgCostAcq, avgCostImpr, avgCostClick);

    demogCombobox.getItems().addAll(percAge, percGender, percIncome, percContext);

    averageCombobox.setPromptText(avgCostAcq);
    demogCombobox.setPromptText(percAge);
  }

  public void setCampaign(Campaign campaign) {
    this.campaign = campaign;
    populateChart(campaign.getDatedAcquisitionCostAverages(), "Acquisitions", average_linechart);
    populateChart(campaign.getAgePercentage(), "Age", demographics_barchart);
    updateAll();
  }

  public void updateAll() {
    setTotalCosts();
    // populateDemogChart();

  }

  private void setTotalCosts() {

    totalClickCost.setText("£" + campaign.getTotalClickCost().toString());
    totalImpresCost.setText("£" + campaign.getTotalImpressionCost().toString());
    totalCost.setText("£" + campaign.getTotalCost().toString());
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

  private void populatePercentage() {

    XYChart.Series<String, Double> seriesAverage = new XYChart.Series<String, Double>();
    seriesAverage.setName("Age");

    seriesAverage.getData().add(new XYChart.Data<String, Double>("Jan", 0.565));
    seriesAverage.getData().add(new XYChart.Data<String, Double>("Feb", 1.242));

    seriesAverage.getData().add(new XYChart.Data<String, Double>("Mar", 1.542));
    seriesAverage.getData().add(new XYChart.Data<String, Double>("Apr", 2.242));
    seriesAverage.getData().add(new XYChart.Data<String, Double>("Jun", 2.542));

    seriesAverage.getData().add(new XYChart.Data<String, Double>("Jul", 3.042));
    seriesAverage.getData().add(new XYChart.Data<String, Double>("Aug", 3.542));
    seriesAverage.getData().add(new XYChart.Data<String, Double>("Sep", 4.042));

    demographics_barchart.getData().add(seriesAverage);
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
    for (String dateString : hm.keySet()) {
      seriesAverage
          .getData()
          .add(new XYChart.Data<String, Double>(dateString, hm.get(dateString).doubleValue()));
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
}
