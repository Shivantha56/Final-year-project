package lk.ijse.gdse.dto.tableModel;

import lk.ijse.gdse.model.CustomerModel;

public class CustomerTable {
    private String name;
    private String email;
    private String address;
    private String age;
    private String phoneNumber;
    protected String nic;

    public CustomerTable() {
    }

    public CustomerTable(String name, String email, String address, String age, String phoneNumber, String nic) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}
