package com.example.alp_coffee;

import java.io.Serializable;

public class Coffee implements Serializable {
String Name;
String Price;
String Image;

public Coffee(){

}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
