package com.comp2211.dashboard.viewmodel;

import de.saxsys.mvvmfx.ViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PrimaryFilterDialogModel implements ViewModel {

    private ObservableList<String> genderList;
    private ObservableList<String> ageList;
    private ObservableList<String> contextList;
    private ObservableList<String> incomeList;

    public void initialize() {
        genderList = FXCollections.observableArrayList();
        ageList = FXCollections.observableArrayList();
        contextList = FXCollections.observableArrayList();
        incomeList = FXCollections.observableArrayList();

        genderList.addAll("Female", "Male");
        incomeList.addAll("Low", "Medium", "High");
        contextList.addAll("News", "Shopping", "Social Media", "Blog");
        ageList.addAll("<25", "25-34", "35-44", "45-54", "<55");
    }

    public ObservableList<String> genderList() { return genderList; }

    public ObservableList<String> incomeList() { return incomeList; }

    public ObservableList<String> contextList() { return contextList; }

    public ObservableList<String> ageList() { return ageList; }

}
