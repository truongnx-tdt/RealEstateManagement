import DAO.DatabaseHelper;
import DAO.PropertyDAO;
import Entities.Property;
import Infrastructures.Enum.PropertyStatus;
import Infrastructures.Utils.TableUtils;
import Manager.CustomerManager;
import Manager.PropertyManager;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static PropertyManager propertyManager = new PropertyManager();

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        // tạo bảng, cấu hình cho database mở ra khi chạy project lần đầu
//        DatabaseHelper.setupDatabase();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Hệ thống quản lý bất động sản ===");
            System.out.println("1. Quản lý tài sản (bất động sản)");
            System.out.println("2. Quản lý khách hàng");
            System.out.println("3. Tìm kiếm và lọc tài sản");
            System.out.println("4. Báo cáo thống kê");
            System.out.println("5. Thoát");
            System.out.print("Chọn chức năng chính theo số thứ tự: ");

            String mainChoice = scanner.nextLine().trim(); //

            switch (mainChoice) {
                case "1":
                    managePropertiesMenu(scanner);
                    break;
                case "2":
                    manageCustomersMenu(scanner);
                    break;
                case "3":
                    searchAndFilterMenu(scanner);
                    break;
                case "4":
                    statisticsMenu(scanner);
                    break;
                case "5":
                    exit = true;
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }

        scanner.close();
    }

    private static void showHelpPro() {
        System.out.println("""
                    Danh sách lệnh quản lý tài sản:
                    - add_property (a): Thêm bất động sản mới.
                    - edit_property (e): Sửa thông tin bất động sản.
                    - delete_property (d): Xóa bất động sản.
                    - list_properties (l): Xem danh sách bất động sản.
                    - exit: Quay lại.
                """);
    }

    private static void managePropertiesMenu(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n\t\t--- Quản Lý Tài Sản (Bất Động Sản) ---");
            System.out.println("""  
                                    Thêm bất động sản mới
                                    Sửa thông tin bất động sản
                                    Xóa bất động sản
                                    Xem danh sách bất động sản
                                    Quay lại
                    """);
            System.out.println("Nhập lệnh (gõ 'help' để xem danh sách lệnh).");
            System.out.print("> Nhập lựa chọn của bạn: ");

            String choice = scanner.nextLine().trim(); // Đọc bỏ dòng trống

            switch (choice) {
                case "a":
                    propertyManager.addProperty();
                    break;
                case "e":
                    propertyManager.editProperty();
                    break;
                case "d":
                    propertyManager.deleteProperty();
                    break;
                case "l":
                    propertyManager.listProperties();
                    break;
                case "help":
                    showHelpPro();
                    break;
                case "exit":
                    back = true;
                    break;
                default:
                    System.out.println("Lệnh không hợp lệ! Gõ 'help' để xem danh sách lệnh.");
            }
        }
    }

    private static void showHelpCus() {
        System.out.println("""
                    Danh sách lệnh quản lý khách hàng:
                    - add_customer (a): Thêm mới khách hàng.
                    - edit_customer (e): Sửa thông tin khách hàng.
                    - delete_customer (d): Xóa khách hàng.
                    - search_customer (s): Tìm kiếm khách hàng.
                    - list_customers (l): Xem danh sách khách hàng.
                    - exit: Quay lại.
                """);
    }

    private static void manageCustomersMenu(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n\t--- Quản Lý Khách Hàng ---");
            System.out.println(""" 
                    Thêm mới khách hàng
                    Sửa thông tin khách hàng
                    Xóa khách hàng
                    Tìm kiếm khách hàng
                    Xem danh sách khách hàng
                    Quay lại
                    """);
            System.out.println("Nhập lệnh (gõ 'help' để xem danh sách lệnh).");
            System.out.print("> Nhập lựa chọn của bạn: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "a":
                    CustomerManager.addCustomer();
                    break;
                case "e":
                    CustomerManager.updateCustomer();
                    break;
                case "d":
                    CustomerManager.deleteCustomer();
                    break;
                case "s":
                    CustomerManager.searchCustomers();
                    break;
                case "l":
                    CustomerManager.viewAllCustomers();
                    break;
                case "help":
                    showHelpCus();
                    break;
                case "exit":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void showHelpSearchorFilter() {
        System.out.println("""
                    Danh sách lệnh Tìm kiếm và lọc tài sản:
                    - search_property (s): Tìm kiếm bất động sản.
                    - filter_property (f): Lọc tài sản.
                    - exit: Quay lại.
                """);
    }

    private static void searchAndFilterMenu(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Tìm Kiếm và Lọc Tài Sản ---");
            System.out.println("Tìm kiếm bất động sản");
            System.out.println("Lọc tài sản");
            System.out.println("Quay lại");
            System.out.println("\nNhập lệnh (gõ 'help' để xem danh sách lệnh).");
            System.out.print("> Nhập lựa chọn của bạn: ");

            String choice = scanner.nextLine().trim(); // Đọc bỏ dòng trống

            switch (choice) {
                case "s":
                    propertyManager.searchProperties();
                    break;
                case "f":
                    propertyManager.filterPropertiesByStatus();
                    break;
                case "help":
                    showHelpSearchorFilter();
                    break;
                case "exit":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void statisticsMenu(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Báo Cáo Thống Kê ---");
            System.out.println("Thống kê số lượng tài sản theo trạng thái");
            System.out.println("Tổng giá trị bất động sản đang rao bán");
            System.out.println("Quay lại");
            System.out.println("\nNhập lệnh (gõ 'help' để xem danh sách lệnh).");
            System.out.print("> Nhập lựa chọn của bạn: ");

            String choice = scanner.nextLine().trim(); // Đọc bỏ dòng trống

            switch (choice) {
                case "r":
                    propertyManager.reportPropertiesByStatus();
                    break;
                case "t":
                    propertyManager.reportTotalValueForSale();
                    break;
                case "help":
                    showHelpReport();
                    break;
                case "exit":
                    back = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void showHelpReport() {
        System.out.println("""
                    Danh sách lệnh Báo cáo thống kê:
                    - report_property (r):  Thống kê số lượng tài sản theo trạng thái.
                    - total_property (t): Tổng giá trị bất động sản đang rao bán.
                    - exit: Thoát chương trình.
                """);
    }

}