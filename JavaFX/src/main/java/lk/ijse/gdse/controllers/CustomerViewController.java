package lk.ijse.gdse.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.gdse.dto.CustomerDTO;
import lk.ijse.gdse.model.CustomerModel;
import lk.ijse.gdse.dto.tableModel.CustomerTable;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class CustomerViewController implements Initializable {
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
    public TableView<CustomerTable> customerTable;
    public TableColumn<CustomerTable, String> customerTableName;
    public TableColumn<CustomerTable, String> customerTableEmail;
    public TableColumn<CustomerTable, String> customerTableAge;
    public TableColumn<CustomerTable, String> customerTableAddress;
    public TableColumn<CustomerTable, String> customerTablePhoneNumber;
    public TableColumn<CustomerTable, String> customerTableNic;
    final FileChooser fileChooser = new FileChooser();
    public ImageView customerImageContainer;
    @FXML
    public Pane customerSearchContainer;
    public Pane customerSearchPane;
    public VBox customerSearchVbox;
    public TextField customerSearchTextField;
    public Pane customerContext;
    public Pane clearSearchFielContainer;
    Desktop desktop = Desktop.getDesktop();
    FileInputStream inputStream;
    CustomerModel customerModel = new CustomerModel();
    File file;
    ObservableList<CustomerTable> customerList = FXCollections.observableArrayList();

    String customerName;
    String customerAddress;
    int customerAge;
    int customerPhoneNumber;
    String customerEmail;
    String customerNic;
    String searchedName;
    String searchedPhoneNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setValuesToTable();
    }

    public void setValuesToTable(){
        ObservableList<CustomerTable> items = customerTable.getItems();
        customerTable.getItems().removeAll(items);
        try {
            List<CustomerDTO> lastAddedCustomerDetails = customerModel.getLastAddedCustomerDetails();
            lastAddedCustomerDetails.forEach((data) -> {
                customerList.add(new CustomerTable(
                        data.getCustomerName(),
                        data.getCustomerEmail(),
                        data.getCustomerAddress(),
                        String.valueOf(data.getCustomerAge()),
                        String.valueOf(data.getCustomerPhoneNumber()),
                        data.getCustomerNic()
                ));
            });
            setValueToTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void customerUpdateBtn(ActionEvent actionEvent) {
        customerName = customerNameText.getText();
        customerAddress = customerAddressTextField.getText();
        customerAge = Integer.parseInt(customerAgeTextField.getText());
        customerPhoneNumber = Integer.parseInt(customerPhoneNumberTextField.getText());
        customerEmail = customerEmailTextField.getText();
        customerNic = customerNicTextField.getText();

        CustomerDTO customerDTO = new CustomerDTO(
                customerName,
                customerEmail,
                customerNic,
                customerAge,
                customerAddress,
                customerPhoneNumber
//                inputStream
        );
        try {
            int executedNumber = customerModel.updateUser(searchedName,searchedPhoneNumber, customerDTO);
            if (executedNumber > 0){
                setValuesToTable();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"customer update success");
                alert.show();
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"can not update customer");
            alert.show();
            System.out.println(e);
        }
    }

    @FXML
    public void customerDeleteBtn(ActionEvent actionEvent) {
        try {
            int i = customerModel.deleteCustomer(searchedName, searchedPhoneNumber);
            setValuesToTable();
            if (i>0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"customer delete success");
                alert.show();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"customer can not delete");
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void customerSaveBtn(ActionEvent actionEvent) {

        customerName = customerNameText.getText();
        customerAddress = customerAddressTextField.getText();
        customerAge = Integer.parseInt(customerAgeTextField.getText());
        customerPhoneNumber = Integer.parseInt(customerPhoneNumberTextField.getText());
        customerEmail = customerEmailTextField.getText();
        customerNic = customerNicTextField.getText();

        CustomerDTO customerDTO = new CustomerDTO(
                customerName,
                customerEmail,
                customerNic,
                customerAge,
                customerAddress,
                customerPhoneNumber,
                inputStream
        );

        try {
            int rowCunt = customerModel.saveCustomer(customerDTO, file);
            if (rowCunt >= 1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "customer save success");
                alert.show();
                customerList.add(0, new CustomerTable(customerName, customerEmail, customerAddress, String.valueOf(customerAge), String.valueOf(customerPhoneNumber), customerNic));
                setValueToTable();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "customer can not save");
                alert.show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    public void customerImageUploadBtn() {

        try {
            fileChooser.setInitialDirectory(new File("C:\\Users\\My Plus\\Downloads"));
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
            );
            file = fileChooser.showOpenDialog(new Stage());

            //read image

            inputStream = new FileInputStream(file);
            customerImageContainer.setImage(new Image(file.getPath()));


        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "can not upload image");
            alert.show();
        }
    }


    /* show file in new associated application */

    public void showCustomerImage(File file) {
        try {
            desktop.open(file);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "something happen can not open the image");
            alert.show();
            throw new RuntimeException(e);
        }
    }


    private void setValueToTable() {

//
        customerTableName.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        customerTableAge.setCellValueFactory(
                new PropertyValueFactory<>("age")
        );
        customerTableAddress.setCellValueFactory(
                new PropertyValueFactory<>("address")
        );
        customerTablePhoneNumber.setCellValueFactory(
                new PropertyValueFactory<>("phoneNumber")
        );
        customerTableNic.setCellValueFactory(
                new PropertyValueFactory<>("nic")
        );
        customerTableEmail.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );
        customerTable.refresh();
        customerTable.setItems(customerList);
    }


    public void setValueToTextFields(CustomerDTO customerDTO) {

        try {
            customerNameText.setText(customerDTO.getCustomerName());
            customerAddressTextField.setText(customerDTO.getCustomerAddress());
            customerAgeTextField.setText(String.valueOf(customerDTO.getCustomerAge()));
            customerEmailTextField.setText(customerDTO.getCustomerEmail());
            customerPhoneNumberTextField.setText(String.valueOf(customerDTO.getCustomerPhoneNumber()));
            customerNicTextField.setText(customerDTO.getCustomerNic());

            FileInputStream fileInputStream = customerDTO.getFileInputStream();
            Image image = new Image(fileInputStream);
            customerImageContainer.setImage(image);
        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR,"error occurred while searching the customer");
            a.show();
            throw new RuntimeException(e);
        }

    }

    public void customerSearchOnKeyPress(KeyEvent keyEvent) {
        CharSequence characters = customerSearchTextField.getCharacters();
        if (characters.length() >= 2) {
            customerSearchPane.setVisible(true);
            if (characters.length() == 2) {
                try {
                    Map<String, String> stringStringMap = customerModel.searchCustomers(customerSearchTextField.getText());
                    ObservableList<Node> children1 = customerSearchVbox.getChildren();
                    List<Node> childrentList = new ArrayList<>(children1);
                    boolean b = customerSearchVbox.getChildren().removeAll(childrentList);
                    System.out.println(b);


                    stringStringMap.forEach((name, number) -> {
                        HBox hBox = new HBox();
                        javafx.scene.control.Label customerNameLbl = new javafx.scene.control.Label(name);
                        javafx.scene.control.Label customerPhoneNumberLbl = new javafx.scene.control.Label(number);
                        ObservableList<Node> children = hBox.getChildren();
                        children.add(customerNameLbl);
                        children.add(customerPhoneNumberLbl);
                        customerSearchVbox.getChildren().add(new Separator());
                        customerSearchVbox.getChildren().add(hBox);
                        hBox.setOnMouseClicked((event) -> {
                            javafx.scene.control.Label customerName = (javafx.scene.control.Label) hBox.getChildren().get(0);
                            javafx.scene.control.Label customerPhoneNumber = (Label) hBox.getChildren().get(1);
                            searchedName = customerName.getText();
                            searchedPhoneNumber = customerPhoneNumber.getText();
                            customerSearchTextField.setText(customerName.getText() + " | " + customerPhoneNumber.getText());
                            clearSearchFielContainer.setVisible(true);


                            try {
                                CustomerDTO searchCustomer = customerModel.getSearchCustomer(Integer.parseInt(customerPhoneNumber.getText()));
                                setValueToTextFields(searchCustomer);
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

        clearSearchFielContainer.setVisible(true);
        if (customerSearchTextField.getText().equals("") || customerSearchTextField.getText() == null){
            clearSearchFielContainer.setVisible(false);
        }

    }

    public void customerSearchOnAction(ActionEvent actionEvent) {

    }

    public void clearFieldValueOnAction(ActionEvent actionEvent) {
        customerContext.getChildren().forEach((node) -> {
            if (node instanceof TextField){
                ((TextField) node).clear();
            }
        });

        customerSearchContainer.getChildren().forEach((node) -> {
            if (node instanceof TextField){
                ((TextField) node).clear();
            }
        });

    }

    public void customerContextOnAction(MouseEvent mouseEvent) {
        customerSearchPane.setVisible(false);
    }

    public void clearSearchFieldOnAction(MouseEvent mouseEvent) {
        customerSearchTextField.clear();
        clearSearchFielContainer.setVisible(false);
        customerSearchPane.setVisible(false);
        clearFieldValueOnAction(new ActionEvent());
    }
}
