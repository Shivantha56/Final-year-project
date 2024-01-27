package lk.ijse.gdse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        Parent load = FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
        stage.setScene(new Scene(load));
        stage.centerOnScreen();
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
