package com.example.fontanalyzer;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.fontanalyzer.DAOs.IDetectionDAO;
import com.example.fontanalyzer.DAOs.IUserDAO;
import com.example.fontanalyzer.Models.Detection;
import com.example.fontanalyzer.Models.User;

@android.arch.persistence.room.Database(entities = {User.class, Detection.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {


    public abstract IUserDAO userDAO();
    public abstract IDetectionDAO detectionDAO();

    private static volatile Database INSTANCE;

    static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
