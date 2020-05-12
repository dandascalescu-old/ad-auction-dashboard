package com.comp2211.dashboard.view;


import com.comp2211.dashboard.viewmodel.*;

import com.jfoenix.controls.JFXButton;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainView implements Initializable, FxmlView<MainViewModel> {

    ViewTuple<PrimaryView, PrimaryViewModel> primaryTutple;
    ViewTuple<DatabaseView, DatabaseViewModel> databaseTuple;
    ViewTuple<CompareView, CompareLeftViewModel> compareTuple;
    ViewTuple<SettingsView, SettingsViewModel> settingsTuple;

    @FXML
    Rectangle recDashboard, recDatabase, recCompare, recSettings;

    @FXML
    private BorderPane mainPane;

    @FXML
    JFXButton dashboardButton;

    public void openDashboardPane(ActionEvent actionEvent) throws IOException {
        recDashboard.setVisible(true);
        recDatabase.setVisible(false);
        recCompare.setVisible(false);
        recSettings.setVisible(false);

        //ViewTuple<PrimaryView, PrimaryViewModel> viewTuple1 = FluentViewLoader.fxmlView(PrimaryViewView.class).load();

        if (primaryTutple == null)
            primaryTutple = FluentViewLoader.fxmlView(PrimaryView.class).load();
        mainPane.setCenter(primaryTutple.getView());
    }

    public void openDatabasePane(ActionEvent actionEvent) throws IOException {
        recDashboard.setVisible(false);
        recDatabase.setVisible(true);
        recCompare.setVisible(false);
        recSettings.setVisible(false);

        databaseTuple = FluentViewLoader.fxmlView(DatabaseView.class).load();
        mainPane.setCenter(databaseTuple.getView());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //dashboardButton.setRipplerFill(new Paint(Color.TRANSPARENT) );

        recDashboard.setVisible(true);
        recDatabase.setVisible(false);
        recCompare.setVisible(false);
        recSettings.setVisible(false);

        primaryTutple = FluentViewLoader.fxmlView(PrimaryView.class).load();
        mainPane.setCenter(primaryTutple.getView());

    }

    public void openFilterDialog(){

    }

    public void openComparePane(ActionEvent event) throws IOException {
        recDashboard.setVisible(false);
        recDatabase.setVisible(false);
        recCompare.setVisible(true);
        recSettings.setVisible(false);

        compareTuple = FluentViewLoader.fxmlView(CompareView.class).load();
        mainPane.setCenter(compareTuple.getView());
    }

    public void openSettingsPane(ActionEvent event) throws IOException{
        recDashboard.setVisible(false);
        recDatabase.setVisible(false);
        recCompare.setVisible(false);
        recSettings.setVisible(true);


        settingsTuple = FluentViewLoader.fxmlView(SettingsView.class).load();
        mainPane.setCenter(settingsTuple.getView());
    }
}
