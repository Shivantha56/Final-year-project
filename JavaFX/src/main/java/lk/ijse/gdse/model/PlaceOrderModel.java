package lk.ijse.gdse.model;

import javafx.scene.control.Alert;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.dto.ItemsDTO;
import lk.ijse.gdse.dto.OrderDTO;
import lk.ijse.gdse.dto.OrderDetailsDTO;

import java.sql.*;

public class PlaceOrderModel {

    public String getLastRecordId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `order` ORDER BY order_id DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
           return resultSet.getString(1);
        }
        return null;
    }

    public void saveOrder(OrderDTO orderDTO , OrderDetailsDTO orderDetailsDTO) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `order` (customer_id, total_price) VALUES ( ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,8);
            preparedStatement.setDouble(2,orderDTO.getTotalPrice());

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            System.out.println(generatedKeys.getString(1));

            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO order_details (order_id, item_id, item_name, item_qty) VALUES (?, ?, ?, ?, ?)");
            preparedStatement1.setString(1,orderDetailsDTO.getOrderId());
            preparedStatement1.setInt(2,1);
            preparedStatement1.setString(3,orderDetailsDTO.getItemName());
            preparedStatement1.setInt(4,orderDetailsDTO.getItemQty());

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
