package com.comp2211.dashboard.view;

import com.comp2211.dashboard.util.Logger;
import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DatabaseDialog {

  @FXML
  StackPane dialogDBStack;

  public void importImpressionAction() {
    Stage stage = (Stage) dialogDBStack.getScene().getWindow();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Import Impressions Log");
    fileChooser.showOpenDialog(stage);
  }

  public void importServerAction() {
    Stage stage = (Stage) dialogDBStack.getScene().getWindow();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Import Server Log");
    fileChooser.showOpenDialog(stage);
  }

  public void importClickAction(){
    Stage stage = (Stage) dialogDBStack.getScene().getWindow();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Import Click Log");
    fileChooser.showOpenDialog(stage);
  }

  public void validateAndSave(ActionEvent event) {
    Logger.log("Save stuff");
    closeDialog();
  }
  
  public void cancelDialogAction(ActionEvent event) {
    closeDialog();
  }

  private void closeDialog() {
    JFXDialog dialog = (JFXDialog) dialogDBStack.getScene().lookup("#ImportDialog");
    dialog.close();
  }
}
