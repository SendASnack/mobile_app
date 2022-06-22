package com.example.sendasnack.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.sendasnack.data.dao.OrderDAO;
import com.example.sendasnack.data.model.Order;
import com.example.sendasnack.data.roomDatabases.OrderRoomDatabase;
import com.example.sendasnack.data.roomDatabases.RiderRoomDatabase;

import java.util.List;
import java.util.Objects;


public class OrderRepository {

    private final OrderDAO mOrderDao;
    private final LiveData<List<Order>> mAllOrders;

    public OrderRepository(Application application) {
        // get the database instance
        OrderRoomDatabase db = OrderRoomDatabase.getDatabase(application);

        // get the contract to interact with the db
        mOrderDao = db.orderDAO();

        // get the current content of orders table
        mAllOrders = mOrderDao.getPricedOrders();
    }

    // Room executes queries on a separate thread; don't need to specify the Executor support
    //for querying
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Order>> getAllWords() {
        return mAllOrders;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Order order) {
        RiderRoomDatabase.databaseWriteExecutor.execute(() -> {
            mOrderDao.insert(order);
        });
    }

    public void deleteAll() {
        RiderRoomDatabase.databaseWriteExecutor.execute(() -> {
            mOrderDao.deleteAll();
        });
    }

    public boolean contains(@NonNull Order order) {
        // using the data that is already in live data, instead of quering the database
        return Objects.requireNonNull(mAllOrders.getValue()).contains(order);

    }

}
