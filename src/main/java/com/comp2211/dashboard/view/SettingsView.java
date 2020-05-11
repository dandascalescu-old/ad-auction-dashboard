package com.comp2211.dashboard.view;

import com.comp2211.dashboard.viewmodel.SettingsViewModel;
import de.saxsys.mvvmfx.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsView implements FxmlView<SettingsViewModel>, Initializable{

    @FXML
    TextField timeTextField, noPagesTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveBounce(ActionEvent event) {

        String time = "";

        String pages = "";

        time = timeTextField.getText();
        pages = noPagesTextField.getText();


    }
}
