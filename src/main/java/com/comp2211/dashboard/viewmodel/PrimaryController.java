package com.comp2211.dashboard.viewmodel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import com.comp2211.dashboard.view.DatabasePanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class PrimaryController implements Initializable {

    @FXML
    BorderPane mainPane;


    @FXML
    private void switchToSecondary() throws IOException {
        //App.setRoot("secondary");
    }

    public void openDatabasePane(ActionEvent event) {

        DatabasePanel databasePanelLayer = new DatabasePanel();
        mainPane.setCenter(databasePanelLayer);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //TreeTableColumn myColumn = new TreeTableColumn("My Column");
        //databaseTreeTable.getColumns().addAll(myColumn);

        System.out.println("Hellow");


    }
}
