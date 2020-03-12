package com.comp2211.dashboard.view;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.viewmodel.PrimaryViewModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
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
  private JFXComboBox<Campaign>campaignCombobox;

  @FXML
  private JFXComboBox<String> averageCombobox;

  @FXML
  private JFXComboBox<Demographic> demographicCombobox;

  @FXML
  private LineChart<String, Number> averageChart;

  @FXML
  private PieChart demographicsChart;

  @FXML
  private Text totalClickCost, totalImpresCost, totalCost, clickThroughRateText, bounceRateText;

  @InjectViewModel
  private PrimaryViewModel viewModel;

  public void initialize() {
    totalClickCost.textProperty().bind(viewModel.totalClickCostProperty());
    totalImpresCost.textProperty().bind(viewModel.totalImpresCostProperty());
    totalCost.textProperty().bind(viewModel.totalCostProperty());
    clickThroughRateText.textProperty().bind(viewModel.clickThroughRateTextProperty());
    // TODO add bounceRateText in PrimaryView.fxml
    //bounceRateText.textProperty().bind(viewModel.bounceRateTextProperty());

    campaignCombobox.setItems(viewModel.campaignsList());
    campaignCombobox.valueProperty().bindBidirectional(viewModel.selectedCampaignProperty());

    averageCombobox.setItems(viewModel.averagesList());
    averageCombobox.valueProperty().bindBidirectional(viewModel.selectedAverageProperty());

    demographicCombobox.setItems(viewModel.demographicsList());
    demographicCombobox.valueProperty().bindBidirectional(viewModel.selectedDemographicProperty()); 

    averageChart.setData(viewModel.averageChartData());
    demographicsChart.setData(viewModel.demographicsChartData());
    demographicsChart.setLegendVisible(false);
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
