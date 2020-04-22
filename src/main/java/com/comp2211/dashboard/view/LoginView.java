package com.comp2211.dashboard.view;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.GUIStarter;
import com.comp2211.dashboard.util.Logger;
import com.comp2211.dashboard.util.Security;
import com.comp2211.dashboard.viewmodel.LoginViewModel;
import com.comp2211.dashboard.viewmodel.MainViewModel;
import com.jfoenix.controls.JFXButton;
import animatefx.animation.AnimationFX;
import animatefx.animation.FadeOut;
import animatefx.animation.FadeOutLeft;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginView implements FxmlView<LoginViewModel> {

  @FXML
  AnchorPane loginPane;

  @FXML
  Text welcomeText;

  @FXML
  TextField usernameLabel;

  @FXML
  PasswordField passwordLabel;

  @FXML
  JFXButton loginButton;

  @FXML
  Pane welcomePane, signinPane;
  
  @InjectViewModel
  private LoginViewModel viewModel;

  public void verifyLogin(ActionEvent actionEvent) {
    String username = usernameLabel.getText().trim();
    String password = passwordLabel.getText().trim();
    
    if(!Security.validateText(username)) {
      Logger.log("Username can only alphanumeric characters, try again.");
      return;
    }
    
    if(!GUIStarter.getDatabaseManager().attemptUserLogin(username, password)) {
      Logger.log("Credentials not recognized, try again.");
      return;
    }
    // TODO get campaigns from UserSession
    Campaign c = new Campaign("Demo Campaign", GUIStarter.getDatabaseManager());
    new Thread() {
      public void run() {
        c.cacheData();
      }
    }.start();

    new FadeOut(signinPane).play();
    AnimationFX newAnimation = new FadeOutLeft(welcomePane);
    newAnimation.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        ViewTuple<MainView, MainViewModel> viewTuple = FluentViewLoader.fxmlView(MainView.class).load();
        Stage appStage = (Stage) loginButton.getScene().getWindow();
        appStage.setScene(new Scene(viewTuple.getView()));
      }
    });

    newAnimation.play();
  }
}
