package com.example.proyectodam.database;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
@Database(entities = {GameEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    private static volatile AppDatabase INSTANCE;

    public abstract GameDao gameDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "games_db"
            ).build();
        }
        return INSTANCE;
    }

}
