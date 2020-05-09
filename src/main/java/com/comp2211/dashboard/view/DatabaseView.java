package com.comp2211.dashboard.view;

import com.comp2211.dashboard.viewmodel.CampaignEntry;
import com.comp2211.dashboard.viewmodel.DatabaseViewModel;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DatabaseView implements FxmlView<DatabaseViewModel>, Initializable {

    @FXML
    StackPane databasePane;

    static JFXDialog dialog;

    @FXML
    JFXTreeTableView<CampaignEntry> databaseTreeTable;

    @InjectViewModel
    private DatabaseViewModel viewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setTableStructure();

    }

    private void setTableStructure() {

        JFXTreeTableColumn<CampaignEntry, String> titleOfCampaignCol = new JFXTreeTableColumn<>("Title");
        titleOfCampaignCol.setPrefWidth(200);
        titleOfCampaignCol.setCellValueFactory(param -> param.getValue().getValue().titleOfCampaignProperty());

        JFXTreeTableColumn<CampaignEntry, String> fileNameCol = new JFXTreeTableColumn<>("File Name");
        fileNameCol.setPrefWidth(619);
        fileNameCol.setCellValueFactory(param -> param.getValue().getValue().fileNameProperty());

        JFXTreeTableColumn<CampaignEntry, String> progressCol = new JFXTreeTableColumn<>("Progress");
        progressCol.setPrefWidth(200);
        progressCol.setCellValueFactory(param -> param.getValue().getValue().progressProperty());

        final TreeItem<CampaignEntry> root = new RecursiveTreeItem<CampaignEntry>(viewModel.getCampaignData(), RecursiveTreeObject::getChildren);
        databaseTreeTable.setRoot(root);
        databaseTreeTable.setShowRoot(false);
        databaseTreeTable.getColumns().setAll(titleOfCampaignCol, fileNameCol, progressCol);

    }

    public void importCampaignAction(javafx.event.ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("DatabaseDialog.fxml"));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setBody(parent);
        dialog = new JFXDialog(databasePane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
        dialog.show();

    }

    private Object getContent() {
        return null;
    }

    static void cancelDialogAction() {
        dialog.close();
    }

    public static void createCampaignFromFiles(String impressionFilePath, String serverFilePath, String clickFilePath) {
        cancelDialogAction();

    }




}


