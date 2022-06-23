package com.example.sendasnack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sendasnack.data.model.Rider;

import java.util.List;

@Dao
public interface RiderDAO {

    // you don't need to explicit the SQL to use the defaults for
    // @Insert, @Update, @Delete

    @Insert ( onConflict = OnConflictStrategy.ABORT)
    void insert(Rider rider);

    @Query("DELETE FROM riders")
    void deleteAll();

    // wrapping the results with live data makes the results changes observable
    @Query("SELECT * from riders ORDER BY name ASC")
    LiveData<List<Rider>> getAlphabetizedRiders();


}

