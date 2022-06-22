package com.example.sendasnack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sendasnack.data.model.Product;
import com.example.sendasnack.data.model.ProductsList;

import java.util.List;

@Dao
public interface ProductDAO {

    // you don't need to explicit the SQL to use the defaults for
    // @Insert, @Update, @Delete

    @Insert ( onConflict = OnConflictStrategy.ABORT)
    void insert(Product product);

    @Query("DELETE FROM products")
    void deleteAll();

    // wrapping the results with live data makes the results changes observable
    @Query("SELECT * from products ORDER BY name ASC")
    LiveData<ProductsList> getAlphabetizedProducts();


}

