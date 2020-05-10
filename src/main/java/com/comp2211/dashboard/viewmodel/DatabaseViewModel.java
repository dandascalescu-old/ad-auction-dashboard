package com.comp2211.dashboard.viewmodel;

import com.comp2211.dashboard.Campaign;
import de.saxsys.mvvmfx.ViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseViewModel implements ViewModel {

    private static ObservableList<CampaignEntry> campaignData;

    public void initialize(){
        if(campaignData == null){
            campaignData = FXCollections.observableArrayList();
        }
        for (CampaignEntry campaignEntry: campaignData) {
            campaignEntry.setProgress("Completed");
        }
    }


    public ObservableList<CampaignEntry> getCampaignData() {
//        campaignData.add(new CampaignEntry("Campaign 1", "clickLog.csv, impression.csv, server.log", "32%"));
//        campaignData.add(new CampaignEntry("Campaign 2", "clickLog.csv, impression.csv, server.log", "42%"));
        return campaignData;
    }

    public static void addNewCampaign(Campaign c){
        if(campaignData == null){
            campaignData = FXCollections.observableArrayList();
        }
        CampaignEntry ce = new CampaignEntry(c, "Checking...", "Checking...", "Loading...");
        campaignData.add(ce);
    }

//    public static void addNewCampaign(String title, String files){
//        campaignData.add(new CampaignEntry(title, files, "Loading..."));
//
//    }

    public static void changeProgressToCompleted(Campaign c){

        for (CampaignEntry campaignEntry: campaignData) {
            if (campaignEntry.getCampaign().equals(c)){
                campaignEntry.setStartDate(c.getCampaignStartDate());
                campaignEntry.setEndDate(c.getCampaignEndDate());
                campaignEntry.setProgress("Completed");
            }
        }
    }

}
