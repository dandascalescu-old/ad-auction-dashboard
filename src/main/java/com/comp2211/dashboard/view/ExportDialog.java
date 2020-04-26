package com.comp2211.dashboard.view;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class ExportDialog {

    @FXML
    StackPane dialogExportStack;

    @FXML
    private JFXCheckBox ctrExportCheckBox;

    @FXML
    private JFXCheckBox convUniquesExportCheckBox;

    @FXML
    private JFXCheckBox totalMetricsExportCheckBox;

    @FXML
    private JFXCheckBox totalCostsExportCheckBox;

    @FXML
    private JFXCheckBox totalOverTimeExportCheckBox;

    @FXML
    private JFXCheckBox demographExportCheckBox;

    @FXML
    private JFXCheckBox averageCostExportCheckBox;

    private String absoluteExportPath;

    public void exportFilesAction(ActionEvent event){

        Stage stage = (Stage) dialogExportStack.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));
        File selectedDirectory = directoryChooser.showDialog(stage);

        absoluteExportPath = selectedDirectory.getAbsolutePath();
        System.out.println(absoluteExportPath);

        checkIfSelected();

        cancelExportDialogAction(event);
    }

    private void checkIfSelected() {

        if(ctrExportCheckBox.isSelected()){

        }if (convUniquesExportCheckBox.isSelected()){

        }if (totalMetricsExportCheckBox.isSelected()){

        }if (totalCostsExportCheckBox.isSelected()){

        }if (totalOverTimeExportCheckBox.isSelected()){

        }if (demographExportCheckBox.isSelected()){

        }if (averageCostExportCheckBox.isSelected()){

        }
    }

    public void cancelExportDialogAction(ActionEvent event) {
        PrimaryView.cancelExportDialogAction();
    }
}
