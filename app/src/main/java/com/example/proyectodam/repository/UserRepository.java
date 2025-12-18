package com.example.proyectodam.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.core.util.Consumer;

import com.example.proyectodam.db.AppDatabase;
import com.example.proyectodam.db.UserDao;
import com.example.proyectodam.db.UserEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserRepository {

    private final UserDao userDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userDao = db.userDao();
    }

    public void register(UserEntity user) {
        executor.execute(() -> userDao.register(user));
    }

    public void userExists(String username, String email, Consumer<Boolean> callback) {
        executor.execute(() -> {
            UserEntity user = userDao.getByUsernameOrEmail(username, email);
            boolean exists = user != null;
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(exists));
        });
    }

    public UserEntity login(String user, String pass) {
        return userDao.getUser(user, pass);
    }
}

