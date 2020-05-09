package com.comp2211.dashboard.viewmodel;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CampaignEntry extends RecursiveTreeObject<CampaignEntry> {


    public StringProperty titleOfCampaign;
    public StringProperty fileName;
    public StringProperty progress;

    public CampaignEntry(String titleOfCampaign, String progress){

        this.titleOfCampaign = new SimpleStringProperty(titleOfCampaign);
        this.progress = new SimpleStringProperty(progress);
    }

    public StringProperty titleOfCampaignProperty(){ return titleOfCampaign; }

    public StringProperty fileNameProperty(){ return fileName; }

    public StringProperty progressProperty(){ return progress; }

    public String getTitle() { return titleOfCampaign.get(); }

    public void setProgress(String progress) { this.progress.set(progress);
    }
}
