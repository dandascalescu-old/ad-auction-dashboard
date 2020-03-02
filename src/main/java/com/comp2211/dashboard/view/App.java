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
    campaign.cacheData(10);
    System.out.println(campaign.getTotalCost());
    FXMLLoader fxmlLoader = new FXMLLoader();
    Parent root = fxmlLoader.load(getClass().getResource("primary.fxml"));
    PrimaryController primaryController = (PrimaryController) fxmlLoader.getController();
    primaryController.setGraphValue(campaign.getDateAverages());

    // Parent root2 = FXMLLoader.load(getClass().getResource("testScene1.fxml"));
    Scene scene = new Scene(root);

    primaryStage.setScene(scene);
    // primaryStage.initStyle(StageStyle.DECORATED);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
