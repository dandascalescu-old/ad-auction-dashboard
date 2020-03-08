package com.comp2211.dashboard.view;

import animatefx.animation.*;
import com.comp2211.dashboard.viewmodel.LoginViewModel;
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

public class LoginView implements Initializable {

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
                ViewTuple<PrimaryView, PrimaryViewModel> viewTuple1 = FluentViewLoader.fxmlView(PrimaryView.class).load();
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



        //Path for the triangleShape1
        /*Path path = new Path();
        path.getElements().add(new MoveTo(100, 0));
        path.getElements().add(new CubicCurveTo(480, 0, 380, 220, 200, 820));
        path.getElements().add(new ClosePath());
        path.setCache(true);
        path.setCacheHint(CacheHint.SPEED);*/

        //Path for the squareShape
        /*Path squarePath = new Path();
        squarePath.getElements().add(new MoveTo(0, 0));
        squarePath.getElements().add(new LineTo(-620, -450));
        squarePath.getElements().add(new ClosePath());

        //Path for the triangleShape2
        Path triangle2Path = new Path();
        triangle2Path.getElements().add(new MoveTo(0,0));
        triangle2Path.getElements().add(new LineTo(-500, -300));
        triangle2Path.getElements().add(new ClosePath());

        //Path for the circleShape
        Path circlePath = new Path();
        circlePath.getElements().add(new MoveTo(0, 0));
        circlePath.getElements().add(new HLineTo(200));
        circlePath.getElements().add(new ClosePath());

        Path rectangePath = new Path();
        rectangePath.getElements().add(new MoveTo(0,0));
        rectangePath.getElements().add(new VLineTo(1000));*/

        /*triangleShape1.setCache(true);
        triangleShape1.setCacheHint(CacheHint.SPEED);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(triangleShape1);
        pathTransition.setDuration(Duration.seconds(35));
        pathTransition.setPath(path);
        pathTransition.setCycleCount(PathTransition.INDEFINITE);
        triangleShape1.setCache(true);
        triangleShape1.setCacheHint(CacheHint.QUALITY);
        triangleShape1.setCacheHint(CacheHint.SPEED);
        pathTransition.play();*/

        /*squareShape.setCache(true);
        squareShape.setCacheHint(CacheHint.QUALITY);
        squareShape.setCacheHint(CacheHint.SPEED);
        PathTransition pathTransitionSquare = new PathTransition();
        pathTransitionSquare.setNode(squareShape);
        pathTransitionSquare.setDuration(Duration.seconds(25));
        pathTransitionSquare.setPath(squarePath);
        pathTransitionSquare.setCycleCount(PathTransition.INDEFINITE);
        pathTransitionSquare.play();

        triangleShape2.setCache(true);
        triangleShape2.setCacheHint(CacheHint.QUALITY);
        triangleShape2.setCacheHint(CacheHint.SPEED);
        PathTransition pathTriangle2Transition = new PathTransition();
        pathTriangle2Transition.setNode(triangleShape2);
        pathTriangle2Transition.setDuration(Duration.seconds(35));
        pathTriangle2Transition.setPath(triangle2Path);
        pathTriangle2Transition.setCycleCount(PathTransition.INDEFINITE);
        pathTriangle2Transition.play();

        circleShape.setCache(true);
        circleShape.setCacheHint(CacheHint.QUALITY);
        circleShape.setCacheHint(CacheHint.SPEED);
        PathTransition pathCircleTransition = new PathTransition();
        pathCircleTransition.setNode(circleShape);
        pathCircleTransition.setDuration(Duration.seconds(15));
        pathCircleTransition.setPath(circlePath);
        pathCircleTransition.setCycleCount(PathTransition.INDEFINITE);
        pathCircleTransition.play();

        rectangleShape.setCache(true);
        rectangleShape.setCacheHint(CacheHint.QUALITY);
        rectangleShape.setCacheHint(CacheHint.SPEED);
        PathTransition pathRectangleTransition = new PathTransition();
        pathRectangleTransition.setNode(rectangleShape);
        pathRectangleTransition.setDuration(Duration.seconds(25));
        pathRectangleTransition.setPath(rectangePath);
        pathRectangleTransition.setCycleCount(PathTransition.INDEFINITE);
        pathRectangleTransition.play();*/

    }
}
