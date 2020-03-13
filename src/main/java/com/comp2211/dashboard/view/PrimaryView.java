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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

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
  private Text totalClickCost, totalImpresCost, totalCost, clickThroughRateText, bounceRateText;

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
    clickThroughRateText.textProperty().bind(viewModel.clickThroughRateTextProperty());
    // TODO add bounceRateText in PrimaryView.fxml
    //bounceRateText.textProperty().bind(viewModel.bounceRateTextProperty());

    ctrText.textProperty().bind(viewModel.clickThroughRateTextProperty());
    bounceRateText.textProperty().bind(viewModel.getBounceRateText());
    conversionUniquesText.textProperty().bind(viewModel.getConversionUniquesText());

    averageCombobox.setItems(viewModel.averagesList());
    averageCombobox.valueProperty().bindBidirectional(viewModel.selectedAverageProperty());

    demographicCombobox.setItems(viewModel.demographicsList());
    demographicCombobox.valueProperty().bindBidirectional(viewModel.selectedDemographicProperty());

    // TODO: caused a nullpointer before.
    //campaignCombobox.setItems(viewModel.campaignsList());
    //campaignCombobox.valueProperty().bindBidirectional(viewModel.selectedCampaignProperty());

    averageChart.setData(viewModel.averageChartData());
    demographicsChart.setData(viewModel.demographicsChartData());

    demographicsChart.setLegendVisible(false);
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
