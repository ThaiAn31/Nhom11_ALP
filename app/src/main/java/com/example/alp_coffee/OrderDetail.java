package com.example.alp_coffee;

public class OrderDetail {
    private Order id_Order;
    private  Coffee id_Coffee;
    private int soLuong;

    public OrderDetail(Order id_Order, Coffee id_Coffee, int soLuong) {
        this.id_Order = id_Order;
        this.id_Coffee = id_Coffee;
        this.soLuong = soLuong;
    }


    public OrderDetail() {
    }

    public Order getId_Order() {
        return id_Order;
    }

    public void setId_Order(Order id_Order) {
        this.id_Order = id_Order;
    }

    public Coffee getId_Coffee() {
        return id_Coffee;
    }

    public void setId_Coffee(Coffee id_Coffee) {
        this.id_Coffee = id_Coffee;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id_Order=" + id_Order +
                ", id_Coffee=" + id_Coffee +
                ", soLuong=" + soLuong +
                '}';
    }
}
