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
import com.example.sendasnack.data.model.Rider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Rider.class}, version = 1, exportSchema = false)
public abstract class RiderRoomDatabase extends RoomDatabase{

    public abstract RiderDAO riderDAO();

    private static RiderRoomDatabase INSTANCE;

    //Executor
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RiderRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RiderRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RiderRoomDatabase.class, "rider_database")
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

                RiderDAO dao = INSTANCE.riderDAO();
                if (dao.getAlphabetizedRiders().getValue().isEmpty()) {
                    Rider rider = new Rider("pmapm@ua.pt", "Pedro Monteiro", "924124562");
                    dao.insert(rider);
                }
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
