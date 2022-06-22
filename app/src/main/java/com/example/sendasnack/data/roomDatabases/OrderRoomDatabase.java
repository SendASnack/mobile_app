package com.example.sendasnack.data.roomDatabases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.sendasnack.data.converter.Converters;
import com.example.sendasnack.data.dao.OrderDAO;
import com.example.sendasnack.data.dao.ProductDAO;
import com.example.sendasnack.data.dao.RiderDAO;
import com.example.sendasnack.data.model.Product;
import com.example.sendasnack.data.model.Order;
import com.example.sendasnack.data.model.ProductsList;
import com.example.sendasnack.data.model.Rider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Order.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class OrderRoomDatabase extends RoomDatabase{

    public abstract OrderDAO orderDAO();

    private static OrderRoomDatabase INSTANCE;

    //Executor
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static OrderRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RiderRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    OrderRoomDatabase.class, "order_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onCreate method to populate the database.
     * For this sample, we clear the database every time it is created.
     */
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.

                OrderDAO dao = INSTANCE.orderDAO();
                dao.deleteAll();

                ProductsList prods = new ProductsList();
                Order order = new Order("Rua Mario Sacramento", "Urbanizaçao Chave", 8.50);
                List<Product> list = new ArrayList<>();
                list.add(new Product("Product 1", "my product 1", 4.50));
                list.add(new Product("Product 2", "my product 2", 4.0));
                prods.setProducts(list);
                order.setProducts(prods);
                dao.insert(order);
                prods = new ProductsList();
                order = new Order("Rua Doutor Lourenço", "Minha casa", 13.50);
                list = new ArrayList<>();
                list.add(new Product("Product 3", "my product 3", 4.50));
                list.add(new Product("Product 4", "my product 4", 9.0));
                prods.setProducts(list);
                order.setProducts(prods);
                dao.insert(order);
            });
        }
    };


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
