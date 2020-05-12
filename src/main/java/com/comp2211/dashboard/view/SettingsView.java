package com.comp2211.dashboard.view;

import com.comp2211.dashboard.viewmodel.SettingsViewModel;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import de.saxsys.mvvmfx.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsView implements FxmlView<SettingsViewModel>, Initializable{

    @FXML
    TextField timeTextField, noPagesTextField;

    @FXML
    JFXCheckBox darkModeCheckBox;

    @FXML
    Slider textSizeSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textSizeSlider.valueProperty().addListener((obs, oldval, newVal) ->
                textSizeSlider.setValue(Math.round(newVal.doubleValue())));

        textSizeSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n < 1.0) return "Small";
                if (n == 2.0) return "Medium";

                return "Large";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Small":
                        return 0d;
                    case "Medium":
                        return 1d;
                    case "Large":
                        return 3d;

                    default:
                        return 3d;
                }
            }
        });

    }

    public void saveBounce(ActionEvent event) {

        String time = "";

        String pages = "";

        time = timeTextField.getText();
        pages = noPagesTextField.getText();


    }

    public void darkModeAction(ActionEvent event) {
            if (darkModeCheckBox.isSelected()){
                System.out.println("Activating Dark Mode");
            }else{

                System.out.println("Deactivating Dark Mode");
            }

    }
}
