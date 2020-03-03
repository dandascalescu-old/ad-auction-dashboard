package com.comp2211.dashboard.viewmodel;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.view.DatabasePanel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextMatchers;
import org.testfx.service.query.NodeQuery;

public class PrimaryController implements Initializable {

    @FXML
    BorderPane mainPane;

    @FXML
    Pane databasePane, dashboardPane;

    @FXML
    JFXButton profileButton;

    @FXML
    JFXComboBox<String> averageCombobox;

    @FXML
    LineChart<String, Double> average_linechart;

    @FXML
    BarChart<String, Double> demographics_barchart;

    @FXML
    JFXComboBox<String> demogCombobox;

    @FXML
    Text averageCostTitle, totalCostTitle, demographicsTitle;

    @FXML
    Text totalClickCost, totalImpresCost, totalCost;

    String avgCostAcq = "Average Cost of Acquisition", avgCostImpr = "Average Cost of Impression", avgCostClick = "Average Cost of Click",
            percAge = "Age", percGender = "Gender", percIncome = "Income", percContext = "Context";

    public void openDatabasePane(ActionEvent event) {
        mainPane.setCenter(databasePane);

    }

    public void openDashboardPane(ActionEvent event){
        mainPane.setCenter(dashboardPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        averageCombobox.getItems().addAll(avgCostAcq, avgCostImpr, avgCostClick);

        demogCombobox.setPromptText("--- Select Metric ---");
        demogCombobox.getItems().addAll(percAge, percGender, percIncome, percContext);

        averageCombobox.setPromptText(avgCostAcq);

        populateDemogChart();
        populateAverageCost();

        setTotalCosts();

    }

    private void setTotalCosts() {

        totalClickCost.setText("40.12£");
        totalImpresCost.setText("38.12£");
        totalCost.setText("78.24£");

    }

    private void populateDemogChart() {

        XYChart.Series<String, Double> seriesAverage= new XYChart.Series<String, Double>();
        seriesAverage.setName("Cost");

        seriesAverage.getData().add(new XYChart.Data<String, Double>("Jan",0.565));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Feb", 1.242));

        seriesAverage.getData().add(new XYChart.Data<String, Double>("Mar", 1.542));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Apr", 2.242));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Jun", 2.542));

        seriesAverage.getData().add(new XYChart.Data<String, Double>("Jul", 3.042));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Aug", 3.542));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Sep", 4.042));

        demographics_barchart.getData().add(seriesAverage);


    }

    public void averageComboboxController(ActionEvent event){

        String averageComboboxValue = averageCombobox.getValue();

        if (averageComboboxValue.equals(avgCostAcq)){
            average_linechart.getData().clear();
            populateAverageCost();
        }else if (averageComboboxValue.equals(avgCostImpr)){
            average_linechart.getData().clear();
            populateAverageImpr();
        }else if (averageComboboxValue.equals(avgCostClick)){
            average_linechart.getData().clear();
            populateAverageClick();
        }

    }

    public void demogComboboxController(){

        String averageComboboxValue = demogCombobox.getValue();

        if (averageComboboxValue.equals(percAge)){
            populateDemogChart();
        }else if (averageComboboxValue.equals(percGender)){
            demographics_barchart.getData().clear();

        }else if (averageComboboxValue.equals(percIncome)){

        }else if (averageComboboxValue.equals(percContext)){

        }

    }


    public void populateAverageCost(){

        XYChart.Series<String, Double> seriesAverage= new XYChart.Series<String, Double>();
        seriesAverage.setName("Cost");

        seriesAverage.getData().add(new XYChart.Data<String, Double>("Jan",0.565));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Feb", 1.242));

        seriesAverage.getData().add(new XYChart.Data<String, Double>("Mar", 1.542));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Apr", 2.242));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Jun", 2.542));

        seriesAverage.getData().add(new XYChart.Data<String, Double>("Jul", 3.042));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Aug", 3.542));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Sep", 4.042));

        average_linechart.getData().add(seriesAverage);

    }

    private void populateAverageImpr() {

        XYChart.Series<String, Double> seriesAverage= new XYChart.Series<String, Double>();
        seriesAverage.setName("Impressions");

        seriesAverage.getData().add(new XYChart.Data<String, Double>("Jan",1.565));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Feb", 2.242));

        seriesAverage.getData().add(new XYChart.Data<String, Double>("Mar", 2.542));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Apr", 3.242));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Jun", 3.542));

        seriesAverage.getData().add(new XYChart.Data<String, Double>("Jul", 4.042));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Aug", 5.542));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Sep", 6.042));

        average_linechart.getData().add(seriesAverage);


    }

    private void populateAverageClick() {

        XYChart.Series<String, Double> seriesAverage= new XYChart.Series<String, Double>();
        seriesAverage.setName("Click");

        seriesAverage.getData().add(new XYChart.Data<String, Double>("Jan",2.565));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Feb", 3.242));

        seriesAverage.getData().add(new XYChart.Data<String, Double>("Mar", 4.542));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Apr", 5.242));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Jun", 6.542));

        seriesAverage.getData().add(new XYChart.Data<String, Double>("Jul", 6.042));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Aug", 7.542));
        seriesAverage.getData().add(new XYChart.Data<String, Double>("Sep", 8.042));

        average_linechart.getData().add(seriesAverage);

    }


    @Test
    public void should_contain_text_with_text(){
        //System.out.println(averageCostTitle.getText());
       // FxAssert.verifyThat(averageCostTitle, TextMatchers.hasText(averageCostTitle.getText()));

        //FxAssert.verifyThat( "#averageCostTitle", LabeledMatchers.hasText(""));



    }
}

