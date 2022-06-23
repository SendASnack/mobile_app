package com.example.sendasnack.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {
    // primary key would often be an auto-generated number; in this case, we are using the email
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    private String description;

    @NonNull
    private double price;

    public Product(@NonNull String name, @NonNull String description, @NonNull double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(){

    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(@NonNull double price) {
        this.price = price;
    }

}
