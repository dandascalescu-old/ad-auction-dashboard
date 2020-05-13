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

  // Storing the tuple to make sure garbage collection doesn't break PrimaryViewModel bindings
  //ViewTuple<PrimaryView, PrimaryViewModel> viewTuple;

  ViewTuple<LoginView, LoginViewModel> viewTuple;

  public static final String DARK_MODE = "DARK_MODE";
  public static final String LIGHT_MODE = "LIGHT_MODE";
  public static final String styleLight = LoginView.class.getResource("/com/comp2211/dashboard/css/styleLight.css").toExternalForm();
  public static final  String styleDark = LoginView.class.getResource("/com/comp2211/dashboard/css/styleDark.css").toExternalForm();

  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle("Ad Analytics Dashboard");
    viewTuple = FluentViewLoader.fxmlView(LoginView.class).load();
    Parent root = viewTuple.getView();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public static void main() {
    launch();
  }

}
