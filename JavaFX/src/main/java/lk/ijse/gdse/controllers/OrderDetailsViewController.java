package lk.ijse.gdse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OrderDetailsViewController {
    @FXML
    private ComboBox<?> itemBrandList;

    @FXML
    private Pane itemContext;

    @FXML
    private ImageView itemImageContainer;

    @FXML
    private TextField itemNameText;

    @FXML
    private TextField itemPriceText;

    @FXML
    private TextField itemQuantityText;

    @FXML
    private Pane itemSearchContainer;

    @FXML
    private Pane itemSearchPane;

    @FXML
    private TextField itemSearchTextField;

    @FXML
    private VBox itemSearchVBox;

    @FXML
    private TableView<?> itemTable;

    @FXML
    private TableColumn<?, ?> itemTableBrand;

    @FXML
    private TableColumn<?, ?> itemTableName;

    @FXML
    private TableColumn<?, ?> itemTablePrice;

    @FXML
    private TableColumn<?, ?> itemTableQty;

    @FXML
    private TableColumn<?, ?> itemTableWarranty;

    @FXML
    private TextField itemWarrantyPeriod;

    @FXML
    private ImageView qrView;

    @FXML
    void ItemContextOnAction(MouseEvent event) {

    }

    @FXML
    void clearTexFieldOnAction(ActionEvent event) {

    }

    @FXML
    void customerSearchOnAction(ActionEvent event) {

    }

    @FXML
    void customerSearchOnKeyPress(KeyEvent event) {

    }

    @FXML
    void generateQrCodeOnAction(ActionEvent event) {

    }

    @FXML
    void itemDeletOnAction(ActionEvent event) {

    }

    @FXML
    void itemSaveOnActrion(ActionEvent event) {

    }

    @FXML
    void itemUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void uploadItemImage(ActionEvent event) {

    }

}
