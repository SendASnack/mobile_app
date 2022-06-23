package com.example.sendasnack.ui.earning;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EarningViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EarningViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is earning fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}