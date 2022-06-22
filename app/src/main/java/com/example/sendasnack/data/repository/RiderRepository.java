package com.example.sendasnack.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.sendasnack.data.dao.RiderDAO;
import com.example.sendasnack.data.model.Rider;
import com.example.sendasnack.data.roomDatabases.RiderRoomDatabase;

import java.util.List;
import java.util.Objects;


public class RiderRepository {

    private final RiderDAO mRiderDao;
    private final LiveData<List<Rider>> mAllRiders;

    public RiderRepository(Application application) {
        // get the database instance
        RiderRoomDatabase db = RiderRoomDatabase.getDatabase(application);

        // get the contract to interact with the db
        mRiderDao = db.riderDAO();

        // get the current content of contacts  table
        mAllRiders = mRiderDao.getAlphabetizedRiders();
    }

    // Room executes queries on a separate thread; don't need to specify the Executor support
    //for querying
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Rider>> getAllWords() {
        return mAllRiders;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Rider rider) {
        RiderRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRiderDao.insert(rider);
        });
    }

    public void deleteAll() {
        RiderRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRiderDao.deleteAll();
        });
    }

    public boolean contains(@NonNull Rider contact) {
        // using the data that is already in live data, instead of quering the database
        return Objects.requireNonNull(mAllRiders.getValue()).contains(contact);

    }

}
