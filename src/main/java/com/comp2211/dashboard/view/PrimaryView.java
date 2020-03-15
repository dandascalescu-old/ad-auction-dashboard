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
import javafx.scene.control.Button;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PrimaryView implements FxmlView<PrimaryViewModel> {

  @FXML
  private Pane databasePane, dashboardPane;

  @FXML
  private JFXButton profileButton, dashboardButton, databaseButton, logoffButton;

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
  private Text totalClickCost, totalImpresCost, totalCost, clickThroughRateText, bounceRateText, ctrText, conversionUniquesText;

  @FXML
  private Button buttonNextPane;
  
  @InjectViewModel
  private PrimaryViewModel viewModel;

  public void initialize() {
    totalClickCost.textProperty().bind(viewModel.totalClickCostProperty());
    totalImpresCost.textProperty().bind(viewModel.totalImpresCostProperty());
    totalCost.textProperty().bind(viewModel.totalCostProperty());
    clickThroughRateText.textProperty().bind(viewModel.clickThroughRateTextProperty());
    bounceRateText.textProperty().bind(viewModel.bounceRateTextProperty());
    ctrText.textProperty().bind(viewModel.clickThroughRateTextProperty());
    bounceRateText.textProperty().bind(viewModel.bounceRateTextProperty());
    conversionUniquesText.textProperty().bind(viewModel.conversionUniquesTextProperty());

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
}
