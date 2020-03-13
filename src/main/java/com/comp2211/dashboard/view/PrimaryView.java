package com.comp2211.dashboard.view;

import animatefx.animation.FadeIn;
import com.comp2211.dashboard.viewmodel.PrimaryViewModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

public class PrimaryView implements FxmlView<PrimaryViewModel> {



  @FXML
  private Pane databasePane, dashboardPane;

  @FXML
  private JFXButton profileButton, dashboardButton, databaseButton, logoffButton;

  @FXML
  private JFXComboBox<String> averageCombobox, demographicCombobox, campaignCombobox;

  @FXML
  private LineChart<String, Double> averageLinechart;

  @FXML
  private BarChart<String, Double> demographicsBarchart, totalMetricBarChart;

  @FXML
  private Text totalClickCost, totalImpresCost, totalCost, clickThroughRateText;

  @InjectViewModel
  private PrimaryViewModel viewModel;

  @FXML
  StackPane stackPane;

  @FXML
  Pane firstPane, secondPane;

  @FXML
  Button buttonNextPane;

  @FXML
  Text ctrText, bounceRateText, conversionUniquesText;

  public void initialize() {
    totalClickCost.textProperty().bind(viewModel.totalClickCostProperty());
    totalImpresCost.textProperty().bind(viewModel.totalImpresCostProperty());
    totalCost.textProperty().bind(viewModel.totalCostProperty());
    clickThroughRateText.textProperty().bind(viewModel.clickThroughRateTextProperty());

    ctrText.textProperty().bind(viewModel.getCtrText());
    bounceRateText.textProperty().bind(viewModel.getBounceRateText());
    conversionUniquesText.textProperty().bind(viewModel.getConversionUniquesText());

    averageCombobox.setItems(viewModel.averagesList());
    averageCombobox.valueProperty().bindBidirectional(viewModel.selectedAverageProperty());

    demographicCombobox.setItems(viewModel.demographicsList());
    demographicCombobox.valueProperty().bindBidirectional(viewModel.selectedDemographicProperty());

    //todo caused a nullpointer before.
    //campaignCombobox.setItems(viewModel.campaignsList());
    //campaignCombobox.valueProperty().bindBidirectional(viewModel.selectedCampaignProperty());

    averageLinechart.setData(viewModel.averageLinechartData());
    demographicsBarchart.setData(viewModel.demographicsBarchartData());
    totalMetricBarChart.setData(viewModel.totalMetricBarChartData());

    demographicsBarchart.setLegendVisible(false);
    averageLinechart.setLegendVisible(false);
  }

  public void firstButton() throws IOException {

    Parent root = FXMLLoader.load(getClass().getResource("SecondPane.fxml"));
    stackPane.getChildren().add(root);

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


}
