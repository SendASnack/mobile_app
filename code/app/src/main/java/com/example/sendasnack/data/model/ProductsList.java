package com.example.sendasnack.data.model;

import java.util.List;

public class ProductsList {

    public List<Product> products;

    public ProductsList(List<Product> products){
        this.products = products;
    }

    public ProductsList(){}

    public void setProducts(List<Product> products){
        this.products = products;
    }

    public List<Product> getProducts(){
        return products;
    }
}
