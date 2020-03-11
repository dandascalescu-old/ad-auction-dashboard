package com.comp2211.dashboard.viewmodel;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.view.ChartPointLabel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LineChartViewModel {
    private ObservableList<String> values;
    private ObservableList<XYChart.Series<String, Number>> lineChartData;

    private StringProperty selectorValue = new SimpleStringProperty("");
    private ObjectProperty<Campaign> selectedCampaign = new SimpleObjectProperty<Campaign>();

    public ObservableList<String> valuesList(){
        return values;
    }
    public StringProperty selectorValueProperty(){
        return selectorValue;
    }
    public ObservableList<XYChart.Series<String, Number>> lineChartData(){
        return lineChartData;
    }
    private void setupSelector() {
        selectorValue.addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Optional<String> matchingAverage = values.stream().filter(newVal::equals).findFirst();
                matchingAverage.ifPresent(avg -> selectorValue.setValue(avg));
//            } else {
//                selectorValue.setValue(avgCostClick);
            }
            HashMap<String, BigDecimal> campaignChartData = selectedCampaign.getValue().getLineChartData(selectorValue.getValue());
            if(campaignChartData != null)
                updateLineChartData(campaignChartData);
        });
        if(values.size() > 0)
            selectorValue.setValue(values.get(0));
    }

    private void updateLineChartData(HashMap<String, BigDecimal> dataMap) {
        lineChartData.clear();
        XYChart.Series<String, Number> s = new XYChart.Series<>();
        s.setName(selectedCampaign.getValue().toString());
        for (Map.Entry<String, BigDecimal> entry : dataMap.entrySet()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue().doubleValue());
            data.setNode(new ChartPointLabel(data.getYValue().toString()));
            s.getData().add(data);
        }
        lineChartData.add(s);
    }
}
