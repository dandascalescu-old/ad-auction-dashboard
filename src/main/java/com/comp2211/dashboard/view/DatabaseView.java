package com.comp2211.dashboard.view;

import java.io.IOException;
import com.comp2211.dashboard.viewmodel.DatabaseViewModel;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

public class DatabaseView implements FxmlView<DatabaseViewModel> {

  private JFXDialog dialog = null;
  private JFXDialogLayout dialogLayout = null;

  @FXML
  private StackPane databasePane;

  @FXML
  private JFXTreeTableView<CampaignDatabaseEntry> databaseTreeTable;

  @InjectViewModel
  private DatabaseViewModel viewModel;

  public void initialize() {
    JFXTreeTableColumn<CampaignDatabaseEntry, String> titleOfCampaignCol = new JFXTreeTableColumn<>("Title");
    titleOfCampaignCol.setPrefWidth(200);
    titleOfCampaignCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CampaignDatabaseEntry, String>, ObservableValue<String>>() {
      @Override
      public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CampaignDatabaseEntry, String> param) {
        return param.getValue().getValue().titleOfCampaign;
      }
    });

    JFXTreeTableColumn<CampaignDatabaseEntry, String> fileNameCol = new JFXTreeTableColumn<>("File Name");
    fileNameCol.setPrefWidth(619);
    fileNameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CampaignDatabaseEntry, String>, ObservableValue<String>>() {
      @Override
      public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CampaignDatabaseEntry, String> param) {
        return param.getValue().getValue().fileName;
      }
    });

    JFXTreeTableColumn<CampaignDatabaseEntry, String> progressCol = new JFXTreeTableColumn<>("Progress");
    progressCol.setPrefWidth(200);
    progressCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CampaignDatabaseEntry, String>, ObservableValue<String>>() {
      @Override
      public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CampaignDatabaseEntry, String> param) {
        return param.getValue().getValue().progress;
      }
    });

    ObservableList<CampaignDatabaseEntry> campaignDatabaseEntries = FXCollections.observableArrayList();
    campaignDatabaseEntries.add(new CampaignDatabaseEntry("Campaign 1", "click_log.csv, impression_log.csv, server_log.csv", "32%"));
    campaignDatabaseEntries.add(new CampaignDatabaseEntry("Campaign 2", "click_log.csv, impression_log.csv, server_log.csv", "42%"));

    final TreeItem<CampaignDatabaseEntry> root = new RecursiveTreeItem<CampaignDatabaseEntry>(campaignDatabaseEntries, RecursiveTreeObject::getChildren);
    databaseTreeTable.setRoot(root);
    databaseTreeTable.setShowRoot(false);
    databaseTreeTable.getColumns().add(titleOfCampaignCol);
    databaseTreeTable.getColumns().add(fileNameCol);
    databaseTreeTable.getColumns().add(progressCol);
  }

  public void importCampaignAction(ActionEvent actionEvent){
    if(dialog == null) {
      try {
        Parent parent = FXMLLoader.load(getClass().getResource("DatabaseDialog.fxml"));
        dialogLayout = new JFXDialogLayout();
        dialogLayout.setBody(parent);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    dialog = new JFXDialog(databasePane, dialogLayout, JFXDialog.DialogTransition.BOTTOM, false);
    dialog.setId("ImportDialog");
    dialog.show();
  }

  class CampaignDatabaseEntry extends RecursiveTreeObject<CampaignDatabaseEntry>{
    private StringProperty titleOfCampaign;
    private StringProperty fileName;
    private StringProperty progress;

    public CampaignDatabaseEntry(String titleOfCampaign, String fileName, String progress){
      this.titleOfCampaign = new SimpleStringProperty(titleOfCampaign);
      this.fileName = new SimpleStringProperty(fileName);
      this.progress = new SimpleStringProperty(progress);
    }
  }
}
