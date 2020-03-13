package com.comp2211.dashboard.view;

import animatefx.animation.*;
import com.comp2211.dashboard.viewmodel.LoginViewModel;
import com.comp2211.dashboard.viewmodel.MainViewModel;
import com.comp2211.dashboard.viewmodel.PrimaryViewModel;
import com.jfoenix.controls.JFXButton;
import com.mysql.cj.x.protobuf.MysqlxCursor;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginView implements FxmlView<LoginViewModel>, Initializable {

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

    Stage appStage;


    public void verifyLogin(ActionEvent actionEvent) throws IOException, InterruptedException {

        new FadeOut(signinPane).play();

        AnimationFX newAnimation = new FadeOutLeft(welcomePane);
        newAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                appStage = (Stage)loginButton.getScene().getWindow();
                ViewTuple<MainView, MainViewModel> viewTuple1 = FluentViewLoader.fxmlView(MainView.class).load();

                Parent root2 = viewTuple1.getView();
                appStage.setScene(new Scene(root2));
                appStage.show();
            }
        });

        newAnimation.play();

        //TODO :: Not secure way of saving the password!
        String username = usernameLabel.getText();
        String password = passwordLabel.getText();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
