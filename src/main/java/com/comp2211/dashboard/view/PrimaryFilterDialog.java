package com.comp2211.dashboard.view;

import com.comp2211.dashboard.viewmodel.PrimaryFilterDialogModel;
import com.jfoenix.controls.JFXComboBox;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;

public class PrimaryFilterDialog implements FxmlView<PrimaryFilterDialogModel> {


    @FXML
    private DatePicker startDatePicker, endDatePicker;

    @FXML
    private JFXComboBox<String> genderComboBox, ageComboBox, incomeComboBox, contextComboBox;

    @InjectViewModel
    private PrimaryFilterDialogModel viewModel;

    public void initialize() {

        genderComboBox.setItems(viewModel.genderList());
        ageComboBox.setItems(viewModel.ageList());
        incomeComboBox.setItems(viewModel.incomeList());
        contextComboBox.setItems(viewModel.contextList());

    }

    @FXML
    void cancelDialog(ActionEvent event) {
        PrimaryView.cancelDialogAction();
    }

    @FXML
    void saveFilter(ActionEvent event){

        LocalDate pickedStartDate = startDatePicker.getValue();
        LocalDate pickedEndDate = endDatePicker.getValue();

        String pickedGender = genderComboBox.getValue();
        String pickedAge = ageComboBox.getValue();
        String pickedIncome = incomeComboBox.getValue();
        String pickedContext = contextComboBox.getValue();


        PrimaryView.cancelDialogAction();
    }

}
