package com.example.alp_coffee;

import java.io.Serializable;

public class Coffee implements Serializable {
    String Name;
    double Price;
    String Image;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Coffee() {

    }

    public Coffee(String name, double price, String id) {
        Name = name;
        Price = price;
        this.id = id;
    }

    public Coffee(String name, double price, String image, String id) {
        Name = name;
        Price = price;
        Image = image;
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
