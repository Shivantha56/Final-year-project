package lk.ijse.gdse.dto.tableModel;

import javafx.scene.control.Button;


public class PlaceOrderTableModel {

    private String itemName;
    private String itemBrand;
    private String warranty;
    private int itemQty;
    private double pricePerItem;
    private double totalPrice;
    private Button button;

    public PlaceOrderTableModel() {
    }

    public PlaceOrderTableModel(String itemName, String itemBrand, String warranty, int itemQty, double pricePerItem, double totalPrice, Button button) {
        this.itemName = itemName;
        this.itemBrand = itemBrand;
        this.warranty = warranty;
        this.itemQty = itemQty;
        this.pricePerItem = pricePerItem;
        this.totalPrice = totalPrice;
        this.button = button;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
