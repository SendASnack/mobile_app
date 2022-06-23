package com.example.sendasnack.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "orders")
public class Order {
    // primary key would often be an auto-generated number; in this case, we are using the email
    @PrimaryKey
    @NonNull
    private String delivery;

    @NonNull
    private String pickUp;

    private String pickUpCoords;

    private String deliveryCoords;

    @NonNull
    private double price;

    private ProductsList products;

    public Order(@NonNull String pickUp, @NonNull String delivery, @NonNull double price) {
        this.pickUp = pickUp;
        this.delivery = delivery;
        this.price = price;
        this.products = products;
    }

    public Order(){

    }

    public String getPickUpCoords(){
        return pickUpCoords;
    }
    public void setPickUpCoords(String pickUpCoords){
        this.pickUpCoords = pickUpCoords;
    }

    public String getDeliveryCoords(){
        return deliveryCoords;
    }
    public void setDeliveryCoords(String deliveryCoords){
        this.deliveryCoords = deliveryCoords;
    }

    @NonNull
    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(@NonNull String pickUp) {
        this.pickUp = pickUp;
    }

    @NonNull
    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(@NonNull String delivery) {
        this.delivery = delivery;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(@NonNull double price) {
        this.price = price;
    }

    public ProductsList getProducts(){
        return products;
    }

    public void setProducts(@NonNull ProductsList products){
        this.products = products;
    }

}
