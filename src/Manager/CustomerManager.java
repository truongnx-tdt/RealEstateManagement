package Manager;

import DAO.CustomerDAO;
import DAO.PropertyDAO;
import Entities.Customer;
import Infrastructures.Utils.InputValidator;
import Infrastructures.Utils.TableUtils;

import java.util.List;
import java.util.Scanner;

public class CustomerManager {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Thêm mới khách hàng vào cơ sở dữ liệu.
     */
    public static void addCustomer() {
        System.out.println("\n--- Thêm Khách Hàng ---");
        String name = InputValidator.getNonEmptyString("Nhập tên khách hàng: ",true);
        String phone = InputValidator.getValidPhoneNumber("Nhập số điện thoại: ", true);
        String email = InputValidator.getValidEmail("Nhập email: ", true);

        List<Integer> availablePropertyIds = PropertyDAO.getPropertyIds();
        String interestedPropertyIds = InputValidator.getValidPropertyIdsAsString(
                "Nhập danh sách ID bất động sản quan tâm (cách nhau bởi dấu phẩy): ", availablePropertyIds, true
        );

        Customer customer = new Customer(0, name, phone, email, interestedPropertyIds);
        CustomerDAO.addCustomer(customer);
    }

    /**
     * Cập nhật thông tin khách hàng.
     */
    public static void updateCustomer() {
        int customerId = InputValidator.getValidInt("Nhập ID khách hàng cần sửa: ");
        Customer customer = CustomerDAO.getCustomerById(customerId);

        if (customer == null) {
            System.out.println("Không tìm thấy khách hàng với ID: " + customerId);
            return;
        }
        System.out.print("Nhập tên mới (để trống để giữ nguyên): ");
        String name = scanner.nextLine().trim();
        String phone = InputValidator.getValidPhoneNumber("Nhập số điện thoại mới (để trống để giữ nguyên): ", false);
        String email = InputValidator.getValidEmail("Nhập email mới (để trống để giữ nguyên): ", false);

        // Lấy danh sách ID bất động sản có sẵn từ cơ sở dữ liệu
        List<Integer> availablePropertyIds = PropertyDAO.getPropertyIds();
        String interestedPropertyIds = InputValidator.getValidPropertyIdsAsString(
                "Nhập danh sách ID bất động sản quan tâm mới (cách nhau bởi dấu phẩy, để trống để giữ nguyên): ", availablePropertyIds, false
        );

        // Cập nhật khách hàng
        customer.setName(name.isEmpty() ? customer.getName() : name);
        customer.setPhone(phone.isEmpty() ? customer.getPhone() : phone);
        customer.setEmail(email.isEmpty() ? customer.getEmail() : email);
        customer.setInterestedProperties(interestedPropertyIds.isEmpty() ? customer.getInterestedProperties() : interestedPropertyIds);

        CustomerDAO.editCustomer(customer);
    }

    /**
     * Xóa khách hàng theo ID.
     */
    public static void deleteCustomer() {
        System.out.println("\n--- Xóa Khách Hàng ---");
        int id = InputValidator.getValidInt("Nhập ID khách hàng cần xóa: ");
        CustomerDAO.deleteCustomer(id);
    }

    /**
     * Hiển thị danh sách tất cả khách hàng.
     */
    public static void viewAllCustomers() {
        System.out.println("\n--- Danh Sách Khách Hàng ---");
        List<Customer> customers = CustomerDAO.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("Không có khách hàng nào trong hệ thống.");
        } else {
            TableUtils.printCustomerTable(customers);
        }
    }

    public static void searchCustomers() {
        String id = InputValidator.getNonEmptyString("Nhập ID khách hàng (để trống để tìm tất cả):: ", false);
        String name = InputValidator.getNonEmptyString("Nhập tên khách hàng (để trống để tìm tất cả): ", false);
        String phone = InputValidator.getValidPhoneNumber("Nhập số điện thoại khách hàng (để trống để tìm tất cả): ", false);
        String email = InputValidator.getValidEmail("Nhập email khách hàng (để trống để tìm tất cả): ", false);

        List<Customer> customers = CustomerDAO.searchCustomers(id.isEmpty() ? null : Integer.valueOf(id), name, phone, email);
        TableUtils.printCustomerTable(customers);
    }
}
