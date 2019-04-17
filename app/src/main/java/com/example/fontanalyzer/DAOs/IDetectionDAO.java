package com.example.fontanalyzer.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.Nullable;

import com.example.fontanalyzer.Models.Detection;
import com.example.fontanalyzer.Models.User;

@Dao
public interface IDetectionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Detection detection);


    @Nullable
    @Query("SELECT * FROM detections WHERE LOWER(content) like LOWER(:content)")
    LiveData<Detection> getDetectionByContent(String content);
}
