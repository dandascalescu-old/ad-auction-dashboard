package com.comp2211.dashboard.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DatabasePanel extends AnchorPane {

    public DatabasePanel(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("secondary.fxml"));

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}