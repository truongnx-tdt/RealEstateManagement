package DAO;

import Entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    /**
     * Thêm mới khách hàng vào cơ sở dữ liệu.
     */
    public static void addCustomer(Customer customer) {
        String sql = """
        INSERT INTO Customer (name, phone, email, interested_properties)
        VALUES (?, ?, ?, ?);
    """;

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getPhone());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getInterestedProperties());
            preparedStatement.executeUpdate();
            System.out.println("Thêm khách hàng thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sửa thông tin khách hàng trong cơ sở dữ liệu.
     */
    public static void editCustomer(Customer customer) {
        String sql = """
        UPDATE Customer
        SET name = ?, phone = ?, email = ?, interested_properties = ?
        WHERE id = ?;
    """;

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getPhone());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getInterestedProperties());
            preparedStatement.setInt(5, customer.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật thông tin khách hàng thành công!");
            } else {
                System.out.println("Không tìm thấy khách hàng với ID: " + customer.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Xóa khách hàng khỏi cơ sở dữ liệu.
     */
    public static void deleteCustomer(int id) {
        String sql = "DELETE FROM Customer WHERE id = ?;";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Xóa khách hàng thành công!");
            } else {
                System.out.println("Không tìm thấy khách hàng với ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy danh sách tất cả khách hàng trong cơ sở dữ liệu.
     */
    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer;";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("interested_properties")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    /**
     * Lấy thông tin khách hàng theo ID.
     */
    public static Customer getCustomerById(int id) {
        String selectSQL = "SELECT * FROM Customer WHERE id = ?;";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Customer(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                            resultSet.getString("interested_properties")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Tìm kiếm khách hàng theo id, tên, số điện thoại, email.
     */
    public static List<Customer> searchCustomers(Integer id, String name, String phone, String email) {
        List<Customer> customers = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Customer WHERE 1=1");

        // Thêm điều kiện tìm kiếm
        if (id != null) {
            query.append(" AND id = ?");
        }
        if (name != null && !name.isEmpty()) {
            query.append(" AND name LIKE ?");
        }
        if (phone != null && !phone.isEmpty()) {
            query.append(" AND phone LIKE ?");
        }
        if (email != null && !email.isEmpty()) {
            query.append(" AND email LIKE ?");
        }

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            int index = 1;
            if (id != null) {
                preparedStatement.setInt(index++, id);
            }
            if (name != null && !name.isEmpty()) {
                preparedStatement.setString(index++, "%" + name + "%");
            }
            if (phone != null && !phone.isEmpty()) {
                preparedStatement.setString(index++, "%" + phone + "%");
            }
            if (email != null && !email.isEmpty()) {
                preparedStatement.setString(index, "%" + email + "%");
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("interested_properties")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }
}
