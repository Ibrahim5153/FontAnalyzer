package com.example.fontanalyzer;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.fontanalyzer.DAOs.IUserDAO;
import com.example.fontanalyzer.Models.User;

public class Repository {

    private IUserDAO userDAO;

    public Repository(Application application) {

        Database database = Database.getDatabase(application);
        this.userDAO = database.userDAO();
    }

    public void InsertUser(User user){

        new insertUserTODb(userDAO).execute(user);
    }

    public LiveData<User> getUserByEmail(String email){

        return userDAO.getUserByEmailAdd(email);
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
}
