package com.comp2211.dashboard.view;

import com.comp2211.dashboard.io.Database;
import com.comp2211.dashboard.viewmodel.*;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainView implements Initializable, FxmlView<MainViewModel> {

    ViewTuple<PrimaryView, PrimaryViewModel> primaryTutple;
    ViewTuple<DatabaseView, DatabaseViewModel> databaseTuple;
    ViewTuple<CompareView, CompareLeftViewModel> compareTuple;

    @FXML
    private BorderPane mainPane;

    public void openDashboardPane(ActionEvent actionEvent) throws IOException {

        //ViewTuple<PrimaryView, PrimaryViewModel> viewTuple1 = FluentViewLoader.fxmlView(PrimaryViewView.class).load();

        if (primaryTutple == null)
            primaryTutple = FluentViewLoader.fxmlView(PrimaryView.class).load();
        mainPane.setCenter(primaryTutple.getView());
    }

    public void openDatabasePane(ActionEvent actionEvent) throws IOException {
        databaseTuple = FluentViewLoader.fxmlView(DatabaseView.class).load();
        mainPane.setCenter(databaseTuple.getView());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        primaryTutple = FluentViewLoader.fxmlView(PrimaryView.class).load();
        mainPane.setCenter(primaryTutple.getView());

    }

    public void openFilterDialog(){

    }

    public void openComparePane(ActionEvent event) throws IOException {
        compareTuple = FluentViewLoader.fxmlView(CompareView.class).load();
        mainPane.setCenter(compareTuple.getView());
    }
}
