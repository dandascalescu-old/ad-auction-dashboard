package com.comp2211.dashboard.viewmodel;

import de.saxsys.mvvmfx.ViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseViewModel implements ViewModel {

    private static ObservableList<CampaignEntry> campaignData;

    public void initialize(){
        campaignData = FXCollections.observableArrayList();
    }


    public ObservableList<CampaignEntry> getCampaignData() {
        campaignData.add(new CampaignEntry("Campaign 1", "clickLog.csv, impression.csv, server.log", "32%"));
        campaignData.add(new CampaignEntry("Campaign 2", "clickLog.csv, impression.csv, server.log", "42%"));
        return campaignData;
    }

    public static void addNewCampaign(String title, String files){
        campaignData.add(new CampaignEntry(title, files, "Loading..."));

    }

    public static void changeProgressToCompleted(String title){

        for (CampaignEntry campaignEntry: campaignData) {
            if (campaignEntry.getTitle().equals(title)){
                campaignEntry.setProgress("Completed");
            }
        }
    }

}
