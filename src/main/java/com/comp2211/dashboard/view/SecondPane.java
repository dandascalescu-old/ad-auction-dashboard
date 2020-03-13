package com.comp2211.dashboard.view;

import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SecondPane implements Initializable {

    @FXML
    Pane secondPane;

    @FXML
    Button frontButton;

    @FXML
    StackPane stackPane2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new FadeIn(secondPane).play();
    }

    public void showBack() throws IOException {
        AnchorPane stack = FXMLLoader.load(getClass().getResource("PrimaryView.fxml"));
        stackPane2.getChildren().setAll(stack);





    }
}
