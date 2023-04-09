package com.example.crosstalk.services;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/**
 * @info This is a common utility to share the data among activities and fragments
 */
public class ShareDataService extends AndroidViewModel {

    private MutableLiveData<String> searchedFor = new MutableLiveData<>("");

    public ShareDataService(@NonNull Application application) {
        super(application);
    }

    public void setSearchedFor(String searchedFor) {
        this.searchedFor.setValue(searchedFor);
    }

    public String getSearchedFor() {
        return searchedFor.getValue();
    }

}
