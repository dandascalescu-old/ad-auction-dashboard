package com.comp2211.dashboard.view;

import com.comp2211.dashboard.viewmodel.PrimaryFilterDialogModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class PrimaryFilterDialog implements FxmlView<PrimaryFilterDialogModel> {
    private static String location = "";
    public JFXButton saveButton;
    //TODO include a 'clear selection' button

    @FXML
    private DatePicker startDatePicker, endDatePicker;

    @FXML
    private JFXComboBox<String> genderComboBox, ageComboBox, incomeComboBox, contextComboBox;

    @InjectViewModel
    private PrimaryFilterDialogModel viewModel;

    @FXML
    Label loadingFilterText;


    public void initialize() {
        startDatePicker.valueProperty().bindBidirectional(viewModel.startDateProperty());
        endDatePicker.valueProperty().bindBidirectional(viewModel.endDateProperty());
        genderComboBox.valueProperty().bindBidirectional(viewModel.genderStringProperty());
        ageComboBox.valueProperty().bindBidirectional(viewModel.ageStringProperty());
        incomeComboBox.valueProperty().bindBidirectional(viewModel.incomeStringProperty());
        contextComboBox.valueProperty().bindBidirectional(viewModel.contextStringProperty());

        genderComboBox.setItems(viewModel.genderList());
        ageComboBox.setItems(viewModel.ageList());
        incomeComboBox.setItems(viewModel.incomeList());
        contextComboBox.setItems(viewModel.contextList());
        loadingFilterText.setVisible(false);
    }

    @FXML
    void cancelDialog(ActionEvent event) {
        startDatePicker.valueProperty().setValue(null);
        endDatePicker.valueProperty().setValue(null);
        genderComboBox.valueProperty().setValue(null);
        ageComboBox.valueProperty().setValue(null);
        incomeComboBox.valueProperty().setValue(null);
        contextComboBox.valueProperty().setValue(null);

        if (location.equals("primary")) {
            PrimaryView.cancelDialogAction();
        }else if (location.equals("leftCompare")){
            CompareView.cancelDialogActionLeft();
        }else if (location.equals("rightCompare")){
            CompareView.cancelDialogActionRight();
        }

    }

    @FXML
    void saveFilter(ActionEvent event){

        loadingFilterText.setVisible(true);
        Thread t = new Thread(){
            @Override
            public void run() {
                saveButton.setDisable(true);
                viewModel.applyFilters();
                loadingFilterText.setVisible(false);
                if (location.equals("primary")) {
                    PrimaryView.cancelDialogAction();
                }else if (location.equals("leftCompare")){
                    CompareView.cancelDialogActionLeft();
                }else if (location.equals("rightCompare")){
                    CompareView.cancelDialogActionRight();
                }
                saveButton.setDisable(false);
            }
        };
        t.start();



    }

    public static void updateLocation(String locationNew){
        location = locationNew;

    }

}
