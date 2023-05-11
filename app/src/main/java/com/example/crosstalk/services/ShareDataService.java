package com.example.crosstalk.services;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.crosstalk.utils.Constants;

import java.util.HashMap;

/**
 * @info This is a common utility to share the data among fragments of a single activity
 */
public class ShareDataService extends AndroidViewModel {

    private MutableLiveData<HashMap<String, Object>> values = new MutableLiveData<>();
    private HashMap<String, Object> map;

    public ShareDataService(@NonNull Application application) {
        super(application);
    }

    public void setSearchedFor(String searchedFor) {
        map = getCurrentHashMap();
        map.put(Constants.SEARCHED_FOR, searchedFor);
        values.setValue(map);
    }

    public String getSearchedFor() {
        map = getCurrentHashMap();
        return (String) map.get(Constants.SEARCHED_FOR);
    }

    public HashMap<String, Object> getCurrentHashMap() {
        HashMap<String, Object> currentMap = values.getValue();
        if (currentMap == null) {
            currentMap = new HashMap<>();
            values.setValue(currentMap);
        }
        return currentMap;
    }

}
