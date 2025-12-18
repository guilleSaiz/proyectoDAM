package com.example.proyectodam.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long register(UserEntity user);

    @Query("SELECT * FROM users WHERE username = :user AND password = :pass LIMIT 1")
    UserEntity getUser(String user, String pass);

    @Query("SELECT * FROM users WHERE username = :user OR email = :mail LIMIT 1")
    UserEntity getByUsernameOrEmail(String user, String mail);

    @Query("SELECT * FROM users WHERE id = :id")
    UserEntity getById(int id);

    @Query("SELECT COUNT(*) FROM users")
    int countUsers();

}

