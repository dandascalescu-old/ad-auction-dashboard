package com.comp2211.dashboard.view;

import com.comp2211.dashboard.viewmodel.PrimaryViewModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PrimaryView implements FxmlView<PrimaryViewModel> {

  @FXML
  private BorderPane mainPane;

  @FXML
  private Pane databasePane, dashboardPane;

  @FXML
  private JFXButton profileButton;

  @FXML
  private JFXComboBox<String> averageCombobox;

  @FXML
  private LineChart<String, Double> averageLinechart;

  @FXML
  private BarChart<String, Double> demographicsBarchart;

  @FXML
  private JFXComboBox<String> demogCombobox, campaignCombobox;

  @FXML
  private Text averageCostTitle, totalCostTitle, demographicsTitle;

  @FXML
  private Text totalClickCost, totalImpresCost, totalCost, clickThroughRateText;

  @InjectViewModel
  private PrimaryViewModel viewModel;

  public void initialize() {
    totalClickCost.textProperty().bind(viewModel.totalClickCostProperty());
    totalImpresCost.textProperty().bind(viewModel.totalImpresCostProperty());
    totalCost.textProperty().bind(viewModel.totalCostProperty());
    clickThroughRateText.textProperty().bind(viewModel.clickThroughRateTextProperty());

    averageCombobox.setItems(viewModel.averagesList());
    averageCombobox.valueProperty().bindBidirectional(viewModel.selectedAverageProperty());

    averageLinechart.setData(viewModel.averageLinechartData());
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
    System.out.println("openDatabasePane");
  }

}
