package com.comp2211.dashboard.viewmodel;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class PieChartViewModel {
    private ObservableList<String> values;
    private ObservableList<PieChart.Data> pieChartData;

    //todo: Consider making a generic type for values that can return percentages as opposed to just demographics
    private ObjectProperty<Demographics.Demographic> selectorValue = new SimpleObjectProperty<>();
    private ObjectProperty<Campaign> selectedCampaign = new SimpleObjectProperty<Campaign>();

    public ObservableList<String> valuesList(){
        return values;
    }
    public ObjectProperty selectorValueProperty(){
        return selectorValue;
    }
    public ObservableList<PieChart.Data> pieChartData(){
        return pieChartData;
    }

    private void setupSelector() {
        selectorValue.addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Optional<Demographics.Demographic> matchingDemographic = Stream.of(Demographics.Demographic.values()).filter(newVal::equals).findFirst();
                matchingDemographic.ifPresent(dem -> selectorValue.setValue(dem));
            } else {
                selectorValue.setValue(Demographics.Demographic.Gender);
            }
            updatePieChartData(selectedCampaign.getValue().getPercentageMap(selectorValue.getValue()));
        });
        selectorValue.setValue(Demographics.Demographic.Gender);
    }

    private void updatePieChartData(HashMap<String, BigDecimal> dataMap) {
        pieChartData.clear();
        for (Map.Entry<String, BigDecimal> entry : dataMap.entrySet()) {
            PieChart.Data pd = new PieChart.Data(entry.getKey(), entry.getValue().doubleValue());
            pd.setName(pd.getName() + " "+ entry.getValue().setScale(1, RoundingMode.HALF_UP).doubleValue() + "%");
            pieChartData.add(pd);
        }
    }
}
