package com.comp2211.dashboard.viewmodel;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.model.data.Filter;
import com.comp2211.dashboard.util.Logger;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class CompareLeftViewModel implements ViewModel {

    // TODO: Refactor to service and inject...
    private ObservableList<String> averages;
    private ObservableList<Demographic> demographics;
    private ObservableList<Campaign> campaigns;
    private ObservableList<String> totals;

    private ObservableList<Series<String, Number>> averageChartData;
    private ObservableList<PieChart.Data> demographicsChartData;
    private ObservableList<Series<String, Number>> totalMetricChartData;

    private StringProperty totalClickCost = new SimpleStringProperty("");
    private StringProperty totalImpresCost = new SimpleStringProperty("");
    private StringProperty totalCost = new SimpleStringProperty("");
    private StringProperty clickThroughRateText = new SimpleStringProperty("");
    private StringProperty bounceRateText = new SimpleStringProperty("");
    private StringProperty conversionsPerUniqueText = new SimpleStringProperty("");
    private StringProperty bouncesPerConversionText = new SimpleStringProperty("");

    private StringProperty totalImpressionsText = new SimpleStringProperty("");
    private StringProperty totalClicksText = new SimpleStringProperty("");
    private StringProperty totalUniquesText = new SimpleStringProperty("");
    private StringProperty totalBouncesText = new SimpleStringProperty("");
    private StringProperty totalConversionsText = new SimpleStringProperty("");

    private StringProperty selectedAverage = new SimpleStringProperty("");
    private StringProperty selectedTotals = new SimpleStringProperty("");
    private ObjectProperty<Demographic> selectedDemographic = new SimpleObjectProperty<>();
    private ObjectProperty<Campaign> selectedCampaign = new SimpleObjectProperty<>();

    private final String avgCostClick = "Average Cost of Click";
    private final String avgCostImpr = "Average Cost of Impression";
    private final String avgCostAcq = "Average Cost of Acquisition";

    private final String totalImpressions = "Impressions";
    private final String totalClicks = "Clicks";
    private final String totalUniques = "Uniques";
    private final String totalBounces = "Bounces";
    private final String totalConversions = "Conversions";

    public void initialize() {
        campaigns = FXCollections.observableArrayList();
        averages = FXCollections.observableArrayList();
        demographics = FXCollections.observableArrayList();
        averageChartData = FXCollections.observableArrayList();
        demographicsChartData = FXCollections.observableArrayList();
        totals = FXCollections.observableArrayList();
        totalMetricChartData = FXCollections.observableArrayList();

        campaigns.addAll(Campaign.getCampaigns());
        averages.addAll(avgCostClick, avgCostImpr, avgCostAcq);
        demographics.addAll(Demographics.Demographic.values());
        totals.addAll(totalImpressions, totalClicks, totalUniques, totalBounces, totalConversions);

        setupCampaignSelector();

        Filter filter = (selectedCampaign.getValue().hasAppliedFilter() ? selectedCampaign.getValue().getAppliedFilter() : new Filter());

        // This was in an runnable block, which has been removed to make testing more manageable
        selectedCampaign.getValue().cacheData(filter);

        updateTotalMetrics();
        updateTotalCosts();
        updateBouncesCountDefault(filter);

        setupDemographicSelector();
        setupAverageSelector();
        setUpTotalsSelector();

        setupFilterReceiving();
    }

    public ObservableList<Campaign> campaignsList() {
        return campaigns;
    }

    public ObservableList<String> totalList() {
        return totals;
    }

    public ObservableList<String> averagesList() {
        return averages;
    }

    public ObservableList<Demographic> demographicsList() {
        return demographics;
    }

    public ObservableList<Series<String, Number>> averageChartData() {
        return averageChartData;
    }

    public ObservableList<Series<String, Number>> totalMetricChartData() {
        return totalMetricChartData;
    }

    public ObservableList<PieChart.Data> demographicsChartData() {
        return demographicsChartData;
    }

    public StringProperty selectedAverageProperty() {
        return selectedAverage;
    }

    public StringProperty selectedTotalMetricProperty() {
        return selectedTotals;
    }

    public ObjectProperty<Demographic> selectedDemographicProperty() {
        return selectedDemographic;
    }

    public ObjectProperty<Campaign> selectedCampaignProperty() {
        return selectedCampaign;
    }

    public StringProperty totalClickCostProperty() {
        return totalClickCost;
    }

    public StringProperty totalImpresCostProperty() {
        return totalImpresCost;
    }

    public StringProperty totalCostProperty() {
        return totalCost;
    }

    public StringProperty clickThroughRateTextProperty() {
        return clickThroughRateText;
    }

    public StringProperty bounceConversionTextProperty() { return bouncesPerConversionText; }

    public StringProperty bounceRateTextProperty() {
        return bounceRateText;
    }

    public StringProperty getConversionUniquesProperty() {
        return conversionsPerUniqueText;
    }

    public StringProperty getTotalImpressionsProperty() {
        return totalImpressionsText;
    }

    public StringProperty getTotalClicksProperty() {
        return totalClicksText;
    }

    public StringProperty getTotalUniquesProperty() {
        return totalUniquesText;
    }

    public StringProperty getTotalBouncesProperty() {
        return totalBouncesText;
    }

    public StringProperty getTotalConversionsProperty() {
        return totalConversionsText;
    }

    private void updateTotalCosts() {
        totalClickCost.setValue("£" + selectedCampaign.getValue().getTotalClickCost().setScale(2, RoundingMode.CEILING).toPlainString());
        totalImpresCost.setValue("£" + selectedCampaign.getValue().getTotalImpressionCost().setScale(2, RoundingMode.CEILING).toPlainString());
        totalCost.setValue("£" + selectedCampaign.getValue().getTotalCost().setScale(2, RoundingMode.CEILING).toPlainString());

        clickThroughRateText.setValue(selectedCampaign.getValue().getClickThroughRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
        conversionsPerUniqueText.setValue(selectedCampaign.getValue().getConversionsPerUnique().setScale(2, RoundingMode.CEILING).toPlainString());
    }

    private void updateTotalMetrics() {
        totalImpressionsText.setValue(String.valueOf(selectedCampaign.getValue().getImpressionDataCount()));
        totalClicksText.setValue(String.valueOf(selectedCampaign.getValue().getClickDataCount()));
        totalUniquesText.setValue(String.valueOf(selectedCampaign.getValue().getUniquesCount()));
        totalConversionsText.setValue(String.valueOf(selectedCampaign.getValue().getConversionsCount()));
    }

    private void updateBouncesCountDefault(Filter filter) {
        updateBouncesCountByPages((byte) 1, filter);
    }

    private void updateBouncesCountByTime(long maxSeconds, boolean allowInf, Filter filter) {
        selectedCampaign.getValue().updateBouncesByTime(maxSeconds, allowInf, filter);
        updateBounceMetrics();
    }

    private void updateBouncesCountByPages(byte maxPages, Filter filter) {
        selectedCampaign.getValue().updateBouncesByPages(maxPages, filter);
        updateBounceMetrics();
    }

    private void updateBounceMetrics() {
        totalBouncesText.setValue(String.valueOf(selectedCampaign.getValue().getBouncesCount()));
        bounceRateText.setValue(selectedCampaign.getValue().getBounceRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
        bouncesPerConversionText.setValue(selectedCampaign.getValue().getBouncesPerConversion().setScale(2, RoundingMode.CEILING).toPlainString());
        if (selectedTotals.getValue().equals(totalBounces))
            updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedBounceTotals());
    }

    private void setupCampaignSelector() {
        selectedCampaign.addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Optional<Campaign> matchingCampaign = Campaign.getCampaigns().stream().filter(newVal::equals).findFirst();
                matchingCampaign.ifPresent(cam -> selectedCampaign.setValue(cam));
                updateCampaign();
            } else {
                selectedCampaign.setValue(Campaign.getCampaigns().get(0));
            }

        });

        selectedCampaign.setValue(Campaign.getCampaigns().get(0));
    }

    private void updateCampaign(){
        Filter filter = (selectedCampaign.getValue().hasAppliedFilter() ? selectedCampaign.getValue().getAppliedFilter() : new Filter());
        updateTotalMetrics();
        updateTotalCosts();
        updateBouncesCountDefault(filter);

        updatePieChartData(selectedCampaign.getValue().getPercentageMap(Demographic.Gender));
        updateAverages();
        updateTotals();
    }

    private void setupAverageSelector() {
        selectedAverage.addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Optional<String> matchingAverage = averages.stream().filter(newVal::equals).findFirst();
                matchingAverage.ifPresent(avg -> selectedAverage.setValue(avg));
            } else {
                selectedAverage.setValue(avgCostClick);
            }
            updateAverages();
        });

        selectedAverage.setValue(avgCostClick);
    }
    private void updateAverages() {
        switch (selectedAverage.getValue()) {
            case avgCostClick:
                updateLineChartData(selectedCampaign.getValue().getDatedClickCostAverages());
                break;
            case avgCostImpr:
                updateLineChartData(selectedCampaign.getValue().getDatedImpressionCostAverages());
                break;
            case avgCostAcq:
                updateLineChartData(selectedCampaign.getValue().getDatedAcquisitionCostAverages());
                break;
        }
    }

    private void setUpTotalsSelector(){
        selectedTotals.addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Optional<String> matchingAverage = totals.stream().filter(newVal::equals).findFirst();
                matchingAverage.ifPresent(avg -> selectedTotals.setValue(avg));
            } else {
                selectedTotals.setValue(totalImpressions);
            }
            updateTotals();
        });

        selectedTotals.setValue(totalImpressions);
    }
    private void updateTotals() {
        //TODO refactor into one method like demographics
        switch (selectedTotals.getValue()) {
            case totalImpressions:
                updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedImpressionTotals());
                break;
            case totalClicks:
                updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedClickTotals());
                break;
            case totalUniques:
                updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedUniqueTotals());
                break;
            case totalBounces:
                updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedBounceTotals());
                break;
            case totalConversions:
                updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedAcquisitionTotals());
                break;
        }
    }

    private void setupDemographicSelector() {
        selectedDemographic.addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Optional<Demographic> matchingDemographic = Stream.of(Demographics.Demographic.values()).filter(newVal::equals).findFirst();
                matchingDemographic.ifPresent(dem -> selectedDemographic.setValue(dem));
            } else {
                selectedDemographic.setValue(Demographic.Gender);
            }
            updatePieChartData(selectedCampaign.getValue().getPercentageMap(selectedDemographic.getValue()));
        });

        selectedDemographic.setValue(Demographic.Gender);
    }

    private void updatePieChartData(HashMap<String, BigDecimal> dataMap) {
        demographicsChartData.clear();
        for (Entry<String, BigDecimal> entry : dataMap.entrySet()) {
            PieChart.Data pd = new PieChart.Data(entry.getKey(), entry.getValue().doubleValue());
            pd.setName(pd.getName() + " "+ entry.getValue().setScale(1, RoundingMode.HALF_UP).doubleValue() + "%");
            demographicsChartData.add(pd);
        }
    }

    private void updateLineChartData(HashMap<String, BigDecimal> dataMap) {
        averageChartData.clear();
        Series<String, Number> s = new Series<>();
        s.setName(selectedCampaign.getValue().toString());
        for (Entry<String, BigDecimal> entry : dataMap.entrySet()) {
            Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue().doubleValue());
            s.getData().add(data);
        }
        averageChartData.add(s);
    }

    private void updateTotalMetricLineChartData(HashMap<String, Long> dataMap) {
        totalMetricChartData.clear();
        Series<String, Number> s = new Series<>();
        s.setName(selectedCampaign.getValue().toString());
        for (Entry<String, Long> entry : dataMap.entrySet()) {

            SimpleDateFormat previousFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd");
            String reformattedStr = null;
            try {

                reformattedStr = myFormat.format(previousFormat.parse(entry.getKey()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Data<String, Number> data = new XYChart.Data<>(reformattedStr, entry.getValue());
            s.getData().add(data);
        }
        totalMetricChartData.add(s);
    }



    private void setupFilterReceiving() {
        MvvmFX.getNotificationCenter().subscribe(PrimaryFilterDialogModel.FILTER_NOTIFICATION, (key, payload) -> {
            try {
                Filter filter = (Filter) payload[0];
                filter.setCampaignID(selectedCampaign.get().getCampaignID());
                selectedCampaign.getValue().cacheData(filter);

                updateTotalMetrics();
                updateTotalCosts();
                //TODO change to apply correct bounce method
                updateBouncesCountDefault(filter);

                updatePieChartData(selectedCampaign.getValue().getPercentageMap(selectedDemographic.getValue()));
                updateTotals();
                updateAverages();

            } catch (ClassCastException e) {
                e.printStackTrace();
                Logger.log("Invalid filter received");
            }
        });
    }

}
