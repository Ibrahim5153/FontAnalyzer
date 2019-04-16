package com.example.fontanalyzer;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.fontanalyzer.Models.User;

import java.util.List;

public class FontViewModel extends AndroidViewModel {


    private Repository mRepository;

    public FontViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);

    }

    public LiveData<User> getUserByEmail(String email){

        return mRepository.getUserByEmail(email);
    }

    public void insertUser(User user){

        mRepository.InsertUser(user);
    }
}
