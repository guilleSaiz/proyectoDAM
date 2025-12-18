package com.example.proyectodam.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "users",
        indices = {
                @Index(value = {"username"}, unique = true),
                @Index(value = {"email"}, unique = true)
        }
)
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String username;

    @NonNull
    public String email;

    @NonNull
    public String password;

    public long createdAt;
}

