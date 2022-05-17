package com.example.alp_coffee;

public class Order {
    private User id_User;
    private String id;

    public Order(String id) {
        this.id = id;
    }

    public Order() {
    }

    public Order(User id_User, String id) {
        this.id_User = id_User;
        this.id = id;
    }

    public User getId_User() {
        return id_User;
    }

    public void setId_User(User id_User) {
        this.id_User = id_User;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
