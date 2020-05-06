package com.comp2211.dashboard.view;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.viewmodel.ExportDialogViewModel;
import com.comp2211.dashboard.viewmodel.PrimaryViewModel;
import com.comp2211.dashboard.viewmodel.PrimaryFilterDialogModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class PrimaryView implements FxmlView<PrimaryViewModel> {

  @FXML
  public Pane totalMetricsOverTimePane;

  @FXML
  public Pane totalMetricsPane;

  @FXML
  public Pane averageCostPane;

  @FXML
  public Pane demographicsPane;

  @FXML
  public Pane totalCostPane;

  @FXML
  public Pane conversionsUniquesPane;

  @FXML
  public Pane ctrPane;

  @FXML
  private BorderPane mainPane;

  @FXML
  private StackPane stackPane2;

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
  private HoverLineChart<String, Number> averageChart;

  @FXML
  private HoverLineChart<String, Number>  totalMetricsLineChart;

  @FXML
  private PieChart demographicsChart;

  @FXML
  private Text totalClickCost, totalImpresCost, totalCost, bounceRateText, totalImpressions, totalClicks, totalUniques, totalBounces, totalConversions, bounceConversionText;

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

  ViewTuple<ExportDialog, ExportDialogViewModel> exportDialogView;

  ViewTuple<PrimaryFilterDialog, PrimaryFilterDialogModel> primaryDialogView;

  static JFXDialog dialogFilter, dialogExport;

  public void initialize() {
    totalClickCost.textProperty().bind(viewModel.totalClickCostProperty());
    totalImpresCost.textProperty().bind(viewModel.totalImpresCostProperty());
    totalCost.textProperty().bind(viewModel.totalCostProperty());

    ctrText.textProperty().bind(viewModel.clickThroughRateTextProperty());
    bounceRateText.textProperty().bind(viewModel.bounceRateTextProperty());
    conversionUniquesText.textProperty().bind(viewModel.getConversionUniquesProperty());
    bounceConversionText.textProperty().bind(viewModel.bounceConversionTextProperty());

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

    totalImpressions.textProperty().bind(viewModel.getTotalImpressionsProperty());
    totalClicks.textProperty().bind(viewModel.getTotalClicksProperty());
    totalUniques.textProperty().bind(viewModel.getTotalUniquesProperty());
    totalBounces.textProperty().bind(viewModel.getTotalBouncesProperty());
    totalConversions.textProperty().bind(viewModel.getTotalConversionsProperty());
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


  public void openFilterDialog(ActionEvent event) {
    if (primaryDialogView == null)
      primaryDialogView = FluentViewLoader.fxmlView(PrimaryFilterDialog.class).load();
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    dialogLayout.setBody(primaryDialogView.getView());
    dialogFilter = new JFXDialog(stackPane2, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
    dialogFilter.setTranslateY(-200);
    dialogFilter.show();
  }

  public void openExportDataWindow(ActionEvent event) throws IOException {
    if (exportDialogView == null)
      exportDialogView = FluentViewLoader.fxmlView(ExportDialog.class).load();
    exportDialogView.getCodeBehind().setPrimaryView(this);
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    dialogLayout.setBody(exportDialogView.getView());
    dialogExport = new JFXDialog(stackPane2, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
    dialogExport.setTranslateY(-200);
    dialogExport.show();
  }

  static void cancelDialogAction() {
    dialogFilter.close();
  }

  static void cancelExportDialogAction(){
    dialogExport.close();
  }
}
