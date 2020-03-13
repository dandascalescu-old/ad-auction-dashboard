package com.comp2211.dashboard.view;

import com.comp2211.dashboard.io.Database;
import com.comp2211.dashboard.viewmodel.DatabaseViewModel;
import com.comp2211.dashboard.viewmodel.MainViewModel;
import com.comp2211.dashboard.viewmodel.PrimaryViewModel;
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

    @FXML
    private BorderPane mainPane;

    public void openDashboardPane(ActionEvent actionEvent) throws IOException {

        //ViewTuple<PrimaryView, PrimaryViewModel> viewTuple1 = FluentViewLoader.fxmlView(PrimaryViewView.class).load();

        ViewTuple<PrimaryView, PrimaryViewModel> viewTuple1 = FluentViewLoader.fxmlView(PrimaryView.class).load();
        mainPane.setCenter(viewTuple1.getView());
    }

    public void openDatabasePane(ActionEvent actionEvent) throws IOException {



        Parent root = FXMLLoader.load(getClass().getResource("DatabaseView.fxml"));
        mainPane.setCenter(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ViewTuple<PrimaryView, PrimaryViewModel> viewTuple1 = FluentViewLoader.fxmlView(PrimaryView.class).load();
        mainPane.setCenter(viewTuple1.getView());

    }

}
