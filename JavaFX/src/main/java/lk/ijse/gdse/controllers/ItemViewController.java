package lk.ijse.gdse.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
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
import lk.ijse.gdse.dto.ItemsDTO;
import lk.ijse.gdse.model.ItemModel;
import lk.ijse.gdse.util.Qr;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ItemViewController implements Initializable {

    public ImageView qrView;
    public TableView<ItemsDTO> itemTable;
    public TableColumn<ItemsDTO,String> itemTableName;
    public TableColumn<ItemsDTO,String> itemTablePrice;
    public TableColumn<ItemsDTO,String> itemTableQty;
    public TableColumn<ItemsDTO,String> itemTableBrand;
    public TableColumn<ItemsDTO,String> itemTableWarranty;
    public ComboBox<String> itemBrandList;
    @FXML
    private Pane clearSearchFielContainer;
    @FXML
    private Pane itemContext;

    @FXML
    private ImageView itemImageContainer;

    @FXML
    private Pane itemSearchContainer;

    @FXML
    private Pane itemSearchPane;

    @FXML
    private TextField itemSearchTextField;

    @FXML
    private VBox itemSearchVBox;
    
    @FXML
    private TextField itemNameText;

    @FXML
    private TextField itemPriceText;

    @FXML
    private TextField itemQuantityText;
    

    @FXML
    private TextField itemWarrantyPeriod;
    private final File qrFile = new File("C:\\Working directory\\Today task\\JavaFX\\src\\main\\resources\\qr\\qr.png");
    FileChooser fileChooser = new FileChooser();
    FileInputStream qrFileInputStream;
    FileInputStream itemImageInputStream;
    File file;
    ItemModel itemModel = new ItemModel();
    ObservableList<ItemsDTO> itemList = FXCollections.observableArrayList();
    String itemsName;
    String itemsBrand;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        itemBrandList.getItems().add("samsung");
        itemBrandList.getItems().add("azus");
        itemBrandList.getItems().add("msi");
        itemBrandList.setValue("samsung");
        setValuesToTable();

    }

    public void setValuesToTable(){
        ObservableList<ItemsDTO> items = itemTable.getItems();
        itemTable.getItems().removeAll(items);
        try {
            List<ItemsDTO> lastAddedCustomerDetails = itemModel.getLastAddedItemDetails();
            lastAddedCustomerDetails.forEach((data) -> {
                itemList.add(new ItemsDTO(
                        data.getItemName(),
                        data.getItemPrice(),
                        data.getItemQty(),
                        data.getItemBrand(),
                        data.getWarrantyPeriods()
                ));
            });
            setValueToTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void ItemContextOnAction(MouseEvent event) {
        itemSearchPane.setVisible(false);
    }

    @FXML
    void clearSearchFieldOnAction(MouseEvent event) {
        itemSearchTextField.clear();
        clearSearchFielContainer.setVisible(false);
        itemSearchPane.setVisible(false);
        clearTexFieldOnAction(new ActionEvent());
    }

    @FXML
    void clearTexFieldOnAction(ActionEvent event) {
        itemContext.getChildren().forEach((node) -> {
            if (node instanceof TextField){
                ((TextField) node).clear();
            }
        });

        itemSearchContainer.getChildren().forEach((node) -> {
            if (node instanceof TextField){
                ((TextField) node).clear();
            }
        });
        qrView.setImage(new Image("C:\\Users\\My Plus\\Downloads"));
        itemImageContainer.setImage(new Image("C:\\Users\\My Plus\\Downloads"));

    }

    @FXML
    void customerSearchOnAction(ActionEvent event) {



    }

    @FXML
    void customerSearchOnKeyPress(KeyEvent even) {
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
                                ItemsDTO itemsDTO = itemModel.getSearchItem(nameLbl.getText());
                                setValueToTextFields(itemsDTO);
                                System.out.println(itemsDTO.getQrCode());
                                System.out.println(itemsDTO.getItemImage());
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
            itemSearchPane.setVisible(false);
        }

        clearSearchFielContainer.setVisible(true);
        if (itemSearchTextField.getText().equals("") || itemSearchTextField.getText() == null){
            clearSearchFielContainer.setVisible(false);
        }
    }

    public void setValueToTextFields(ItemsDTO itemsDTO) {

        try {
            itemNameText.setText(itemsDTO.getItemName());
            itemPriceText.setText(String.valueOf(itemsDTO.getItemPrice()));
            itemQuantityText.setText(String.valueOf(itemsDTO.getItemQty()));
            itemBrandList.setValue(itemsDTO.getItemBrand());
            itemWarrantyPeriod.setText(itemsDTO.getWarrantyPeriods());

            FileInputStream qrFileInput = itemsDTO.getQrCode();
            FileInputStream imageFileInput = itemsDTO.getItemImage();

            qrView.setImage(new Image(imageFileInput));
            itemImageContainer.setImage(new Image(qrFileInput));
        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR,"error occurred while searching the customer");
            a.show();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void itemDeletOnAction(ActionEvent event) {

        try {
            int i = itemModel.deleteItem(itemsName, itemsBrand);
            if (i>0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"customer delete success");
                alert.show();
                setValuesToTable();
                clearTexFieldOnAction(new ActionEvent());
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"customer can not delete");
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void itemSaveOnActrion(ActionEvent event)  {
        /* save item in the database */

        ItemsDTO itemsDTO = new ItemsDTO(
                itemNameText.getText(),
                Double.parseDouble(itemPriceText.getText()),
                Integer.parseInt(itemQuantityText.getText()),
                itemBrandList.getValue(),
                itemWarrantyPeriod.getText(),
                itemImageInputStream,
                qrFileInputStream
        );

        try {
            int i = itemModel.saveItem(itemsDTO);
            if (i>0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"customer save success");
                alert.show();
                itemList.add(0, new ItemsDTO(itemsDTO.getItemName(),itemsDTO.getItemPrice(),itemsDTO.getItemQty(),itemsDTO.getItemBrand(),itemsDTO.getWarrantyPeriods()));
                setValueToTable();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"can not save customer");
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void itemUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void uploadItemImage(ActionEvent event) {
        try {
            fileChooser.setInitialDirectory(new File("C:\\Users\\My Plus\\Downloads"));
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
            );
            file = fileChooser.showOpenDialog(new Stage());
            itemImageInputStream = new FileInputStream(file);
            itemImageContainer.setImage(new Image(file.getPath()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "can not upload image");
            alert.show();
        }
    }

    public void generateQrCodeOnAction(ActionEvent actionEvent) throws InterruptedException, FileNotFoundException {


            Qr qr = new Qr(new ItemsDTO(
                    itemNameText.getText(),
                    Double.parseDouble(itemPriceText.getText()),
                    Integer.parseInt(itemQuantityText.getText()),
                    itemBrandList.getValue(),
                    itemWarrantyPeriod.getText()
            ));
            qr.encode();
            ItemsDTO i = qr.decode();
            System.out.println(i.getItemName());

            final Image image = new Image("C:\\Working directory\\Today task\\JavaFX\\src\\main\\resources\\qr\\qr.png");
            qrView.setImage(image);
            System.out.println(image.getUrl());
            qrFileInputStream = new FileInputStream(qrView.getImage().getUrl());
    }

    private void setValueToTable() {

        itemTableName.setCellValueFactory(
                new PropertyValueFactory<>("itemName")
        );
        itemTablePrice.setCellValueFactory(
                new PropertyValueFactory<>("itemPrice")
        );
        itemTableQty.setCellValueFactory(
                new PropertyValueFactory<>("itemQty")
        );
        itemTableBrand.setCellValueFactory(
                new PropertyValueFactory<>("itemBrand")
        );
        itemTableWarranty.setCellValueFactory(
                new PropertyValueFactory<>("warrantyPeriods")
        );
        itemTable.refresh();
        itemTable.setItems(itemList);
    }
}
