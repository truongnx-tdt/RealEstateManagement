package DAO;

import Entities.Property;
import Infrastructures.Enum.PropertyStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO {

    /**
     * Thêm mới bất động sản vào cơ sở dữ liệu.
     */
    public static void addProperty(Property property) {
        String insertSQL = """
            INSERT INTO Property (name, address, price, area, description, status)
            VALUES (?, ?, ?, ?, ?, ?);
        """;

// Add an import statement or define the DatabaseHelper class
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, property.getName());
            preparedStatement.setString(2, property.getAddress());
            preparedStatement.setDouble(3, property.getPrice());
            preparedStatement.setDouble(4, property.getArea());
            preparedStatement.setString(5, property.getDescription());
            preparedStatement.setString(6, property.getStatus().toString());

            preparedStatement.executeUpdate();
            System.out.println("Thêm bất động sản thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sửa thông tin bất động sản trong cơ sở dữ liệu.
     */
    public static void editProperty(Property property) {

        String updateSQL = """
            UPDATE Property
            SET name = ?, address = ?, price = ?, area = ?, description = ?, status = ?
            WHERE id = ?;
        """;

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, property.getName());
            preparedStatement.setString(2, property.getAddress());
            preparedStatement.setDouble(3, property.getPrice());
            preparedStatement.setDouble(4, property.getArea());
            preparedStatement.setString(5, property.getDescription());
            preparedStatement.setString(6, property.getStatus().toString());
            preparedStatement.setInt(7, property.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật bất động sản thành công!");
            } else {
                System.out.println("Không tìm thấy bất động sản với ID: " + property.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xóa bất động sản khỏi cơ sở dữ liệu.
     */
    public static void deleteProperty(int id) {
        String deleteSQL = "DELETE FROM Property WHERE id = ?;";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Xóa bất động sản thành công!");
            } else {
                System.out.println("Không tìm thấy bất động sản với ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy danh sách tất cả bất động sản trong cơ sở dữ liệu.
     */
    public static List<Property> getAllProperties() {
        List<Property> properties = new ArrayList<>();
        String selectSQL = "SELECT * FROM Property;";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                properties.add(new Property(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("area"),
                        resultSet.getString("description"),
                        PropertyStatus.valueOf(resultSet.getString("status"))
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Lấy thông tin bất động sản theo ID.
     */
    public static Property getPropertyById(int id) {
        String selectSQL = "SELECT * FROM Property WHERE id = ?;";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Property(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("address"),
                            resultSet.getDouble("price"),
                            resultSet.getDouble("area"),
                            resultSet.getString("description"),
                            PropertyStatus.valueOf(resultSet.getString("status"))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy danh sách ID của tất cả bất động sản trong cơ sở dữ liệu.
     */
    public static List<Integer> getPropertyIds() {
        List<Integer> propertyIds = new ArrayList<>();
        String selectSQL = "SELECT id FROM Property";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                propertyIds.add(resultSet.getInt("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return propertyIds;
    }

    /**
     * Tìm kiếm bất động sản theo giá bán, diện tích, địa chỉ và trạng thái.
     */
    public static List<Property> searchProperties(Double minPrice, Double maxPrice, Double minArea, Double maxArea, String address, PropertyStatus status) {
        List<Property> properties = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Property WHERE 1=1");

        // Thêm điều kiện tìm kiếm
        if (minPrice != null) {
            query.append(" AND price >= ?");
        }
        if (maxPrice != null) {
            query.append(" AND price <= ?");
        }
        if (minArea != null) {
            query.append(" AND area >= ?");
        }
        if (maxArea != null) {
            query.append(" AND area <= ?");
        }
        if (address != null && !address.isEmpty()) {
            query.append(" AND address LIKE ?");
        }
        if (status != null) {
            query.append(" AND status = ?");
        }

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            int index = 1;
            if (minPrice != null) {
                preparedStatement.setDouble(index++, minPrice);
            }
            if (maxPrice != null) {
                preparedStatement.setDouble(index++, maxPrice);
            }
            if (minArea != null) {
                preparedStatement.setDouble(index++, minArea);
            }
            if (maxArea != null) {
                preparedStatement.setDouble(index++, maxArea);
            }
            if (address != null && !address.isEmpty()) {
                preparedStatement.setString(index++, "%" + address + "%");
            }
            if (status != null) {
                preparedStatement.setString(index, status.toString());
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                properties.add(new Property(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("area"),
                        resultSet.getString("description"),
                        PropertyStatus.valueOf(resultSet.getString("status"))
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties;
    }

    /**
     * Lọc bất động sản theo trạng thái.
     */
    public static List<Property> filterPropertiesByStatus(PropertyStatus status) {
        List<Property> properties = new ArrayList<>();
        String query = "SELECT * FROM Property WHERE status = ?";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, status.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                properties.add(new Property(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("area"),
                        resultSet.getString("description"),
                        PropertyStatus.valueOf(resultSet.getString("status"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return properties;
    }

    /**
     * Thống kê số lượng tài sản theo trạng thái.
     */
    public static int countPropertiesByStatus(PropertyStatus status) {
        String query = "SELECT COUNT(*) FROM Property WHERE status = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, status.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Tính tổng giá trị bất động sản đang rao bán.
     */
    public static double calculateTotalValueForSale() {
        String query = "SELECT SUM(price) FROM Property WHERE status = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, PropertyStatus.DANG_BAN.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
