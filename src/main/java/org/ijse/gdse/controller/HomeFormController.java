package org.ijse.gdse.controller;

import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeFormController implements Initializable {
    public HBox mangeCustomerHBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mangeCustomerHBox.isPressed();
    }
}
