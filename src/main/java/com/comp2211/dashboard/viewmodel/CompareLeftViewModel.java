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
    private ObservableList<PieChart.Data> demographicsChartDataRight;
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


    // ----------- Right Side ------------------
    private StringProperty totalClickCostRight = new SimpleStringProperty("");
    private StringProperty totalImpresCostRight = new SimpleStringProperty("");
    private StringProperty totalCostRight = new SimpleStringProperty("");
    private StringProperty clickThroughRateTextRight = new SimpleStringProperty("");
    private StringProperty bounceRateTextRight = new SimpleStringProperty("");
    private StringProperty conversionsPerUniqueTextRight = new SimpleStringProperty("");
    private StringProperty bouncesPerConversionTextRight = new SimpleStringProperty("");

    private StringProperty totalImpressionsTextRight = new SimpleStringProperty("");
    private StringProperty totalClicksTextRight = new SimpleStringProperty("");
    private StringProperty totalUniquesTextRight = new SimpleStringProperty("");
    private StringProperty totalBouncesTextRight = new SimpleStringProperty("");
    private StringProperty totalConversionsTextRight = new SimpleStringProperty("");

    private ObjectProperty<Campaign> selectedCampaignRight = new SimpleObjectProperty<>();
    private ObjectProperty<Demographic> selectedDemographicRight = new SimpleObjectProperty<>();
    //------------------------------------------

    private StringProperty selectedAverage = new SimpleStringProperty("");
    private StringProperty selectedTotals = new SimpleStringProperty("");
    private ObjectProperty<Demographic> selectedDemographic = new SimpleObjectProperty<>();
    private ObjectProperty<Campaign> selectedCampaign = new SimpleObjectProperty<>();
    private ObjectProperty<Campaign> selectedRightCampaign = new SimpleObjectProperty<>();




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
        demographicsChartDataRight = FXCollections.observableArrayList();
        totals = FXCollections.observableArrayList();
        totalMetricChartData = FXCollections.observableArrayList();

        campaigns.addAll(Campaign.getCampaigns());
        averages.addAll(avgCostClick, avgCostImpr, avgCostAcq);
        demographics.addAll(Demographics.Demographic.values());
        totals.addAll(totalImpressions, totalClicks, totalUniques, totalBounces, totalConversions);

        setupCampaignSelector();
        setupCampaignSelectorRight();

        Filter filter = (selectedCampaign.getValue().hasAppliedFilter() ? selectedCampaign.getValue().getAppliedFilter() : new Filter());
        Filter filterRight = (selectedCampaignRight.getValue().hasAppliedFilter() ? selectedCampaignRight.getValue().getAppliedFilter() : new Filter());

        // This was in an runnable block, which has been removed to make testing more manageable
        selectedCampaign.getValue().cacheData(filter);
        selectedCampaignRight.getValue().cacheData(filter);

        updateTotalMetrics();
        updateTotalCosts();
        updateBouncesCountDefault(filter);

        updateTotalCostsRight();
        updateTotalMetricsRight();
        updateBouncesCountDefaultRight(filter);

        setupDemographicSelector();
        setupAverageSelector();
        setUpTotalsSelector();

        setupDemographicSelectorRight();

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

    public ObservableList<PieChart.Data> demographicsChartDataRight() {
        return demographicsChartDataRight;
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

    public ObjectProperty<Demographic> selectedDemographicPropertyRight() {
        return selectedDemographicRight;
    }

    public ObjectProperty<Campaign> selectedCampaignProperty() {
        return selectedCampaign;
    }

    public ObjectProperty<Campaign> selectedRightCampaignPropertyRight() {
        return selectedCampaignRight;
    }

    public StringProperty totalClickCostProperty() {
        return totalClickCost;
    }

    public StringProperty totalClickCostPropertyRight(){ return totalClickCostRight;}

    public StringProperty totalImpresCostProperty() {
        return totalImpresCost;
    }

    public StringProperty totalImpresCostPropertyRight() {
        return totalImpresCostRight;
    }

    public StringProperty totalCostProperty() {
        return totalCost;
    }

    public StringProperty totalCostPropertyRight() {
        return totalCostRight;
    }

    public StringProperty clickThroughRateTextProperty() {
        return clickThroughRateText;
    }

    public StringProperty clickThroughRateTextPropertyRight() {
        return clickThroughRateTextRight;
    }

    public StringProperty bounceConversionTextProperty() { return bouncesPerConversionText; }

    public StringProperty bounceConversionTextPropertyRight() { return bouncesPerConversionTextRight; }

    public StringProperty bounceRateTextProperty() {
        return bounceRateText;
    }

    public StringProperty bounceRateTextPropertyRight() {
        return bounceRateTextRight;
    }

    public StringProperty getConversionUniquesProperty() {
        return conversionsPerUniqueText;
    }

    public StringProperty getConversionUniquesPropertyRight() {
        return conversionsPerUniqueTextRight;
    }

    public StringProperty getTotalImpressionsProperty() {
        return totalImpressionsText;
    }

    public StringProperty getTotalImpressionsPropertyRight() {
        return totalImpressionsTextRight;
    }

    public StringProperty getTotalClicksProperty() {
        return totalClicksText;
    }

    public StringProperty getTotalClicksPropertyRight() {
        return totalClicksTextRight;
    }

    public StringProperty getTotalUniquesProperty() {
        return totalUniquesText;
    }

    public StringProperty getTotalUniquesPropertyRight() { return totalUniquesTextRight; }

    public StringProperty getTotalBouncesProperty() {
        return totalBouncesText;
    }

    public StringProperty getTotalBouncesPropertyRight() {
        return totalBouncesTextRight;
    }

    public StringProperty getTotalConversionsProperty() {
        return totalConversionsText;
    }

    public StringProperty getTotalConversionsPropertyRight() {
        return totalConversionsTextRight;
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


    private void updateTotalCostsRight() {

        totalClickCostRight.setValue("£" + selectedCampaignRight.getValue().getTotalClickCost().setScale(2, RoundingMode.CEILING).toPlainString());
        totalImpresCostRight.setValue("£" + selectedCampaignRight.getValue().getTotalImpressionCost().setScale(2, RoundingMode.CEILING).toPlainString());
        totalCostRight.setValue("£" + selectedCampaignRight.getValue().getTotalCost().setScale(2, RoundingMode.CEILING).toPlainString());
        clickThroughRateTextRight.setValue(selectedCampaignRight.getValue().getClickThroughRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
        conversionsPerUniqueTextRight.setValue(selectedCampaignRight.getValue().getConversionsPerUnique().setScale(2, RoundingMode.CEILING).toPlainString());
    }

    private void updateTotalMetricsRight() {

        totalImpressionsTextRight.setValue(String.valueOf(selectedCampaignRight.getValue().getImpressionDataCount()));
        totalClicksTextRight.setValue(String.valueOf(selectedCampaignRight.getValue().getClickDataCount()));
        totalUniquesTextRight.setValue(String.valueOf(selectedCampaignRight.getValue().getUniquesCount()));
        totalConversionsTextRight.setValue(String.valueOf(selectedCampaignRight.getValue().getConversionsCount()));
    }


    private void updateBouncesCountDefault(Filter filter) {
        updateBouncesCountByPages((byte) 1, filter);
    }

    private void updateBouncesCountDefaultRight(Filter filter) {

        updateBouncesCountByPagesRight((byte) 1, filter);
    }


    private void updateBouncesCountByTime(long maxSeconds, boolean allowInf, Filter filter) {
        selectedCampaign.getValue().updateBouncesByTime(maxSeconds, allowInf, filter);
        updateBounceMetrics();
    }

    private void updateBouncesCountByPages(byte maxPages, Filter filter) {
        selectedCampaign.getValue().updateBouncesByPages(maxPages, filter);
        updateBounceMetrics();
    }

    private void updateBouncesCountByPagesRight(byte maxPages, Filter filter) {
        selectedCampaignRight.getValue().updateBouncesByPages(maxPages, filter);
        updateBounceMetricsRight();
    }

    private void updateBounceMetrics() {
        totalBouncesText.setValue(String.valueOf(selectedCampaign.getValue().getBouncesCount()));
        bounceRateText.setValue(selectedCampaign.getValue().getBounceRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
        bouncesPerConversionText.setValue(selectedCampaign.getValue().getBouncesPerConversion().setScale(2, RoundingMode.CEILING).toPlainString());
        if (selectedTotals.getValue().equals(totalBounces))
            updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedBounceTotals());
    }

    private void updateBounceMetricsRight() {
        totalBouncesTextRight.setValue(String.valueOf(selectedCampaignRight.getValue().getBouncesCount()));
        bounceRateTextRight.setValue(selectedCampaignRight.getValue().getBounceRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
        bouncesPerConversionTextRight.setValue(selectedCampaignRight.getValue().getBouncesPerConversion().setScale(2, RoundingMode.CEILING).toPlainString());
        //if (selectedTotalsRight.getValue().equals(totalBounces))
          //  updateTotalMetricLineChartData(selectedCampaignRight.getValue().getDatedBounceTotals());
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


    private void setupCampaignSelectorRight() {
        selectedCampaignRight.addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Optional<Campaign> matchingCampaign = Campaign.getCampaigns().stream().filter(newVal::equals).findFirst();
                matchingCampaign.ifPresent(cam -> selectedCampaignRight.setValue(cam));
                updateCampaignRight();
            } else {
                selectedCampaignRight.setValue(Campaign.getCampaigns().get(0));
            }

        });

        selectedCampaignRight.setValue(Campaign.getCampaigns().get(0));
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

    private void updateCampaignRight(){
        Filter filter = (selectedCampaignRight.getValue().hasAppliedFilter() ? selectedCampaignRight.getValue().getAppliedFilter() : new Filter());
        updateTotalMetricsRight();
        updateTotalCostsRight();
        updateBouncesCountDefaultRight(filter);

        updatePieChartData(selectedCampaignRight.getValue().getPercentageMap(Demographic.Gender));
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
                averageChartData.clear();
                updateLineChartData(selectedCampaign.getValue().getDatedClickCostAverages());
                updateLineChartData(selectedCampaignRight.getValue().getDatedClickCostAverages());
                break;
            case avgCostImpr:
                averageChartData.clear();
                updateLineChartData(selectedCampaign.getValue().getDatedImpressionCostAverages());
                updateLineChartData(selectedCampaignRight.getValue().getDatedImpressionCostAverages());
                break;
            case avgCostAcq:
                averageChartData.clear();
                updateLineChartData(selectedCampaign.getValue().getDatedAcquisitionCostAverages());
                updateLineChartData(selectedCampaignRight.getValue().getDatedAcquisitionCostAverages());
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
                totalMetricChartData.clear();
                updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedImpressionTotals());
                updateTotalMetricLineChartData(selectedCampaignRight.getValue().getDatedImpressionTotals());
                break;
            case totalClicks:
                totalMetricChartData.clear();
                updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedClickTotals());
                updateTotalMetricLineChartData(selectedCampaignRight.getValue().getDatedClickTotals());
                break;
            case totalUniques:
                totalMetricChartData.clear();
                updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedUniqueTotals());
                updateTotalMetricLineChartData(selectedCampaignRight.getValue().getDatedUniqueTotals());


                break;
            case totalBounces:
                totalMetricChartData.clear();
                updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedBounceTotals());
                updateTotalMetricLineChartData(selectedCampaignRight.getValue().getDatedBounceTotals());

                break;
            case totalConversions:
                totalMetricChartData.clear();
                updateTotalMetricLineChartData(selectedCampaign.getValue().getDatedAcquisitionTotals());
                updateTotalMetricLineChartData(selectedCampaignRight.getValue().getDatedAcquisitionTotals());

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

    private void setupDemographicSelectorRight() {
        selectedDemographicRight.addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Optional<Demographic> matchingDemographic = Stream.of(Demographics.Demographic.values()).filter(newVal::equals).findFirst();
                matchingDemographic.ifPresent(dem -> selectedDemographicRight.setValue(dem));
            } else {
                selectedDemographicRight.setValue(Demographic.Gender);
            }
            updatePieChartDataRight(selectedCampaignRight.getValue().getPercentageMap(selectedDemographicRight.getValue()));
        });

        selectedDemographicRight.setValue(Demographic.Gender);
    }

    private void updatePieChartData(HashMap<String, BigDecimal> dataMap) {
        demographicsChartData.clear();
        for (Entry<String, BigDecimal> entry : dataMap.entrySet()) {
            PieChart.Data pd = new PieChart.Data(entry.getKey(), entry.getValue().doubleValue());
            pd.setName(pd.getName() + " "+ entry.getValue().setScale(1, RoundingMode.HALF_UP).doubleValue() + "%");
            demographicsChartData.add(pd);
        }
    }

    private void updatePieChartDataRight(HashMap<String, BigDecimal> dataMap) {
        demographicsChartDataRight.clear();
        for (Entry<String, BigDecimal> entry : dataMap.entrySet()) {
            PieChart.Data pd = new PieChart.Data(entry.getKey(), entry.getValue().doubleValue());
            pd.setName(pd.getName() + " "+ entry.getValue().setScale(1, RoundingMode.HALF_UP).doubleValue() + "%");
            demographicsChartDataRight.add(pd);
        }
    }

    private void updateLineChartData(HashMap<String, BigDecimal> dataMap) {

        Series<String, Number> s = new Series<>();
        s.setName(selectedCampaign.getValue().toString());
        for (Entry<String, BigDecimal> entry : dataMap.entrySet()) {
            Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue().doubleValue());
            s.getData().add(data);
        }
        averageChartData.add(s);
    }

    private void updateTotalMetricLineChartData(HashMap<String, Long> dataMap) {

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
