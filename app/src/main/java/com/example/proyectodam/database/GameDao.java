package com.example.proyectodam.database;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGame(GameEntity game);

    @Query("SELECT * FROM games WHERE id = :id")
    GameEntity getGameById(int id);

    @Query("SELECT * FROM games")
    List<GameEntity> getAllGames();
    @Query("SELECT * FROM games ORDER BY id DESC LIMIT :limit")
    List<GameEntity> getLatestGames(int limit);
    @Query("SELECT COUNT(*) FROM games")
    int getGameCount();
    @Query("DELETE FROM games WHERE id NOT IN (SELECT id FROM games ORDER BY id DESC LIMIT :limit)")
    void deleteOldGames(int limit);
}
