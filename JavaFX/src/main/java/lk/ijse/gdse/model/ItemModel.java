package lk.ijse.gdse.model;

import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.dto.CustomerDTO;
import lk.ijse.gdse.dto.ItemsDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemModel {

    public int saveItem(ItemsDTO itemsDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item (item_name,item_price,item_qty,itemBrand,warranty_period,item_image,item_qr) VALUES (?,?,?,?,?,?,?)");
        preparedStatement.setObject(1,itemsDTO.getItemName());
        preparedStatement.setObject(2,itemsDTO.getItemPrice());
        preparedStatement.setObject(3,itemsDTO.getItemQty());
        preparedStatement.setObject(4,itemsDTO.getItemBrand());
        preparedStatement.setObject(5,itemsDTO.getWarrantyPeriods());
        preparedStatement.setBinaryStream(6,itemsDTO.getItemImage());
        preparedStatement.setBinaryStream(7,itemsDTO.getQrCode());

        return preparedStatement.executeUpdate();

    }

    public List<ItemsDTO> getLastAddedItemDetails() throws SQLException {
        /* get the customers details of last added 10 */
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item ORDER BY item_id DESC LIMIT 10");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ItemsDTO> list = new ArrayList<>();

        while (resultSet.next()) {
            String name = resultSet.getString(2);
            double itemPrice = resultSet.getDouble(3);
            int itemQty = resultSet.getInt(4);
            String itemBrand = resultSet.getString(5);
            String warrantyPeriod = resultSet.getString(6);

            list.add(new ItemsDTO(name, itemPrice, itemQty, itemBrand,warrantyPeriod));
        }
        return list;
    }

    public Map<String, String> searchCustomers(String characters) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT item_name , itemBrand FROM item WHERE item_name LIKE ?");
        preparedStatement.setString(1, "%" + characters + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        Map<String, String> customerList = new HashMap<>();
        while (resultSet.next()) {
            String itemName = resultSet.getString(1);
            String itemBrand = resultSet.getString(2);
            customerList.put(itemName, itemBrand);
        }
        return customerList;
    }

    public ItemsDTO getSearchItem(String name) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item WHERE item_name = ?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            if (resultSet.next()) {
                String itemName = resultSet.getString(2);
                double itemPrice = resultSet.getDouble(3);
                int itemQty = resultSet.getInt(4);
                String itemBrand = resultSet.getString(5);
                String warrantyPeriod = resultSet.getString(6);
                InputStream itemImageBinaryStream = resultSet.getBinaryStream(7);
                InputStream qrBinaryStream = resultSet.getBinaryStream(8);

                File qrTempFile;
                File itemImageTempFile;
                try {
                    qrTempFile = File.createTempFile("tempQrImage", ".tmp");
                    itemImageTempFile = File.createTempFile("tempItemImage", ".tmp");


                    // Write the binary stream data to the temporary file
                    try (FileOutputStream qrOutputStream = new FileOutputStream(qrTempFile);
                         FileOutputStream itemImageOutPutStream = new FileOutputStream(itemImageTempFile);) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = qrBinaryStream.read(buffer)) != -1) {
                            itemImageOutPutStream.write(buffer, 0, bytesRead);
                        }
                        while ((bytesRead = itemImageBinaryStream.read(buffer)) != -1) {
                            qrOutputStream.write(buffer, 0, bytesRead);
                        }
                    } catch (Exception e) {
                        throw new Exception(e);
                    }
                } catch (Exception e) {
                    throw new Exception(e);
                }
//
                System.out.println(qrTempFile.getPath());
                System.out.println(itemImageTempFile.getPath());
                return new ItemsDTO(itemName, itemPrice, itemQty, itemBrand, warrantyPeriod, new FileInputStream(itemImageTempFile),new FileInputStream(qrTempFile));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public int deleteItem(String itemName, String itemBrand) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM item WHERE item_name = ? AND itemBrand = ?");
        preparedStatement.setObject(1,itemName);
        preparedStatement.setObject(2,itemBrand);
        return preparedStatement.executeUpdate();


    }

}


