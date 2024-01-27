package lk.ijse.gdse.dto;

public class OrderDTO {
//    private String orderId;
    private int customerId;
    private double totalPrice;

    public OrderDTO() {
    }

    public OrderDTO(int customerId, double totalPrice) {
        this.customerId = customerId;
        this.totalPrice = totalPrice;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
