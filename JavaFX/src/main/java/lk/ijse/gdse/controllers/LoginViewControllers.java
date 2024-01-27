package lk.ijse.gdse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class LoginViewControllers {
    @FXML
    public TextField loginEmailTextField;
    @FXML
    public PasswordField loginPasswordTextField;
    @FXML
    public Label loginErrorLabel;
    @FXML
    public AnchorPane loginAnchorPane;

    @FXML
    public void loginBtnOnAction(ActionEvent actionEvent) throws IOException {
        //checking the user email and password
        String email = loginEmailTextField.getText();
        String password = loginPasswordTextField.getText();

        if (email.equals("") || password.equals("")) {
            //set label null password is true
            loginErrorLabel.setText(null);
            //navigate to the next view
            Stage stage = (Stage) loginAnchorPane.getScene().getWindow();
            stage.close();

            //loading the dashboard
            Parent load = FXMLLoader.load(getClass().getResource("/views/DashBoardView.fxml"));
            stage.setScene(new Scene(load));
            stage.show();


        } else {
            //show error message on the login page
            loginErrorLabel.setText("Please try again");
        }

    }
}
