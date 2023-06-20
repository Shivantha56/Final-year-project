package org.ijse.gdse.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController  {
    public ImageView closeBtn;





    private void closeLoginForm(){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();

    }
    public void closeBtnMouseClicked(MouseEvent mouseEvent) {

        closeLoginForm();
    }

    public void loginBtnOnAction(ActionEvent actionEvent) {
        closeLoginForm();
        Stage primaryStage = new Stage();
        try {
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/HomeForm.fxml"))));
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.setTitle("log in form");
            primaryStage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Cannot load UI. contact developer.").show();
        }
    }
}
