package lk.ijse.gdse.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lk.ijse.gdse.dto.CustomerDTO;
import lk.ijse.gdse.dto.ItemsDTO;
import lk.ijse.gdse.dto.OrderDTO;
import lk.ijse.gdse.dto.OrderDetailsDTO;
import lk.ijse.gdse.dto.tableModel.PlaceOrderTableModel;
import lk.ijse.gdse.model.CustomerModel;
import lk.ijse.gdse.model.ItemModel;
import lk.ijse.gdse.model.PlaceOrderModel;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PlaceOrderViewController implements Initializable {
    public TableColumn<PlaceOrderTableModel, String> tableItemName;
    public TableColumn<PlaceOrderTableModel, String> tableItemBrand;
    public TableColumn<PlaceOrderTableModel, String> tableItemWarranty;
    public TableColumn<PlaceOrderTableModel, Integer> tableItemQty;
    public TableColumn<PlaceOrderTableModel, Double> tablePricePerItem;
    public TableColumn<PlaceOrderTableModel, Double> tableTotalPrice;
    public TableColumn<PlaceOrderTableModel, Button> tableDeleteBtn;
    public TableView<PlaceOrderTableModel> itemTable;
    public TextField itemSearchTextField;
    public Pane itemSearchPane;
    public VBox itemSearchVBox;
    public Pane clearSearchFielContainer;
    public TextField orderItemName;
    public TextField orderItemPrice;
    public TextField orderItemQuantity;
    public ComboBox<String> orderBrandList;
    public TextField orderItemWarranty;
    public TextField customerSearchTextField;
    public Pane customerSearchPane;
    public VBox customerSearchVBox;
    public TextField orderCustomerName;
    public Pane clearCustomerFielCOntainer;
    public Button addToCartBtn;
    public ComboBox<Integer> selectItemQty;
    public Label totalPriceLbl;
    public Label orderIdLbl;
    ObservableList<PlaceOrderTableModel> itemList = FXCollections.observableArrayList();
    ItemModel itemModel = new ItemModel();
    CustomerModel customerModel = new CustomerModel();
    String itemsName;
    String itemsBrand;
    String customerName;
    String customerPhoneNumber;
    boolean isCoustomerFound = false;
    boolean isItemFound = false;
    PlaceOrderTableModel tableModel;
    CustomerDTO searchCustomer;
    ItemsDTO itemsDTO;
    Button deletButton;
    PlaceOrderModel placeOrderModel = new PlaceOrderModel();

    public void placeOrderContextOnAction(MouseEvent mouseEvent) {
        itemSearchPane.setVisible(false);
        customerSearchPane.setVisible(false);
    }

    public void clearTexFieldOnAction(ActionEvent actionEvent) {
    }

    public void plceOrderItemSearch(ActionEvent actionEvent) {
    }

    public void placeOrderCustomerSearch(ActionEvent actionEvent) {
    }

    public void itemsAddToCartOnAction(ActionEvent actionEvent) {
        /* when click the button add values to the table */
        deletButton = new Button("Delete");
        PlaceOrderTableModel item = new PlaceOrderTableModel(
                itemsDTO.getItemName(),
                itemsDTO.getItemBrand(),
                itemsDTO.getWarrantyPeriods(),
                selectItemQty.getValue(),
                itemsDTO.getItemPrice(),
                itemsDTO.getItemPrice() * selectItemQty.getValue(),
                deletButton);
        deletButton.setOnAction(event -> {
            System.out.println(event);
            removeTableRow(item);
        });
        itemList.add(item);
        setValueToTable();


    }

    private void removeTableRow(PlaceOrderTableModel item) {
        itemList.remove(item);
        setValueToTable(); // Update the table after removing the row
    }

    public void itemSearchOnKeyPress(KeyEvent keyEvent) {
        CharSequence characters = itemSearchTextField.getCharacters();
        if (characters.length() >= 2) {
            itemSearchPane.setVisible(true);
            if (characters.length() == 2) {
                try {
                    Map<String, String> stringStringMap = itemModel.searchCustomers(itemSearchTextField.getText());
                    ObservableList<Node> children1 = itemSearchVBox.getChildren();
                    List<Node> childrentList = new ArrayList<>(children1);
                    boolean b = itemSearchVBox.getChildren().removeAll(childrentList);
                    System.out.println(b);


                    stringStringMap.forEach((name, number) -> {
                        HBox hBox = new HBox();
                        javafx.scene.control.Label itemNameLbl = new javafx.scene.control.Label(name);
                        javafx.scene.control.Label itemBrandLbl = new javafx.scene.control.Label(number);
                        ObservableList<Node> children = hBox.getChildren();
                        children.add(itemNameLbl);
                        children.add(itemBrandLbl);
                        itemSearchVBox.getChildren().add(new Separator());
                        itemSearchVBox.getChildren().add(hBox);
                        hBox.setOnMouseClicked((event) -> {
                            javafx.scene.control.Label nameLbl = (javafx.scene.control.Label) hBox.getChildren().get(0);
                            javafx.scene.control.Label brandLbl = (Label) hBox.getChildren().get(1);
                            itemsName = nameLbl.getText();
                            itemsBrand = brandLbl.getText();
                            itemSearchTextField.setText(nameLbl.getText() + " | " + brandLbl.getText());
                            clearSearchFielContainer.setVisible(true);


                            try {
                                itemsDTO = itemModel.getSearchItem(nameLbl.getText());
                                setValueToTextFields(itemsDTO);
                                System.out.println(itemsDTO.getQrCode());
                                System.out.println(itemsDTO.getItemImage());
                                isItemFound = true;
                            } catch (Exception e) {
                                isCoustomerFound = false;
                                isItemFound = false;
                                Alert a = new Alert(Alert.AlertType.ERROR, "try again");
                                a.show();
                                throw new RuntimeException(e);
                            }finally {
                                addToCartBtn.setDisable(!(isItemFound && isCoustomerFound));
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
            itemSearchPane.setVisible(false);
        }

        clearSearchFielContainer.setVisible(true);
        if (itemSearchTextField.getText().equals("") || itemSearchTextField.getText() == null){
            clearSearchFielContainer.setVisible(false);
        }
    }


    private void setValueToTable() {

        tableItemName.setCellValueFactory(
                new PropertyValueFactory<>("itemName")
        );
        tableItemBrand.setCellValueFactory(
                new PropertyValueFactory<>("itemBrand")
        );
        tableItemWarranty.setCellValueFactory(
                new PropertyValueFactory<>("warranty")
        );
        tableItemQty.setCellValueFactory(
                new PropertyValueFactory<>("itemQty")
        );
        tablePricePerItem.setCellValueFactory(
                new PropertyValueFactory<>("pricePerItem")
        );
        tableTotalPrice.setCellValueFactory(
                new PropertyValueFactory<>("totalPrice")
        );
        tableDeleteBtn.setCellValueFactory(
                new PropertyValueFactory<>("button")
        );
        itemTable.refresh();
        itemTable.setItems(itemList);
        ArrayList<Double> priceList = new ArrayList<>();
        itemList.forEach((item) -> {
            priceList.add(item.getTotalPrice());
        });

        double totalPrice = 0.00;
        for (double e : priceList){
            totalPrice += e;
        }

        totalPriceLbl.setText(String.valueOf(totalPrice));
    }

    public void setValueToTextFields(ItemsDTO itemsDTO) {

        try {
            orderItemName.setText(itemsDTO.getItemName());
            orderItemPrice.setText(String.valueOf(itemsDTO.getItemPrice()));
            orderItemQuantity.setText(String.valueOf(itemsDTO.getItemQty()));
            orderBrandList.setValue(itemsDTO.getItemBrand());
            orderItemWarranty.setText(itemsDTO.getWarrantyPeriods());

            List<Integer> list = new ArrayList<>();
            int q = itemsDTO.getItemQty();
            ObservableList<Integer> items = selectItemQty.getItems();
            selectItemQty.getItems().removeAll(items);
            for (int i = 1; i<= q ; i++){
                list.add(i);
                selectItemQty.getItems().add(i);
            }
            selectItemQty.setValue(1);

        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR,"error occurred while searching the customer");
            a.show();
            throw new RuntimeException(e);
        }

    }
    
    public void clearSearchFieldOnAction(MouseEvent mouseEvent) {
        itemSearchPane.setVisible(false);
        itemSearchTextField.clear();
        customerSearchPane.setVisible(false);
        addToCartBtn.setDisable(true);
        isItemFound = false;
        isCoustomerFound = false;
//        clearTexFieldOnAction(new ActionEvent());
    }

    public void customerSearchOnKeyPress(KeyEvent keyEvent) {
        CharSequence characters = customerSearchTextField.getCharacters();
        if (characters.length() >= 2) {
            customerSearchPane.setVisible(true);
            if (characters.length() == 2) {
                try {
                    Map<String, String> stringStringMap = customerModel.searchCustomers(customerSearchTextField.getText());
                    ObservableList<Node> children1 = customerSearchVBox.getChildren();
                    List<Node> childrentList = new ArrayList<>(children1);
                    boolean b = customerSearchVBox.getChildren().removeAll(childrentList);
                    System.out.println(b);


                    stringStringMap.forEach((name, number) -> {
                        HBox hBox = new HBox();
                        javafx.scene.control.Label customerNameLbl = new javafx.scene.control.Label(name);
                        javafx.scene.control.Label customerPhoneNumberLbl = new javafx.scene.control.Label(number);
                        ObservableList<Node> children = hBox.getChildren();
                        children.add(customerNameLbl);
                        children.add(customerPhoneNumberLbl);
                        customerSearchVBox.getChildren().add(new Separator());
                        customerSearchVBox.getChildren().add(hBox);
                        hBox.setOnMouseClicked((event) -> {
                            javafx.scene.control.Label customerName = (javafx.scene.control.Label) hBox.getChildren().get(0);
                            javafx.scene.control.Label customerPhoneNumber = (Label) hBox.getChildren().get(1);
                            this.customerName = customerName.getText();
                            this.customerPhoneNumber = customerPhoneNumber.getText();
                            customerSearchTextField.setText(customerName.getText() + " | " + customerPhoneNumber.getText());
                            clearCustomerFielCOntainer.setVisible(true);


                            try {
                                searchCustomer = customerModel.getSearchCustomer(Integer.parseInt(customerPhoneNumber.getText()));
                                orderCustomerName.setText(searchCustomer.getCustomerName());
                                System.out.println(searchCustomer);
                                isCoustomerFound = true;
                            } catch (Exception e) {
                                isItemFound = false;
                                isCoustomerFound = false;
                                Alert a = new Alert(Alert.AlertType.ERROR, "try again");
                                a.show();
                                throw new RuntimeException(e);
                            } finally {
                                addToCartBtn.setDisable(!(isItemFound && isCoustomerFound));
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

        clearCustomerFielCOntainer.setVisible(true);
        if (customerSearchTextField.getText().equals("") || customerSearchTextField.getText() == null){
            clearCustomerFielCOntainer.setVisible(false);
        }
    }

    public void clearCustomerSearchFieldOnAction(MouseEvent mouseEvent) {
        customerSearchPane.setVisible(false);
        clearCustomerFielCOntainer.setVisible(false);
        customerSearchTextField.clear();
        addToCartBtn.setDisable(true);
        isItemFound = false;
        isCoustomerFound = false;
    }

    public void deleteTebleRowOnAction(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
        placeOrderModel.saveOrder(
                new OrderDTO(),
                new OrderDetailsDTO()
        );
    }

    public void nextOrderId(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            orderIdLbl.setText(placeOrderModel.getLastRecordId());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"can not get informations");
            alert.show();
            throw new RuntimeException(e);
        }
    }
}
