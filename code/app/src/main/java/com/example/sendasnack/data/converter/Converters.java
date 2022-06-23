package com.example.sendasnack.data.converter;

import androidx.room.TypeConverter;

import com.example.sendasnack.data.model.ProductsList;
import com.google.gson.Gson;

public class Converters {
    @TypeConverter
    public static String fromHolder(ProductsList h) {
        Gson gson = new Gson();
        String json = gson.toJson(h);
        return json;
    }
    @TypeConverter
    public static ProductsList toHolder(String s) {
        return new Gson().fromJson(s, ProductsList.class);
    }
}
