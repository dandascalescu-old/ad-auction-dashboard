package com.comp2211.dashboard.view;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.viewmodel.PrimaryViewModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class PrimaryView implements FxmlView<PrimaryViewModel> {

  @FXML
  private BorderPane mainPane;

  @FXML
  private Pane databasePane, dashboardPane;

  @FXML
  private JFXButton profileButton, dashboardButton, databaseButton, logoffButton;

  @FXML
  private JFXComboBox<Campaign> campaignCombobox;

  @FXML
  private JFXComboBox<String> averageCombobox, totalMetricCombobox;

  @FXML
  private JFXComboBox<Demographic> demographicCombobox;

  @FXML
  private LineChart<String, Number> averageChart;

  @FXML
  private LineChart<String, Number>  totalMetricsLineChart;

  @FXML
  private PieChart demographicsChart;

  @FXML
  private Text totalClickCost, totalImpresCost, totalCost, bounceRateText, totalImpressions, totalClicks, totalUniques, totalBounces, totalConversions;

  @InjectViewModel
  private PrimaryViewModel viewModel;

  @FXML
  StackPane stackPane;

  @FXML
  Pane firstPane, secondPane;

  @FXML
  Button buttonNextPane;

  @FXML
  Text ctrText, conversionUniquesText;

  public void initialize() {
    totalClickCost.textProperty().bind(viewModel.totalClickCostProperty());
    totalImpresCost.textProperty().bind(viewModel.totalImpresCostProperty());
    totalCost.textProperty().bind(viewModel.totalCostProperty());

    // TODO add bounceRateText in PrimaryView.fxml
    //bounceRateText.textProperty().bind(viewModel.bounceRateTextProperty());
    ctrText.textProperty().bind(viewModel.clickThroughRateTextProperty());
    bounceRateText.textProperty().bind(viewModel.getBounceRateText());
    conversionUniquesText.textProperty().bind(viewModel.getConversionUniquesText());

    // TODO: caused a nullpointer before.
    campaignCombobox.setItems(viewModel.campaignsList());
    campaignCombobox.valueProperty().bindBidirectional(viewModel.selectedCampaignProperty());

    averageCombobox.setItems(viewModel.averagesList());
    averageCombobox.valueProperty().bindBidirectional(viewModel.selectedAverageProperty());

    totalMetricCombobox.setItems(viewModel.totalList());
    totalMetricCombobox.valueProperty().bindBidirectional(viewModel.selectedTotalMetricProperty());

    demographicCombobox.setItems(viewModel.demographicsList());
    demographicCombobox.valueProperty().bindBidirectional(viewModel.selectedDemographicProperty());

    averageChart.setData(viewModel.averageChartData());
    averageChart.setLegendVisible(false);

    demographicsChart.setData(viewModel.demographicsChartData());
    demographicsChart.setLegendVisible(false);

    totalMetricsLineChart.setData(viewModel.totalMetricChartData());
    totalImpressions.textProperty().bind(viewModel.getTotalImpressionsText());
    totalClicks.textProperty().bind(viewModel.getTotalClicksText());
    totalUniques.textProperty().bind(viewModel.getTotalUniquesText());
    totalBounces.textProperty().bind(viewModel.getTotalBouncesText());
    totalConversions.textProperty().bind(viewModel.getTotalConversionsText());
  }


  public void campaignComboboxController() {
    System.out.println("campaignComboboxController");
  }

  public void averageComboboxController() {
    System.out.println("averageComboboxController");
  }

  public void demogComboboxController() {
    System.out.println("demogComboboxController");
  }

  public void openDashboardPane() {
    System.out.println("openDashboardPane");
  }

  public void openDatabasePane() {

  }

  public void saveDemo() {
    viewModel.saveChart(demographicsChart);
  }

}
