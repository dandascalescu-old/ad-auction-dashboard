package com.comp2211.dashboard.viewmodel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class SecondaryController implements Initializable {

    @FXML
    JFXTreeTableView databaseTreeTable;

    @FXML
    private void openDashboardPane() throws IOException {
        //App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        JFXTreeTableColumn<Person, String> nameColumn = new JFXTreeTableColumn<>("Name");
        JFXTreeTableColumn<Person, String> ageColumn = new JFXTreeTableColumn<>("Age");
        JFXTreeTableColumn<Person, String> addressColumn = new JFXTreeTableColumn<>("Address");

        nameColumn.setPrefWidth(150);
        addressColumn.setPrefWidth(150);
        ageColumn.setPrefWidth(150);

        nameColumn.setCellValueFactory(param -> param.getValue().getValue().personName);

        ageColumn.setCellValueFactory(param -> param.getValue().getValue().age);

        addressColumn.setCellValueFactory(param -> param.getValue().getValue().address);


        ObservableList<Person> peopleList = FXCollections.observableArrayList();
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));
        peopleList.add(new Person("Chuck", "22", "Portswood road 1"));


        final TreeItem<Person> root = new RecursiveTreeItem<Person>(peopleList, RecursiveTreeObject::getChildren);
        databaseTreeTable.setRoot(root);
        databaseTreeTable.setShowRoot(false);


        databaseTreeTable.getColumns().addAll(nameColumn, ageColumn, addressColumn);

    }



    class Person extends RecursiveTreeObject<Person> {
        StringProperty personName;
        StringProperty age;
        StringProperty address;

        public Person(String name,String age,String address)
        {
            this.personName = new SimpleStringProperty(name);
            this.age  = new SimpleStringProperty(age);
            this.address = new SimpleStringProperty(address);

        }


    }
}