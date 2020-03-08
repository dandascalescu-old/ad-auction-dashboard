package com.comp2211.dashboard.view;

import com.comp2211.dashboard.viewmodel.LoginViewModel;
import com.comp2211.dashboard.viewmodel.PrimaryViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** JavaFX App */
public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle("Ad Analytics Dashboard");

    //ViewTuple<LoginView, LoginViewModel> viewTuple = FluentViewLoader.fxmlView(LoginView.class).load();
    Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
    stage.setScene(new Scene(root));
    stage.show();
  }

  public static void main() {
    launch();
  }
}
