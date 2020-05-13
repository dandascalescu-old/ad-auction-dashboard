package com.comp2211.dashboard.view;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics;
import com.comp2211.dashboard.viewmodel.CompareLeftViewModel;
import com.comp2211.dashboard.viewmodel.CompareRightViewModel;
import com.comp2211.dashboard.viewmodel.PrimaryFilterDialogModel;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXToggleNode;
import de.saxsys.mvvmfx.*;
import de.saxsys.mvvmfx.internal.viewloader.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CompareView implements FxmlView<CompareLeftViewModel>, Initializable{

    public static final String GRAN_NOTIFICATION = "TOTALS_GRANULARITY";
    public static final String GRAN_NOTIFICATION_AVG = "AVGS_GRANULARITY";
    public static final String GRAN_NOTIFICATION_COST_TOTAL_LEFT = "COST_TOTALS_GRANULARITY_LEFT";
    public static final String GRAN_NOTIFICATION_COST_TOTAL_RIGHT = "COST_TOTALS_GRANULARITY_RIGHT";
    public static final String GRAN_NOTIFICATION_RATES = "RATES_GRANULARITY";

    @FXML
    private JFXComboBox<Campaign> campaignComboboxLeft, campaignComboboxRight;

    @FXML
    private Text ctrTextLeft, bounceRateTextLeft, convUniqTxtLeft, bounceConvTextLeft;

    @FXML
    private Text totalClickCostLeft, totalImpressCostLeft, totalCostLeft;

    @FXML
    private Text totalImpressionsLeft, totalClicksLeft, totalUniquesLeft, totalBouncesLeft, totalConversionsLeft;

    @FXML
    private Text ctrTextRight, bounceRateTextRight, convUniqTxtRight, bounceConvTextRight;

    @FXML
    private Text totalClickCostRight, totalImpressCostRight, totalCostRight;

    @FXML
    private Text totalImpressionsRight, totalClicksRight, totalUniquesRight, totalBouncesRight, totalConversionsRight;

    @FXML
    private JFXComboBox<String> averageCombobox, totalCombobox, rateCombobox;

    @FXML
    private JFXComboBox<Demographics.Demographic> demogRightCombobox, demogLeftCombobox;

    @FXML
    private HoverLineChart<String, Number> averageChart, totalCostChartRight, totalCostChartLeft, rateChart;

    @FXML
    private PieChart demogLeftChart, demogRightChart;

    @FXML
    private StackPane stackPane2;



    @InjectViewModel
    private CompareLeftViewModel viewModelLeft;


    @FXML
    private HoverLineChart<String, Number> totalChart;

    ViewTuple<PrimaryFilterDialog, PrimaryFilterDialogModel> primaryDialogViewLeft;

    ViewTuple<PrimaryFilterDialog, PrimaryFilterDialogModel> primaryDialogViewRight;

    static JFXDialog dialogFilterLeft, dialogFilterRight;

    @FXML
    JFXToggleNode totalDay, totalTwelve, totalSix,
            averageDay, averageTwelve, averageSix,
            totalCostRightSix, totalCostRightTwelve, totalCostRightDay,
            rateSix, rateTwelve, rateDay,
            totalCostLeftSix, totalCostLeftTwelve, totalCostLeftDay;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeLeftSide();
        initializeRightSide();

    }

    private void initializeRightSide() {

        //Right Side Campaign Box
        campaignComboboxRight.setItems(viewModelLeft.campaignsList());
        campaignComboboxRight.valueProperty().bindBidirectional(viewModelLeft.selectedRightCampaignPropertyRight());

        ctrTextRight.textProperty().bind(viewModelLeft.clickThroughRateTextPropertyRight());
        totalClickCostRight.textProperty().bind(viewModelLeft.totalClickCostPropertyRight());
        totalImpressCostRight.textProperty().bind(viewModelLeft.totalImpresCostPropertyRight());
        totalCostRight.textProperty().bind(viewModelLeft.totalCostPropertyRight());
        convUniqTxtRight.textProperty().bind(viewModelLeft.getConversionUniquesPropertyRight());
        totalImpressionsRight.textProperty().bind(viewModelLeft.getTotalImpressionsPropertyRight());
        totalClicksRight.textProperty().bind(viewModelLeft.getTotalClicksPropertyRight());
        totalUniquesRight.textProperty().bind(viewModelLeft.getTotalUniquesPropertyRight());
        totalConversionsRight.textProperty().bind(viewModelLeft.getTotalConversionsPropertyRight());

        totalBouncesRight.textProperty().bind(viewModelLeft.getTotalBouncesPropertyRight());
        bounceRateTextRight.textProperty().bind(viewModelLeft.bounceRateTextPropertyRight());
        bounceConvTextRight.textProperty().bind(viewModelLeft.bounceConversionTextPropertyRight());



        demogRightCombobox.setItems(viewModelLeft.demographicsList());
        demogRightCombobox.valueProperty().bindBidirectional(viewModelLeft.selectedDemographicPropertyRight());

        demogRightChart.setData(viewModelLeft.demographicsChartDataRight());
        demogRightChart.setLegendVisible(false);

        totalCostChartRight.setData(viewModelLeft.totalCostChartDataRight());
        totalCostChartRight.setLegendVisible(false);


    }

    private void initializeLeftSide() {

        //Left Side Campaign Box
        campaignComboboxLeft.setItems(viewModelLeft.campaignsList());
        campaignComboboxLeft.valueProperty().bindBidirectional(viewModelLeft.selectedCampaignProperty());

        //Texts
        ctrTextLeft.textProperty().bind(viewModelLeft.clickThroughRateTextProperty());
        bounceRateTextLeft.textProperty().bind(viewModelLeft.bounceRateTextProperty());
        convUniqTxtLeft.textProperty().bind(viewModelLeft.getConversionUniquesProperty());
        bounceConvTextLeft.textProperty().bind(viewModelLeft.bounceConversionTextProperty());
        totalClickCostLeft.textProperty().bind(viewModelLeft.totalClickCostProperty());
        totalImpressCostLeft.textProperty().bind(viewModelLeft.totalImpresCostProperty());
        totalCostLeft.textProperty().bind(viewModelLeft.totalCostProperty());
        totalImpressionsLeft.textProperty().bind(viewModelLeft.getTotalImpressionsProperty());
        totalClicksLeft.textProperty().bind(viewModelLeft.getTotalClicksProperty());
        totalUniquesLeft.textProperty().bind(viewModelLeft.getTotalUniquesProperty());
        totalBouncesLeft.textProperty().bind(viewModelLeft.getTotalBouncesProperty());
        totalConversionsLeft.textProperty().bind(viewModelLeft.getTotalConversionsProperty());

        averageCombobox.setItems(viewModelLeft.averagesList());
        averageCombobox.valueProperty().bindBidirectional(viewModelLeft.selectedAverageProperty());

        demogLeftCombobox.setItems(viewModelLeft.demographicsList());
        demogLeftCombobox.valueProperty().bindBidirectional(viewModelLeft.selectedDemographicProperty());

        demogLeftChart.setData(viewModelLeft.demographicsChartData());
        demogLeftChart.setLegendVisible(false);

        totalCostChartLeft.setData(viewModelLeft.totalCostChartDataLeft());
        totalCostChartLeft.setLegendVisible(false);

        totalCombobox.setItems(viewModelLeft.totalList());
        totalCombobox.valueProperty().bindBidirectional(viewModelLeft.selectedTotalMetricProperty());

        totalChart.setData(viewModelLeft.totalMetricChartData());
        totalChart.setLegendVisible(true);
        totalChart.getXAxis().setTickLabelRotation(-30);
        totalChart.getYAxis().setAutoRanging(true);

        averageChart.setData(viewModelLeft.averageChartData());
        averageChart.setLegendVisible(true);
        averageChart.getYAxis().setAutoRanging(true);

        rateCombobox.setItems(viewModelLeft.ratesList());
        rateCombobox.valueProperty().bindBidirectional(viewModelLeft.selectedRatePropery());

        rateChart.setData(viewModelLeft.rateChartData());
        rateChart.setLegendVisible(true);
        rateChart.getXAxis().setTickLabelRotation(-30);

    }

    public void changeAverageGran(ActionEvent event) {
        if (averageSix.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION_AVG, (byte) 6);
        } else if (averageTwelve.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION_AVG, (byte) 12);
        } else if (averageDay.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION_AVG, (byte) 24);
        }
    }

    public void changeTotalCostLeftGran(ActionEvent event) {
        if(totalCostLeftSix.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION_COST_TOTAL_LEFT, (byte) 6);
        }else if(totalCostLeftTwelve.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION_COST_TOTAL_LEFT, (byte) 12);
        }else if(totalCostLeftDay.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION_COST_TOTAL_LEFT, (byte) 24);
        }
    }

    public void changeTotalCostRightGran(ActionEvent event) {
        if(totalCostRightSix.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION_COST_TOTAL_RIGHT, (byte) 6);
        }else if(totalCostRightTwelve.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION_COST_TOTAL_RIGHT, (byte) 12);
        }else if(totalCostRightDay.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION_COST_TOTAL_RIGHT, (byte) 24);
        }
    }

    public void changeRatesGran(ActionEvent event) {
        if(rateSix.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION_RATES, (byte) 6);
        }else if(rateTwelve.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION_RATES, (byte) 12);
        }else if(rateDay.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION_RATES, (byte) 24);
        }
    }

    public void changeTotalMetricsGran(ActionEvent event) {
        if (totalSix.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION, (byte) 6);
        } else if (totalTwelve.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION, (byte) 12);
        } else if (totalDay.isSelected()){
            MvvmFX.getNotificationCenter().publish(CompareView.GRAN_NOTIFICATION, (byte) 24);
        }

    }

    public void openFilterDialogLeft(ActionEvent event) {
        if (primaryDialogViewLeft == null)
            primaryDialogViewLeft = FluentViewLoader.fxmlView(PrimaryFilterDialog.class).load();
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        primaryDialogViewLeft.getViewModel().updateLocation("leftCompare");
        dialogLayout.setBody(primaryDialogViewLeft.getView());
        dialogFilterLeft = new JFXDialog(stackPane2, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
        dialogFilterLeft.setTranslateY(-950);
        dialogFilterLeft.show();
    }

    public void openFilterDialogRight(ActionEvent event) {
        if (primaryDialogViewRight == null)
            primaryDialogViewRight = FluentViewLoader.fxmlView(PrimaryFilterDialog.class).load();
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        primaryDialogViewLeft.getViewModel().updateLocation("rightCompare");
        dialogLayout.setBody(primaryDialogViewRight.getView());
        dialogFilterRight = new JFXDialog(stackPane2, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
        dialogFilterRight.setTranslateY(-950);
        dialogFilterRight.show();
    }


}
