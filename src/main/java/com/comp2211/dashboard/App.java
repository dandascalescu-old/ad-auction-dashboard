package com.comp2211.dashboard;

import com.comp2211.dashboard.view.HelloWorldView;
import com.comp2211.dashboard.viewmodel.HelloWorldViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import de.saxsys.mvvmfx.ViewTuple;


public class App extends Application {

  public static void main(String... args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle("Hello World Application");

    final ViewTuple<HelloWorldView, HelloWorldViewModel> viewTuple = FluentViewLoader.fxmlView(HelloWorldView.class).load();

    final Parent root = viewTuple.getView();
    stage.setScene(new Scene(root));
    stage.show();
  }

}