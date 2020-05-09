package com.comp2211.dashboard.viewmodel;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CampaignEntry extends RecursiveTreeObject<CampaignEntry> {


    private StringProperty titleOfCampaign;
    private StringProperty fileName;
    private StringProperty startDate;
    private StringProperty endDate;
    private StringProperty progress;

    public CampaignEntry(String titleOfCampaign, String startDate, String endDate, String progress){
        this.titleOfCampaign = new SimpleStringProperty(titleOfCampaign);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        this.progress = new SimpleStringProperty(progress);
    }

    public StringProperty titleOfCampaignProperty(){ return titleOfCampaign; }

    public StringProperty fileNameProperty(){ return fileName; }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public StringProperty endDateProperty() {
        return endDate;
    }

    public StringProperty progressProperty(){ return progress; }

    public String getTitle() { return titleOfCampaign.get(); }

    public void setProgress(String progress) { this.progress.set(progress);
    }
}
