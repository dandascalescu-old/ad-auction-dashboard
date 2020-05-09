package com.comp2211.dashboard.view;

import com.comp2211.dashboard.GUIStarter;
import com.comp2211.dashboard.io.DataImporter;
import com.comp2211.dashboard.viewmodel.DatabaseViewModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import de.saxsys.mvvmfx.MvvmFX;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class DatabaseDialog {

    @FXML
    public TextField campaignTitle;

    @FXML
    StackPane dialogDBStack;

    @FXML
    JFXDialog dialog;

    @FXML
    Text alertAddingText;

    @FXML
    JFXButton importImpressionButton;

    private String impressionFilePath = "", serverFilePath = "", clickFilePath = "";

    private String impressionFileName = "", serverFileName = "", clickFileName = "";

    private DataImporter dataImporter = new DataImporter(GUIStarter.getDatabaseManager());

    private String lastDirectory = null;

    @FXML
    void importImpressionAction() {
        chooseFile("Impression Log");
    }

    @FXML
    void importServerAction() {
        chooseFile("Server Log");
    }

    @FXML
    void importClickAction(){
        chooseFile("Click Log");
    }

    private void chooseFile(String logType){
        Stage stage = (Stage) dialogDBStack.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import " + logType);

        if(lastDirectory!=null){
            fileChooser.setInitialDirectory(new File(lastDirectory));
        }

        File file = fileChooser.showOpenDialog(stage);

        try{
            switch(logType){
                case "Impression Log":
                    impressionFilePath = file.getAbsolutePath();
                    impressionFileName = file.getName();
                    break;
                case "Server Log":
                    serverFilePath = file.getAbsolutePath();
                    serverFileName = file.getName();
                    break;
                case "Click Log":
                    clickFilePath = file.getAbsolutePath();
                    serverFileName = file.getName();
                    break;
            }
        }catch(Exception ignored){}

        if (file != null) {
            lastDirectory = file.getParent();
            System.out.println("Absolute path (" + logType + "): " + file.getAbsolutePath());
        }
    }


    public void createCampaignFromFiles(ActionEvent event){
        if (impressionFilePath.equals("") || serverFilePath.equals("") || clickFilePath.equals("") || campaignTitle.getText().equals("")){
            alertAddingText.setText("BEFORE SAVING FILL ALL FIELDS!");
        }else{
            Thread t = new Thread(() -> {
                try {
                    dataImporter.startImport(campaignTitle.getText(), new File(impressionFilePath), new File(clickFilePath), new File(serverFilePath));
                    Platform.runLater(() -> {
                        DatabaseViewModel.addNewCampaign(campaignTitle.getText(), impressionFileName + " " + serverFileName + " " + clickFileName);
                        MvvmFX.getNotificationCenter().publish("Imported");
                        DatabaseViewModel.changeProgressToCompleted(campaignTitle.getText());
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            t.start();
            DatabaseView.cancelDialogAction();
        }

    }

    public void cancelDialogAction(ActionEvent event) {
        DatabaseView.cancelDialogAction();
    }

}
