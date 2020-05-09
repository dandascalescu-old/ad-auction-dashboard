package com.comp2211.dashboard.viewmodel;

import com.comp2211.dashboard.model.data.Filter;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

import static com.comp2211.dashboard.model.data.Demographics.*;

public class PrimaryFilterDialogModel implements ViewModel {

    private NotificationCenter notificationCenter;
    public static final String FILTER_NOTIFICATION = "FILTERS";

    private ObservableList<String> genderList;
    private ObservableList<String> ageList;
    private ObservableList<String> contextList;
    private ObservableList<String> incomeList;

    private ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private StringProperty genderString = new SimpleStringProperty("");
    private StringProperty ageString = new SimpleStringProperty("");
    private StringProperty incomeString = new SimpleStringProperty("");
    private StringProperty contextString = new SimpleStringProperty("");

    private IntegerProperty campaignID = new SimpleIntegerProperty(1);

    public void initialize() {
        notificationCenter = MvvmFX.getNotificationCenter();

        genderList = FXCollections.observableArrayList();
        ageList = FXCollections.observableArrayList();
        contextList = FXCollections.observableArrayList();
        incomeList = FXCollections.observableArrayList();

        genderList.addAll("Female", "Male");
        incomeList.addAll("Low", "Medium", "High");
        contextList.addAll("News", "Shopping", "Social Media", "Blog");
        ageList.addAll("<25", "25-34", "35-44", "45-54", ">54");
    }

    public ObservableList<String> genderList() {
        return genderList;
    }

    public ObservableList<String> ageList() {
        return ageList;
    }

    public ObservableList<String> incomeList() {
        return incomeList;
    }

    public ObservableList<String> contextList() {
        return contextList;
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public StringProperty genderStringProperty() {
        return genderString;
    }

    public StringProperty ageStringProperty() {
        return ageString;
    }

    public StringProperty incomeStringProperty() {
        return incomeString;
    }

    public StringProperty contextStringProperty() {
        return contextString;
    }

    public void applyFilters() {
        notificationCenter.publish(FILTER_NOTIFICATION,
                new Filter(startDate.getValue(), endDate.getValue(),
                        genderString.getValue(), ageString.getValue(), incomeString.getValue(), contextString.getValue()
                ));


    }



}