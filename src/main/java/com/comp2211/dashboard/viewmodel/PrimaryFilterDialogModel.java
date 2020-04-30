package com.comp2211.dashboard.viewmodel;

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.beans.property.ObjectProperty;
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
                new Filter(
                        startDate.getValue(), endDate.getValue(),
                        genderString.getValue(), ageString.getValue(), incomeString.getValue(), contextString.getValue()
                ));
    }

    public static class Filter {
        public LocalDate startDate, endDate;
        public int gender, age, income, context;

        public Filter() {
            this.gender = -1;
            this.age = -1;
            this.income = -1;
            this.context = -1;
        }

        public Filter(LocalDate startDate, LocalDate endDate, String gender, String age, String income, String context) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.gender = getDemographicInt(Demographic.Gender, gender);
            this.age = getDemographicInt(Demographic.Age, age);
            this.income = getDemographicInt(Demographic.Income, income);
            this.context = getDemographicInt(Demographic.Context, context);
        }

        public boolean isEqualTo(Filter other) {
            return (this.startDate == null ? other.startDate == null : this.startDate.isEqual(other.startDate)) &&
                    (this.endDate == null ? other.endDate == null : this.endDate.isEqual(other.endDate)) &&
                    (this.gender == other.gender) &&
                    (this.age == other.age) &&
                    (this.income == other.income) &&
                    (this.context == other.context);
        }
    }
}