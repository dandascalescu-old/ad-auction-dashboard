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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

    Stage appStage;
    Parent root;

    @FXML
    JFXButton loginButton;

    @FXML
    Pane welcomePane, signinPane;

    @FXML
    Ellipse circleShape;

    @FXML
    Polygon triangleShape1, triangleShape2;

    @FXML
    Rectangle squareShape, rectangleShape;




    public void openPrimaryView(ActionEvent actionEvent) throws IOException, InterruptedException {

        /*appStage = (Stage)loginButton.getScene().getWindow();
        ViewTuple<PrimaryView, PrimaryViewModel> viewTuple1 = FluentViewLoader.fxmlView(PrimaryView.class).load();
        Parent root2 = viewTuple1.getView();
        appStage.setScene(new Scene(root2));
        appStage.show();*/


        new FadeOutLeft(welcomePane).play();
        new FadeOut(signinPane).play();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Path for the triangleShape1
        Path path = new Path();
        path.getElements().add(new MoveTo(100, 0));
        path.getElements().add(new CubicCurveTo(480, 0, 380, 220, 200, 820));
        path.getElements().add(new ClosePath());

        //Path for the squareShape
        Path squarePath = new Path();
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
        rectangePath.getElements().add(new VLineTo(1000));


        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(triangleShape1);
        pathTransition.setDuration(Duration.seconds(35));
        pathTransition.setPath(path);
        pathTransition.setCycleCount(PathTransition.INDEFINITE);
        pathTransition.play();

        PathTransition pathTransitionSquare = new PathTransition();
        pathTransitionSquare.setNode(squareShape);
        pathTransitionSquare.setDuration(Duration.seconds(25));
        pathTransitionSquare.setPath(squarePath);
        pathTransitionSquare.setCycleCount(PathTransition.INDEFINITE);
        pathTransitionSquare.play();

        PathTransition pathTriangle2Transition = new PathTransition();
        pathTriangle2Transition.setNode(triangleShape2);
        pathTriangle2Transition.setDuration(Duration.seconds(35));
        pathTriangle2Transition.setPath(triangle2Path);
        pathTriangle2Transition.setCycleCount(PathTransition.INDEFINITE);
        pathTriangle2Transition.play();

        PathTransition pathCircleTransition = new PathTransition();
        pathCircleTransition.setNode(circleShape);
        pathCircleTransition.setDuration(Duration.seconds(15));
        pathCircleTransition.setPath(circlePath);
        pathCircleTransition.setCycleCount(PathTransition.INDEFINITE);
        pathCircleTransition.play();

        PathTransition pathRectangleTransition = new PathTransition();
        pathRectangleTransition.setNode(rectangleShape);
        pathRectangleTransition.setDuration(Duration.seconds(25));
        pathRectangleTransition.setPath(rectangePath);
        pathRectangleTransition.setCycleCount(PathTransition.INDEFINITE);
        pathRectangleTransition.play();

    }
}
