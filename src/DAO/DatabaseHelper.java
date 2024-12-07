package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String DATABASE_URL = "jdbc:sqlite:src/SQLite/real_estate.db";
    /**
     * Kết nối tới cơ sở dữ liệu SQLite.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    /**
     * Thiết lập cơ sở dữ liệu và tạo bảng.
     */
    public static void setupDatabase() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            // Tạo bảng Property
            String createPropertyTable = """
                CREATE TABLE IF NOT EXISTS Property (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    address TEXT NOT NULL,
                    price REAL NOT NULL,
                    area REAL NOT NULL,
                    description TEXT,
                    status TEXT NOT NULL
                );
            """;

            // Tạo bảng Customer
            String createCustomerTable = """
                CREATE TABLE IF NOT EXISTS Customer (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    phone TEXT NOT NULL,
                    email TEXT NOT NULL,
                    interested_properties TEXT -- Lưu danh sách ID bất động sản quan tâm, cách nhau bằng dấu phẩy
                );
            """;


            // Thực thi tạo bảng
            statement.execute(createPropertyTable);
            statement.execute(createCustomerTable);

            // Chèn dữ liệu mẫu
            insertSampleData(connection);

            System.out.println("Database setup and initial data inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Chèn dữ liệu mẫu vào cơ sở dữ liệu.
     */
    private static void insertSampleData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Dữ liệu mẫu cho bảng Property
            String insertProperties = """
                INSERT INTO Property (name, address, price, area, description, status)
                VALUES
                    ('Căn hộ ABC', '123 Đường XYZ, Hà Nội', 2000000000, 100, '3 phòng ngủ, nội thất đầy đủ', 'Đang bán'),
                    ('Nhà phố DEF', '456 Đường UVW, TP.HCM', 5000000000, 150, '4 phòng ngủ, có sân vườn', 'Cho thuê'),
                    ('Biệt thự GHI', '789 Đường LMN, Hà Nội', 12000000000, 300, 'Biệt thự 5 phòng ngủ, hồ bơi riêng', 'Đã bán');
            """;

            // Dữ liệu mẫu cho bảng Customer
            String insertCustomers = """
                INSERT INTO Customer (name, phone, email, interested_properties)
                VALUES
                    ('Nguyễn Văn A', '0123456789', 'vana@gmail.com', '1,2'),
                    ('Trần Thị B', '0987654321', 'thib@gmail.com', '2,3');
            """;

            // Thực thi chèn dữ liệu mẫu
            statement.executeUpdate(insertProperties);
            statement.executeUpdate(insertCustomers);
        }
    }
}
