package com.example.sendasnack.data.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sendasnack.data.repository.RiderRepository;
import com.example.sendasnack.data.model.Rider;

import java.util.List;

/**
 * The ViewModel class is designed to store and manage UI-related data
 *  ViewModel objects are automatically retained during configuration changes
 *  so that data they hold is immediately available to the next activity or fragment instance
 *
 */
public class RiderViewModel extends AndroidViewModel {

    // reference to the Repository where to get data
    private final RiderRepository mRepository;

    // the current data to display in the activity
    // since this is a live data, updated will be pushed automatically from db to UI
    private final LiveData<List<Rider>> mAllContacts;

    public RiderViewModel(Application application) {
        super(application);

        // get and observe the list of contacts provided by the Repository
        mRepository = new RiderRepository(application);
        mAllContacts = mRepository.getAllWords();
    }

    public LiveData<List<Rider>> getAllContacts() { return mAllContacts; }

    public void insert(Rider rider) {
        // send the new Contact into the database using the Repository abstraction
        mRepository.insert(rider);
    }



}
