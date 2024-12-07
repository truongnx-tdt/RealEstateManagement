package Infrastructures.Utils;

import Infrastructures.Enum.PropertyStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);

    // Kiểm tra đầu vào là số double
    public static double getValidDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số hợp lệ!");
            }
        }
    }

    // Kiểm tra đầu vào là số nguyên int
    public static int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên hợp lệ!");
            }
        }
    }

    // Kiểm tra số điện thoại hợp lệ (10 chữ số)
    public static String getValidPhoneNumber(String prompt, boolean required) {
        while (true) {
            System.out.print(prompt);
            String phoneNumber = scanner.nextLine().trim();
            if(phoneNumber.isEmpty() && !required) {
                return phoneNumber;
            }
            if (phoneNumber.matches("\\d{10}")) {
                return phoneNumber;
            } else {
                System.out.println("Lỗi: Số điện thoại phải có đúng 10 chữ số!");
            }
        }
    }

    /**
     * Kiểm tra email hợp lệ.
     */
    public static String getValidEmail(String prompt, boolean required) {
        while (true) {
            System.out.print(prompt);
            String email = scanner.nextLine().trim();
            if(email.isEmpty() && !required) {
                return email;
            }
            // Regex kiểm tra định dạng email hợp lệ
            if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                return email;
            } else {
                System.out.println("Lỗi: Email không hợp lệ! Vui lòng nhập lại.");
            }
        }
    }
    public static String getValidPropertyIdsAsString(String prompt, List<Integer> availablePropertyIds, boolean required) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty() && !required) {
                return input;
            }
            // Tách các ID bằng dấu phẩy
            String[] ids = input.split(",");
            StringBuilder validIds = new StringBuilder();
            boolean isValid = true;

            for (String idStr : ids) {
                try {
                    int id = Integer.parseInt(idStr.trim());
                    if (availablePropertyIds.contains(id)) {
                        // Nếu ID hợp lệ, thêm vào chuỗi
                        if (validIds.length() > 0) {
                            validIds.append(",");
                        }
                        validIds.append(id);
                    } else {
                        System.out.println("Lỗi: ID bất động sản " + id + " không tồn tại!");
                        isValid = false;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: " + idStr + " không phải là số hợp lệ!");
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                return validIds.toString(); // Trả về chuỗi ID hợp lệ
            } else {
                System.out.println("Vui lòng nhập lại danh sách ID bất động sản hợp lệ!");
            }
        }
    }


    // Kiểm tra chuỗi không được để trống
    public static String getNonEmptyString(String prompt, boolean required) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if(input.isEmpty() && !required) {
                return input;
            }
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Lỗi: Trường này không được để trống!");
            }
        }
    }

    public static PropertyStatus getValidPropertyStatus(Scanner scanner) {
        PropertyStatus status = null;
        boolean valid = false;

        do {
            try {
                System.out.print("Trạng thái (1: Đang bán, 2: Cho thuê, 3: Đã bán): ");
                int statusCode = Integer.parseInt(scanner.nextLine());
                status = PropertyStatus.fromCode(statusCode);

                if (status != null) {
                    valid = true; // Thoát khỏi vòng lặp khi nhập đúng
                } else {
                    System.out.println("Giá trị không hợp lệ! Vui lòng chọn lại.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên hợp lệ.");
            }
        } while (!valid);

        return status;
    }

}
