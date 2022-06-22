package com.example.sendasnack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sendasnack.data.model.Order;

import java.util.List;

@Dao
public interface OrderDAO {

    // you don't need to explicit the SQL to use the defaults for
    // @Insert, @Update, @Delete

    @Insert ( onConflict = OnConflictStrategy.ABORT)
    void insert(Order order);

    @Query("DELETE FROM orders")
    void deleteAll();

    // wrapping the results with live data makes the results changes observable
    @Query("SELECT * from orders ORDER BY price ASC")
    LiveData<List<Order>> getPricedOrders();


}

