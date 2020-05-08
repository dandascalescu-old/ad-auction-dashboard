package com.comp2211.dashboard.view;

import com.comp2211.dashboard.viewmodel.PrimaryFilterDialogModel;
import com.jfoenix.controls.JFXComboBox;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class PrimaryFilterDialog implements FxmlView<PrimaryFilterDialogModel> {
    //TODO include a 'clear selection' button

    @FXML
    private DatePicker startDatePicker, endDatePicker;

    @FXML
    private JFXComboBox<String> genderComboBox, ageComboBox, incomeComboBox, contextComboBox;

    @InjectViewModel
    private PrimaryFilterDialogModel viewModel;

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
    }

    @FXML
    void cancelDialog(ActionEvent event) {
        startDatePicker.valueProperty().setValue(null);
        endDatePicker.valueProperty().setValue(null);
        genderComboBox.valueProperty().setValue(null);
        ageComboBox.valueProperty().setValue(null);
        incomeComboBox.valueProperty().setValue(null);
        contextComboBox.valueProperty().setValue(null);

        PrimaryView.cancelDialogAction();
    }

    @FXML
    void saveFilter(ActionEvent event){
        viewModel.applyFilters();

        PrimaryView.cancelDialogAction();
    }

}
