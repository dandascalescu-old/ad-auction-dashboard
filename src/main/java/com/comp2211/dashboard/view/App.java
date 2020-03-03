package com.comp2211.dashboard.view;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.io.DatabaseManager;
import com.comp2211.dashboard.viewmodel.PrimaryController;
import com.comp2211.dashboard.viewmodel.PrimaryViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import eu.lestard.doc.VisibleForTesting;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** JavaFX App */
public class App extends Application {

  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException {
//    DatabaseManager.init();
//    Campaign campaign = new Campaign("1");
//    campaign.cacheData(0);
//    System.out.println(campaign.getTotalCost());
//
//    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PrimaryView.fxml"));
//    Parent root = fxmlLoader.load();
//    PrimaryController primaryController = (PrimaryController) fxmlLoader.getController();
//
//    System.out.println(primaryController);
//    System.out.println(campaign);
//
//    primaryController.setCampaign(campaign);
//
//    //primaryController.setGraphValue(campaign.getDateAverages());
//
//    // Parent root2 = FXMLLoader.load(getClass().getResource("testScene1.fxml"));
//    Scene scene = new Scene(root);
//
//    primaryStage.setScene(scene);
//    // primaryStage.initStyle(StageStyle.DECORATED);
//
//    primaryStage.setScene(scene);
//    primaryStage.show();
//
//    stage.setTitle("Hello World Application");

    ViewTuple<PrimaryView, PrimaryViewModel> viewTuple = FluentViewLoader.fxmlView(PrimaryView.class).load();

    Parent root = viewTuple.getView();
    stage.setScene(new Scene(root));
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }



}
