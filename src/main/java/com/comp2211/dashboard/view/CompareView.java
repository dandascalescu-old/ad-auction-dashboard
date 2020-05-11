package com.comp2211.dashboard.view;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics;
import com.comp2211.dashboard.viewmodel.CompareLeftViewModel;
import com.comp2211.dashboard.viewmodel.CompareRightViewModel;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleNode;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.internal.viewloader.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CompareView implements FxmlView<CompareLeftViewModel>, Initializable{

    @FXML
    private JFXComboBox<Campaign> campaignComboboxLeft, campaignComboboxRight;

    @FXML
    private Text ctrTextLeft, bounceRateTextLeft, convUniqTxtLeft, bounceConvTextLeft;

    @FXML
    private JFXComboBox<String> averageCombobox, totalCombobox;

    @FXML
    private JFXComboBox<Demographics.Demographic> demogRightCombobox, demogLeftCombobox;

    @FXML
    private LineChart<String, Number> averageChart;

    @FXML
    private PieChart demogLeftChart, demogRightChart;


    @FXML
    private JFXToggleNode averageSix, averageTwelve, averageDay;

    @FXML
    private Text ctrTextRight, bounceConvTextRight, bounceRateTextRight, convUniqTxtRight;

    @InjectViewModel
    private CompareLeftViewModel viewModelLeft;


    @FXML
    private LineChart<String, Number> totalChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeLeftSide();

    }

    private void initializeLeftSide() {
        //
        campaignComboboxLeft.setItems(viewModelLeft.campaignsList());
        campaignComboboxLeft.valueProperty().bindBidirectional(viewModelLeft.selectedCampaignProperty());

        ctrTextLeft.textProperty().bind(viewModelLeft.clickThroughRateTextProperty());
        bounceRateTextLeft.textProperty().bind(viewModelLeft.bounceRateTextProperty());
        convUniqTxtLeft.textProperty().bind(viewModelLeft.getConversionUniquesProperty());
        bounceConvTextLeft.textProperty().bind(viewModelLeft.bounceConversionTextProperty());

        averageCombobox.setItems(viewModelLeft.averagesList());
        averageCombobox.valueProperty().bindBidirectional(viewModelLeft.selectedAverageProperty());

        averageChart.setData(viewModelLeft.averageChartData());
        averageChart.setLegendVisible(false);
        averageChart.getYAxis().setAutoRanging(true);

        demogLeftCombobox.setItems(viewModelLeft.demographicsList());
        demogLeftCombobox.valueProperty().bindBidirectional(viewModelLeft.selectedDemographicProperty());

        demogLeftChart.setData(viewModelLeft.demographicsChartData());
        demogLeftChart.setLegendVisible(false);

        totalCombobox.setItems(viewModelLeft.totalList());
        totalCombobox.valueProperty().bindBidirectional(viewModelLeft.selectedTotalMetricProperty());

        totalChart.setData(viewModelLeft.totalMetricChartData());
        totalChart.setLegendVisible(false);
        totalChart.getXAxis().setTickLabelRotation(-30);
        totalChart.getYAxis().setAutoRanging(true);
        //

        campaignComboboxRight.setItems(viewModelLeft.campaignsList());
        campaignComboboxRight.valueProperty().bindBidirectional(viewModelLeft.selectedRightCampaignProperty());

        //ctrTextRight.textProperty().bind(viewModelRight.clickThroughRateTextProperty());





    }

    public void changeAverageGran(ActionEvent event) {
    }
}
