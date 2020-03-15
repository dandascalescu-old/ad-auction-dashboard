package com.comp2211.dashboard.view;

import com.comp2211.dashboard.viewmodel.DatabaseViewModel;
import com.comp2211.dashboard.viewmodel.MainViewModel;
import com.comp2211.dashboard.viewmodel.PrimaryViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class MainView implements FxmlView<MainViewModel> {

  private ViewTuple<PrimaryView, PrimaryViewModel> primaryViewTuple;
  private ViewTuple<DatabaseView, DatabaseViewModel> databaseViewTuple;
  
  @FXML
  private BorderPane mainPane;
  
  @InjectViewModel
  private MainViewModel viewModel;

  public void openDashboardPane(ActionEvent actionEvent) {
    mainPane.setCenter(primaryViewTuple.getView());
  }

  public void openDatabasePane(ActionEvent actionEvent) {
    mainPane.setCenter(databaseViewTuple.getView());
  }

  public void initialize() {    
    primaryViewTuple = FluentViewLoader.fxmlView(PrimaryView.class).load();
    databaseViewTuple = FluentViewLoader.fxmlView(DatabaseView.class).load();
    mainPane.setCenter(primaryViewTuple.getView());
  }
}
