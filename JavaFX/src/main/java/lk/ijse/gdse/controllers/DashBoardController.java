package lk.ijse.gdse.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse.dto.CustomerDTO;
import lk.ijse.gdse.model.CustomerModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class DashBoardController implements Initializable {
    @FXML
    public Pane dashBoardContext;
    @FXML
    public AnchorPane mainContext;
    @FXML
    public Label pageNameLbl;
    @FXML
    public Pane customerContext;
    @FXML
    public TextField customerNameText;
    @FXML
    public TextField customerEmailTextField;
    @FXML
    public TextField customerAddressTextField;
    @FXML
    public TextField customerAgeTextField;
    @FXML
    public TextField customerNicTextField;
    @FXML
    public TextField customerPhoneNumberTextField;
    @FXML
    public Pane contextSetterPane;
    @FXML
    public TextField customerSearchTextField;
    @FXML
    public Button customerSearchBtn;
    @FXML
    public Pane customerSearchContainer;
    public Pane customerSearchPane;
    public VBox customerSearchVbox;
    FileChooser fileChooser = new FileChooser();
    String visible;
    CustomerModel model = new CustomerModel();

    Pane searchingDataPane = new Pane();
    VBox vBox = new VBox();
    ScrollPane scrollPane = new ScrollPane();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        dashBoardContext.setVisible(true);


    }


    @FXML
    public void clickNavigationBtn(MouseEvent mouseEvent) throws IOException {

        HBox source = (HBox) mouseEvent.getSource();
        String accessibleText = source.getAccessibleText();
        dashBoardContext.setVisible(false);
        customerContext.setVisible(false);
        pageNameLbl.setText(accessibleText.toUpperCase());

        boolean contextChild = contextSetterPane.getChildren().isEmpty();
        if (!contextChild){
//            contextSetterPane.
//                    getChildren().
//                    forEach((node) -> {
//                        contextSetterPane.getChildren().removeAll(node);
            contextSetterPane.
                    getChildren().remove(0);
        }

        switch (accessibleText) {

            case "dashboard": {

            }

            case "customer": {
                URL resource = getClass().getResource("/views/CustomerView.fxml");
                AnchorPane load = FXMLLoader.load(resource);
                contextSetterPane.getChildren().add(load);
                break;
            }
            case "item": {
                URL resource = getClass().getResource("/views/ItemView.fxml");
                AnchorPane load = FXMLLoader.load(resource);
                contextSetterPane.getChildren().add(load);
                break;
            }
            case "orderDetails": {
                URL resource = getClass().getResource("/views/OrderDetailsVieew.fxml");
                AnchorPane load = FXMLLoader.load(resource);
                contextSetterPane.getChildren().add(load);
                break;
            }
            case "newOrder": {
                URL resource = getClass().getResource("/views/PlaceOrderView.fxml");
                AnchorPane load = FXMLLoader.load(resource);
                contextSetterPane.getChildren().add(load);
                break;
            }
        }

    }


    public void customerImageUploadBtn(ActionEvent actionEvent) {

        File file = new File("C:\\Users\\My Plus\\Downloads");
        Stage stage = new Stage();
        fileChooser.setInitialDirectory(file);
        fileChooser.showOpenDialog(stage);
    }

    public void customerUpdateBtn(ActionEvent actionEvent) {
        System.out.println();
    }

    public void customerDeleteBtn(ActionEvent actionEvent) {
    }

    public void customerSaveBtn(ActionEvent actionEvent) {
    }

    public void customerSearchBtnOnAction(ActionEvent actionEvent) {
//        Pane searchingDataPane = new Pane();
//        searchingDataPane.setBackground(new Background(new BackgroundFill(Color.BLUE,new CornerRadii(0),new Insets(0))));
//        searchingDataPane.maxWidth(customerSearchContainer.getWidth());
//        searchingDataPane.maxHeight(30);
//
//        customerSearchContainer.getChildren().add(searchingDataPane);
    }

    public void customerSearchOnAction(ActionEvent actionEvent) {

//        System.out.println("sd");
//
//        Pane searchingDataPane = new Pane();
//
//        customerSearchContainer.getChildren().add(searchingDataPane);
//        searchingDataPane.setBackground(new Background(new BackgroundFill(Color.BLUE,new CornerRadii(0),new Insets(0))));
//        searchingDataPane.maxWidth(customerSearchContainer.getWidth());
//        searchingDataPane.maxHeight(150);
//        searchingDataPane.setPrefWidth(customerSearchContainer.getPrefWidth());
//        searchingDataPane.setPrefHeight(customerSearchContainer.getPrefHeight());

    }

    public void customerSearchOnKeyPress(KeyEvent keyEvent) {

        CharSequence characters = customerSearchTextField.getCharacters();
        if (characters.length() >= 2) {
            customerSearchPane.setVisible(true);
            if (characters.length() == 2) {
                try {
                    Map<String, String> stringStringMap = model.searchCustomers(customerSearchTextField.getText());
                    ObservableList<Node> children1 = customerSearchVbox.getChildren();
                    List<Node> childrentList = new ArrayList<>(children1);
                    boolean b = customerSearchVbox.getChildren().removeAll(childrentList);
                    System.out.println(b);


                    stringStringMap.forEach((name, number) -> {
                        HBox hBox = new HBox();
                        Label customerNameLbl = new Label(name);
                        Label customerPhoneNumberLbl = new Label(number);
                        ObservableList<Node> children = hBox.getChildren();
                        children.add(customerNameLbl);
                        children.add(customerPhoneNumberLbl);
                        customerSearchVbox.getChildren().add(new Separator());
                        customerSearchVbox.getChildren().add(hBox);
                        hBox.setOnMouseClicked((event) -> {
                            Label customerName = (Label) hBox.getChildren().get(0);
                            Label customerPhoneNumber = (Label) hBox.getChildren().get(1);
                            System.out.println(customerName.getText());
                            System.out.println(customerPhoneNumber.getText());
                            customerSearchTextField.setText(customerName.getText() + " | " + customerPhoneNumber.getText());


                            try {
                                CustomerDTO searchCustomer = model.getSearchCustomer(Integer.parseInt(customerPhoneNumber.getText()));
                                System.out.println(searchCustomer);
                            } catch (Exception e) {
                                Alert a = new Alert(Alert.AlertType.ERROR, "try again");
                                a.show();
                                throw new RuntimeException(e);
                            }
                        });
                    });

                } catch (SQLException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "try again");
                    a.show();
                    throw new RuntimeException(e);
                }
            }
        } else {
            customerSearchPane.setVisible(false);
        }
    }
}
