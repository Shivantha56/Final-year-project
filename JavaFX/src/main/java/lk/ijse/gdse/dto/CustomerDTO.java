package lk.ijse.gdse.dto;

import java.io.FileInputStream;
import java.io.InputStream;

public class CustomerDTO {

    private int customer_id;
    private String customerName;
    private String customerEmail;
    private String customerNic;
    private int customerAge;
    private String customerAddress;
    private int  customerPhoneNumber;
    private FileInputStream fileInputStream;

    public CustomerDTO(int customer_id, String customerName, String customerEmail, String customerNic, int customerAge, String customerAddress, int customerPhoneNumber, FileInputStream fileInputStream) {
        this.customer_id = customer_id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerNic = customerNic;
        this.customerAge = customerAge;
        this.customerAddress = customerAddress;
        this.customerPhoneNumber = customerPhoneNumber;
        this.fileInputStream = fileInputStream;
    }

    public CustomerDTO(String customerName, String customerEmail, String customerNic, int customerAge, String customerAddress, int customerPhoneNumber) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerNic = customerNic;
        this.customerAge = customerAge;
        this.customerAddress = customerAddress;
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public CustomerDTO() {
    }

    public CustomerDTO(String customerName, String customerEmail, String customerNic, int customerAge, String customerAddress, int customerPhoneNumber, FileInputStream fileInputStream) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerNic = customerNic;
        this.customerAge = customerAge;
        this.customerAddress = customerAddress;
        this.customerPhoneNumber = customerPhoneNumber;
        this.fileInputStream = fileInputStream;
    }

    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerNic() {
        return customerNic;
    }

    public void setCustomerNic(String customerNic) {
        this.customerNic = customerNic;
    }

    public int getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(int customerAge) {
        this.customerAge = customerAge;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(int customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerNic='" + customerNic + '\'' +
                ", customerAge=" + customerAge +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerPhoneNumber='" + customerPhoneNumber + '\'' +
                ", customerImage='" + fileInputStream + '\'' +
                '}';
    }
}
