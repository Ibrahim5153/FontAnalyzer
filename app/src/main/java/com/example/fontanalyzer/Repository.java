package com.example.fontanalyzer;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.fontanalyzer.DAOs.IDetectionDAO;
import com.example.fontanalyzer.DAOs.IUserDAO;
import com.example.fontanalyzer.Models.Detection;
import com.example.fontanalyzer.Models.User;

public class Repository {

    private IUserDAO userDAO;
    private IDetectionDAO detectionDAO;

    public Repository(Application application) {

        Database database = Database.getDatabase(application);
        this.userDAO = database.userDAO();
        this.detectionDAO = database.detectionDAO();
    }

    public void InsertUser(User user){

        new insertUserTODb(userDAO).execute(user);
    }

    public LiveData<User> getUserByEmail(String email){

        return userDAO.getUserByEmailAdd(email);
    }

    public void InsertDetectionToDb(Detection detection){

        new insertDetectionTODb(detectionDAO).execute(detection);
    }

    public LiveData<Detection> getDetectionByContent(String content){

       return detectionDAO.getDetectionByContent(content);
    }

    private static class insertUserTODb extends AsyncTask<User, Void, Void> {

        private IUserDAO userDAO;

        public insertUserTODb(IUserDAO dao) {
            userDAO = dao;
        }


        @Override
        protected Void doInBackground(User... ids) {
            userDAO.insert(ids[0]);
            return null;
        }
    }

    private static class insertDetectionTODb extends AsyncTask<Detection, Void, Void> {

        private IDetectionDAO detectionDAO;

        public insertDetectionTODb(IDetectionDAO dao) {
            detectionDAO = dao;
        }


        @Override
        protected Void doInBackground(Detection... ids) {
            detectionDAO.insert(ids[0]);
            return null;
        }
    }
}
