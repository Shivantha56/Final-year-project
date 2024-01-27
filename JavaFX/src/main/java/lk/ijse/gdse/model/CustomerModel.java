package lk.ijse.gdse.model;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.dto.CustomerDTO;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerModel {

    public int saveCustomer(CustomerDTO customerDTO, File file) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customer (customer_name,customer_address,customer_age,customer_email,customer_nic,customer_phoneNumber,customer_image) VALUES (?,?,?,?,?,?,?)");
        preparedStatement.setObject(1, customerDTO.getCustomerName());
        preparedStatement.setObject(2, customerDTO.getCustomerAddress());
        preparedStatement.setObject(3, customerDTO.getCustomerAge());
        preparedStatement.setObject(4, customerDTO.getCustomerEmail());
        preparedStatement.setObject(5, customerDTO.getCustomerNic());
        preparedStatement.setObject(6, customerDTO.getCustomerPhoneNumber());
        preparedStatement.setBinaryStream(7, customerDTO.getFileInputStream(), (int) file.length());
        return preparedStatement.executeUpdate();

    }

    public List<CustomerDTO> getLastAddedCustomerDetails() throws SQLException {
        /* get the customers details of last added 10 */
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer ORDER BY customer_id DESC LIMIT 10");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<CustomerDTO> list = new ArrayList<>();

        while (resultSet.next()) {
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            int age = resultSet.getInt(4);
            String email = resultSet.getString(5);
            String nic = resultSet.getString(6);
            int phoneNumber = resultSet.getInt(7);
//            String phoneNumber = resultSet.getString(6);

            list.add(new CustomerDTO(name, email, nic, age, address, phoneNumber));
        }
        return list;
    }

    public Map<String, String> searchCustomers(String characters) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT customer_name , customer_phoneNumber FROM customer WHERE customer_name LIKE ?");
        preparedStatement.setString(1, "%" + characters + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        Map<String, String> customerList = new HashMap<>();
        while (resultSet.next()) {
            String name = resultSet.getString(1);
            String phoneNumber = resultSet.getString(2);
            customerList.put(name, phoneNumber);
        }
        return customerList;
    }

    public CustomerDTO getSearchCustomer(int phoneNumber) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE customer_phoneNumber = ?");
        preparedStatement.setInt(1, phoneNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            if (resultSet.next()) {
                int customerId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                int age = resultSet.getInt(4);
                String email = resultSet.getString(5);
                String nic = resultSet.getString(6);
                int cPhoneNumber = resultSet.getInt(7);
                InputStream binaryStream = resultSet.getBinaryStream(8);

                File tempFile;
                try {
                    tempFile = File.createTempFile("tempimage", ".tmp");


                    // Write the binary stream data to the temporary file
                    try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = binaryStream.read(buffer)) != -1) {
                            fileOutputStream.write(buffer, 0, bytesRead);
                        }
                    } catch (Exception e) {
                        throw new Exception(e);
                    }
                } catch (Exception e) {
                    throw new Exception(e);
                }
//
                System.out.println(tempFile.getPath());
                return new CustomerDTO(customerId,name, email, nic, age, address, cPhoneNumber, new FileInputStream(tempFile));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public int updateUser(String name , String phoneNumber,CustomerDTO customerDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer SET customer_name = ?, customer_address = ?, customer_age = ?, customer_email = ?, customer_nic = ?, customer_phoneNumber = ? WHERE customer_name = ? AND customer_phoneNumber = ?");
        preparedStatement.setObject(1,customerDTO.getCustomerName());
        preparedStatement.setObject(2,customerDTO.getCustomerAddress());
        preparedStatement.setObject(3,customerDTO.getCustomerAge());
        preparedStatement.setObject(4,customerDTO.getCustomerEmail());
        preparedStatement.setObject(5,customerDTO.getCustomerNic());
        preparedStatement.setObject(6,customerDTO.getCustomerPhoneNumber());
        preparedStatement.setObject(7,name);
        preparedStatement.setObject(8,phoneNumber);

        return preparedStatement.executeUpdate();


    }

    public int deleteCustomer(String customerName, String phoneNumber) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE customer_phoneNumber = ? AND customer_name = ?");
        preparedStatement.setObject(1,phoneNumber);
        preparedStatement.setObject(2,customerName);
        return preparedStatement.executeUpdate();
    }

}

