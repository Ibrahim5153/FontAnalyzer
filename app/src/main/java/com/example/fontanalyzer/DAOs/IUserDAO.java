package com.example.fontanalyzer.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.Nullable;

import com.example.fontanalyzer.Models.User;


@Dao
public interface IUserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User userModel);

    @Query("SELECT * from users WHERE id = :catId")
    LiveData<User> getUserById(int catId);

    @Nullable
    @Query("SELECT * FROM USERS WHERE LOWER(email) like LOWER(:emailAdd)")
    LiveData<User> getUserByEmailAdd(String emailAdd);
}
