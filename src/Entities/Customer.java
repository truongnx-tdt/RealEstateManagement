package Entities;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String interestedProperties; // IDs of interested properties

    // Constructor
    public Customer(int id, String name, String phone, String email, String interestedProperties) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.interestedProperties = interestedProperties;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInterestedProperties() {
        return interestedProperties;
    }

    public void setInterestedProperties(String interestedProperties) {
        this.interestedProperties = interestedProperties;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Tên: " + name +
                ", SĐT: " + phone +
                ", Email: " + email +
                ", BĐS quan tâm: " + interestedProperties;
    }
}
