package com.example.fontanalyzer;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.fontanalyzer.Models.Detection;
import com.example.fontanalyzer.Models.User;

import java.util.ArrayList;
import java.util.List;

public class FontViewModel extends AndroidViewModel {


    private Repository mRepository;

    public FontViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);

    }

    public LiveData<List<Detection>> getAllDetections(){

        return mRepository.getAllDetection();
    }

    public LiveData<User> getUserByEmail(String email){

        return mRepository.getUserByEmail(email);
    }

    public void insertUser(User user){

        mRepository.InsertUser(user);
    }

    public LiveData<Detection> getDetectionByContent(String content){

        return  mRepository.getDetectionByContent(content);
    }

    public void insertDetection(Detection detection){

        mRepository.InsertDetectionToDb(detection);
    }
}
