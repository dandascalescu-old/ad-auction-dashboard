package com.comp2211.dashboard.view;

import com.comp2211.dashboard.viewmodel.PrimaryViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** JavaFX App */
public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle("Ad Analytics Dashboard");

    ViewTuple<PrimaryView, PrimaryViewModel> viewTuple = FluentViewLoader.fxmlView(PrimaryView.class).load();

    Parent root = viewTuple.getView();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public static void main() {
    launch();
  }
}
