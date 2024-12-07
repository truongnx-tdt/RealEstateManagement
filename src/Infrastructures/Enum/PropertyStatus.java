package Infrastructures.Enum;

public enum PropertyStatus {
    DANG_BAN(1, "Đang bán"),
    CHO_THUE(2, "Cho thuê"),
    DA_BAN(3, "Đã bán");

    private final int code;
    private final String description;

    PropertyStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PropertyStatus fromCode(int code) {
        for (PropertyStatus status : PropertyStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
