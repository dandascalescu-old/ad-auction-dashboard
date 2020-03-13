package com.comp2211.dashboard.view;

import com.comp2211.dashboard.viewmodel.DatabaseViewModel;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.saxsys.mvvmfx.FxmlView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DatabaseView implements FxmlView<DatabaseViewModel>, Initializable {

    @FXML
    StackPane databasePane;





    @FXML
    JFXTreeTableView<CampaignDatabaseEntry> databaseTreeTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        campaignDatabaseEntries.add(new CampaignDatabaseEntry("Campaign 1", "clickLog.csv, impression.csv, server.log", "32%"));
        campaignDatabaseEntries.add(new CampaignDatabaseEntry("Campaign 2", "clickLog.csv, impression.csv, server.log", "42%"));

        final TreeItem<CampaignDatabaseEntry> root = new RecursiveTreeItem<CampaignDatabaseEntry>(campaignDatabaseEntries, RecursiveTreeObject::getChildren);
        databaseTreeTable.setRoot(root);
        databaseTreeTable.setShowRoot(false);
        databaseTreeTable.getColumns().setAll(titleOfCampaignCol, fileNameCol, progressCol);
    }

    public void importCampaignAction(ActionEvent event){



    }

    public void importCampaignAction(javafx.event.ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("DatabaseDialog.fxml"));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setBody(parent);
        JFXDialog dialog = new JFXDialog(databasePane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);


        dialog.show();

    }

    private Object getContent() {
        return null;
    }



    @FXML
    void cancelDialogAction() {
        //dialog.close();
    }

    class CampaignDatabaseEntry extends RecursiveTreeObject<CampaignDatabaseEntry>{

        StringProperty titleOfCampaign;
        StringProperty fileName;
        StringProperty progress;

        public CampaignDatabaseEntry(String titleOfCampaign, String fileName, String progress){

            this.titleOfCampaign = new SimpleStringProperty(titleOfCampaign);
            this.fileName = new SimpleStringProperty(fileName);
            this.progress = new SimpleStringProperty(progress);
        }
    }

}


