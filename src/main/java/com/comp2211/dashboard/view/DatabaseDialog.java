package com.comp2211.dashboard.view;


import com.comp2211.dashboard.viewmodel.DatabaseViewModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class DatabaseDialog {

    @FXML
    StackPane dialogDBStack;

    @FXML
    JFXDialog dialog;

    @FXML
    Text alertAddingText;

    @FXML
    JFXButton importImpressionButton;

    @FXML
    TextField campaignTitle;

    private String impressionFilePath = "", serverFilePath = "", clickFilePath = "";

    private String impressionFileName = "", serverFileName = "", clickFileName = "";

    @FXML
    void importImpressionAction() {

        Stage stage = (Stage) dialogDBStack.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Impressions Log");

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            impressionFilePath = file.getAbsolutePath();
            impressionFileName = file.getName();
            System.out.println("Absolute path (impression): " + impressionFilePath);
        }
    }

    @FXML
    void importServerAction() {

        Stage stage = (Stage) dialogDBStack.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Server Log");

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            serverFilePath = file.getAbsolutePath();
            serverFileName = file.getName();
            System.out.println("Absolute path (Server): " + serverFilePath);

        }
    }

    @FXML
    void importClickAction(){

        Stage stage = (Stage) dialogDBStack.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Click Log");

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            clickFilePath = file.getAbsolutePath();
            clickFileName = file.getName();
            System.out.println("Absolute path (Click): " + clickFilePath);
        }


    }


    public void createCampaignFromFiles(ActionEvent event){
        if (impressionFilePath.equals("") || serverFilePath.equals("") || clickFilePath.equals("") || campaignTitle.getText().equals("")){
            alertAddingText.setText("BEFORE SAVING ADD ALL FILES!");

        }else{

            DatabaseView.createCampaignFromFiles(impressionFilePath, serverFilePath, clickFilePath);
            DatabaseViewModel.addNewCampaign(campaignTitle.getText(), impressionFileName + " " + serverFileName + " " + clickFileName);

        }

    }

    public void cancelDialogAction(ActionEvent event) {
        DatabaseView.cancelDialogAction();
    }


}
