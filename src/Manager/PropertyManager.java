package Manager;

import DAO.PropertyDAO;
import Entities.Property;
import Infrastructures.Enum.PropertyStatus;
import Infrastructures.Utils.InputValidator;
import Infrastructures.Utils.TableUtils;

import java.util.List;
import java.util.Scanner;

public class PropertyManager {
    private static Scanner scanner = new Scanner(System.in);

    public void addProperty() {
        System.out.println("Thêm mới bất động sản (các trường * là bắt buộc):");
        String name = InputValidator.getNonEmptyString("Nhập tên*: ",true);
        String address = InputValidator.getNonEmptyString("Nhập địa chỉ*: ",true);
        double price = InputValidator.getValidDouble("Nhập giá tiền*: ");
        double area = InputValidator.getValidDouble("Nhập diện tích*: ");
        System.out.print("Nhập mô tả: ");
        String description = scanner.nextLine();
        System.out.println("Chọn trạng thái*:");
        PropertyStatus status = InputValidator.getValidPropertyStatus(scanner);

        Property property = new Property(name, address, price, area, description, status);
        PropertyDAO.addProperty(property);
    }

    public void editProperty() {

        System.out.println("Sửa bất động sản:");
        int id = InputValidator.getValidInt("Nhập ID bất động sản cần sửa: ");

        Property propertyEdit = PropertyDAO.getPropertyById(id);
        if (propertyEdit == null) {
            System.out.println("Không tìm thấy bất động sản với ID: " + id);
            return;
        }

        // Nhập tên (giữ nguyên nếu để trống)
        System.out.print("Nhập tên mới (để trống nếu không muốn thay đổi): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            propertyEdit.setName(name);
        }

        // Nhập địa chỉ (giữ nguyên nếu để trống)
        System.out.print("Nhập địa chỉ mới (để trống nếu không muốn thay đổi): ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) {
            propertyEdit.setAddress(address);
        }

        // Nhập giá mới (kiểm tra để trống)
        System.out.print("Nhập giá mới (để trống nếu không muốn thay đổi): ");
        String priceInput = scanner.nextLine();
        if (!priceInput.isEmpty()) {
            try {
                double price = InputValidator.getValidDouble("Xác nhận Nhập lại giá tiền: ");
                propertyEdit.setPrice(price);
            } catch (NumberFormatException e) {
                System.out.println("Giá không hợp lệ! Giá trị sẽ giữ nguyên.");
            }
        }

        // Nhập diện tích mới (kiểm tra để trống)
        System.out.print("Nhập diện tích mới (để trống nếu không muốn thay đổi): ");
        String areaInput = scanner.nextLine();
        if (!areaInput.isEmpty()) {
            try {
                double area = InputValidator.getValidDouble("Xác nhận Nhập lại diện tích: ");
                propertyEdit.setArea(area);
            } catch (NumberFormatException e) {
                System.out.println("Diện tích không hợp lệ! Giá trị sẽ giữ nguyên.");
            }
        }

        // Nhập mô tả (giữ nguyên nếu để trống)
        System.out.print("Nhập mô tả mới (để trống nếu không muốn thay đổi): ");
        String description = scanner.nextLine();
        if (!description.isEmpty()) {
            propertyEdit.setDescription(description);
        }

        // Nhập trạng thái
        PropertyStatus status = InputValidator.getValidPropertyStatus(scanner);
        if (status != null) {
            propertyEdit.setStatus(status);
        }

        PropertyDAO.editProperty(propertyEdit);
    }

    public void deleteProperty() {
        System.out.println("Xóa bất động sản:");
        int id = InputValidator.getValidInt("Nhập ID bất động sản cần xóa: ");
        PropertyDAO.deleteProperty(id);
    }

    public void listProperties() {
        List<Property> properties = PropertyDAO.getAllProperties();
        if (properties.isEmpty()) {
            System.out.println("Không có bất động sản nào trong hệ thống.");
        } else {
            TableUtils.printPropertyTable(properties);
        }
    }

    public void searchProperties() {
        String minPrice = InputValidator.getNonEmptyString("Nhập mức giá thấp nhất (để trống để không giới hạn): ", false);
        String maxPrice = InputValidator.getNonEmptyString("Nhập mức giá cao nhất (để trống để không giới hạn): ", false);
        String minArea = InputValidator.getNonEmptyString("Nhập diện tích thấp nhất (để trống để không giới hạn): ", false);
        String maxArea = InputValidator.getNonEmptyString("Nhập diện tích cao nhất (để trống để không giới hạn): ", false);
        String address = InputValidator.getNonEmptyString("Nhập địa chỉ (để trống để tìm tất cả): ", false);
        PropertyStatus status = InputValidator.getValidPropertyStatus(scanner);

        List<Property> properties = PropertyDAO.searchProperties(!minPrice.isEmpty() ? Double.valueOf(minPrice) : null, !maxPrice.isEmpty() ? Double.valueOf(maxPrice) : null,
                !minArea.isEmpty() ? Double.valueOf(minArea) : null, !maxArea.isEmpty() ? Double.valueOf(maxArea) : null, address, status);
        TableUtils.printPropertyTable(properties);
    }

    /**
     * Lọc tài sản theo trạng thái.
     */
    public void filterPropertiesByStatus() {
        System.out.println("Chọn trạng thái để lọc:");
        System.out.println("1: Đang bán");
        System.out.println("2: Cho thuê");
        System.out.println("3: Đã bán");

        int choice = InputValidator.getValidInt("Nhập lựa chọn: ");
        PropertyStatus status;

        switch (choice) {
            case 1:
                status = PropertyStatus.DANG_BAN;
                break;
            case 2:
                status = PropertyStatus.CHO_THUE;
                break;
            case 3:
                status = PropertyStatus.DA_BAN;
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                return;
        }

        List<Property> filteredProperties = PropertyDAO.filterPropertiesByStatus(status);
        if (filteredProperties.isEmpty()) {
            System.out.println("Không có bất động sản nào phù hợp với trạng thái đã chọn.");
        } else {
            TableUtils.printPropertyTable(filteredProperties);
        }
    }
    /**
     * Thống kê số lượng tài sản theo trạng thái.
     */
    public void reportPropertiesByStatus() {
        System.out.println("Thống kê số lượng tài sản theo trạng thái:");
        for (PropertyStatus status : PropertyStatus.values()) {
            int count = PropertyDAO.countPropertiesByStatus(status);
            System.out.printf("- %s: %d tài sản%n", status.getDescription(), count);
        }
    }

    /**
     * Tính tổng giá trị bất động sản đang rao bán.
     */
    public void reportTotalValueForSale() {
        double totalValue = PropertyDAO.calculateTotalValueForSale();
        System.out.printf("Tổng giá trị bất động sản đang rao bán: %.2f VND%n", totalValue);
    }
}
