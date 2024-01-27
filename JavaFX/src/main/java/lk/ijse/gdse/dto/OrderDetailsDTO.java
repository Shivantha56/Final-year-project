package lk.ijse.gdse.dto;

public class OrderDetailsDTO {

    private String orderId;
    private int itemId;
    private String itemName;
    private int itemQty;
//    private int pricePerItem;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(String orderId, int itemId, String itemName, int itemQty) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQty = itemQty;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }
}
