package lk.ijse.gdse.dto;

import java.io.FileInputStream;
import java.io.Serializable;

public class ItemsDTO implements Serializable {
    private String itemName;
    private double itemPrice;
    private int itemQty;
    private String itemBrand;
    private String warrantyPeriods;
    private FileInputStream itemImage;
    private FileInputStream qrCode;

    public ItemsDTO() {
    }

    public ItemsDTO(String itemName) {
        this.itemName = itemName;
    }



    public ItemsDTO(String itemName, double itemPrice, int itemQty, String itemBrand, String warrantyPeriods) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
        this.itemBrand = itemBrand;
        this.warrantyPeriods = warrantyPeriods;
    }

    public ItemsDTO(String itemName, double itemPrice, int itemQty, String itemBrand, String warrantyPeriods, FileInputStream itemImage, FileInputStream qrCode) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
        this.itemBrand = itemBrand;
        this.warrantyPeriods = warrantyPeriods;
        this.itemImage = itemImage;
        this.qrCode = qrCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getWarrantyPeriods() {
        return warrantyPeriods;
    }

    public void setWarrantyPeriods(String warrantyPeriods) {
        this.warrantyPeriods = warrantyPeriods;
    }

    public FileInputStream getItemImage() {
        return itemImage;
    }

    public void setItemImage(FileInputStream itemImage) {
        this.itemImage = itemImage;
    }

    public FileInputStream getQrCode() {
        return qrCode;
    }

    public void setQrCode(FileInputStream qrCode) {
        this.qrCode = qrCode;
    }

}
