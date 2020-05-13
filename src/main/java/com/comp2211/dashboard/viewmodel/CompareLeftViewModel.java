package com.comp2211.dashboard.viewmodel;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.model.data.Demographics;
import com.comp2211.dashboard.model.data.Demographics.Demographic;
import com.comp2211.dashboard.model.data.Filter;
import com.comp2211.dashboard.util.Logger;
import com.comp2211.dashboard.view.CompareView;
import com.comp2211.dashboard.view.PrimaryView;
import com.comp2211.dashboard.view.SettingsView;
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

import javafx.application.Platform;
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
    private ObservableList<String> rates;

    private ObservableList<Series<String, Number>> averageChartData;
    private ObservableList<PieChart.Data> demographicsChartData;
    private ObservableList<PieChart.Data> demographicsChartDataRight;
    private ObservableList<Series<String, Number>> totalMetricChartData;
    private ObservableList<Series<String, Number>> totalCostChartDataRight;
    private ObservableList<Series<String, Number>> totalCostChartDataLeft;
    private ObservableList<Series<String, Number>> rateChartData;

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
    private StringProperty selectedRate = new SimpleStringProperty("");



    private final String avgCostClick = "Average Cost of Click";
    private final String avgCostImpr = "Average Cost of Impression";
    private final String avgCostAcq = "Average Cost of Acquisition";

    private final String totalImpressions = "Impressions";
    private final String totalClicks = "Clicks";
    private final String totalUniques = "Uniques";
    private final String totalBounces = "Bounces";
    private final String totalConversions = "Conversions";

    private final String rateBounce = "Bounce Rate";
    private final String rateCTR = "Click Through Rate";

    public void initialize() {
        campaigns = FXCollections.observableArrayList();
        averages = FXCollections.observableArrayList();
        demographics = FXCollections.observableArrayList();
        averageChartData = FXCollections.observableArrayList();
        demographicsChartData = FXCollections.observableArrayList();
        demographicsChartDataRight = FXCollections.observableArrayList();
        totals = FXCollections.observableArrayList();
        totalMetricChartData = FXCollections.observableArrayList();
        totalCostChartDataRight = FXCollections.observableArrayList();
        totalCostChartDataLeft = FXCollections.observableArrayList();
        rates = FXCollections.observableArrayList();
        rateChartData = FXCollections.observableArrayList();

        campaigns.addAll(Campaign.getCampaigns());
        averages.addAll(avgCostClick, avgCostImpr, avgCostAcq);
        demographics.addAll(Demographics.Demographic.values());
        totals.addAll(totalImpressions, totalClicks, totalUniques, totalBounces, totalConversions);
        rates.addAll(rateBounce, rateCTR);

        setupCampaignSelector();
        setupCampaignSelectorRight();


        Filter filter = (selectedCampaign.getValue().hasAppliedFilter() ? selectedCampaign.getValue().getAppliedFilter() : new Filter());
        Filter filterRight = (selectedCampaignRight.getValue().hasAppliedFilter() ? selectedCampaignRight.getValue().getAppliedFilter() : new Filter());

        // This was in an runnable block, which has been removed to make testing more manageable
        selectedCampaign.getValue().cacheData(filter);
        selectedCampaignRight.getValue().cacheData(filterRight);

        updateTotalMetrics();
        updateTotalCosts();

        setupDemographicSelector();
        setupAverageSelector();
        setUpTotalsSelector();
        updateTotalCostsRight();
        updateTotalMetricsRight();

        updateBouncesCountDefaultRight(filterRight);
        updateBouncesCountDefaultLeft(filter);

        setUpRatesSelector();

        setupDemographicSelectorRight();

        setupFilterReceivingLeft();
        setupFilterReceivingRight();

        setupGranReceiving();
        setupGranAvgsReceiving();
        setupGranCostTotalsReceivingLeft();
        setupGranCostTotalsReceivingRight();
        setupGranRatesReceiving();

        setupBounceReceiving();
        setupCampaignReceiving();
        setupGranResetReceiving();

    }

    public ObservableList<String> ratesList() {return rates; }

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

    public StringProperty selectedRatePropery() { return selectedRate; }

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

    public ObservableList<Series<String, Number>> totalCostChartDataRight() {
        return totalCostChartDataRight;
    }

    public ObservableList<Series<String, Number>> totalCostChartDataLeft() {
        return totalCostChartDataLeft;
    }

    public ObservableList<Series<String, Number>> rateChartData() {
        return rateChartData;
    }

    private void updateTotalCosts() {
        totalClickCost.setValue("£" + selectedCampaign.getValue().getTotalClickCost().setScale(2, RoundingMode.CEILING).toPlainString());
        totalImpresCost.setValue("£" + selectedCampaign.getValue().getTotalImpressionCost().setScale(2, RoundingMode.CEILING).toPlainString());
        totalCost.setValue("£" + selectedCampaign.getValue().getTotalCost().setScale(2, RoundingMode.CEILING).toPlainString());

        clickThroughRateText.setValue(selectedCampaign.getValue().getClickThroughRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
        conversionsPerUniqueText.setValue(selectedCampaign.getValue().getConversionsPerUnique().setScale(2, RoundingMode.CEILING).toPlainString());

        updateTotalCostLineChartData(selectedCampaign.getValue().getDatedCostTotals());
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

        updateTotalCostLineChartDataRight(selectedCampaignRight.getValue().getDatedCostTotals());

    }


    private void updateBouncesCountDefault(Filter filter) {
        updateBouncesCountByPages((byte) 1, filter);
    }

    private void updateBouncesCountDefaultRight(Filter filter) {

        updateBouncesCountByPagesRight((byte) 1, filter);
    }

    private void updateBouncesCountDefaultLeft(Filter filter) {

        updateBouncesCountByPagesLeft((byte) 1, filter);
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

    private void updateBouncesCountByPagesLeft(byte maxPages, Filter filter) {
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

    private void updateBounceMetricsRight() {
        totalBouncesTextRight.setValue(String.valueOf(selectedCampaignRight.getValue().getBouncesCount()));
        bounceRateTextRight.setValue(selectedCampaignRight.getValue().getBounceRate().setScale(2, RoundingMode.CEILING).toPlainString() + "%");
        bouncesPerConversionTextRight.setValue(selectedCampaignRight.getValue().getBouncesPerConversion().setScale(2, RoundingMode.CEILING).toPlainString());
        if (selectedTotals.getValue().equals(totalBounces))
            updateTotalMetricLineChartData(selectedCampaignRight.getValue().getDatedBounceTotals());
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

    private void setUpRatesSelector() {
        selectedRate.addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Optional<String> matchingAverage = rates.stream().filter(newVal::equals).findFirst();
                matchingAverage.ifPresent(avg -> selectedRate.setValue(avg));
            } else {
                selectedRate.setValue(rateBounce);
            }
            updateRates();
        });

        selectedRate.setValue(rateBounce);
    }

    private void updateRates(){
        switch (selectedRate.getValue()) {
            case rateBounce :
                rateChartData.clear();
                updateRatesLineChartData(selectedCampaign.getValue().getDatedBounceRates());
                updateRatesLineChartData(selectedCampaignRight.getValue().getDatedCTRs());
                break;
            case rateCTR :
                rateChartData.clear();
                updateRatesLineChartData(selectedCampaign.getValue().getDatedCTRs());
                updateRatesLineChartData(selectedCampaignRight.getValue().getDatedCTRs());
                break;
        }
    }

    private void updateRatesLineChartData(HashMap<String, BigDecimal> dataMap) {

        Series<String, Number> s = new Series<>();
        s.setName(selectedCampaign.getValue().toString());
        for (Entry<String, BigDecimal> entry : dataMap.entrySet()) {

            SimpleDateFormat previousFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat previousFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy/ \nMM/dd -\n HHmm");
            String reformattedStr = null;
            try {
                reformattedStr = myFormat.format(previousFormat.parse(entry.getKey()));
            } catch (ParseException e) {
                try {
                    reformattedStr = myFormat.format(previousFormat2.parse(entry.getKey()));
                } catch (ParseException e2) {
                    System.err.println(e2);
                }
            }

            Data<String, Number> data = new XYChart.Data<>(reformattedStr, entry.getValue());
            s.getData().add(data);
        }
        rateChartData.add(s);
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

            //TODO change format for each chart
            SimpleDateFormat previousFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat previousFormat2 = new SimpleDateFormat("MM-dd");
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy/\nMM/dd -\nHHmm");
            String reformattedStr = null;
            try {
                reformattedStr = myFormat.format(previousFormat.parse(entry.getKey()));
            } catch (ParseException e) {
                try {
                    reformattedStr = myFormat.format(previousFormat2.parse(entry.getKey()));
                } catch (ParseException e2) {
                    System.err.println(e2);
                }
            }


            Data<String, Number> data = new XYChart.Data<>(reformattedStr, entry.getValue().doubleValue());
            s.getData().add(data);
        }
        averageChartData.add(s);
    }



    private void updateTotalMetricLineChartData(HashMap<String, Long> dataMap) {
        Series<String, Number> s = new Series<>();
        s.setName(selectedCampaign.getValue().toString());
        for (Entry<String, Long> entry : dataMap.entrySet()) {

            //TODO change format for each chart
            SimpleDateFormat previousFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat previousFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy/ \nMM/dd -\n HHmm");
            String reformattedStr = null;
            try {
                reformattedStr = myFormat.format(previousFormat.parse(entry.getKey()));
            } catch (ParseException e) {
                try {
                    reformattedStr = myFormat.format(previousFormat2.parse(entry.getKey()));
                } catch (ParseException e2) {
                    System.err.println(e2);
                }
            }

            Data<String, Number> data = new XYChart.Data<>(reformattedStr, entry.getValue().doubleValue());
            s.getData().add(data);
        }
        totalMetricChartData.add(s);
    }



    private void setupFilterReceivingLeft() {
        MvvmFX.getNotificationCenter().subscribe(PrimaryFilterDialogModel.FILTER_NOTIFICATION_LEFTCOMPARE, (key, payload) -> {
            try {
                Filter filter = (Filter) payload[0];
                filter.setCampaignID(selectedCampaign.get().getCampaignID());
                Logger.log("[INFO] Applying filter...");
                Thread runLater = new Thread(){
                    @Override
                    public void run() {
                        Platform.runLater(()->{
                            selectedCampaign.getValue().resetGranularity();
                            updateTotalMetrics();
                            updateTotalCosts();
                            //TODO change to apply correct bounce method
                            selectedCampaign.getValue().updateBouncesByPages((byte)1, filter);

                            updatePieChartData(selectedCampaign.getValue().getPercentageMap(selectedDemographic.getValue()));
                            updateTotals();
                            updateAverages();
                            updateTotalCostLineChartData(selectedCampaign.getValue().getDatedCostTotals());
                            updateRates();
                            Logger.log("[INFO] Filter applied successfully.");
                        });
                    }
                };
                selectedCampaign.getValue().cacheData(filter);

                runLater.start();

            } catch (ClassCastException e) {
                e.printStackTrace();
                Logger.log("Invalid filter received");
            }
        });
    }

    private void setupFilterReceivingRight() {
        MvvmFX.getNotificationCenter().subscribe(PrimaryFilterDialogModel.FILTER_NOTIFICATION_RIGHTCOMPARE, (key, payload) -> {
            try {
                Filter filter = (Filter) payload[0];
                filter.setCampaignID(selectedCampaign.get().getCampaignID());
                Logger.log("[INFO] Applying filter...");
                Thread runLater = new Thread(){
                    @Override
                    public void run() {
                        Platform.runLater(()->{
                            selectedCampaign.getValue().resetGranularity();
                            updateTotalMetrics();
                            updateTotalCosts();
                            //TODO change to apply correct bounce method
                            selectedCampaign.getValue().updateBouncesByPages((byte)1, filter);

                            updatePieChartData(selectedCampaign.getValue().getPercentageMap(selectedDemographic.getValue()));
                            updateTotals();
                            updateAverages();
                            updateTotalCostLineChartData(selectedCampaign.getValue().getDatedCostTotals());
                            updateRates();
                            Logger.log("[INFO] Filter applied successfully.");
                        });
                    }
                };
                selectedCampaign.getValue().cacheData(filter);

                runLater.start();

            } catch (ClassCastException e) {
                e.printStackTrace();
                Logger.log("Invalid filter received");
            }
        });
    }

    private void updateTotalCostLineChartDataRight(HashMap<String, BigDecimal> dataMap) {
        totalCostChartDataRight.clear();
        Series<String, Number> s = new Series<>();
        s.setName(selectedCampaign.getValue().toString());
        for (Entry<String, BigDecimal> entry : dataMap.entrySet()) {

            SimpleDateFormat previousFormat = new SimpleDateFormat("yyyy-MM-dd \n HH:mm:ss");
            SimpleDateFormat previousFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy/ \nMM/dd -\n HHmm");
            String reformattedStr = null;
            try {
                reformattedStr = myFormat.format(previousFormat.parse(entry.getKey()));
            } catch (ParseException e) {
                try {
                    reformattedStr = myFormat.format(previousFormat2.parse(entry.getKey()));
                } catch (ParseException e2) {
                    System.err.println(e2);
                }
            }

            Data<String, Number> data = new XYChart.Data<>(reformattedStr, entry.getValue().divide(BigDecimal.valueOf(100L), RoundingMode.CEILING));
            s.getData().add(data);
        }
        totalCostChartDataRight.add(s);
    }

    private void updateTotalCostLineChartData(HashMap<String, BigDecimal> dataMap) {
        totalCostChartDataLeft.clear();
        Series<String, Number> s = new Series<>();
        s.setName(selectedCampaign.getValue().toString());
        for (Entry<String, BigDecimal> entry : dataMap.entrySet()) {

            SimpleDateFormat previousFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat previousFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy/ \nMM/dd -\n HHmm");
            String reformattedStr = null;
            try {
                reformattedStr = myFormat.format(previousFormat.parse(entry.getKey()));
            } catch (ParseException e) {
                try {
                    reformattedStr = myFormat.format(previousFormat2.parse(entry.getKey()));
                } catch (ParseException e2) {
                    System.err.println(e2);
                }
            }

            Data<String, Number> data = new XYChart.Data<>(reformattedStr, entry.getValue().divide(BigDecimal.valueOf(100L), RoundingMode.CEILING));
            s.getData().add(data);
        }
        totalCostChartDataLeft.add(s);
    }

    private void setupGranAvgsReceiving() {
        MvvmFX.getNotificationCenter().subscribe(CompareView.GRAN_NOTIFICATION_AVG, (key, payload) -> {
            try {
                byte granularity = (byte) payload[0];
                Logger.log("[INFO] Changing granularity...");
                selectedCampaign.getValue().updateAvgsGranularity(granularity);
                updateAverages();

                Logger.log("[INFO] Granularity changed successfully.");
            } catch (ClassCastException e) {
                e.printStackTrace();
                Logger.log("[ERROR] Invalid granularity received.");
            }
        });
    }

    private void setupGranReceiving() {
        MvvmFX.getNotificationCenter().subscribe(CompareView.GRAN_NOTIFICATION, (key, payload) -> {
            try {
                byte granularity = (byte) payload[0];
                Logger.log("[INFO] Changing granularity...");
                selectedCampaign.getValue().updateTotalsGranularity(granularity);
                updateTotals();

                Logger.log("[INFO] Granularity changed successfully.");
            } catch (ClassCastException e) {
                e.printStackTrace();
                Logger.log("[ERROR] Invalid granularity received.");
            }
        });
    }

    private void setupGranCostTotalsReceivingLeft() {
        MvvmFX.getNotificationCenter().subscribe(CompareView.GRAN_NOTIFICATION_COST_TOTAL_LEFT, (key, payload) -> {
            try {
                byte granularity = (byte) payload[0];
                Logger.log("[INFO] Changing granularity...");
                selectedCampaign.getValue().updateCostTotalsGranularity(granularity);
                updateTotalCostLineChartData(selectedCampaign.getValue().getDatedCostTotals());

                Logger.log("[INFO] Granularity changed successfully.");
            } catch (ClassCastException e) {
                e.printStackTrace();
                Logger.log("[ERROR] Invalid granularity received.");
            }
        });
    }

    private void setupGranCostTotalsReceivingRight() {
        MvvmFX.getNotificationCenter().subscribe(CompareView.GRAN_NOTIFICATION_COST_TOTAL_RIGHT, (key, payload) -> {
            try {
                byte granularity = (byte) payload[0];
                Logger.log("[INFO] Changing granularity...");
                selectedCampaignRight.getValue().updateCostTotalsGranularity(granularity);
                updateTotalCostLineChartDataRight(selectedCampaignRight.getValue().getDatedCostTotals());

                Logger.log("[INFO] Granularity changed successfully.");
            } catch (ClassCastException e) {
                e.printStackTrace();
                Logger.log("[ERROR] Invalid granularity received.");
            }
        });
    }

    private void setupGranRatesReceiving() {
        MvvmFX.getNotificationCenter().subscribe(CompareView.GRAN_NOTIFICATION_RATES, (key, payload) -> {
            try {
                byte granularity = (byte) payload[0];
                Logger.log("[INFO] Changing granularity...");
                selectedCampaign.getValue().updateRatesGranularity(granularity);
                updateRates();

                Logger.log("[INFO] Granularity changed successfully.");
            } catch (ClassCastException e) {
                e.printStackTrace();
                Logger.log("[ERROR] Invalid granularity received.");
            }
        });
    }

    private void setupBounceReceiving() {
        MvvmFX.getNotificationCenter().subscribe(SettingsView.BOUNCES, (key, payload) -> {
            updateBounceMetricsRight();
            updateBounceMetrics();
        });
    }

    private void setupCampaignReceiving(){
        MvvmFX.getNotificationCenter().subscribe("Imported", (key, payload) -> {
            campaigns.setAll(Campaign.getCampaigns());
            DatabaseViewModel.changeProgressToCompleted((Campaign) payload[0]);
            System.out.println("campaigns set.");
        });
    }

    private void setupGranResetReceiving() {
        MvvmFX.getNotificationCenter().subscribe(Campaign.RESET_GRAN, (key, payload) -> {
            updateTotals();
            updateAverages();
            updateTotalCostLineChartDataRight(selectedCampaignRight.getValue().getDatedCostTotals());
            updateTotalCostLineChartData(selectedCampaignRight.getValue().getDatedCostTotals());
            updateRates();
        });
    }

}
