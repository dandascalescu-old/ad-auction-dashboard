package com.comp2211.dashboard.view;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.io.DatabaseManager;
import com.comp2211.dashboard.viewmodel.PrimaryController;
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
  public void start(Stage primaryStage) throws IOException {
    DatabaseManager.init();
    Campaign campaign = new Campaign("1");
    campaign.cacheData(0);
    System.out.println("Click Data loaded: " + campaign.isClickDataLoaded() + ", " + campaign.getClickDataList().size());
    System.out.println("Impression Data loaded: " + campaign.isImpressionDataLoaded() + ", " + campaign.getImpressionDataList().size());
    System.out.println("Server Data loaded: " + campaign.isServerDataLoaded() + ", " + campaign.getServerDataList().size());
    System.out.println(campaign.getTotalCost());
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("primary.fxml"));
    PrimaryController primaryController = (PrimaryController) fxmlLoader.getController();
    primaryController.setGraphValue(campaign.getDateAverages());

    // Parent root2 = FXMLLoader.load(getClass().getResource("testScene1.fxml"));
    Scene scene = new Scene(fxmlLoader.load());

    primaryStage.setScene(scene);
    // primaryStage.initStyle(StageStyle.DECORATED);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
