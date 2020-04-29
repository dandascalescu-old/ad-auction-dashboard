package com.comp2211.dashboard.view;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.GUIStarter;
import com.comp2211.dashboard.viewmodel.LoginViewModel;
import com.comp2211.dashboard.viewmodel.MainViewModel;
import com.comp2211.dashboard.viewmodel.PrimaryFilterDialogModel.Filter;
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

  public void initialize() {
    usernameLabel.textProperty().bindBidirectional(viewModel.usernameStringProperty());
    passwordLabel.textProperty().bindBidirectional(viewModel.passwordStringProperty());

    viewModel.subscribe(LoginViewModel.SHOW_AUTHENTICATED_VIEW, (key, payload) -> {
      // TODO: get campaigns from UserSession
      Campaign campaign1 = new Campaign("Demo Campaign 1", GUIStarter.getDatabaseManager());
      Campaign campaign2 = new Campaign("Demo Campaign 2", GUIStarter.getDatabaseManager());
      campaign1.cacheData(new Filter());
      campaign2.cacheData(new Filter());

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
    });
  }

  public void verifyLogin(ActionEvent actionEvent) {
    viewModel.getLoginCommand().execute();
  }
}
