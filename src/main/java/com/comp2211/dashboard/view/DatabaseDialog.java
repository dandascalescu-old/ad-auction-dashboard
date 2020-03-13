package com.comp2211.dashboard.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DatabaseDialog {

    @FXML
    StackPane dialogDBStack;

    @FXML
    JFXDialog dialog;

    @FXML
    JFXButton importImpressionButton;

    @FXML
    void importImpressionAction() {

        Stage stage = (Stage) dialogDBStack.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Impressions Log");
        fileChooser.showOpenDialog(stage);
    }

    @FXML
    void importServerAction() {

        Stage stage = (Stage) dialogDBStack.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Server Log");
        fileChooser.showOpenDialog(stage);
    }

    @FXML
    void importClickAction(){

        Stage stage = (Stage) dialogDBStack.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Click Log");
        fileChooser.showOpenDialog(stage);

    }


    public void cancelDialogAction(ActionEvent event) {


        JFXDialog tb = (JFXDialog) dialog.getScene().lookup("#dialog");
        tb.close();

    }

}
