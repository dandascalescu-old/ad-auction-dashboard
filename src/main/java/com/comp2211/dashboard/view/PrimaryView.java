package com.comp2211.dashboard.view;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.viewmodel.ExportDialogViewModel;
import com.comp2211.dashboard.viewmodel.PrimaryViewModel;
import com.comp2211.dashboard.viewmodel.PrimaryFilterDialogModel;
import com.jfoenix.controls.*;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class PrimaryView implements FxmlView<PrimaryViewModel> {


  final double SCALE_DELTA = 1.1;
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
  private StackPane stackPane2, totalLineChartPane;

  @FXML
  private Pane databasePane, dashboardPane;

  @FXML
  private JFXButton profileButton, dashboardButton, databaseButton, logoffButton;

  @FXML
  private JFXComboBox<Campaign> campaignCombobox;

  @FXML
  private JFXComboBox<String> averageCombobox, totalMetricCombobox, rateCombobox;

  @FXML
  private JFXComboBox<Demographic> demographicCombobox;

  @FXML
  private BarChart<String, Number> averageChart;

  @FXML
  private LineChart<String, Number> rateChart;

  @FXML
  private LineChart<String, Number> totalMetricsLineChart;

  @FXML
  private LineChart<String, Number> totalCostChart;

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

  @FXML
  JFXToggleNode totalMetricDay, totalMetricTwelve, totalMetricSix,
          averageDay, averageTwelve, averageSix,
          totalCostSix, totalCostTwelve, totalCostDay,
          ratesSix, ratesTwelve, ratesDay;


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
    averageChart.getYAxis().setAutoRanging(true);
    //averageChart.getXAxis().setTickLabelRotation(-30);

    demographicsChart.setData(viewModel.demographicsChartData());
    demographicsChart.setLegendVisible(false);

    totalMetricsLineChart.setData(viewModel.totalMetricChartData());
    totalMetricsLineChart.setLegendVisible(false);
    totalMetricsLineChart.getXAxis().setTickLabelRotation(-30);
    totalMetricsLineChart.getYAxis().setAutoRanging(true);

    totalImpressions.textProperty().bind(viewModel.getTotalImpressionsProperty());
    totalClicks.textProperty().bind(viewModel.getTotalClicksProperty());
    totalUniques.textProperty().bind(viewModel.getTotalUniquesProperty());
    totalBounces.textProperty().bind(viewModel.getTotalBouncesProperty());
    totalConversions.textProperty().bind(viewModel.getTotalConversionsProperty());

    rateCombobox.setItems(viewModel.ratesList());
    rateCombobox.valueProperty().bindBidirectional(viewModel.selectedRatePropery());

    //TODO:: uncomment when primaryviewmodel is ready to read data.
    /*rateChart.setData(viewModel.rateChartData());
    rateChart.setLegendVisible(false);
    rateChart.getXAxis().setTickLabelRotation(-30);*/

    //TODO:: uncomment when primaryviewmodel is ready to read data.
    /*totalCostChart.setData(viewModel.totalCostChartData());
    totalCostChart.setLegendVisible(false);
    totalCostChart.getXAxis().setTickLabelRotation(-30);*/

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

  public void changeTotalGran(ActionEvent event){

          if (totalMetricSix.isSelected()){


          }else if(totalMetricTwelve.isSelected()){


          }else if(totalMetricDay.isSelected()){

          }
  }

  public void changeAverageGran(ActionEvent event){
          if(averageSix.isSelected()){


          }else if(averageTwelve.isSelected()){


          }else if(averageDay.isSelected()){

          }

  }

  public void changeTotalCostGran(ActionEvent event) {

        if(totalCostSix.isSelected()){


        }else if(totalCostTwelve.isSelected()){


        }else if(totalCostDay.isSelected()){

        }
  }

  public void changeRatesGran(ActionEvent event) {

    if(ratesSix.isSelected()){


    }else if(ratesTwelve.isSelected()){


    }else if(ratesDay.isSelected()){

    }
  }


  public void openFilterDialog(ActionEvent event) {
    if (primaryDialogView == null)
      primaryDialogView = FluentViewLoader.fxmlView(PrimaryFilterDialog.class).load();
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    dialogLayout.setBody(primaryDialogView.getView());
    dialogFilter = new JFXDialog(stackPane2, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
    dialogFilter.setTranslateY(-400);
    dialogFilter.show();
  }

  public void openExportDataWindow(ActionEvent event) throws IOException {
    if (exportDialogView == null)
      exportDialogView = FluentViewLoader.fxmlView(ExportDialog.class).load();
    exportDialogView.getCodeBehind().setPrimaryView(this);
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    dialogLayout.setBody(exportDialogView.getView());
    dialogExport = new JFXDialog(stackPane2, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
    dialogExport.setTranslateY(-400);
    dialogExport.show();
  }

  static void cancelDialogAction() {
    dialogFilter.close();
  }

  static void cancelExportDialogAction(){
    dialogExport.close();
  }


}
