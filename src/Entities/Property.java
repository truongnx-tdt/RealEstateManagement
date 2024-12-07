package Entities;

import Infrastructures.Enum.PropertyStatus;

public class Property {
    private int id;
    private String name;
    private String address;
    private double price;
    private double area;
    private String description;
    private PropertyStatus status;

    // Constructor
    public Property(int id, String name, String address, double price, double area, String description, PropertyStatus status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.area = area;
        this.description = description;
        this.status = status;
    }

    public Property(String name, String address, double price, double area, String description, PropertyStatus status) {
        this.name = name;
        this.address = address;
        this.price = price;
        this.area = area;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "ID: " + id +
                ", Tên: " + name +
                ", Địa chỉ: " + address +
                ", Giá: " + price +
                ", Diện tích: " + area + " m²" +
                ", Mô tả: " + description +
                ", Trạng thái: " + status.getDescription();
    }
}

