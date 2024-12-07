package Infrastructures.Utils;

import Entities.Customer;
import Entities.Property;

import java.util.List;

public class TableUtils {

    public static void printPropertyTable(List<Property> properties) {
        // Tiêu đề bảng
        String header = String.format(
                "| %-4s | %-20s | %-30s | %-12s | %-8s | %-40s | %-10s |",
                "ID", "Tên", "Địa chỉ", "Giá (VND)", "Diện tích (m²)", "Mô tả", "Trạng thái"
        );

        // Đường kẻ
        String line = "+------+----------------------+--------------------------------+--------------+----------+------------------------------------------+------------+";

        // In tiêu đề
        System.out.println(line);
        System.out.println(header);
        System.out.println(line);

        // In dữ liệu
        for (Property property : properties) {
            System.out.printf(
                    "| %-4d | %-20s | %-30s | %-12.0f | %-8.2f | %-40s | %-10s |%n",
                    property.getId(),
                    property.getName(),
                    property.getAddress(),
                    property.getPrice(),
                    property.getArea(),
                    property.getDescription().length() > 40
                            ? property.getDescription().substring(0, 37) + "..."
                            : property.getDescription(),
                    property.getStatus().getDescription()
            );
        }

        // Đường kẻ cuối
        System.out.println(line);
    }

    public static void printCustomerTable(List<Customer> customers) {
        // Tiêu đề bảng
        String header = String.format(
                "| %-4s | %-20s | %-15s | %-25s | %-40s |",
                "ID", "Tên", "Số điện thoại", "Email", "Bất động sản quan tâm"
        );

        // Đường kẻ
        String line = "+------+----------------------+-----------------+---------------------------+------------------------------------------+";

        // In tiêu đề
        System.out.println(line);
        System.out.println(header);
        System.out.println(line);

        // In dữ liệu
        for (Customer customer : customers) {
            System.out.printf(
                    "| %-4d | %-20s | %-15s | %-25s | %-40s |%n",
                    customer.getId(),
                    customer.getName(),
                    customer.getPhone(),
                    customer.getEmail(),
                    customer.getInterestedProperties().length() > 40
                            ? customer.getInterestedProperties().substring(0, 37) + "..."
                            : customer.getInterestedProperties()
            );
        }

        // Đường kẻ cuối
        System.out.println(line);
    }
}
